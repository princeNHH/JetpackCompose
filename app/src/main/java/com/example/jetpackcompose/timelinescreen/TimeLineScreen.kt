package com.example.jetpackcompose.timelinescreen

import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetpackcompose.model.VideoItem
import com.example.jetpackcompose.viewmodel.VideoViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VideoTimeline(viewModel: VideoViewModel, navController: NavController) {
    val videoItems = viewModel.timelineItems.observeAsState(emptyList())

    LazyVerticalGrid(
        columns = GridCells.FixedSize(110.dp),  // Define the fixed number of columns in the grid
        modifier = Modifier.fillMaxSize().padding(10.dp, 64.dp,10.dp, 0.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        videoItems.value.forEach { item ->
            if (item.isDate) {
                // Use the item block for the DateHeader
                item(span = { GridItemSpan(maxLineSpan) }) {
                    DateHeader(date = item.date)
                }
            } else {
                // This is a video item
                item {
                    VideoThumbnail(videoItem = item, onClick = {
                        navController.navigate("detailScreen/${Uri.encode(item.mPath)}")
                    })
                }
            }
        }
    }
}

@Composable
fun DateHeader(date: Date) {
    // Display the date as a header in the grid
    val displayFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
    Text(
        text = displayFormat.format(date),
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp, 10.dp),
        textAlign = TextAlign.Start
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun VideoThumbnail(videoItem: VideoItem, onClick: () -> Unit) {
    Box(
        modifier = Modifier.size(110.dp)
            .clickable(onClick = onClick)
    ){
        GlideImage(
            model = videoItem.mPath,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}