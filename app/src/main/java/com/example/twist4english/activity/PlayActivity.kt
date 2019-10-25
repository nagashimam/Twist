package com.example.twist4english.activity

import android.Manifest.permission.RECORD_AUDIO
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.twist4english.*
import com.example.twist4english.contract.PlayContract
import com.example.twist4english.fragment.TongueTwisterSlideFragment
import kotlinx.android.synthetic.main.activity_main.*

const val TONGUE_TWISTER = "TONGUE_TWISTER"
const val REQUEST_RECORD_AUDIO = 1

class PlayActivity : AppCompatActivity(), PlayContract {
    private lateinit var speechRecognizer: SpeechRecognizer

    override fun start() {
        val permission = ContextCompat.checkSelfPermission(this, RECORD_AUDIO)
        if (permission == PackageManager.PERMISSION_GRANTED) {
            startRecording()
        } else {
            requestPermissionToRecord()
        }
    }

    private fun startRecording() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL, //
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM //
                )
            }.apply {
                putExtra(
                    RecognizerIntent.EXTRA_PROMPT, //
                    tongueTwisters[mPager.currentItem] //
                )
            }

        speechRecognizer.startListening(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        if (requestIsAccepted(requestCode, grantResults)) {
            startRecording()
        } else {
            finish()
        }
    }

    private fun requestIsAccepted(requestCode: Int, grantResults: IntArray): Boolean {
        return when {
            requestCode != REQUEST_RECORD_AUDIO -> false
            grantResults.isEmpty() -> false
            else -> grantResults.first() == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissionToRecord() {
        ActivityCompat.requestPermissions(this, arrayOf(RECORD_AUDIO),
            REQUEST_RECORD_AUDIO
        )
    }

    override fun end() {
    }

    override fun showMessage(msg: String) {
    }

    override fun getExpectedResult(): Pair<String, Float> =
        Pair(tongueTwisters[mPager.currentItem], intent.getFloatExtra(CONFIDENCE_SCORE, 0F))

    override fun showNextTongueTwister() {
        mPager.currentItem++
    }

    override fun proceedToScoreActivity() {
    }

    private lateinit var mPager: OneWaySwipePager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        setSupportActionBar(toolbar)

        mPager = findViewById<OneWaySwipePager>(R.id.pager).apply {
            this.adapter = TongueTwisterSlidePagerAdapter(
                supportFragmentManager
            )
            setDirection(SwipeDirection.LEFT)
        }

        speechRecognizer =
            SpeechRecognizer
                .createSpeechRecognizer(applicationContext)
                .apply { setRecognitionListener(
                    TwistRecognitionListener(
                        this@PlayActivity
                    )
                ) }

        fab.setOnClickListener { start() }
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

