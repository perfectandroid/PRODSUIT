package com.perfect.prodsuit.View.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import java.nio.file.Files
import java.text.SimpleDateFormat
import java.util.*


class VoiceRecordingActivity : AppCompatActivity(), View.OnClickListener {
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO)
    private var audioRecordingPermissionGranted = false

    private var fileName: String? = null
    private var startRecordingButton: ImageView? = null
    private var stopRecordingButton: ImageView? = null
    private var playRecordingButton: ImageView? = null
    private var stopPlayingButton: ImageView? = null
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var player1: MediaPlayer? = null

    private var lottimic: LottieAnimationView? = null
    private var lotti_play: LottieAnimationView? = null

    private var recImg: ImageView? = null
    private var recordTime: Chronometer? = null
    private var startTime: Long = 0
    private var isRecording = false

    //  private var isplaying=false
    private var playDialog: Dialog? = null

    private var voicedata: String? = null
    private var voicedatabyte: ByteArray? = null
    private var RECORD_PLAY: Int? = 1038

    private val handler = Handler(Looper.getMainLooper())
//    private val timeRunnable = object : Runnable {
//        override fun run() {
//            val currentTime = System.currentTimeMillis()
//            val elapsedTime = currentTime - startTime
//            val formattedTime =
//                SimpleDateFormat("mm:ss", Locale.getDefault()).format(Date(elapsedTime))
//            recordTime!!.text = "Time : $formattedTime"
//            handler.postDelayed(this, 1000)
//        }
//    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        ActivityCompat.requestPermissions(
            this, permissions, REQUEST_RECORD_AUDIO_PERMISSION
        )
        setContentView(R.layout.activity_voice_recording)
        // val intent = intent

//        val from = intent.getStringExtra("name11")
//
//        Log.i("response1212","name11555=="+from)

        setRegViews()

        recordTime!!.stop()

        lottimic = findViewById<LottieAnimationView>(R.id.lottieImg)
        lottimic!!.setAnimation(R.raw.record)
        startRecordingButton = findViewById<ImageView>(R.id.activity_main_record)

        stopRecordingButton = findViewById<ImageView>(R.id.activity_main_stop)
//        stopRecordingButton!!.isClickable = false

        playRecordingButton = findViewById<ImageView>(R.id.activity_main_play)
        playRecordingButton!!.setOnClickListener(View.OnClickListener { playRecording() })
        stopPlayingButton = findViewById<ImageView>(R.id.activity_main_stop_playing)
        stopPlayingButton!!.setOnClickListener(View.OnClickListener { stopPlaying() })




        startRecordingButton!!.setOnClickListener(View.OnClickListener {
            lottimic!!.loop(true)
            lottimic!!.playAnimation()

//            recImg!!.visibility=View.GONE
//            lottimic!!.visibility=View.VISIBLE
            startRecording()
            isRecording = true
            startRecordingButton!!.isClickable = false
            stopRecordingButton!!.isClickable = true


        })


        stopRecordingButton!!.setOnClickListener(View.OnClickListener {

            if (isRecording) {
                lottimic!!.pauseAnimation()

                stopRecording()
                playRecordPopUp()
                recordTime!!.visibility = View.GONE
                startRecordingButton!!.isClickable = true
                stopRecordingButton!!.isClickable = false
            }


        })
