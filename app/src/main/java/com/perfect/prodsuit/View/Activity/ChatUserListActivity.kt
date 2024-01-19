package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.gson.Gson
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ChatAllUserList
import com.perfect.prodsuit.Model.ChatMessageList
import com.perfect.prodsuit.Model.ChatRegisterUsersModel
import com.perfect.prodsuit.Model.ModelProjectCheckList
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ChatAllUserAdapter
import com.perfect.prodsuit.View.Adapter.ChatUserAdapter
import com.perfect.prodsuit.View.Adapter.EmployeeAdapter
import com.perfect.prodsuit.Viewmodel.AttanceMarkingUpdateViewModel
import com.perfect.prodsuit.Viewmodel.ChatRegisterUserListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ChatUserListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var TAG : String = "ChatUserListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var name :  String? = ""
    var mobile :  String? = ""
    var chatKey :  String? = ""
    var senderID :  String? = ""

    var db : DBHelper? = null
  //  private lateinit var databaseRef : DatabaseReference
    private lateinit var databaseRef1 : DatabaseReference
    var chatAllUserList =  mutableListOf<ChatAllUserList>()
    var messageLists =  mutableListOf<ChatMessageList>()
    private var dialogUserSheet : Dialog? = null

    private var recycchatUser : RecyclerView? = null

    internal var imgUserlist: ImageView? = null
    var userArray: JSONArray? = null

    lateinit var chatRegisterUserListViewModel: ChatRegisterUserListViewModel
    var chatRegisterCount = 0
    var chatRegisterUsersModel = ArrayList<ChatRegisterUsersModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chat_user_list)
        context = this@ChatUserListActivity

        chatRegisterUserListViewModel = ViewModelProvider(this).get(ChatRegisterUserListViewModel::class.java)

        setRegViews()

        db = DBHelper(this, null)
        name = intent.getStringExtra("name")
        mobile = intent.getStringExtra("mobile")

        Log.e(TAG,"83333   "+name+"  :  "+mobile)

       // databaseRef = FirebaseDatabase.getInstance().getReference("User")
        databaseRef1 = FirebaseDatabase.getInstance().getReference("Chat")

       // getAllUserListPopup()

        var MobileNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF4, 0)
        senderID = MobileNumberSP.getString("MobileNumber", "")

        getChatUser()
        deleteChatList()

    }

    private fun deleteChatList() {

        val currentTimestamp: Long = System.currentTimeMillis()
        val fiveDayTimestamp: Long = currentTimestamp - (5 * 24 * 60 * 60 * 1000)

        databaseRef1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


            }

            override fun onCancelled(databaseError: DatabaseError) {
                //  progressDialog!!.dismiss()
            }
        })
    }

    private fun getChatUser() {
        userArray = JSONArray()
        userArray = db!!.getChatUserData()
        Log.e(TAG,"633333  userArray  : "+userArray)

        if (userArray!!.length() >0){
            val lLayout = GridLayoutManager(this@ChatUserListActivity, 1)
            recycchatUser!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ChatUserAdapter(this@ChatUserListActivity, userArray!!)
            recycchatUser!!.adapter = adapter
            adapter!!.setClickListener(this@ChatUserListActivity)
        }
    }


    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imgUserlist = findViewById<ImageView>(R.id.imgUserlist)
        recycchatUser = findViewById<RecyclerView>(R.id.recycchatUser)

        imback!!.setOnClickListener(this)
        imgUserlist!!.setOnClickListener(this)

    }

    private fun getAllUserListPopup() {

       // var query = databaseRef.orderByChild("CompanyCategory").equalTo("8")
//        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//
//                try {
//                    Log.e(TAG,"966  "+dataSnapshot.childrenCount)
//                    db!!.deleteFirebaseUser()
//                    messageLists.clear()
//                    for (data in dataSnapshot.children) {
//                        val user = data.getValue(ChatAllUserList::class.java)
//                        if (user != null && user.mobile != mobile){
//
//                            var lastmessage = ""
//                            var unseenMessage = 0
//
//                            chatAllUserList.add(user)
//                            var chatUserKey = ""
////                            databaseRef1.addListenerForSingleValueEvent(object : ValueEventListener {
////                                override fun onDataChange(snapshot: DataSnapshot) {
////
////                                    var getChatCounts = snapshot.childrenCount
////                                    if (getChatCounts > 0){
////
////                                        for (dataSnapshot1 in snapshot.children) {
////                                            var getKey = dataSnapshot1.key
////                                            chatKey = getKey
////                                            var getUserOne = dataSnapshot1.child("user_1").value
////                                            var getUserTwo = dataSnapshot1.child("user_2").value
////
////
////
////                                            if ((getUserOne!!.equals(user.mobile) && getUserTwo!!.equals(mobile)) || (getUserOne!!.equals(mobile) && getUserTwo!!.equals(user.mobile))){
////                                                Log.e(TAG,"7777771   "+chatKey+"  :  "+getUserOne+"  :  "+getUserTwo)
////                                                chatUserKey = chatKey.toString()
////                                                db!!.addFirebaseUser(user.name.toString(),user.BranchName.toString(),getUserOne!!.toString(),getUserTwo!!.toString(),chatUserKey)
////                                            }
////
////                                        }
////
////
////                                    }
////                                }
////
////                                override fun onCancelled(error: DatabaseError) {
////
////                                }
////
////                            })
//                            Log.e(TAG,"7777773   "+chatUserKey+"  :  "+mobile+"  :  "+user.mobile+"   :  "+messageLists.size)
//
//
//                            db!!.addFirebaseUser(user.name.toString(),user.BranchName.toString(),mobile!!.toString(),user.mobile.toString(),"")
//                            messageLists.add(ChatMessageList(user.name,user.mobile,lastmessage,unseenMessage,chatUserKey!!,user.BranchName))
//                        }
//                        else{
//                            senderID = data.key
//                            Log.e(TAG,"senderID  6444   "+senderID)
//                        }
//                    }
//
//                    if (messageLists.size > 0){
//                        for (i in 0 until messageLists.size) {
//                            Log.e(TAG,"10555  "+messageLists[i].mobile+"  : "+messageLists[i].name+"  : "+messageLists[i].BranchName+"  : "+messageLists[i].chatUserKey)
//
//                        }
//                    }
//
////                    if (messageLists.size > 0){
////                        userListPopup(messageLists)
////                    }
//
//                }catch (e: Exception){
//                    Log.e(TAG,"16444   "+e.toString())
//                }
//
//
//
////                if (messageLists.size > 0){
////                    val lLayout = GridLayoutManager(this@UserListActivity, 1)
////                    recycUserList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////                    val adapter = UserListAdapter(this@UserListActivity, messageLists)
////                    recycUserList!!.adapter = adapter
////                    adapter!!.setClickListener(this@UserListActivity)
////                }
//
//               // progressDialog!!.dismiss()
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//              //  progressDialog!!.dismiss()
//            }
//        })
    }

    private fun userListPopup(chatRegisterUsersModel: MutableList<ChatRegisterUsersModel>) {
        try {
            dialogUserSheet = Dialog(this)
            dialogUserSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogUserSheet!! .setContentView(R.layout.chat_alluser_popup)
            dialogUserSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogUserSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            var recycAllUserList = dialogUserSheet!! .findViewById(R.id.recycAllUserList) as RecyclerView
            val etsearch = dialogUserSheet!!.findViewById(R.id.etsearch) as EditText

            var lastmessage = ""
            var unseenMessage = 0
            messageLists.clear()
            var chatUserKey = ""
            for (i in 0 until chatRegisterUsersModel.size) {
                messageLists.add(ChatMessageList(chatRegisterUsersModel[i].EmployeeName,chatRegisterUsersModel[i].PhoneNo,lastmessage,unseenMessage,chatUserKey
                    ,chatRegisterUsersModel[i].BranchName,chatRegisterUsersModel[i].FK_Branch,chatRegisterUsersModel[i].PnUserToken))
            }


            val lLayout = GridLayoutManager(this@ChatUserListActivity, 1)
            recycAllUserList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ChatAllUserAdapter(this@ChatUserListActivity, messageLists)
            recycAllUserList!!.adapter = adapter
            adapter!!.setClickListener(this@ChatUserListActivity)

            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    messageLists.clear()

                    for (k in 0 until chatRegisterUsersModel.size) {
                        var jsonObject = chatRegisterUsersModel[k]
                      //  if (textlength <= jsonObject.EmployeeName) {
                            if (jsonObject.EmployeeName!!.toLowerCase().trim()
                                    .contains(etsearch!!.text.toString().toLowerCase().trim())
                            ) {
                                messageLists.add(ChatMessageList(chatRegisterUsersModel[k].EmployeeName,chatRegisterUsersModel[k].PhoneNo,lastmessage,unseenMessage,chatRegisterUsersModel[k].PnUserToken!!,chatRegisterUsersModel[k].EmployeeName))
                            }

                        }



                        val adapter = ChatAllUserAdapter(this@ChatUserListActivity, messageLists)
                        recycAllUserList!!.adapter = adapter
                        recycAllUserList!!.adapter = adapter
                        adapter!!.setClickListener(this@ChatUserListActivity)

                    }


            })

