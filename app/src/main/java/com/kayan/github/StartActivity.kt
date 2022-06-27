package com.kayan.github

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kayan.github.kotlin.R
import com.kayan.github.module.main.MainActivity
import org.jetbrains.anko.clearTask

/**
 * 启动页
 * Created by kayan
 * Date: 2022-06-18
 */
class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val intent = Intent(this, MainActivity::class.java)
        intent.clearTask()
        startActivity(intent)

        finish()

    }

    override fun onBackPressed() {

    }
}