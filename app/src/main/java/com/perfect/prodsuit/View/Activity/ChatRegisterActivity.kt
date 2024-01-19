package com.perfect.prodsuit.View.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.database.*
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DBHelper
import com.perfect.prodsuit.R

class ChatRegisterActivity : AppCompatActivity() {

    var TAG : String = "ChatRegisterActivity"
    private lateinit var databaseRef : DatabaseReference
//    var userList =  mutableListOf<UserList>()
    var db : DBHelper? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_chat_register)

        databaseRef = FirebaseDatabase.getInstance().getReference("User")
        db = DBHelper(this, null)

//        registerFirebaseUser()
        val Fbase_MobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF69, 0)
        if (Fbase_MobileSP.getString("Fbase_Mobile", "").equals("")){
            registerFirebaseUser()
        }else{
            val Fbase_NameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF68, 0)
            val Fbase_MobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF69, 0)

            val i = Intent(this@ChatRegisterActivity, ChatUserListActivity::class.java)
            i.putExtra("name",Fbase_NameSP.getString("Fbase_Name",""))
            i.putExtra("mobile",Fbase_MobileSP.getString("Fbase_Mobile",""))
            startActivity(i)
            finish()
        }

    }

    private fun registerFirebaseUser() {

        val UserNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF2, 0)
        var MobileNumberSP = applicationContext.getSharedPreferences(Config.SHARED_PREF4, 0)
        val FK_EmployeeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF1, 0)
        val FK_BranchSP = applicationContext.getSharedPreferences(Config.SHARED_PREF37, 0)
        val BranchNameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF45, 0)
        val FK_BranchTypeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF38, 0)
        val FK_CompanySP = applicationContext.getSharedPreferences(Config.SHARED_PREF39, 0)
        val FK_BranchCodeUserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF40, 0)
        val FK_UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF41, 0)
        val UserRoleSP = applicationContext.getSharedPreferences(Config.SHARED_PREF42, 0)
        val IsAdminSP = applicationContext.getSharedPreferences(Config.SHARED_PREF43, 0)
        val ID_UserSP = applicationContext.getSharedPreferences(Config.SHARED_PREF44, 0)
        val FK_DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF55, 0)
        val DepartmentSP = applicationContext.getSharedPreferences(Config.SHARED_PREF56, 0)
        val CompanyCategorySP = applicationContext.getSharedPreferences(Config.SHARED_PREF46, 0)
        val UserCodeSP = applicationContext.getSharedPreferences(Config.SHARED_PREF36, 0)

        //rrrr

        var name = UserNameSP.getString("UserName", "")
        var mobile = MobileNumberSP.getString("MobileNumber", "")
        var FK_Employee = FK_EmployeeSP.getString("FK_Employee", "")
        var FK_Branch = FK_BranchSP.getString("FK_Branch", "")
        var BranchName = BranchNameSP.getString("BranchName", "")
        var FK_BranchType = FK_BranchTypeSP.getString("FK_BranchType", "")
        var FK_Department = FK_DepartmentSP.getString("FK_Department", "")
        var Department = DepartmentSP.getString("Department", "")
        var FK_Company = FK_CompanySP.getString("FK_Company", "")
        var FK_BranchCodeUser = FK_BranchCodeUserSP.getString("FK_BranchCodeUser", "")
        var FK_UserRole = FK_UserRoleSP.getString("FK_UserRole", "")
        var UserRole = UserRoleSP.getString("UserRole", "")
        var IsAdmin = IsAdminSP.getString("IsAdmin", "")
        var ID_User = ID_UserSP.getString("ID_User", "")
        var CompanyCategory = CompanyCategorySP.getString("CompanyCategory", "")
        var UserCode = UserCodeSP.getString("UserCode", "")


        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(mobile!!).hasChild(mobile!!)){
                  //  Toast.makeText(applicationContext,"Mobile Already Exist", Toast.LENGTH_SHORT).show()
                    Log.e(TAG,"42004   Mobile Already Exist")
                }
                else{
                    databaseRef.child(mobile!!).child("name").setValue(name)
                    databaseRef.child(mobile!!).child("mobile").setValue(mobile)
                    databaseRef.child(mobile!!).child("FK_Employee").setValue(FK_Employee)
                    databaseRef.child(mobile!!).child("FK_Branch").setValue(FK_Branch)
                    databaseRef.child(mobile!!).child("BranchName").setValue(BranchName)
                    databaseRef.child(mobile!!).child("FK_BranchType").setValue(FK_BranchType)
                    databaseRef.child(mobile!!).child("FK_Department").setValue(FK_Department)
                    databaseRef.child(mobile!!).child("Department").setValue(Department)
                    databaseRef.child(mobile!!).child("FK_Company").setValue(FK_Company)
                    databaseRef.child(mobile!!).child("FK_BranchCodeUser").setValue(FK_BranchCodeUser)
                    databaseRef.child(mobile!!).child("FK_UserRole").setValue(FK_UserRole)
                    databaseRef.child(mobile!!).child("UserRole").setValue(UserRole)
                    databaseRef.child(mobile!!).child("IsAdmin").setValue(IsAdmin)
                    databaseRef.child(mobile!!).child("ID_User").setValue(ID_User)
                    databaseRef.child(mobile!!).child("CompanyCategory").setValue(CompanyCategory)
                    databaseRef.child(mobile!!).child("UserCode").setValue(UserCode)

                  //  Toast.makeText(applicationContext,"Success", Toast.LENGTH_SHORT).show()
                    Log.e(TAG,"42005   Success")

                    val Fbase_NameSP = applicationContext.getSharedPreferences(Config.SHARED_PREF68, 0)
                    val Fbase_NameEditer = Fbase_NameSP.edit()
                    Fbase_NameEditer.putString("Fbase_Name",name)
                    Fbase_NameEditer.commit()

                    val Fbase_MobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF69, 0)
                    val Fbase_MobileEditer = Fbase_MobileSP.edit()
                    Fbase_MobileEditer.putString("Fbase_Mobile",mobile)
                    Fbase_MobileEditer.commit()

                    val i = Intent(this@ChatRegisterActivity, ChatUserListActivity::class.java)
                    i.putExtra("name",name)
                    i.putExtra("mobile",mobile)
                    startActivity(i)
                    finish()
                }
            }

            override fun onCancelled(error: DatabaseError) {

                Log.e(TAG,"13666   "+error.message)
            }

        })
    }

    private fun initialization() {

        var mobile = "8075283549"
        var name = "Ranjith"
        var email = "ranjith@gmail.com"

    }
}