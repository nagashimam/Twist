package com.example.twist4english.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.twist4english.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_score.*


class ScoreActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        setSupportActionBar(toolbar)

        progressBar = findViewById(R.id.progressbar)
        linearLayout = findViewById(R.id.linearLayout)

        val score = intent.getIntExtra(SCORE, 0)
        val bonus = intent.getIntExtra(CONFIDENCE_SCORE, 0)
        addScore(score, bonus)

        fab.setOnClickListener {
            Intent(this, MainActivity::class.java)
                .apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                .also {
                    startActivity(it)
                }
        }
    }

    private fun addScore(score: Int, bonus: Int) {
        FirebaseFirestore.getInstance().apply {
            collection("scores")
                .add(mapOf("score" to score * bonus))
                .addOnSuccessListener { readScores(this, score, bonus) }
                .addOnFailureListener { showErrorMessage() }
        }
    }

    private fun readScores(db: FirebaseFirestore, score: Int, bonus: Int) {
        db.collection("scores")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val scoreSize = task.result?.size() ?: 1
                    var counter = 0
                    task.result?.forEach {
                        if (it.data["score"].toString().toInt() > score * bonus) {
                            counter++
                        }
                    }

                    val percent = (counter.toDouble() / scoreSize.toDouble() * 100).toInt()
                    val displayPercent = if (percent == 0) {
                        1
                    } else {
                        percent
                    }

                    setDataToTextViews(
                        score,
                        bonus,
                        displayPercent
                    )
                } else {
                    showErrorMessage()
                }
            }
    }

    private fun setDataToTextViews(score: Int, bonus: Int, rank: Int) {
        progressBar.visibility = ProgressBar.INVISIBLE
        linearLayout.visibility = View.VISIBLE

        findViewById<TextView>(R.id.score).text = score.toString()
        findViewById<TextView>(R.id.bonus).text = bonus.toString()
        findViewById<TextView>(R.id.total).text = (score * bonus).toString()
        findViewById<TextView>(R.id.rank).text = "${rank}%"
    }

    private fun showErrorMessage() {
        progressBar.visibility = ProgressBar.INVISIBLE

        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("We couldn't connect to the database.\nPlease try again.")
            .setPositiveButton("OK", null)
            .show()
    }

}