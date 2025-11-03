package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScaleCircleScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ScaleCircleScreen(modifier: Modifier = Modifier) {
    val viewModel: ScaleViewModel = viewModel()

    val shuffledScales by viewModel.shuffledScalesFlow.collectAsState()
    val currentIndex by viewModel.currentIndexFlow.collectAsState()
    val previousScale by viewModel.previousScaleFlow.collectAsState()
    val completionCount by viewModel.completionCountFlow.collectAsState()

    // Don't show anything until data is loaded
    if (shuffledScales.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        )
        return
    }

    val currentScale = shuffledScales[currentIndex]

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        // Previous scale circle - top left
        if (previousScale != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(24.dp)
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = previousScale!!,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // Completion counter - top right
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
        ) {
            Text(
                text = "×$completionCount",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Main scale circle - center
        Box(
            modifier = Modifier
                .size(250.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary)
                .clickable {
                    viewModel.moveToNextScale(shuffledScales, currentIndex)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentScale,
                fontSize = 72.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScaleCirclePreview() {
    MyApplicationTheme {
        ScaleCircleScreen()
    }
}