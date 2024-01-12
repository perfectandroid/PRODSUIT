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
import com.perfect.prodsuit.Api.ApiInterface
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ChatAdapter1
import com.perfect.prodsuit.fire.FcmMessage
import com.perfect.prodsuit.fire.NotificationPayload
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Date
import java.text.SimpleDateFormat
import retrofit2.Response


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


                val currentTimestamp: Long = System.currentTimeMillis()

                // Calculate the timestamp for 2 hours ago
                val twoHoursAgoTimestamp: Long = currentTimestamp - (2 * 60 * 60 * 1000)

                Log.e(TAG,"10000000  json       "+ twoHoursAgoTimestamp)


//                // Delete data
//                if (dataSnapshot.exists()) {
//                    for (appleSnapshot in dataSnapshot.child(chatKey).child("message").children) {
//                        Log.e(TAG,"10000000  json       "+ appleSnapshot.key)
//
//                        if (appleSnapshot.key == "1704891987196") {
//                            appleSnapshot.ref.removeValue().addOnSuccessListener {
//                                // Handle successful deletion
//                                Log.e(TAG,"100000001  Success       "+ appleSnapshot.key)
//                            }
//                                .addOnFailureListener {
//                                    // Handle failure
//                                    Log.e(TAG,"100000002  failure       "+ appleSnapshot.key)
//                                }
//                        }
//
//                    }
//                }

                Log.e(TAG,"104444444   "+chatKey+"  :  "+dataSnapshot.child(chatKey).key)
