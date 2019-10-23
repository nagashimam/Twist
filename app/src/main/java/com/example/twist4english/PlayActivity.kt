package com.example.twist4english

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PlayActivity : AppCompatActivity() {

    private val requiredScore by lazy { intent.getIntExtra(CONFIDENCE_SCORE, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

