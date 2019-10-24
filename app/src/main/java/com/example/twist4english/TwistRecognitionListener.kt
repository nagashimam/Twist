package com.example.twist4english

import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.SpeechRecognizer.*
//RESULTS_RECOGNITION
class TwistRecognitionListener(private val contract: PlayContract) :
    RecognitionListener,
    PlayPresenerContract {

    // region 何もしない
    override fun onBeginningOfSpeech() {}

    override fun onBufferReceived(buffer: ByteArray) {}

    override fun onEndOfSpeech() {}

    override fun onRmsChanged(rmsdB: Float) {}

    override fun onEvent(eventType: Int, params: Bundle) {}

    override fun onPartialResults(partialResults: Bundle) {}
    // endregion

    override fun onError(error: Int) {
        contract.showMessage(getErrorReason(error))
        restart()
    }

    private fun getErrorReason(error: Int): String {
        return when (error) {
            ERROR_AUDIO -> "Audio didn't work well."
            ERROR_CLIENT -> "There's something wrong with your device. "
            ERROR_INSUFFICIENT_PERMISSIONS -> "You didn't give us proper permissions."
            ERROR_NETWORK -> "There's something wrong with the network."
            ERROR_NETWORK_TIMEOUT -> "Network operation timed out."
            ERROR_NO_MATCH -> "No recognition result matched."
            ERROR_RECOGNIZER_BUSY -> "Recognition service is busy."
            ERROR_SERVER -> "There's something wrong with Google's server."
            ERROR_SPEECH_TIMEOUT -> "You were quiet for too long"
            else -> "There's something wrong."
        }
    }

    override fun onReadyForSpeech(params: Bundle) {
        contract.showMessage("Read as fast as you can!")
    }

    override fun onResults(results: Bundle) {
        val speech = results.getStringArrayList(RESULTS_RECOGNITION)
            ?.first()
            ?: ""

        val score = results.getFloatArray(CONFIDENCE_SCORES)
            ?.first()
            ?: 0f

        PlayModel.judgeResult(Triple(speech, score, contract.getRequiredScore()), this)
    }

    private fun restart() {
        with(contract) {
            end()
            start()
        }
    }

}