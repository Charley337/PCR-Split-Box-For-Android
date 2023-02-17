package com.example.pcrsplitboxforandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcrhelper.DataHandler
import com.example.pcrhelper.Homeworks
import com.example.pcrhelper.Plan
import com.example.pcrhelper.Util
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

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
    var systemLog: String by mutableStateOf("")
    var txtDataStateText: String by mutableStateOf("尚未获取数据")
    var btnGoEnabled: Boolean by mutableStateOf(false)
    var resultPlanList: List<Plan> by mutableStateOf(emptyList())

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
            systemLog = "请稍后操作！ ${Date().time}"
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
            systemLog = "请选择阶段"
            return
        } else if (mainSemaphore.optionP()) {
            viewModelScope.launch(Dispatchers.Default) {
                btnGoEnabled = false
                homeworks = null
                planList = null
                homeworks = DataHandler.getHomeworksFromData()
                planList = homeworks?.getPlanList(stage = Util.numberToEnChar[chooseStageState])
                systemLog = "已获取所有方案"
                btnGoEnabled = true
                mainSemaphore.optionV()
            }
        } else {
            systemLog = "请稍后操作！ ${Date().time}"
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
            systemLog = "请选择BOSS！ ${Date().time}"
            return
        } else if (mainSemaphore.optionP()) {
            viewModelScope.launch(Dispatchers.Default) {
                if (planList == null || planList!!.isEmpty()) {
                    systemLog = "尚未获取方案或者方案表为空"
                    btnGoEnabled = false
                    return@launch
                }
                val tempResultPlanList: MutableList<Plan> = mutableListOf()
                var cnt = 0
                val tempSortedChooseBossState = chooseBossState.sorted()
                val tempSortedKingList: List<String> = listOf(
                    "${Util.numberToEnChar[chooseStageState]}${tempSortedChooseBossState[0]}",
                    "${Util.numberToEnChar[chooseStageState]}${tempSortedChooseBossState[1]}",
                    "${Util.numberToEnChar[chooseStageState]}${tempSortedChooseBossState[2]}"
                )
                for (i in planList!!.indices) {
                    val it = planList!![i]
                    if (listOf(Util.snToKing(it.h1.sn), Util.snToKing(it.h2.sn), Util.snToKing(it.h3.sn)) == tempSortedKingList) {
                        cnt++
                        tempResultPlanList.add(it)
                        if (cnt >= 10) {
                            break
                        }
                    }
                }
                resultPlanList = tempResultPlanList
                mainSemaphore.optionV()
            }
        } else {
            systemLog = "请稍后操作！ ${Date().time}"
        }
    }

}