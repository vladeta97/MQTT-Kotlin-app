package com.example.testmqtt.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TopicViewModelFactory (
        private val arg:Int,
        private val application:Application
        ): ViewModelProvider.NewInstanceFactory() {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(TopicsViewModel::class.java)) {
                    return TopicsViewModel(arg, application) as T
                }
                throw IllegalArgumentException("Unknown ViewModel Class")
            }

        }
