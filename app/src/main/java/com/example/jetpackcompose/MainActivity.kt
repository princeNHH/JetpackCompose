package com.example.jetpackcompose

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackcompose.ui.theme.JetpackComposeTheme
import com.example.jetpackcompose.viewmodel.VideoViewModel
import com.example.jetpackcompose.timelinescreen.VideoTimeline
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetpackcompose.detailscreen.DetailScreen

private lateinit var selectedTab: String

class MainActivity : ComponentActivity() {
    val viewModel: VideoViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        selectedTab = "Video"
        setContent {
            JetpackComposeTheme {
                MainScreen(viewModel)
            }
        }

    }
}

@Composable
fun MainScreen(viewModel: VideoViewModel) {

    var selectedTab by remember { mutableStateOf("Video") }
    val navController = rememberNavController()
    Box(modifier = Modifier.fillMaxSize()) {
        // Your main content goes here
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp) // Adjust padding to ensure BottomBar is not covered
        ) {
            // Content of your screen

            NavHost(navController = navController, startDestination = "gridScreen") {
                composable("gridScreen") {
                    VideoTimeline(viewModel, navController)
                }
                composable(
                    "detailScreen/{itemPath}",
                    arguments = listOf(navArgument("itemPath") { type = NavType.StringType })
                ) { backStackEntry ->
                    val itemPath = backStackEntry.arguments?.getString("itemPath")
                    itemPath?.let { DetailScreen(it) }
                }
            }
        }
        BottomBar(
            onVideoTabClick = { selectedTab = "Video"},
            onAlbumTabClick = { selectedTab = "Album" },
            selectedTab = selectedTab,
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .align(Alignment.BottomCenter)// Adjust height as needed
        )
        // BottomBar positioned at the bottom of the screen
    }
}

@Composable
fun BottomBar(
    onVideoTabClick: () -> Unit,
    onAlbumTabClick: () -> Unit,
    selectedTab: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)  // Center the Row vertically in the Box
                ,  // Set height of the BottomBar
            horizontalArrangement = Arrangement.SpaceEvenly,  // Distribute tabs evenly
            verticalAlignment = Alignment.CenterVertically  // Center items inside the Row
        ) {
            TabItem(
                text = "Video",
                isSelected = selectedTab == "Video",
                onClick = onVideoTabClick,
            )

            TabItem(
                text = "Album",
                isSelected = selectedTab == "Album",
                onClick = onAlbumTabClick,
            )
        }
    }
}
@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun TabItem(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .size(120.dp, 40.dp)
            .clip(CircleShape)
            .clickable(
                // Tạo hiệu ứng ripple khi click
                indication = rememberRipple(
                    bounded = true, // Ripple chỉ bên trong giới hạn của hình tròn
                    color = Color.Gray, // Màu của ripple (có thể thay đổi)
                    radius = 200.dp // Bán kính của ripple, có thể điều chỉnh theo ý muốn
                ),
                interactionSource = MutableInteractionSource(), // Điều này giúp xử lý các sự kiện touch
                onClick = onClick
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            style = TextStyle(fontSize = 18.sp)
            )
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(2.dp)
                .background(if (isSelected) Color.Black else Color.Transparent)
        )
    }
}
