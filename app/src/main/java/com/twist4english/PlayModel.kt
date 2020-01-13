package com.twist4english

import com.twist4english.contract.PlayPresenterContract
import java.util.Locale
import kotlin.collections.ArrayList

typealias PlayResult = Pair<Pair<String, Float>, Pair<String, Float>>
object PlayModel {

    private val scores = ArrayList<Float>()
    fun judgeResult(result: PlayResult, presenterContract: PlayPresenterContract) {

        if (isValidResult(result)) {
            val score = result.second.second
            scores.add(score)
            if (scores.size >= 3) {
                presenterContract.finish(scores)
            } else {
                presenterContract.nextTongueTwister(score)
            }
        } else {
            presenterContract.retry(result.second)
        }
    }

    private fun isValidResult(result: PlayResult): Boolean {
        val (expectedResult, actualResult) = result
        val formatter = { str: String ->
            str.toLowerCase(Locale.getDefault())
                .replace(",", "")
                .replace(".", "")
                .replace(";", "")
                .replace(":", "")
                .replace("  ", " ")
        }
        val expectedSpeech = formatter(expectedResult.first)
        val actualSpeech = formatter(actualResult.first)
        return expectedSpeech == actualSpeech && expectedResult.second <= actualResult.second
    }
}