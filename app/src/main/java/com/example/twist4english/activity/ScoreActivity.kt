package com.example.twist4english.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.twist4english.R
import kotlinx.android.synthetic.main.activity_main.*

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        setSupportActionBar(toolbar)
    }
}