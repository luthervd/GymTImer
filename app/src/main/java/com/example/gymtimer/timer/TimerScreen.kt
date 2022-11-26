package com.example.gymtimer.timer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gymtimer.model.TimerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtimer.model.PlayState
import java.security.AllPermission

@Composable
fun TimerScreen(viewModel: TimerViewModel = viewModel() ){
    val context = LocalContext.current
    val viewState = viewModel.viewState.observeAsState()
    val progress = viewModel.progress.observeAsState()
    if(viewState.value?.hasCurrent() == true){
        if(viewState.value?.playState == PlayState.READY_TO_PLAY){
            viewModel.startTimer(context)
        }

        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Round number ${viewState?.value?.roundNumber ?: 1} of ${viewState?.value?.numberOfRounds ?: 1}",
                color = Color.Black,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Normal,
                fontSize = 30.sp
            )
            Spacer(modifier=Modifier.weight(1f))
            Text(
                text = "${viewState.value?.current?.name ?: ""}",
                color = Color.Black,
                style = MaterialTheme.typography.h2,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier=Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(Modifier.padding(40.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = Color.Red,
                        modifier = Modifier.size(250.dp),
                        progress = progress?.value ?: 10f,
                        strokeWidth = 12.dp
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
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "${viewState.value?.next?.name ?: ""} ${viewState.value?.next?.startingTime ?: "No time set"}",
                            color = Color.Black,
                            style = MaterialTheme.typography.h2,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp

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
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceAround) {

                OutlinedButton(
                    onClick = { viewModel.requeue(context) },
                    modifier = Modifier.size(100.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border = BorderStroke(3.dp, Color.Blue),
                    contentPadding = PaddingValues(0.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)

                ) {
                    Icon(Icons.Default.RestartAlt, contentDescription = "content description",modifier=Modifier.size(50.dp))
                }

                OutlinedButton(
                    onClick =  clickAction,
                    modifier = Modifier.size(100.dp),  //avoid the oval shape
                    shape = CircleShape,
                    border = BorderStroke(3.dp, Color.Blue),
                    contentPadding = PaddingValues(10.dp),  //avoid the little icon
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)

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
                style = MaterialTheme.typography.h2,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Bold
            )
            OutlinedButton(
                onClick = { viewModel.requeue(context) },
                modifier = Modifier.size(50.dp),  //avoid the oval shape
                shape = CircleShape,
                border = BorderStroke(1.dp, Color.Blue),
                contentPadding = PaddingValues(0.dp),  //avoid the little icon
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)

            ) {
                Icon(Icons.Default.RestartAlt, contentDescription = "content description")
            }
        }
    }
}