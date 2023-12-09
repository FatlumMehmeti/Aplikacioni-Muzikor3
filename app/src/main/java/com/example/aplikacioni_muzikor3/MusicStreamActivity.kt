package com.gauravk.audiovisualizersample.ui

import android.R
import android.app.ProgressDialog
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.gauravk.audiovisualizer.visualizer.BlastVisualizer
import java.io.IOException

class MusicStreamActivity : AppCompatActivity() {
    var imgButton: ImageButton? = null
    var mVisualizer: BlastVisualizer? = null
    var mediaPlayer: MediaPlayer? = null
    var stream = "http://stream.radioreklama.bg/radio1rock128"
    var prepared = false
    var started = false
    private var progressDialog: ProgressDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_stream)
        imgButton = findViewById<ImageButton>(R.id.playbtn)
        imgButton.setImageResource(R.drawable.playbtn)
        mVisualizer = findViewById(R.id.blast)
        mediaPlayer = MediaPlayer()
        mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        val audioSessionId = mediaPlayer!!.audioSessionId
        if (audioSessionId != AudioManager.ERROR) {
            mVisualizer.setAudioSessionId(mediaPlayer!!.audioSessionId)
        }
        progressDialog = ProgressDialog(this)
        progressDialog!!.setMessage("Loading...")
        progressDialog!!.show()
        PlayerTask().execute(stream)
        imgButton.setOnClickListener(View.OnClickListener {
            if (started) {
                started = false
                mediaPlayer!!.pause()
                imgButton.setImageResource(R.drawable.playbtn)
            } else {
                started = true
                mediaPlayer!!.start()
                mediaPlayer!!.setOnPreparedListener { mp -> mp.start() }
                imgButton.setImageResource(R.drawable.pausebtn)
            }
        })
    }

    internal inner class PlayerTask :
        AsyncTask<String?, Void?, Boolean>() {
        protected override fun doInBackground(vararg strings: String): Boolean {
            try {
                mediaPlayer!!.setDataSource(strings[0])
                mediaPlayer!!.prepare()
                prepared = true
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return prepared
        }

        override fun onPostExecute(aBoolean: Boolean) {
            super.onPostExecute(aBoolean)
            if (progressDialog!!.isShowing) {
                progressDialog!!.cancel()
            }
            imgButton!!.setImageResource(R.drawable.playbtn)
        }
    }

    override fun onPause() {
        super.onPause()
        if (started) {
            mediaPlayer!!.pause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (started) {
            //mediaPlayer.start();
            mediaPlayer!!.setOnPreparedListener { mp -> mp.start() }
            mediaPlayer!!.prepareAsync()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (prepared) {
            mediaPlayer!!.release()
        }
        if (mVisualizer != null) mVisualizer.release()
    }
}