package com.example.twist4english.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.twist4english.R
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_score.*


class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        setSupportActionBar(toolbar)

        val score = intent.getIntExtra(SCORE, 0)
        val bonus = intent.getIntExtra(CONFIDENCE_SCORE, 0)
        addScore(score, bonus)
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
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result != null) {
                    val scoreSize = task.result?.size() ?: 1
                    var counter = 0
                    task.result?.forEach {
                        if (it.data["score"].toString().toInt() > score) {
                            counter++
                        }
                    }

                    setDataToTextViews(
                        score,
                        bonus,
                        (counter.toDouble() / scoreSize.toDouble() * 100).toInt()
                    )
                } else {
                    showErrorMessage()
                }
            }
    }

    private fun setDataToTextViews(score: Int, bonus: Int, rank: Int) {
        findViewById<TextView>(R.id.score).text = score.toString()
        findViewById<TextView>(R.id.bonus).text = bonus.toString()
        findViewById<TextView>(R.id.total).text = (score * bonus).toString()
        findViewById<TextView>(R.id.rank).text = "${rank}%"
    }

    private fun showErrorMessage() {

    }

}