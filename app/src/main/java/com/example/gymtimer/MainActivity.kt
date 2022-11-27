package com.example.gymtimer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymtimer.addTimer.AddRoundTimer
import com.example.gymtimer.model.TimerViewModel
import com.example.gymtimer.timer.TimerScreen
import com.example.gymtimer.ui.theme.GymTimerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: TimerViewModel by viewModels()
        setContent {
            GymTimerTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        modifier = Modifier.fillMaxSize(),
                        navController = navController,
                        startDestination = "addRound"
                    ) {
                        composable(route = "addRound"){
                            AddRoundTimer(navController,viewModel)
                        }
                        composable(route = "timer"){
                            TimerScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}
