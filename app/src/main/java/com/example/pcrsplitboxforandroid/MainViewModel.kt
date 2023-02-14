package com.example.pcrsplitboxforandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

class MainViewModel : ViewModel() {

    companion object {
        var chooseStageState: Int = 0
        val chooseBossState: MutableList<Int> = mutableListOf(0, 0, 0)
    }

}