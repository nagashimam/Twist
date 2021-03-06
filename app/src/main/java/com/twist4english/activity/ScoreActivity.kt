package com.twist4english.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.functions.FirebaseFunctions
import com.twist4english.R
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
        val data = hashMapOf(
            "score" to score,
            "bonus" to bonus
        )

        FirebaseFunctions.getInstance()
            .getHttpsCallable("calculateDisplayPercent")
            .call(data)
            .continueWith { task ->
                Log.v("戻り値", task.result?.data as String)
                setDataToTextViews(score, bonus, Integer.parseInt(task.result?.data as String))
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