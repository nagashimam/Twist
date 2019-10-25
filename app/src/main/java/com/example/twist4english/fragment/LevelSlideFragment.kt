package com.example.twist4english.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.twist4english.R
import com.example.twist4english.activity.CONFIDENCE_SCORE
import com.example.twist4english.activity.TITLE


class LevelSlideFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return arguments?.let {
            val view = inflater.inflate(R.layout.fragment_level, container, false)
            val linearLayout = view.findViewById<LinearLayout>(R.id.level_pager)

            linearLayout.findViewById<TextView>(R.id.level_title).text =
                it.getString(TITLE)

            val requiredConfidence = it.getFloat(CONFIDENCE_SCORE).toString()
            val message =
                "means you have to get $requiredConfidence points or more for each tongue twister!"
            linearLayout.findViewById<TextView>(R.id.level_description).text = message

            return view
        }

    }
}