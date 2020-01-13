package com.twist4english.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.twist4english.R
import com.twist4english.activity.TONGUE_TWISTER


class TongueTwisterSlideFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return arguments?.let {
            val view = inflater.inflate(R.layout.fragment_play, container, false)
            val linearLayout = view.findViewById<LinearLayout>(R.id.play_pager)
            linearLayout.findViewById<TextView>(R.id.tongue_twister).apply {
                text = it.getString(TONGUE_TWISTER)
            }

            return view
        }

    }
}