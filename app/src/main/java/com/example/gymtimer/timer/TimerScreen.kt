package com.example.gymtimer.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtimer.model.TimerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtimer.model.PlayState

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun TimerScreen( viewModel: TimerViewModel = viewModel() , modifier: Modifier = Modifier.onKeyEvent {
        if(it.key == Key.Escape) {
            viewModel.pauseTimer()
        }
        true
    }){
    val context = LocalContext.current
    val viewState = viewModel.viewState.observeAsState()
    val progress = viewModel.progress.observeAsState()
    if(viewState.value?.hasCurrent() == true){
        if(viewState.value?.playState == PlayState.READY_TO_PLAY){
            viewModel.startTimer(context)
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(MaterialTheme.colorScheme.primary),

                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 60.sp,
                text = "Round number ${viewState?.value?.roundNumber ?: 1} of ${viewState?.value?.numberOfRounds ?: 1}",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Text(modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
                .height(40.dp),
                text = viewState.value?.current?.name ?: "",
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 40.sp,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier=Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(Modifier.padding(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(300.dp),
                        progress = progress?.value ?: 10f,
                        strokeWidth = 20.dp
                    )
                    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = viewState.value?.time ?: "No time set",
                            color = Color.Black,
                            fontSize = 40.sp
                        )
                    }
                }
            }
            if (viewState.value?.hasNext() == true) {
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                    Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Next : ",
                            color = Color.Black,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = "${viewState.value?.next?.name ?: ""} ${viewState.value?.next?.startingTime ?: "No time set"}",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp

                        )
                    }
                }
            }
            var img: ImageVector = Icons.Default.PlayArrow
            val clickAction: () -> Unit = if(viewState?.value?.playState == PlayState.PAUSED) {
                {viewModel.resume(context)}
            }else{
                img = Icons.Default.Pause
                {viewModel.pauseTimer()}
            }
            Spacer(modifier=Modifier.weight(1f))
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), horizontalArrangement = Arrangement.SpaceAround) {

                FilledIconButton(
                    onClick = { viewModel.requeue(context) },
                    modifier = Modifier.size(100.dp)

                ) {
                    Icon(Icons.Default.RestartAlt, contentDescription = "content description",modifier=Modifier.size(50.dp))
                }

                FilledIconButton(
                    onClick =  clickAction,
                    modifier = Modifier.size(100.dp)
                ) {
                    Icon(img, contentDescription = "content description",modifier=Modifier.size(50.dp))
                }
            }
        }
    }
    else{
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = "Finished",
                color = Color.Black,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            FilledIconButton(
                onClick = { viewModel.requeue(context) },
                modifier = Modifier.size(100.dp),
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Blue)
            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = "content description",modifier=Modifier.size(50.dp))
            }
        }
    }
}