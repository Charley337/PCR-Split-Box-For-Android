package com.example.pcrsplitboxforandroid

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.pcrsplitboxforandroid.ui.theme.PcrSplitBoxForAndroidTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val mainActivity: MainActivity = this

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = applicationContext
        setContent {
            PcrSplitBoxForAndroidTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.94F)
                            .verticalScroll(rememberScrollState()),
                        color = MaterialTheme.colors.background
                    ) {
                        when (mainViewModel.navigationIndex) {
                            0 -> {
                                OperationPage(mainViewModel, mainActivity)
                            }
                            1 -> {
                                SettingPage(mainViewModel)
                            }
                            else -> {
                                throw Exception("未定义的导航页号: ${mainViewModel.navigationIndex}")
                            }
                        }
                    }
                    BottomNavigationCompose(mainViewModel)
                }
            }
        }
    }

    fun jumpToBrowserActivity(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            intent.data = Uri.parse(url)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            println("当前手机未安装浏览器")
        }
    }

    fun toastShortShow(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun copyToClipboard(text: String) {
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText("Label", text)
        clipboardManager.setPrimaryClip(clipData)
        toastShortShow("已复制到剪切板")
    }

}