package com.example.twist4english

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

const val TITLE = "TITLE"
const val CONFIDENCE_SCORE = "CONFIDENCE_SCORE"

class MainActivity : AppCompatActivity() {

    private lateinit var mPager : ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mPager = findViewById<ViewPager>(R.id.pager).apply {
            this.adapter = ScreenSlidePagerAdapter(supportFragmentManager)
        }

        fab.setOnClickListener {
            val selectedLevel = Level.values()[mPager.currentItem]
            startActivity(Intent(this, PlayActivity::class.java).apply {
                putExtra(CONFIDENCE_SCORE, selectedLevel.requiredConfidentScore)
            })
        }
    }

}

private class ScreenSlidePagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = Level.values().size

    override fun getItem(position: Int): Fragment {
        return LevelSlideFragment().apply {
            val level = Level.values()[position]
            arguments = Bundle().apply {
                putFloat(CONFIDENCE_SCORE, level.requiredConfidentScore)
                putString(TITLE, level.title)
            }
        }
    }

}

enum class Level(val title: String, val requiredConfidentScore: Float) {
    EASY("Easy", 70F),
    MEDIUM("Medium", 80F),
    HARD("Hard", 90F);
}