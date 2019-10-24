package com.example.twist4english

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

//音声認識のActivityのrequestCode
private const val REQUEST_SPEECH_RECOGNIZER = 3000

const val TONGUE_TWISTER = "TONGUE_TWISTER"

class PlayActivity : AppCompatActivity() {

    private val requiredScore by lazy { intent.getIntExtra(CONFIDENCE_SCORE, 0) }
    private lateinit var mPager: OneWaySwipePager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        setSupportActionBar(toolbar)
        mPager = findViewById<OneWaySwipePager>(R.id.pager).apply {
            this.adapter = TongueTwisterSlidePagerAdapter(supportFragmentManager)
            setDirection(SwipeDirection.LEFT)
        }

        fab.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, tongueTwisters[mPager.currentItem])
            startActivityForResult(intent, REQUEST_SPEECH_RECOGNIZER)
        }
    }
}

private class TongueTwisterSlidePagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = tongueTwisters.size

    override fun getItem(position: Int): Fragment {
        return TongueTwisterSlideFragment().apply {
            arguments = Bundle().apply { putString(TONGUE_TWISTER, tongueTwisters[position]) }
        }
    }

}

