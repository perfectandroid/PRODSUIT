package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.perfect.prodsuit.Helper.PlayerService
import com.perfect.prodsuit.Helper.RecorderService
import com.perfect.prodsuit.R
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*


class VoiceRecordingActivity : AppCompatActivity() {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var audioRecordingPermissionGranted = false

    private var fileName: String? = null
    private var startRecordingButton: ImageView? = null
    private  var stopRecordingButton: ImageView? = null
    private  var playRecordingButton: ImageView? = null
    private  var stopPlayingButton: ImageView? = null
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null

    private var lottimic: LottieAnimationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this, permissions,REQUEST_RECORD_AUDIO_PERMISSION
        )
        setContentView(R.layout.activity_voice_recording)
        startRecordingButton = findViewById<ImageView>(R.id.activity_main_record)

        stopRecordingButton = findViewById<ImageView>(R.id.activity_main_stop)
        stopRecordingButton!!.setOnClickListener(View.OnClickListener { stopRecording() })
        playRecordingButton = findViewById<ImageView>(R.id.activity_main_play)
        playRecordingButton!!.setOnClickListener(View.OnClickListener { playRecording() })
        stopPlayingButton = findViewById<ImageView>(R.id.activity_main_stop_playing)
        stopPlayingButton!!.setOnClickListener(View.OnClickListener { stopPlaying() })

        lottimic = findViewById<LottieAnimationView>(R.id.lottieImg)
        lottimic!!.setAnimation(R.raw.record)
        startRecordingButton!!.setOnClickListener(View.OnClickListener {
           lottimic!!.loop(true)
            lottimic!!.playAnimation()
                 startRecording()

        })
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
   //     fileName = filesDir.path + "/" + uuid + ".3gp"
        fileName = filesDir.path + "/" + uuid + ".mp3"
        Log.i("response999","out:="+filesDir.path)
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
//        if (player != null) {
//            player!!.release()
//            player = null
//            stopPlayerService()
//        }

        Log.i("response999222","fileName:="+fileName)
        val filePath =fileName
        val audioToByteArray = audioToByteArray(filePath!!)

        if (audioToByteArray !=null)
        {
          //  convert11(audioToByteArray,fileName)
            Log.i("response999222","out:="+audioToByteArray.toString())
            playByteArray(audioToByteArray)
        }
        else
        {
            Log.i("response999222","out:= error audio file")
        }


//..................
//        try {
//            val path = fileName // Audio File path
//            val inputStream: InputStream = FileInputStream(path)
//            val myByteArray: ByteArray = getBytesFromInputStream(inputStream)
//
//            Log.i("response999","out:="+myByteArray.toString())
//            // ...
//        } catch (e: IOException) {
//            // Handle error...
//        }


        //........................
//        val path = fileName // Audio File path
//
//        val inputStream: InputStream = FileInputStream(path)
//        val arr: Byte = readByte(inputStream)
//
//        Log.d("byte: ", "" + arr.toString())


    }

    private fun playByteArray(mp3SoundByteArray: ByteArray) {
        try {
            val Mytemp = File.createTempFile("TCL", "mp3", cacheDir)
            Mytemp.deleteOnExit()
            val fos = FileOutputStream(Mytemp)
            fos.write(mp3SoundByteArray)
            fos.close()
          var mediaPlayer: MediaPlayer? = null
         //   var mediaPlayer = MediaPlayer()
            mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.fromFile(Mytemp));
            mediaPlayer.start();



//            val MyFile = FileInputStream(Mytemp)
//            mediaPlayer.setDataSource(Mytemp.getf)
//            mediaPlayer.prepare()
//            mediaPlayer.start()
        } catch (ex: IOException) {
            val s = ex.toString()
            ex.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun getBytesFromInputStream(`is`: InputStream): ByteArray {
        val os = ByteArrayOutputStream()
        val buffer = ByteArray(0xFFFF)
        var len = `is`.read(buffer)
        while (len != -1) {
            os.write(buffer, 0, len)
            len = `is`.read(buffer)
        }
        return os.toByteArray()
    }

    fun audioToByteArray(filepath: String):ByteArray?
    {
        val file=File(filepath)

        if (!file.exists())
        {
            return null
        }

        val fileSize=file.length().toInt()
        val buffer =ByteArray(fileSize)

        try {
            val inputStream =FileInputStream(file)
            inputStream.read(buffer)
            inputStream.close()
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }


        return buffer
    }

    @Throws(IOException::class)
    fun convert11(buf: ByteArray?, path: String?) {
        val bis = ByteArrayInputStream(buf)
        val fos = FileOutputStream(path)
        val b = ByteArray(1024)
        var readNum: Int
        while (bis.read(b).also { readNum = it } != -1) {
            fos.write(b, 0, readNum)
        }
    }
    fun byteToAudioFile(bytearray: ByteArray,outputPath:String)
    {
        try {
            val outputStream = FileInputStream(outputPath)


            outputStream.close()

        }
        catch (e:IOException)
        {
            e.printStackTrace()
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