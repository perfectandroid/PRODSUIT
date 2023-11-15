package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.Charset

class LeadRequestAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var voiceDataString: String? = ""
    var voiceCheck: String? = ""
    internal val TAG : String = "LeadRequestAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    private var clickListener1: ItemClickListenerValue? = null
    private var voicedataByte : Byte? =null
    private var voicedataByte1 : ByteArray? =null
    private var voicedataString : String? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_lead_request, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.txtName.text         = jsonObject!!.getString("Customer")
                holder.txtDepartment.text         = jsonObject!!.getString("Mobile")
                holder.txtDescription.text         = jsonObject!!.getString("DESCRIPTION")
                holder.txtAssignedDate.text         = jsonObject!!.getString("AssignedDate")

//                voiceCheck=jsonObject!!.getString("blnVoiceData")
//                if (voiceCheck.equals("1")){
//
//                    holder.llVoice!!.visibility=View.VISIBLE
//                }else{
//                    holder.llVoice!!.visibility=View.GONE
//                }


         //       voicedataString="AAAAGGZ0eXAzZ3A0AAAAAGlzb20zZ3A0AAAERm1vb3YAAABsbXZoZAAAAADhNyBL4TcgSwAAA+gAAAiYAAEAAAEAAAAAAAAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAIAAAB1bWV0YQAAACFoZGxyAAAAAAAAAABtZHRhAAAAAAAAAAAAAAAAAAAAACtrZXlzAAAAAAAAAAEAAAAbbWR0YWNvbS5hbmRyb2lkLnZlcnNpb24AAAAhaWxzdAAAABkAAAABAAAAEWRhdGEAAAABAAAAADkAAANddHJhawAAAFx0a2hkAAAAB+E3IEvhNyBLAAAAAQAAAAAAAAiYAAAAAAAAAAAAAAAAAQAAAAABAAAAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAQAAAAAAAAAAAAAAAAAAC+W1kaWEAAAAgbWRoZAAAAADhNyBL4TcgSwAAH0AAAETAAAAAAAAAACxoZGxyAAAAAAAAAABzb3VuAAAAAAAAAAAAAAAAU291bmRIYW5kbGUAAAACpW1pbmYAAAAQc21oZAAAAAAAAAAAAAAAJGRpbmYAAAAcZHJlZgAAAAAAAAABAAAADHVybCAAAAABAAACaXN0YmwAAABFc3RzZAAAAAAAAAABAAAANXNhbXIAAAAAAAAAAQAAAAAAAAAAAAEAEAAAAAAfQAAAAAAAEWRhbXIgICAAAIP/AAEAAAAgc3R0cwAAAAAAAAACAAAAAQAAAKAAAABtAAAAoAAAAcxzdHN6AAAAAAAAAAAAAABuAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAgAAAAIAAAACAAAAAcc3RzYwAAAAAAAAABAAAAAQAAAG4AAAABAAAAFHN0Y28AAAAAAAAAAQAADJcAAAgxZnJlZQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"



              //  voiceData2 = Base64.getEncoder().encodeToString(voicedataByte)

//               voicedataByte=voicedataString!!.toByteArray()
//                playByteArray(voicedataByte!!)
                holder.voiceData!!.setTag(position)
                holder.voiceData!!.setOnClickListener(View.OnClickListener {

                    voiceDataString=jsonObject!!.getString("VoiceData")
                  //  voicedataByte1=voicedataByte.toByteArray()

                  //  playString(voiceDataString)
                    Log.i("wewewe", "value string legth===="+voiceDataString!!.length)
                //    Log.i("weee", "value string adapter byte="+jsonObject!!.getString("VoiceData").toByteArray())

                    clickListener1!!.onClick(position, "LeadrequestVoiceClick",voiceDataString!!)

                  //  playByteArray(voicedataByte!!)

                })

                holder.llRequest!!.setTag(position)
                holder.llRequest!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(
                        position,
                        "LeadrequestClick"
                    )
                })

            }


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }

    }



    private fun playByteArray(voicedataByte: ByteArray) {

        try {
            val Mytemp = File.createTempFile("TCL", "mp3", context.cacheDir)
            Mytemp.deleteOnExit()
            val fos = FileOutputStream(Mytemp)
            fos.write(voicedataByte)
            fos.close()
//          var mediaPlayer: MediaPlayer? = null
//
//            mediaPlayer = MediaPlayer.create(applicationContext, Uri.fromFile(Mytemp));
//            mediaPlayer.start();

            val mediaplayer = MediaPlayer.create(context, Uri.fromFile(Mytemp))
            mediaplayer.start()


//            val url = "/data/user/0/com.perfect.prodsuite/files/04317026-78c4-459d-8f98-c3b334ff2711.mp3"
//            val mediaPlayer = MediaPlayer()
//            mediaPlayer.setDataSource(url)
//            mediaPlayer.prepare()
//            mediaPlayer.start()


//            val MyFile = FileInputStream(Mytemp)
//            mediaPlayer.setDataSource(Mytemp.getf)
//            mediaPlayer.prepare()
//            mediaPlayer.start()
        } catch (ex: IOException) {
            val s = ex.toString()
            ex.printStackTrace()
        }

    }

//    private fun playByteArray(mp3SoundByteArray: ByteArray) {
//        try {
//            val Mytemp = File.createTempFile("TCL", "mp3", cacheDir)
//            Mytemp.deleteOnExit()
//            val fos = FileOutputStream(Mytemp)
//            fos.write(mp3SoundByteArray)
//            fos.close()
//            //    var mediaPlayer: MediaPlayer? = null
//            var mediaPlayer = MediaPlayer()
//            mediaPlayer = MediaPlayer.create(context, Uri.fromFile(Mytemp));
//            mediaPlayer.start();
//
//
////            val MyFile = FileInputStream(Mytemp)
////            mediaPlayer.setDataSource(Mytemp.getf)
////            mediaPlayer.prepare()
////            mediaPlayer.start()
//        } catch (ex: IOException) {
//            val s = ex.toString()
//            ex.printStackTrace()
//        }
//    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtName         : TextView
        internal var txtDepartment        : TextView
        internal var txtDescription        : TextView
        internal var txtAssignedDate        : TextView
        internal var llRequest    : LinearLayout
//        internal var llVoice    : LinearLayout
        internal var voiceData        : TextView
        init {
            txtName         = v.findViewById<View>(R.id.txtName) as TextView
            txtDepartment        = v.findViewById<View>(R.id.txtDepartment) as TextView
            txtDescription        = v.findViewById<View>(R.id.txtDescription) as TextView
            txtAssignedDate        = v.findViewById<View>(R.id.txtAssignedDate) as TextView
            voiceData        = v.findViewById<View>(R.id.voiceData) as TextView

            llRequest    = v.findViewById<View>(R.id.llRequest) as LinearLayout
//            llVoice    = v.findViewById<View>(R.id.llVoice) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
    fun setClickListener1(itemClickListener: ItemClickListenerValue?) {
        clickListener1 = itemClickListener
    }

}