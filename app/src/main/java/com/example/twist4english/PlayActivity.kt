package com.example.twist4english

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

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

