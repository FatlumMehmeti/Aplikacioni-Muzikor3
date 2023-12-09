package com.gauravk.audiovisualizersample.ui

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gauravk.audiovisualizer.visualizer.HiFiVisualizer
import com.gauravk.audiovisualizersample.utils.AudioPlayer

class HiFiActivity : AppCompatActivity() {
    private var mAudioPlayer: AudioPlayer? = null
    private var mVisualizer: HiFiVisualizer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_fi)
        mVisualizer = findViewById(R.id.hifi)
        mAudioPlayer = AudioPlayer()
    }

    override fun onStart() {
        super.onStart()
        startPlayingAudio(R.raw.sample)
    }

    override fun onStop() {
        super.onStop()
        stopPlayingAudio()
    }

    private fun startPlayingAudio(resId: Int) {
        mAudioPlayer.play(this, resId, object : AudioPlayerEvent() {
            fun onCompleted() {
                if (mVisualizer != null) mVisualizer.hide()
            }
        })
        val audioSessionId: Int = mAudioPlayer.getAudioSessionId()
        if (audioSessionId != -1) mVisualizer.setAudioSessionId(audioSessionId)
    }

    private fun stopPlayingAudio() {
        if (mAudioPlayer != null) mAudioPlayer.stop()
        if (mVisualizer != null) mVisualizer.release()
    }
}