/*
        Copyright 2018 Gaurav Kumar

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/
package com.gauravk.audiovisualizersample.ui

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gauravk.audiovisualizer.visualizer.BarVisualizer
import com.gauravk.audiovisualizersample.utils.AudioPlayer

class BarActivity : AppCompatActivity() {
    private var mVisualizer: BarVisualizer? = null
    private var mAudioPlayer: AudioPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bar)
        mVisualizer = findViewById(R.id.bar)
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
        mAudioPlayer.play(this, resId, object : AudioPlayer.AudioPlayerEvent() {
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