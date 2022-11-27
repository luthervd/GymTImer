package com.example.gymtimer.addTimer

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SetTimerAction(description: String, formattedTime: String,value: Number, onTimeChanged: (time : Number) -> Unit){
    val height = 70.dp
    val lineHeight = 50.sp;
    val fontSize = 30.sp;
    val fontSize2 = 50.sp;
    Row(modifier = Modifier.fillMaxWidth().size(height), horizontalArrangement =  Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
        Text(text = description,
            fontSize = fontSize,
            modifier = Modifier.fillMaxHeight().padding(0.dp,10.dp,0.dp,0.dp),
            lineHeight = lineHeight)
        Text(text = formattedTime,
            fontSize = fontSize2,
            modifier = Modifier.fillMaxHeight(),
            lineHeight = lineHeight)
        SetNumberButton(
            number = value,
            setNumberAction = SetNumberAction.INCREMENT,
            incrementAmount = 5000,
            onValueChanged = onTimeChanged
        )
        SetNumberButton(
            number = value,
            setNumberAction = SetNumberAction.DECREMENT,
            incrementAmount = 5000,
            onValueChanged = onTimeChanged
        )
    }
}