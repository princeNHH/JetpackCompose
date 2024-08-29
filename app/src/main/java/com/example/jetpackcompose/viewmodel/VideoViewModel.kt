package com.example.jetpackcompose.viewmodel

import android.app.Application
import android.content.ContentUris
import android.database.ContentObserver
import android.database.Cursor
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.MediaStore.Video
import androidx.annotation.OptIn
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import com.example.jetpackcompose.model.VideoItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class VideoViewModel(application: Application) : AndroidViewModel(application) {

    private val _timelineItems = MutableLiveData<List<VideoItem>>()
    val timelineItems: LiveData<List<VideoItem>> get() = _timelineItems

    private val contentObserver = object : ContentObserver(Handler(Looper.getMainLooper())) {
        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            loadVideos()
        }
    }

    init {
        loadVideos()
        application.contentResolver.registerContentObserver(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            true,
            contentObserver
        )
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
    }

    @OptIn(UnstableApi::class)
    fun loadVideos() {
        val listVideo = mutableListOf<VideoItem>()
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DATE_ADDED
        )

        getApplication<Application>().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            null
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)
            val dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)
                val duration = cursor.getLong(durationColumn)
                val size = cursor.getLong(sizeColumn)
                val date = cursor.getLong(dateAddedColumn) * 1000L

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                val videoItem = VideoItem(contentUri.toString(), false,Date(date), duration, false)
                listVideo.add(videoItem)
            }
        }
        _timelineItems.postValue(groupVideosByDate(listVideo))
    }

    private fun groupVideosByDate(listVideo: List<VideoItem>): List<VideoItem> {
        val timelineItems = mutableListOf<VideoItem>()
        val groupedVideos = listVideo.groupBy { videoItem ->
            SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(videoItem.date)
        }

        groupedVideos.forEach { (date, videos) ->
            // Create a placeholder VideoItem for the date (or you can create a separate class for dates)
            val dateItem = VideoItem(
                mPath = date,  // Using date as a string in mPath for now
                isSelected = false,
                isDate = true,
                date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date) ?: Date(),
                duration = 0L  // Duration is set to 0 for date items
            )
            timelineItems.add(dateItem)

            // Add videos under that date
            videos.forEach { video ->
                timelineItems.add(video)
            }
        }

        return timelineItems
    }
}
