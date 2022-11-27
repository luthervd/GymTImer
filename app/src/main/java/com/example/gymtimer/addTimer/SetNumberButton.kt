package com.example.gymtimer.addTimer

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SetNumberButton(number:Number, setNumberAction: SetNumberAction, incrementAmount: Number = 1, onValueChanged:(timeArg: Number)->Unit){

    val clickAction : () -> Unit
    val imageVec: ImageVector
    if(setNumberAction == SetNumberAction.INCREMENT) {
        clickAction = {

            val newTime = number.toLong() + incrementAmount.toLong()
            onValueChanged(newTime)
        }
        imageVec = Icons.Default.Add
    }
    else{
        clickAction = {
            val newTime = number.toLong() - incrementAmount.toLong()
            onValueChanged(newTime)
        }
        imageVec = Icons.Default.Remove
    }
    FilledIconButton(
        onClick = clickAction

    ) {
        Icon(imageVec, contentDescription = "content description")
    }
}