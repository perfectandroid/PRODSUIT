package com.perfect.prodsuit.View.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ChatAdapter1
import java.sql.Date
import java.text.SimpleDateFormat

class ChatActivity1 : AppCompatActivity() , View.OnClickListener{

    var active = false
    var TAG : String = "ChatActivity1"
    private lateinit var dataChatRef : DatabaseReference
    private var name:String?=""
    private var mobile:String?=""
    private var senderID:String?=""
    private var ReceiverID:String?=""
    private var chatKey = ""
    private var BranchName = ""
    private var userMobile:String?=""

    var txt_name : TextView? = null
    var edt_message : EditText? = null
    var img_back : ImageView? = null
    var img_send : ImageView? = null

    private var sendMessage:String?=""
    var recycChatList: RecyclerView? = null
    var chatAdapter1: ChatAdapter1? = null
    var modelCount = 0

    var db : DBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chat1)

        dataChatRef = FirebaseDatabase.getInstance().getReference("Chat")
        db = DBHelper(this, null)

        if (getIntent().hasExtra("name")) {
            name = intent.getStringExtra("name")
        }
        if (getIntent().hasExtra("mobile")) {
            mobile = intent.getStringExtra("mobile")
            Log.e(TAG,"77777771    "+mobile)
        }
        if (getIntent().hasExtra("senderID")) {
            senderID = intent.getStringExtra("senderID")
            Log.e(TAG,"77777772    "+senderID)
        }
        if (getIntent().hasExtra("ReceiverID")) {
            ReceiverID = intent.getStringExtra("ReceiverID")
            Log.e(TAG,"77777773    "+ReceiverID)
        }

        if (getIntent().hasExtra("chatKey")) {
            chatKey = intent.getStringExtra("chatKey").toString()
            Log.e(TAG,"77777774    "+chatKey)
        }
        if (getIntent().hasExtra("BranchName")) {
            BranchName = intent.getStringExtra("BranchName").toString()
            Log.e(TAG,"77777775    "+BranchName)
        }



        var timestampSP = applicationContext.getSharedPreferences(Config.SHARED_PREF70, 0)
        var timestampEditer = timestampSP.edit()
        timestampEditer.putString("timestamp","")
        timestampEditer.commit()

        initialization()
        txt_name!!.text = name
        userMobile = applicationContext.getSharedPreferences(Config.SHARED_PREF69, 0).getString("Fbase_Mobile","")

        modelCount = 0
        // if (isIpen){
        val lLayout = GridLayoutManager(this@ChatActivity1, 1)
        recycChatList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
        chatAdapter1 = ChatAdapter1(this@ChatActivity1,userMobile!!)
        recycChatList!!.adapter = chatAdapter1




        Log.e(TAG,"104444443   "+chatKey+"  :  ")
        // val keyReference1 = dataChatRef.child(chatKey)
        dataChatRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                Log.e(TAG,"104444444   "+chatKey+"  :  "+dataSnapshot.child(chatKey).key)
                //if (chatModel.size>0){
                if (active){

                    for (appleSnapshot in dataSnapshot.child(chatKey).child("message").children) {
                        Log.e(TAG,"12555555  json       ")
                        Log.e(TAG,"12555555  json       "+ appleSnapshot.key)
                        if (appleSnapshot.hasChild("mobile") && appleSnapshot.hasChild("msg")){

                            var msgTimeStamp = appleSnapshot.key
                            var getMobile = appleSnapshot.child("mobile").value
                            var getMsg = appleSnapshot.child("msg").value

                            Log.e(TAG,"13666666     "+msgTimeStamp+"  :  "+getMobile+"  :  "+getMsg)

                            // val timeStamp = msgTimeStamp!.toInt()
                            val date = Date(msgTimeStamp!!.toLong())

                            Log.e(TAG,"1234567890     "+date)
                            val sdfDate = SimpleDateFormat("dd-MM-yyyy")
                            val sdfTime = SimpleDateFormat("HH:mm")

                            var formatedDate = sdfDate.format(date)
                            var formatedTime = changeTimeformate(sdfTime.format(date).toString())
                            Log.e(TAG,"123456789022     "+formatedDate+"  :  "+formatedTime)

                            if (chatAdapter1!!.itemCount == 0){
                                var timestampSP = applicationContext.getSharedPreferences(Config.SHARED_PREF70, 0)
                                var timestampEditer = timestampSP.edit()
                                timestampEditer.putString("timestamp",msgTimeStamp)
                                timestampEditer.commit()

                                chatAdapter1!!.addChat(getMobile!!.toString(),getMsg!!.toString(),formatedDate,formatedTime)
                                recycChatList!!.smoothScrollToPosition(chatAdapter1!!.itemCount - 1)
                            }else{
                                if (msgTimeStamp!!.toLong()  > timestampSP.getString("timestamp","")!!.toLong() ){


                                    var timestampSP = applicationContext.getSharedPreferences(Config.SHARED_PREF70, 0)
                                    var timestampEditer = timestampSP.edit()
                                    timestampEditer.putString("timestamp",msgTimeStamp)
                                    timestampEditer.commit()

                                    chatAdapter1!!.addChat(getMobile!!.toString(),getMsg!!.toString(),formatedDate,formatedTime)
                                    recycChatList!!.smoothScrollToPosition(chatAdapter1!!.itemCount - 1)
                                }
                            }


                        }


                    }
                }

            }


            override fun onCancelled(databaseError: DatabaseError) {

            }
        })

    }

    private fun changeTimeformate(formatedTime: String): String {

        var result = ""
        try {
            var hr = (formatedTime.substringBefore(":")).toInt()
            var minute = (formatedTime.substringAfter(":"))

            Log.e(TAG,"  1800001   "+minute.toString().length)
            if (minute.length == 1){
                minute = "0"+minute
                Log.e(TAG,"  1800002   "+minute)
            }
            Log.e(TAG,"  1800003   "+minute)
            if (hr > 12){
                var hrs =(hr - 12).toString()
                result = hrs+":"+minute+" PM"
            }else{
                result = hr.toString()+":"+minute+" AM"
            }
        }
        catch (e : Exception){
            Log.e(TAG,"  168888   "+e.toString())
        }

        return result
    }

    private fun initialization() {

        txt_name = findViewById(R.id.txt_name)
        edt_message = findViewById(R.id.edt_message)
        img_back = findViewById(R.id.img_back)
        img_send = findViewById(R.id.img_send)

        recycChatList = findViewById(R.id.recycChatList) as RecyclerView

        img_back!!.setOnClickListener(this)
        img_send!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.img_back->{
                finish()
            }

            R.id.img_send->{
                sendMessage = edt_message!!.text.toString()
                if (sendMessage.equals("")){

                }
                else{

                    if (chatKey.equals("")){
                        var isGnerated = genreateChatKey()
                        if (isGnerated){
                            // sendMessageToFirebase()
                        }
                    }else{
                        sendMessageToFirebase()
                    }

                }
            }
        }
    }

    private fun genreateChatKey(): Boolean  {

        var isGenerated = false
        var generateKey = 1001
        chatKey = generateKey.toString()
        Log.e(TAG,"189999991      json       ")
        dataChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    Log.e(TAG,"1899999920  json       "+dataSnapshot.childrenCount)
                    var ch = ((dataSnapshot.childrenCount).toInt())+generateKey
                    chatKey = ch.toString()
                    Log.e(TAG,"1899999921  json       "+chatKey)
                    isGenerated = true
                    sendMessageToFirebase()
                }else{
                    Log.e(TAG,"189999993  json       "+generateKey)
                    sendMessageToFirebase()
                }

            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG,"189999994  json       ")
            }
        })

        Log.e(TAG,"189999995      chatKey       "+chatKey)
