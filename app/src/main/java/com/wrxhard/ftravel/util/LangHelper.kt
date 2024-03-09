package com.wrxhard.ftravel.util

import android.app.Activity
import android.content.Context
import android.speech.tts.TextToSpeech
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import java.util.Locale

object LangHelper {
    private lateinit var translator: Translator
    private lateinit var textToSpeech: TextToSpeech


    fun initTrans(options: TranslatorOptions, conditions: DownloadConditions, callBack: (Event<*>) -> Unit) {
        translator = Translation.getClient(options)
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                callBack(Event.Success("Model downloaded"))
            }
            .addOnFailureListener {
                callBack(Event.Failure("Error: $it"))
            }
    }
    fun initSpeechToText(context: Context){
        // Initialize TextToSpeech
        textToSpeech = TextToSpeech(context) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.US
            }
        }
    }
    fun convertTextToSpeech(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }


    fun translate(text: String, callBack: (Event<*>) -> Unit) {
        if (::translator.isInitialized)
        {
            translator.translate(text)
                .addOnSuccessListener { translateText ->
                    callBack(Event.Success(translateText))
                }
                .addOnFailureListener { exception ->
                    callBack(Event.Failure("Error: $exception"))
                }
        }else{
            callBack(Event.Failure("Translator not initialized"))
        }
    }

    fun closeTrans() {
        translator.close()
    }
    fun closeTextToSpeech(){
        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}