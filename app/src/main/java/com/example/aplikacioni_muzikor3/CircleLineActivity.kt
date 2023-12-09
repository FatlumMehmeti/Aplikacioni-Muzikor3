package com.gauravk.audiovisualizersample.ui

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gauravk.audiovisualizer.visualizer.CircleLineVisualizer
import com.gauravk.audiovisualizersample.utils.AudioPlayer

class CircleLineActivity : AppCompatActivity() {
    private var mAudioPlayer: AudioPlayer? = null
    private var mVisualizer: CircleLineVisualizer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_line)
        mVisualizer = findViewById(R.id.blob)
        mAudioPlayer = AudioPlayer()
        mVisualizer.setDrawLine(true)
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