package com.perfect.prodsuit.View.Activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ChatAllUserList
import com.perfect.prodsuit.Model.ChatMessageList
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ChatAllUserAdapter
import com.perfect.prodsuit.View.Adapter.ChatUserAdapter
import org.json.JSONArray

class ChatUserListActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    var TAG : String = "ChatUserListActivity"

    var name :  String? = ""
    var mobile :  String? = ""
    var chatKey :  String? = ""
    var senderID :  String? = ""

    var db : DBHelper? = null
    private lateinit var databaseRef : DatabaseReference
    private lateinit var databaseRef1 : DatabaseReference
    var chatAllUserList =  mutableListOf<ChatAllUserList>()
    var messageLists =  mutableListOf<ChatMessageList>()
    private var dialogUserSheet : Dialog? = null

    private var recycchatUser : RecyclerView? = null

    internal var imgUserlist: ImageView? = null
    var userArray: JSONArray? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chat_user_list)

        setRegViews()

        db = DBHelper(this, null)
        name = intent.getStringExtra("name")
        mobile = intent.getStringExtra("mobile")

        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        databaseRef1 = FirebaseDatabase.getInstance().getReference("Chat")

     //   getAllUserListPopup()
        getChatUser()

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
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {


                try {
                    Log.e(TAG,"966  "+dataSnapshot.childrenCount)
                    db!!.deleteFirebaseUser()
                    messageLists.clear()
                    for (data in dataSnapshot.children) {
                        val user = data.getValue(ChatAllUserList::class.java)
                        if (user != null && user.mobile != mobile){

                            var lastmessage = ""
                            var unseenMessage = 0

                            chatAllUserList.add(user)
                            var chatUserKey = ""
//                            databaseRef1.addListenerForSingleValueEvent(object : ValueEventListener {
//                                override fun onDataChange(snapshot: DataSnapshot) {
//
//                                    var getChatCounts = snapshot.childrenCount
//                                    if (getChatCounts > 0){
//
//                                        for (dataSnapshot1 in snapshot.children) {
//                                            var getKey = dataSnapshot1.key
//                                            chatKey = getKey
//                                            var getUserOne = dataSnapshot1.child("user_1").value
//                                            var getUserTwo = dataSnapshot1.child("user_2").value
//
//
//
//                                            if ((getUserOne!!.equals(user.mobile) && getUserTwo!!.equals(mobile)) || (getUserOne!!.equals(mobile) && getUserTwo!!.equals(user.mobile))){
//                                                Log.e(TAG,"7777771   "+chatKey+"  :  "+getUserOne+"  :  "+getUserTwo)
//                                                chatUserKey = chatKey.toString()
//                                                db!!.addFirebaseUser(user.name.toString(),user.BranchName.toString(),getUserOne!!.toString(),getUserTwo!!.toString(),chatUserKey)
//                                            }
//
//                                        }
//
//
//                                    }
//                                }
//
//                                override fun onCancelled(error: DatabaseError) {
//
//                                }
//
//                            })
                            Log.e(TAG,"7777773   "+chatUserKey+"  :  "+mobile+"  :  "+user.mobile+"   :  "+messageLists.size)


                            db!!.addFirebaseUser(user.name.toString(),user.BranchName.toString(),mobile!!.toString(),user.mobile.toString(),"")
                            messageLists.add(ChatMessageList(user.name,user.mobile,lastmessage,unseenMessage,chatUserKey!!,user.BranchName))
                        }
                        else{
                            senderID = data.key
                            Log.e(TAG,"senderID  6444   "+senderID)
                        }
                    }

                    if (messageLists.size > 0){
                        for (i in 0 until messageLists.size) {
                            Log.e(TAG,"10555  "+messageLists[i].mobile+"  : "+messageLists[i].name+"  : "+messageLists[i].BranchName+"  : "+messageLists[i].chatUserKey)

                        }
                    }

                    if (messageLists.size > 0){
                        userListPopup(messageLists)
                    }

                }catch (e: Exception){
                    Log.e(TAG,"16444   "+e.toString())
                }



//                if (messageLists.size > 0){
//                    val lLayout = GridLayoutManager(this@UserListActivity, 1)
//                    recycUserList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                    val adapter = UserListAdapter(this@UserListActivity, messageLists)
//                    recycUserList!!.adapter = adapter
//                    adapter!!.setClickListener(this@UserListActivity)
//                }

               // progressDialog!!.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {
              //  progressDialog!!.dismiss()
            }
        })
    }

    private fun userListPopup(messageLists: MutableList<ChatMessageList>) {
        try {
            dialogUserSheet = Dialog(this)
            dialogUserSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogUserSheet!! .setContentView(R.layout.chat_alluser_popup)
            dialogUserSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogUserSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            var recycAllUserList = dialogUserSheet!! .findViewById(R.id.recycAllUserList) as RecyclerView


            val lLayout = GridLayoutManager(this@ChatUserListActivity, 1)
            recycAllUserList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ChatAllUserAdapter(this@ChatUserListActivity, messageLists)
            recycAllUserList!!.adapter = adapter
            adapter!!.setClickListener(this@ChatUserListActivity)

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
                getAllUserListPopup()
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
            i.putExtra("ReceiverID",chatAllUserList[position].mobile)
            i.putExtra("BranchName",chatAllUserList[position].BranchName)
            i.putExtra("chatKey",keys)

            startActivity(i)

        }

        if (data.equals("userChatClicks")){

            val jsonObject = userArray!!.getJSONObject(position)
            val i = Intent(this@ChatUserListActivity, ChatActivity1::class.java)
            i.putExtra("name",jsonObject.getString("name"))
            i.putExtra("mobile",jsonObject.getString("user_2"))
            i.putExtra("senderID",jsonObject.getString("senderID"))
            i.putExtra("ReceiverID",jsonObject.getString("user_2"))
            i.putExtra("BranchName",jsonObject.getString("BranchName"))
            i.putExtra("chatKey",jsonObject.getString("chatkey"))

            startActivity(i)
        }

    }

    override fun onRestart() {
        super.onRestart()
        // Activity is restarting
        getChatUser()
    }
}