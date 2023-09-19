package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.perfect.prodsuit.Helper.PlayerService
import com.perfect.prodsuit.Helper.RecorderService
import com.perfect.prodsuit.R
import java.io.IOException
import java.util.*

class VoiceRecordingActivity : AppCompatActivity() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var audioRecordingPermissionGranted = false

    private var fileName: String? = null
    private var startRecordingButton: Button? = null
    private  var stopRecordingButton: Button? = null
    private  var playRecordingButton: Button? = null
    private  var stopPlayingButton: Button? = null
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, permissions,REQUEST_RECORD_AUDIO_PERMISSION
        )
        setContentView(R.layout.activity_voice_recording)
        startRecordingButton = findViewById<Button>(R.id.activity_main_record)
        startRecordingButton!!.setOnClickListener(View.OnClickListener { startRecording() })
        stopRecordingButton = findViewById<Button>(R.id.activity_main_stop)
        stopRecordingButton!!.setOnClickListener(View.OnClickListener { stopRecording() })
        playRecordingButton = findViewById<Button>(R.id.activity_main_play)
        playRecordingButton!!.setOnClickListener(View.OnClickListener { playRecording() })
        stopPlayingButton = findViewById<Button>(R.id.activity_main_stop_playing)
        stopPlayingButton!!.setOnClickListener(View.OnClickListener { stopPlaying() })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
             REQUEST_RECORD_AUDIO_PERMISSION -> audioRecordingPermissionGranted =
                grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!audioRecordingPermissionGranted) {
            finish()
        }
    }

    private fun startRecording() {
        val uuid = UUID.randomUUID().toString()
        fileName = filesDir.path + "/" + uuid + ".3gp"
        Log.i(VoiceRecordingActivity::class.java.getSimpleName(), fileName!!)
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder!!.setOutputFile(fileName)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            recorder!!.prepare()
        } catch (e: IOException) {
            Log.e(
                VoiceRecordingActivity::class.java.getSimpleName() + ":startRecording()",
                "prepare() failed"
            )
        }
        recorder!!.start()
        startRecorderService()
    }

    private fun stopRecording() {
        if (recorder != null) {
            recorder!!.release()
            recorder = null
            stopRecorderService()
        }
    }

    private fun playRecording() {
        player = MediaPlayer()
        try {
            player!!.setDataSource(fileName)
            player!!.setOnCompletionListener(OnCompletionListener { stopPlaying() })
            player!!.prepare()
            player!!.start()
            startPlayerService()
        } catch (e: IOException) {
            Log.e(
                VoiceRecordingActivity::class.java.getSimpleName() + ":playRecording()",
                "prepare() failed"
            )
        }
    }

    private fun stopPlaying() {
        if (player != null) {
            player!!.release()
            player = null
            stopPlayerService()
        }
    }

    private fun startRecorderService() {
        val serviceIntent = Intent(this, RecorderService::class.java)
        serviceIntent.putExtra("inputExtra", "Recording in progress")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopRecorderService() {
        val serviceIntent = Intent(this, RecorderService::class.java)
        stopService(serviceIntent)
    }

    private fun startPlayerService() {
        val serviceIntent = Intent(this, PlayerService::class.java)
        serviceIntent.putExtra("inputExtra", "Playing recording")
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    private fun stopPlayerService() {
        val serviceIntent = Intent(this, PlayerService::class.java)
        stopService(serviceIntent)
    }
}