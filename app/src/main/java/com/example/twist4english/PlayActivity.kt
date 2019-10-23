package com.example.twist4english

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlayActivity : AppCompatActivity() {

    private val requiredScore by lazy { intent.getIntExtra(CONFIDENCE_SCORE, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.v(
            "ConfidenceScore",
            requiredScore.toString()
        )
        findViewById<TextView>(R.id.textView).text =
            savedInstanceState?.getInt(CONFIDENCE_SCORE)?.toString()
    }
}

