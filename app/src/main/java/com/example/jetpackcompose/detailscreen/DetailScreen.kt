package com.example.jetpackcompose.detailscreen

import android.app.ActionBar
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jetpackcompose.R
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(path: String){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ViewPager(path = path)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
@Composable
fun ViewPager(path: String){
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    HorizontalPager(state = pagerState) { page ->
        // Our page content
        GlideImage(
            model = path,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }

// scroll to page
//    val coroutineScope = rememberCoroutineScope()
//    Button(onClick = {
//        coroutineScope.launch {
//            // Call scroll to on pagerState
//            pagerState.animateScrollToPage(5)
//        }
//    }) {
//        Text("Jump to Page 5")
//    }
}

@Composable
fun ToolBar(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.back_icon), contentDescription = null)
            }
        }
        Box{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.more_icon), contentDescription = null)
            }
        }
    }
}
@Composable
fun ActionBar(){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.favorite_icon), contentDescription = null)
            }
        }
        Box{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.edit_icon), contentDescription = null)
            }
        }
        Box {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.info_icon), contentDescription = null)
            }
        }
        Box{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.share_icon), contentDescription = null)
            }
        }
        Box{
            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(R.drawable.delete_icon), contentDescription = null)
            }
        }
    }
}

@Preview
@Composable
fun Greeting(){
    com.example.jetpackcompose.detailscreen.ViewPager("null")
}