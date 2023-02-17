package com.example.pcrsplitboxforandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcrhelper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class MainViewModel : ViewModel() {

    private class MySemaphore(valueInit: Int) {
        private var value: Int = if (valueInit >= 0) {
            valueInit
        } else {
            0
        }

        @Synchronized
        fun optionP(): Boolean {
            return if (value == 0) {
                false
            } else {
                value--
                true
            }
        }

        @Synchronized
        fun optionV() {
            value++
        }
    }

    var btnChooseStageState: Boolean by mutableStateOf(false)
    var btnListChooseBossState: List<Boolean> by mutableStateOf(listOf(false, false, false))
    var resultContent: String by mutableStateOf("")
    var txtDataStateText: String by mutableStateOf("尚未获取数据")

    var btnChooseStageText: String = "  请选择当前阶段  "
    val btnListChooseBossText: MutableList<String> = mutableListOf(
        "  选  择  ", "  选  择  ", "  选  择  "
    )

    var chooseStageState: Int = 0
    var chooseBossState: MutableList<Int> = mutableListOf(0, 0, 0)

    private val mainSemaphore: MySemaphore = MySemaphore(1)

    var homeworks: Homeworks? = null
    var planList: List<Plan>? = null

    fun onBtnGetDataClicked() {
        if (mainSemaphore.optionP()) {
            viewModelScope.launch {
                txtDataStateText = if (DataHandler.getData() == listOf(true, true, true)) {
                    "获取数据成功"
                } else {
                    "获取数据失败"
                }
                mainSemaphore.optionV()
            }
        } else {
            resultContent = "mainSemaphore is locked! ${Date().time}"
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

    fun onBtnGetPlanListClicked() {
        if (chooseStageState == 0) {
            return
        } else if (mainSemaphore.optionP()) {
            viewModelScope.launch(Dispatchers.Default) {
                homeworks = null
                planList = null
                homeworks = DataHandler.getHomeworksFromData()
                planList = homeworks?.getPlanList(stage = Util.numberToEnChar[chooseStageState])
                txtDataStateText = "已获取所有方案"
                mainSemaphore.optionV()
            }
        } else {
            resultContent = "mainSemaphore is locked! ${Date().time}"
        }
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
        if (chooseBossState.contains(0)) {
            return
        } else if (mainSemaphore.optionP()) {
            viewModelScope.launch(Dispatchers.Default) {
                if (planList == null || planList!!.isEmpty()) {
                    resultContent = "planList is null or empty"
                    return@launch
                }
                var tempResult = ""
                var cnt = 0
                val tempSortedChooseBossState = chooseBossState.sorted()
                val tempSortedKingList: List<String> = listOf(
                    "${Util.numberToEnChar[chooseStageState]}${tempSortedChooseBossState[0]}",
                    "${Util.numberToEnChar[chooseStageState]}${tempSortedChooseBossState[1]}",
                    "${Util.numberToEnChar[chooseStageState]}${tempSortedChooseBossState[2]}"
                )
                planList!!.forEach {
                    if (listOf(Util.snToKing(it.h1.sn), Util.snToKing(it.h2.sn), Util.snToKing(it.h3.sn)) == tempSortedKingList) {
                        cnt++
                        if (cnt <= 10) {
                            tempResult += "number: ${cnt}\ndamage: ${it.damage}\nscore: ${it.score}\n[${it.sn}]\nborrow: ${it.borrow}\n${it.names}\nh1:\n${it.h1.video}\nh2:\n${it.h2.video}\nh3:\n${it.h3.video}\n\n"
                        }
                    }
                }
                resultContent = tempResult
                mainSemaphore.optionV()
            }
        } else {
            resultContent = "mainSemaphore is locked! ${Date().time}"
        }
    }

}