//        sendMessageToFirebase()

        return isGenerated
    }

    private fun sendMessageToFirebase() {
        //                    chatKey = "2"
        edt_message!!.setText("")
        val messageId = dataChatRef.push().key
        Log.e(TAG,"messageId  97777   "+chatKey)
        if (senderID != null && messageId != null) {
            val currentTimeMillis = System.currentTimeMillis().toString()
            val Fbase_MobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF69, 0)
//                        val chatMessage = ChatMessage(mobileSP.getString("mobile",""), mobile,sendMessage,currentTimeMillis.toInt())
//                      //  dataChatRef.child(chatKey.toString()).setValue(chatMessage)
//                        dataChatRef.child(messageId).setValue(chatMessage)

            var logUser = Fbase_MobileSP.getString("Fbase_Mobile","")
            db!!.addFirebaseChatUser(name!!,BranchName,logUser!!,mobile!!,chatKey,senderID!!)
            dataChatRef.child(chatKey.toString()).child("user_1").setValue(Fbase_MobileSP.getString("Fbase_Mobile",""))
            dataChatRef.child(chatKey.toString()).child("user_2").setValue(mobile)
            dataChatRef.child(chatKey.toString()).child("message").child(currentTimeMillis).child("msg").setValue(sendMessage)
            dataChatRef.child(chatKey.toString()).child("message").child(currentTimeMillis).child("mobile").setValue(Fbase_MobileSP.getString("Fbase_Mobile",""))
        }
    }

    override fun onStart() {
        super.onStart();
        active = true
    }

    override fun onStop() {
        super.onStop()
        active = false
    }

    override fun onRestart() {
        super.onRestart()
        active = true
    }
    fun isKeyChecking(context: Context): Boolean {

        var result = false

        dataChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                var getChatCounts = snapshot.childrenCount
                Log.e(TAG,"10444444222   "+getChatCounts)
                for (dataSnapshot1 in snapshot.children) {
                    var getKey = dataSnapshot1.key
                    var getUserOne = dataSnapshot1.child("user_1").value
                    var getUserTwo = dataSnapshot1.child("user_2").value

                    if ((getUserOne!!.equals(mobile) && getUserTwo!!.equals(userMobile)) || (getUserOne!!.equals(userMobile) && getUserTwo!!.equals(mobile))){

                        chatKey = getKey.toString()
                        Log.e(TAG,"104444441   "+getKey+"  :  "+getUserOne+"  :  "+getUserTwo)
                        result = true
                    }
                    else{
                        Log.e(TAG,"104444442   "+getKey+"  :  "+getUserOne+"  :  "+getUserTwo)
                    }

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return result

    }



}