//
//        var intent=new
//        intent.putExtra("voicedata", "voicedata")
//        setResult(RECORD_PLAY!!,intent)
//        finish()


    }

    override fun onBackPressed() {
        if (player != null) {
            player!!.release()
            player = null
            stopPlayerService()


        }
        if (recorder != null) {
            stopRecording()
        }

        finish()
        super.onBackPressed()
    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        recImg = findViewById<ImageView>(R.id.recImg)
        recordTime = findViewById<Chronometer>(R.id.recordTime)


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
        recordTime!!.visibility = View.VISIBLE
        recordTime!!.setBase(SystemClock.elapsedRealtime());
        recordTime!!.stop()
        val uuid = UUID.randomUUID().toString()
        //     fileName = filesDir.path + "/" + uuid + ".3gp"
        fileName = filesDir.path + "/" + uuid + ".mp3"
        Log.i("response999", "out:=" + filesDir.path)
        Log.i(VoiceRecordingActivity::class.java.getSimpleName(), fileName!!)
        recorder = MediaRecorder()

        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder!!.setOutputFile(fileName)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            recorder!!.prepare()
            recorder!!.start()

            recordTime!!.start()
            isRecording = true

        } catch (e: IOException) {
            Log.e(
                VoiceRecordingActivity::class.java.getSimpleName() + ":startRecording()",
                "prepare() failed"
            )
        }
        //  recorder!!.start()
        startRecorderService()
    }

    private fun stopRecording() {
        if (recorder != null) {
            isRecording = false


            recordTime!!.setBase(SystemClock.elapsedRealtime());
            recordTime!!.stop()

            recorder!!.stop()
            recorder!!.release()
            recorder = null
            stopRecorderService()

            //......pop up

//            playRecordPopUp()
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun playRecordPopUp() {

        try {

            var isplaying = false
            var pausePosition = 0
            val dialog = Dialog(this@VoiceRecordingActivity)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.playrecord_popup)
            dialog.setCanceledOnTouchOutside(false);
            player1 = MediaPlayer()
            //  player1!!.setDataSource(fileName)
            // dialog.setCancelable(false);
            val playbtn: ImageView = dialog.findViewById<ImageView>(R.id.playbtn)
            val pausebtn: ImageView = dialog.findViewById<ImageView>(R.id.pausebtn)
            val lotti_play: LottieAnimationView =
                dialog.findViewById<LottieAnimationView>(R.id.lotti_play)
            val backclose: ImageView = dialog.findViewById<ImageView>(R.id.backclose)
            val re_record: Button = dialog.findViewById<Button>(R.id.re_record)
            val submitbtn: Button = dialog.findViewById<Button>(R.id.submitbtn)

            submitbtn.setOnClickListener(View.OnClickListener {

                Log.i("response999222", "fileName:=" + fileName)
                val filePath = fileName

                Log.i("wewewe", "out filePath:=" + filePath)


                val fileBytes = Files.readAllBytes(File(filePath).toPath())
                val base64String = Base64.getEncoder().encodeToString(fileBytes)
                Log.i("wewewe", "base64String " + base64String)
                Log.i("wewewe", "out base64String length:=" + base64String.length)

//                val decodedBytes = Base64.getDecoder().decode(base64String)
//                Log.i("response999222", "out decodedBytes:=" + decodedBytes)
//
//                val  tempAudio=File.createTempFile("tempAudio",null,applicationContext.cacheDir)
//                tempAudio.writeBytes(decodedBytes)
//                val mediaPlayer=MediaPlayer()
//                mediaPlayer.setDataSource(tempAudio.absolutePath)
//                mediaPlayer.prepare()
//                mediaPlayer.start()

                val audioToByteArray = audioToByteArray(filePath!!)
                val audiodata=getAudioFileData(filePath)

                if (audiodata!=null)
                {
                    Log.i("wewewe", "audiodata length:=" + audiodata.size)
                }

                val stringByte=Base64.getEncoder().encode(audioToByteArray)
               val voicedataByte2 = Base64.getDecoder().decode(stringByte)
              //  val b64 = stringByte.toByteArray().toBase64()

                Log.i("response999222", "outstring:=" + stringByte)
                Log.i("response999222", "out last:=" + voicedataByte2)
             //   Log.i("response999222", "out:=" + audioToByteArray)
//                if (audioToByteArray != null) {
//                    playByteArray(audioToByteArray)
//                }

                if (audioToByteArray != null) {


//                    dialog.dismiss()
                    voicedatabyte = audioToByteArray
                    voicedata = audioToByteArray.toString()
                    if (voicedata != null) {
                        //  val intent = Intent(this@VoiceRecordingActivity, VoiceRecordingActivity::class.java)

                        intent.putExtra("voicedata", base64String)
                        intent.putExtra("voicedatabyte", voicedatabyte)
                        setResult(RECORD_PLAY!!, intent)
                        finish()
                    }


//                     playByteArray(audioToByteArray)
                } else {
                    Log.i("response999222", "out:= error audio file")
                }

            })

            re_record.setOnClickListener(View.OnClickListener {
                dialog.dismiss()

                if (player1 != null) {
                    player1!!.release()
                    player1 = null
                    stopPlayerService()
                }

                lottimic!!.loop(true)
                lottimic!!.playAnimation()
//                lottimic!!.speed= 2F
//                isRecording=true
//            recImg!!.visibility=View.GONE
//            lottimic!!.visibility=View.VISIBLE
                startRecording()
                startRecordingButton!!.isClickable = false
                stopRecordingButton!!.isClickable = true
                startRecordingButton!!.visibility = View.GONE


            })

            backclose.setOnClickListener(View.OnClickListener {
                dialog.dismiss()
                recordTime!!.visibility = View.GONE
                stopRecordingButton!!.isClickable = false
                startRecordingButton!!.isClickable = true
                startRecordingButton!!.visibility = View.VISIBLE
//                if (player != null) {
//                    player!!.release()
//                    player = null
//                    stopPlayerService()
//
//
//                }


                if (player1 != null) {
                    player1!!.release()
                    player1 = null
                    stopPlayerService()
                }
            })

            lotti_play!!.setAnimation(R.raw.play_wave)

            playbtn.setOnClickListener(View.OnClickListener {


                if (!isplaying)
                {
                    if (pausePosition==0)
                    {
//                        lotti_play!!.loop(true)
//                        lotti_play!!.playAnimation()
                        player1 = MediaPlayer()
                        try {
                            player1!!.setDataSource(fileName)
                            player1!!.setOnCompletionListener(OnCompletionListener {
                                pausePosition=0
                                lotti_play!!.pauseAnimation()
                                isplaying = false
                                pausebtn!!.visibility = View.GONE
                                playbtn!!.visibility = View.VISIBLE

                                stopPlaying()


                            })
                            isplaying = true
                            playbtn!!.visibility = View.GONE
                            pausebtn!!.visibility = View.VISIBLE
                            // playbtn.setBackgroundResource(R.drawable.pause_button)
                            player1!!.prepare()
                            player1!!.start()
                            lotti_play!!.loop(true)
                            lotti_play!!.playAnimation()
                            startPlayerService()
                        } catch (e: IOException) {
                            Log.e(
                                VoiceRecordingActivity::class.java.getSimpleName() + ":playRecording()",
                                "prepare() failed"
                            )
                        }
                    }
                    else
                    {

//                        lotti_play!!.loop(true)
//                        lotti_play!!.playAnimation()
                        player1 = MediaPlayer()
                        try {
                            player1!!.setDataSource(fileName)

                            player1!!.setOnCompletionListener(OnCompletionListener {
                                pausePosition=0
                                lotti_play!!.pauseAnimation()
                                isplaying = false
                                pausebtn!!.visibility = View.GONE
                                playbtn!!.visibility = View.VISIBLE

                                stopPlaying()


                            })
                            isplaying = true
                            playbtn!!.visibility = View.GONE
                            pausebtn!!.visibility = View.VISIBLE
                            // playbtn.setBackgroundResource(R.drawable.pause_button)
                            player1?.prepare()
                            player1!!.start()
                            player1!!.seekTo(pausePosition)
                            lotti_play!!.loop(true)
                            lotti_play!!.playAnimation()
                            startPlayerService()
                        } catch (e: IOException) {
                            Log.e(
                                VoiceRecordingActivity::class.java.getSimpleName() + ":playRecording()",
                                "prepare() failed"
                            )
                        }
                    }
                }


















//                lotti_play!!.loop(true)
//                lotti_play!!.playAnimation()
//                player1 = MediaPlayer()
//                try {
//                    player1!!.setDataSource(fileName)
//                    player1!!.setOnCompletionListener(OnCompletionListener {
//                        lotti_play!!.pauseAnimation()
//                        isplaying = false
//                        pausebtn!!.visibility = View.GONE
//                        playbtn!!.visibility = View.VISIBLE
//
//                        stopPlaying()
//
//
//                    })
//                    isplaying = true
//                    playbtn!!.visibility = View.GONE
//                    pausebtn!!.visibility = View.VISIBLE
//                    // playbtn.setBackgroundResource(R.drawable.pause_button)
//                    player1!!.prepare()
//                    player1!!.start()
//
//                    startPlayerService()
//                } catch (e: IOException) {
//                    Log.e(
//                        VoiceRecordingActivity::class.java.getSimpleName() + ":playRecording()",
//                        "prepare() failed"
//                    )
//                }


            })

            pausebtn.setOnClickListener(View.OnClickListener {
                isplaying = false
                player1!!.pause()

                pausePosition=player1!!.currentPosition
                lotti_play!!.pauseAnimation()
                pausebtn!!.visibility = View.GONE
                playbtn!!.visibility = View.VISIBLE
            })

            dialog.show()


//            var playbtn: ImageView? = null
//            playDialog = Dialog(this)
//            playDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            playDialog!!.setContentView(R.layout.playrecord_popup)
//            //   playDialog!!.window!!.attributes.gravity = Gravity.CENTER_HORIZONTAL
//
//            val window: Window? = playDialog!!.getWindow()
//            //      window!!.setBackgroundDrawableResource(android.R.color.transparent);
//            window!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            )
//            playbtn = playDialog!!.findViewById(R.id.playbtn) as ImageView
//            lotti_play = playDialog!!.findViewById(R.id.lotti_play) as LottieAnimationView
//
//            lotti_play!!.setAnimation(R.raw.play_wave)
//
//            playbtn.setOnClickListener(View.OnClickListener {
//
//                lotti_play!!.loop(true)
//                lotti_play!!.playAnimation()
//
//                //    playRecording()
//
//                player = MediaPlayer()
//                try {
//                    player!!.setDataSource(fileName)
//                    player!!.setOnCompletionListener(OnCompletionListener {
//                        lotti_play!!.pauseAnimation()
//                        stopPlaying()
//
//
//                    })
//                    player!!.prepare()
//                    player!!.start()
//
//                    startPlayerService()
//                } catch (e: IOException) {
//                    Log.e(
//                        VoiceRecordingActivity::class.java.getSimpleName() + ":playRecording()",
//                        "prepare() failed"
//                    )
//                }
//            })
//
//            playDialog!!.show()
        } catch (e: Exception) {

            e.printStackTrace()
        }

    }

    private fun getAudioFileData(filePath: String): ByteArray? {

        val file=File(filePath)
        if (!file.exists())
        {
            return null
        }
        try {
            val inputStream =FileInputStream(file)
            val fileLength =file.length().toInt()
            val audioData =ByteArray(fileLength)

            inputStream.read(audioData)
            inputStream.close()
            return  audioData
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }
        return  null
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

//        Log.i("response999222","fileName:="+fileName)
//        val filePath =fileName
//        val audioToByteArray = audioToByteArray(filePath!!)
//
//        if (audioToByteArray !=null)
//        {
//          //  convert11(audioToByteArray,fileName)
//            Log.i("response999222","out:="+audioToByteArray.toString())
//            playByteArray(audioToByteArray)
//        }
//        else
//        {
//            Log.i("response999222","out:= error audio file")
//        }


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

    fun audioTobase64(filepath: String) :String?
    {
        val audioFile =File(filepath)
        if (!audioFile.exists()) {
            return null
        }

        try {
            val inputStream =FileInputStream(audioFile)
            val byte=ByteArray(audioFile.length().toInt())
            inputStream.read(byte)
            inputStream.close()
            return  Base64.getEncoder().encodeToString(byte)
        }
        catch (e:IOException)
        {
            e.printStackTrace()
        }

        return null
    }

    fun audioToByteArray(filepath: String): ByteArray? {
        val file = File(filepath)

        if (!file.exists()) {
            return null
        }

        val fileSize = file.length().toInt()
        val buffer = ByteArray(fileSize)

        try {
            val inputStream = FileInputStream(file)
            inputStream.read(buffer)
            inputStream.close()
        } catch (e: IOException) {
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

    fun byteToAudioFile(bytearray: ByteArray, outputPath: String) {
        try {
            val outputStream = FileInputStream(outputPath)


            outputStream.close()

        } catch (e: IOException) {
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

    override fun onClick(v: View) {
        when (v.id) {

            R.id.imback -> {
                if (player != null) {
                    player!!.release()
                    player = null
                    stopPlayerService()
                }
                finish()
            }
//            R.id.activity_main_record ->
//            {
//                lottimic!!.loop(true)
//                lottimic!!.playAnimation()
//                startRecording()
//              }


        }
    }
}