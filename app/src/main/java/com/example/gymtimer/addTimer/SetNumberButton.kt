package com.example.gymtimer.addTimer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

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
    OutlinedButton(
        onClick = clickAction,
        modifier = Modifier.size(50.dp),  //avoid the oval shape
        shape = CircleShape,
        border = BorderStroke(1.dp, Color.Blue),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue)

    ) {
        Icon(imageVec, contentDescription = "content description")
    }
}