//            val lLayout = GridLayoutManager(this@CommonSearchActivity, 1)
//            recycModule!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            searchModuleAdapter = SearchModuleAdapter(this@CommonSearchActivity, searchModuleArrayList)
//            recycModule!!.adapter = searchModuleAdapter
//            searchModuleAdapter.setClickListener(this@CommonSearchActivity)

            dialogUserSheet!!.show()

        }catch (e : Exception){

        }
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.imgUserlist->{
               // getAllUserListPopup()
                chatRegisterCount = 0
                getChatRegisterUsers()
            }
        }
    }

    private fun getChatRegisterUsers() {
        var Reqmode = "130"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                chatRegisterUserListViewModel.getChatRegisterUser(this, Reqmode!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (chatRegisterCount == 0) {
                                    chatRegisterCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   29000   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("UserDetails")
                                        var lastmessage = ""
                                        var unseenMessage = 0
                                        var arrayList = jobjt.getJSONArray("UserList")
                                        val gson = Gson()
                                        chatRegisterUsersModel = gson.fromJson(arrayList.toString(),Array<ChatRegisterUsersModel>::class.java).toList() as ArrayList<ChatRegisterUsersModel>
//                                        messageLists.clear()
//                                        for (i in 0 until chatRegisterUsersModel.size) {
//                                            messageLists.add(ChatMessageList(chatRegisterUsersModel[i].EmployeeName,chatRegisterUsersModel[i].PhoneNo,lastmessage,unseenMessage,chatRegisterUsersModel[i].PnUserToken!!,chatRegisterUsersModel[i].EmployeeName))
//                                        }
//

                                        if (chatRegisterUsersModel.size > 0){
                                            userListPopup(chatRegisterUsersModel)
                                        }

                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ChatUserListActivity,
                                            R.style.MyDialogTheme
                                        )
                                        builder.setMessage(jObject.getString("EXMessage"))
                                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        }
                                        val alertDialog: AlertDialog = builder.create()
                                        alertDialog.setCancelable(false)
                                        alertDialog.show()
                                    }
                                }

                            } else {
//                                 Toast.makeText(
//                                     applicationContext,
//                                     "Some Technical Issues.",
//                                     Toast.LENGTH_LONG
//                                 ).show()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                applicationContext,
                                "" + Config.SOME_TECHNICAL_ISSUES,
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("userAllClicks")){

            dialogUserSheet!!.dismiss()
            var keys = db!!.getChatKeyFromUser(mobile!!,messageLists[position].mobile!!)
            Log.e(TAG,"Keys  160000   "+keys)
            val i = Intent(this@ChatUserListActivity, ChatActivity1::class.java)
            i.putExtra("name",messageLists[position].name)
            i.putExtra("mobile",messageLists[position].mobile)
            i.putExtra("senderID",senderID)
            i.putExtra("ReceiverID",messageLists[position].mobile)
            i.putExtra("BranchName",messageLists[position].BranchName)
            i.putExtra("chatKey",keys)
            i.putExtra("userToken",messageLists[position].PnUserToken)

            startActivity(i)

        }

        if (data.equals("userChatClicks")){

            val jsonObject = userArray!!.getJSONObject(position)
            Log.e(TAG,"jsonObject  160000   "+jsonObject)
            val i = Intent(this@ChatUserListActivity, ChatActivity1::class.java)
            i.putExtra("name",jsonObject.getString("name"))
            i.putExtra("mobile",jsonObject.getString("user_2"))
            i.putExtra("senderID",jsonObject.getString("senderID"))
            i.putExtra("ReceiverID",jsonObject.getString("user_2"))
            i.putExtra("BranchName",jsonObject.getString("BranchName"))
            i.putExtra("chatKey",jsonObject.getString("chatkey"))
            i.putExtra("userToken",jsonObject.getString("userToken"))

            startActivity(i)
        }

    }

    override fun onRestart() {
        super.onRestart()
        // Activity is restarting
        getChatUser()
    }
}