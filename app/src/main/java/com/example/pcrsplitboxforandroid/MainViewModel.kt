package com.example.pcrsplitboxforandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcrhelper.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var btnChooseStageState: Boolean by mutableStateOf(false)
    var btnListChooseBossState: List<Boolean> by mutableStateOf(listOf(false, false, false))
    var debugContent by mutableStateOf("")

    var btnChooseStageText: String = "  请选择当前阶段  "
    val btnListChooseBossText: MutableList<String> = mutableListOf(
        "  选  择  ", "  选  择  ", "  选  择  "
    )

    var chooseStageState: Int = 0
    var chooseBossState: MutableList<Int> = mutableListOf(0, 0, 0)

    var homeworks: Homeworks? = null
    var planList: List<Plan>? = null

    fun onBtnGetDataClicked() {
        viewModelScope.launch {
            debugContent = DataHandler.getData().toString()
        }
    }

    fun onBtnChooseStageClicked1() {
        chooseStageState = 0
        btnChooseStageState = !btnChooseStageState
    }

    fun onBtnChooseStageClicked2(i: Int) {
        chooseStageState = i
        btnChooseStageState = !btnChooseStageState
        btnChooseStageText = "  当前阶段:  ${Util.numberToEnChar[i]}  面  "
    }

    fun onBtnListChooseBossClicked1(i: Int) {
        val tempList1: MutableList<Int> =
            chooseBossState.toMutableList()
        tempList1[i] = 0
        chooseBossState = tempList1
        val tempList2: MutableList<Boolean> =
            btnListChooseBossState.toMutableList()
        tempList2[i] = !tempList2[i]
        btnListChooseBossState = tempList2
    }

    fun onBtnListChooseBossClicked2(i: Int, j: Int) {
        val tempList1: MutableList<Int> = chooseBossState.toMutableList()
        tempList1[i] = j
        chooseBossState = tempList1
        btnListChooseBossText[i] = "  ${Util.numberToZhChar[j]}  王  "
        val tempList2: MutableList<Boolean> = btnListChooseBossState.toMutableList()
        tempList2[i] = !tempList2[i]
        btnListChooseBossState = tempList2
    }

    fun onBtnGoClicked() {
        homeworks = DataHandler.getHomeworksFromData()
        planList = homeworks!!.getPlanList(stage = 'C')
        if (planList!!.isEmpty()) {
            debugContent = "planList is null or empty"
            return
        }
        var tempResult = ""
        var cnt = 0
        planList!!.forEach {
            if (listOf(Util.snToKing(it.h1.sn), Util.snToKing(it.h2.sn), Util.snToKing(it.h3.sn)) == listOf("C1", "C2", "C3")) {
                cnt++
                if (cnt <= 3) {
                    tempResult += "number: ${cnt}\ndamage: ${it.damage}\nscore: ${it.score}\n[${it.sn}]\nborrow: ${it.borrow}\n${it.names}\nh1:\n${it.h1.video}\nh2:\n${it.h2.video}\nh3:\n${it.h3.video}\n\n\n"
                }
            }
        }
        debugContent = tempResult
    }

}