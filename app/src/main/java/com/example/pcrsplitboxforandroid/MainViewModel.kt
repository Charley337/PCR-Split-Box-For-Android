package com.example.pcrsplitboxforandroid

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pcrhelper.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

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
    var tfValue: String by mutableStateOf("")
    var tfValueBan: String by mutableStateOf("")
    var txtDataStateText: String by mutableStateOf("尚未获取数据")
    var btnGoEnabled: Boolean by mutableStateOf(false)
    var resultPlanList: List<Plan> by mutableStateOf(emptyList())
    var navigationIndex: Int by mutableStateOf(0)
    var tfBanListValue: String by mutableStateOf("")
    var tfLackListValue: String by mutableStateOf("")

    var btnChooseStageText: String = "  请选择当前阶段  "
    val btnListChooseBossText: MutableList<String> = mutableListOf(
        "  选  择  ", "  选  择  ", "  选  择  "
    )

    var chooseStageState: Int = 0
    var chooseBossState: MutableList<Int> = mutableListOf(0, 0, 0)

    private val mainSemaphore: MySemaphore = MySemaphore(1)

    var homeworks: Homeworks? = null
    var planList: List<Plan>? = null

    fun onBtnGetDataClicked(mainActivity: MainActivity) {
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
            mainActivity.toastShortShow("请稍后操作！")
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

    fun onBtnGetPlanListClicked(mainActivity: MainActivity) {
        if (chooseStageState == 0) {
            mainActivity.toastShortShow("请选择阶段")
            return
        } else if (mainSemaphore.optionP()) {
            mainActivity.toastShortShow("计算中，请稍等")
            viewModelScope.launch(Dispatchers.Default) {
                btnGoEnabled = false
                homeworks = null
                planList = null
                homeworks = DataHandler.getHomeworksFromData()
                planList = homeworks?.getPlanList(stage = Util.numberToEnChar[chooseStageState])
                btnGoEnabled = true
                mainSemaphore.optionV()
            }
        } else {
            mainActivity.toastShortShow("请稍后操作！")
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

    fun onBtnGoClicked(mainActivity: MainActivity) {
        if (chooseBossState.contains(0)) {
            mainActivity.toastShortShow("请选择BOSS！")
            return
        } else if (mainSemaphore.optionP()) {
            viewModelScope.launch(Dispatchers.Default) {
                if (planList == null || planList!!.isEmpty()) {
                    mainActivity.toastShortShow("尚未获取方案或者方案表为空")
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
                val tempUsedSn: List<String> = tfValue.split(" ")
                val tempBannedSn: List<String> = tfValueBan.split(" ")
                for (i in planList!!.indices) {
                    val it = planList!![i]
                    if (listOf(Util.snToKing(it.h1.sn), Util.snToKing(it.h2.sn), Util.snToKing(it.h3.sn)) == tempSortedKingList) {
                        var tempFlag = true
                        if (tfValue != "") {
                            for (j in tempUsedSn.indices) {
                                tempFlag = tempFlag && it.sn.contains(tempUsedSn[j])
                                if (!tempFlag) {
                                    break
                                }
                            }
                        }
                        if (tfValueBan != "") {
                            for (j in tempBannedSn.indices) {
                                tempFlag = tempFlag && (!(it.sn.contains(tempBannedSn[j])))
                                if (!tempFlag) {
                                    break
                                }
                            }
                        }
                        if (tempFlag) {
                            cnt++
                            tempResultPlanList.add(it)
                            if (cnt >= 15) {
                                break
                            }
                        }
                    }
                }
                resultPlanList = tempResultPlanList
                mainSemaphore.optionV()
            }
        } else {
            mainActivity.toastShortShow("请稍后操作！")
        }
    }

    fun onSnClicked(sn: String) {
        if (tfValue.isEmpty()) {
            tfValue = sn
        } else if (tfValue.last() == ' ') {
            tfValue += sn
        } else {
            tfValue += " $sn"
        }
    }

    fun onSnLongClicked(sn: String) {
        if (tfValueBan.isEmpty()) {
            tfValueBan = sn
        } else if (tfValueBan.last() == ' ') {
            tfValueBan += sn
        } else {
            tfValueBan += " $sn"
        }
    }

    fun onBottomNavigationClicked(item: Int) {
        if (navigationIndex == item) {
            return
        }
        when (item) {
            0 -> {
                /*save to banList and lackList*/
                val tempBanList: MutableList<String> =
                    tfBanListValue.split(' ').toMutableList()
                val tempLackList: MutableList<String> =
                    tfLackListValue.split(' ').toMutableList()
                while (tempBanList.remove("")) { continue }
                while (tempLackList.remove("")) { continue }
                if (Util.banList.toSet() != tempBanList.toSet()) {
                    Util.banList = tempBanList
                    /*save to Room database*/
                    ConfigurationDatabase
                        .getInstance(MainActivity.context)
                        .getConfigurationDao()
                        .insertConfig(Configuration("banList", JSONArray(tempBanList).toString()))
                }
                if (Util.lackList.toSet() != tempLackList.toSet()) {
                    Util.lackList = tempLackList
                    /*save to Room database*/
                    ConfigurationDatabase
                        .getInstance(MainActivity.context)
                        .getConfigurationDao()
                        .insertConfig(Configuration("lackList", JSONArray(tempLackList).toString()))
                }
            }
            1 -> {
                /*load from banList and lackList*/
                var tempTfBanListValue = ""
                var tempTfLackListValue = ""
                for (i in Util.banList.indices) {
                    tempTfBanListValue += "${Util.banList[i]} "
                }
                for (i in Util.lackList.indices) {
                    tempTfLackListValue += "${Util.lackList[i]} "
                }
                tfBanListValue = tempTfBanListValue
                tfLackListValue = tempTfLackListValue
            }
        }
        navigationIndex = item
    }

}