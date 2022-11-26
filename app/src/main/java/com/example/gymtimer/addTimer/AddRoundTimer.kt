package com.example.gymtimer.addTimer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    fun save(){
        val roundTimer = RoundTimer("My Round Timer")
        roundTimer.addWork(Timer(workTime, "Work"))
        roundTimer.addRest(Timer(restTime, "Rest"))
        roundTimer.addNumber(rounds)
        viewModel.setTimerSection(roundTimer)
        navController.navigate("timer")
    }
    Column(Modifier.fillMaxSize().padding(10.dp),
           verticalArrangement = Arrangement.SpaceBetween) {
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
          Text(text = "Work")
          Text(text = formatTime(workTime))
          SetNumberButton(
              number = workTime,
              setNumberAction = INCREMENT,
              incrementAmount = 5000,
              onValueChanged = { setWorkTime(it as Long) })
          SetNumberButton(
              number = workTime,
              setNumberAction = DECREMENT,
              incrementAmount = 5000,
              onValueChanged = { setWorkTime(it as Long) })
        }
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
          Text(text = "Rest")
          Text(text = formatTime(restTime))
          SetNumberButton(
              number = restTime,
              setNumberAction = INCREMENT,
              incrementAmount = 5000,
              onValueChanged = { setRestTime(it as Long) })
          SetNumberButton(
              number = restTime,
              setNumberAction = DECREMENT,
              incrementAmount = 5000,
              onValueChanged = { setRestTime(it as Long) })
        }
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
          Text(text = "Rounds")
          Text(text = rounds.toString())
          SetNumberButton(
              number = rounds,
              setNumberAction = INCREMENT,
              incrementAmount = 1,
              onValueChanged = { setRounds(it as Long) })
          SetNumberButton(
              number = rounds,
              setNumberAction = DECREMENT,
              incrementAmount = 1,
              onValueChanged = { setRounds(it as Long) })
        }
        Row(Modifier.fillMaxWidth(), Arrangement.SpaceEvenly) {
          Button(onClick = { save()}){
              Text(text = "Run Timer")
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