//                if (chatKey.equals("")){
//
//                    if (dataSnapshot.exists()) {
//
//                        for (dataSnapshot1 in dataSnapshot.children) {
//                            var getUserOne = dataSnapshot1.child("user_1").value
//                            var getUserTwo = dataSnapshot1.child("user_2").value
//
//                            Log.e(TAG,"2499991010  json       "+getUserOne+"  :  "+getUserTwo)
//                            Log.e(TAG,"2499991011  json       "+senderID+"  :  "+ReceiverID)
//                            Log.e(TAG,"24999911  json       "+dataSnapshot1.key)
//
//                            if ((senderID!!.equals(getUserOne) && ReceiverID!!.equals(getUserTwo)) || (senderID!!.equals(getUserTwo) && ReceiverID!!.equals(getUserOne))){
//                                Log.e(TAG,"24999912     "+ dataSnapshot1.key)
//                                chatKey = dataSnapshot.key.toString()
//                                break
//                            }
//                        }
//                    }
//
//                }

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
                  //  chatKey = "1001"
                    Log.e(TAG,"22666   "+chatKey)
                    if (chatKey.equals("")){
                        var isGnerated = genreateChatKey()
                        if (isGnerated){
                            // sendMessageToFirebase()
                        }
                    }else{
                        sendMessageToFirebase()
                    }

                  //  generateKey()

                }
            }
        }
    }

    private fun generateKey() {

        dataChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
                    Log.e(TAG,"2499991  json       "+dataSnapshot.childrenCount)
                    for (dataSnapshot1 in dataSnapshot.children) {

                        var getUserOne = dataSnapshot1.child("user_1").value
                        var getUserTwo = dataSnapshot1.child("user_2").value

                        Log.e(TAG,"24999910  json       "+getUserOne+"  :  "+getUserTwo)
                        Log.e(TAG,"24999911  json       "+dataSnapshot1.key)

                        if ((senderID!!.equals(getUserOne) && ReceiverID!!.equals(getUserTwo)) || (senderID!!.equals(getUserTwo) && ReceiverID!!.equals(getUserOne))){

                            break
                        }
                    }

                }else{
                    Log.e(TAG,"2499992  json       ")
                 //   sendMessageToFirebase()
                }

            }


            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG,"189999994  json       ")
            }
        })
    }

    private fun genreateChatKey(): Boolean  {

        var isGenerated = false
        var generateKey = 1001
      //  chatKey = generateKey.toString()
        Log.e(TAG,"189999991      json       ")
        dataChatRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                if (dataSnapshot.exists()) {
//                    Log.e(TAG,"1899999920  json       "+dataSnapshot.childrenCount)
//                    var ch = ((dataSnapshot.childrenCount).toInt())+generateKey
//                    chatKey = ch.toString()
//                    Log.e(TAG,"1899999921  json       "+chatKey)
//                    isGenerated = true
//                    sendMessageToFirebase()

                    Log.e(TAG,"2499991  json       "+dataSnapshot.childrenCount)
                    for (dataSnapshot1 in dataSnapshot.children) {

                        var getUserOne = dataSnapshot1.child("user_1").value
                        var getUserTwo = dataSnapshot1.child("user_2").value

                        Log.e(TAG,"2499991010  json       "+getUserOne+"  :  "+getUserTwo)
                        Log.e(TAG,"2499991011  json       "+senderID+"  :  "+ReceiverID)
                        Log.e(TAG,"24999911  json       "+dataSnapshot1.key)

                        if ((senderID!!.equals(getUserOne) && ReceiverID!!.equals(getUserTwo)) || (senderID!!.equals(getUserTwo) && ReceiverID!!.equals(getUserOne))){
                            Log.e(TAG,"24999912     "+ dataSnapshot1.key)
                            chatKey = dataSnapshot1.key.toString()
                            break
                        }
                    }

                    if (chatKey.equals("")){
                        Log.e(TAG,"24999913  json       "+chatKey)
                        var ch = ((dataSnapshot.childrenCount).toInt())+generateKey
                        chatKey = ch.toString()
                    }
                    Log.e(TAG,"24999914  json       "+chatKey)
                    isGenerated = true
//                    sendMessageToFirebase()

                }else{
                    Log.e(TAG,"24999915  json       "+generateKey)
                    chatKey = generateKey.toString()
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
            dataChatRef.child(chatKey.toString()).child("message").child(currentTimeMillis).child("read").setValue("0")
            dataChatRef.child(chatKey.toString()).child("message").child(currentTimeMillis).child("mobile").setValue(Fbase_MobileSP.getString("Fbase_Mobile",""))

            sendFcmMessage()
        }
    }

    private fun sendFcmMessage() {
       try {
           val retrofit = Retrofit.Builder()
               .baseUrl("https://fcm.googleapis.com/") // FCM server base URL
               .addConverterFactory(GsonConverterFactory.create())
               .build()

           val fcmApiService = retrofit.create(ApiInterface::class.java)
           val notification = JSONObject()
           val notifcationBody = JSONObject()
           try {
               notifcationBody.put("title", "Enter_title")
               notifcationBody.put("message", "Message")   //Enter your notification message
               notification.put("to", "topic")
               notification.put("data", notifcationBody)
               Log.e("TAG", "try")
           } catch (e: JSONException) {
               Log.e("TAG", "onCreate: " + e.message)
           }

           val deviceToken = "c0DyBRi6Sm2C9TPleVAq3B:APA91bHIA47fa_dVWPMGnmSmoX8aFeIt1O6XesNVxp-yNRBOWxEWZ1upQB4XP9yuYD8Ejw43Qex3flNrQsN8xnRpvuueS_vJ1mTRlJw-Z7myLbJH4M_lF1Q7DJYV0UiGjS3wRCL3fR1J"
           val fcmMessage = FcmMessage(
               to = deviceToken,
               data = mapOf("key1" to "value1", "key2" to "value2"),
               notification = NotificationPayload(title = "Your Title", body = "Your Message")
           )

           Log.e(TAG,"fcmMessage 444477771    "+fcmMessage)

           val call: Call<Void> = fcmApiService.sendFcmMessage(fcmMessage)

           call.enqueue(object : Callback<Void> {
               override fun onResponse(call: Call<Void>, response: Response<Void>) {
                   if (response.isSuccessful) {
                       // Successfully sent FCM message
                       println("FCM message sent successfully")
                       Log.e(TAG,"  44447777   FCM message sent successfully")
                   } else {
                       // Handle error
                       println("Failed to send FCM message. Error: ${response.errorBody()?.string()}")
                       Log.e(TAG,"  44447777   Failed to send FCM message. Error: ${response.errorBody()?.string()}")
                   }
               }

               override fun onFailure(call: Call<Void>, t: Throwable) {
                   // Handle failure
                   println("Failed to send FCM message. Exception: ${t.message}")
               }
           })
       }
       catch (e:Exception){

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