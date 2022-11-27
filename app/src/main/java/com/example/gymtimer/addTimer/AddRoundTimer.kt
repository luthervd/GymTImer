package com.example.gymtimer.addTimer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gymtimer.model.TimerViewModel
import com.example.gymtimer.model.RoundTimer
import com.example.gymtimer.model.Timer
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymtimer.addTimer.SetNumberAction.*

@Composable
fun AddRoundTimer(navController: NavController, viewModel: TimerViewModel = viewModel()) {

    val (workTime, setWorkTime) = rememberSaveable {mutableStateOf(10000L)}
    val (restTime, setRestTime) = rememberSaveable {mutableStateOf(10000L)}
    val (rounds, setRounds) = rememberSaveable {mutableStateOf(10L)}
    val spaceHeight = 50.dp;
    fun save(){
        val roundTimer = RoundTimer("My Round Timer")
        roundTimer.addWork(Timer(workTime, "Work"))
        roundTimer.addRest(Timer(restTime, "Rest"))
        roundTimer.addNumber(rounds)
        viewModel.setTimerSection(roundTimer)
        navController.navigate("timer")
    }
    Column(Modifier.fillMaxSize().padding(0.dp,0.dp,0.dp,25.dp),
           verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .height(60.dp)
                .background(MaterialTheme.colorScheme.primary),
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 60.sp,
            text = "Set up timer : ",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Normal,
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(spaceHeight))
        SetTimerAction("Work",formatTime(workTime),workTime,5000) { setWorkTime(it as Long) }
        Spacer(modifier = Modifier.height(spaceHeight))
        SetTimerAction("Rest",formatTime(restTime),restTime,5000) { setRestTime(it as Long) }
        Spacer(modifier = Modifier.height(spaceHeight))
        SetTimerAction("Rounds",rounds.toString(),rounds,1) { setRounds(it as Long) }
        Spacer(modifier = Modifier.weight(1f))
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
            FilledIconButton(
                onClick =  { save()},
                modifier = Modifier.size(100.dp)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = "content description",modifier=Modifier.size(50.dp))
            }
        }
    }
}

fun formatTime(time: Long): String{
    val minutes = time / 1000 / 60
    val seconds = time / 1000 % 60
    val minuteView = if(minutes < 10) "0$minutes" else "$minutes"
    val secondsView = if(seconds < 10) "0$seconds" else "$seconds"
    return "$minuteView:$secondsView"
}
