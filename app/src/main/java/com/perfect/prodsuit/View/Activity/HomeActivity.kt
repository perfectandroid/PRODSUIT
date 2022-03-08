package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R

class HomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private var drawer_layout: DrawerLayout? = null
    private var nav_view: NavigationView? = null
    private var btn_menu: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemain)
        setRegViews()
    }

    private fun setRegViews() {
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        btn_menu = findViewById(R.id.btn_menu)
        btn_menu!!.setOnClickListener(this)
        nav_view!!.setNavigationItemSelectedListener(this)
        nav_view!!.setItemIconTintList(null);
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_menu -> {
                if (drawer_layout!!.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout!!.closeDrawer(GravityCompat.START)
                } else {
                    drawer_layout!!.openDrawer(GravityCompat.START)
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                val i = Intent(this@HomeActivity, ProfileActivity::class.java)
                startActivity(i)
            }
            R.id.nav_changempin -> {
                changeMpin()
            }
            R.id.nav_about -> {
                val i = Intent(this@HomeActivity, AboutUsActivity::class.java)
                startActivity(i)
            }
            R.id.nav_contact -> {
                val i = Intent(this@HomeActivity, ContactUsActivity::class.java)
                startActivity(i)
            }
            R.id.nav_share -> {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    getString(R.string.app_name) + "\t" + "Invite You  \n\n For Android Users \n\n http://play.google.com/store/apps/details?id=" + getPackageName() + "\n"
                )
                startActivity(Intent.createChooser(shareIntent, "Invite this App to your friends"))
            }
            R.id.nav_logout -> {
                doLogout()
            }
            R.id.nav_quit -> {
                quit()
            }
        }
        drawer_layout!!.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun doLogout() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.logout_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btnYes) as Button
            val btn_No = dialog1 .findViewById(R.id.btnNo) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                dologoutchanges()
                startActivity(Intent(this@HomeActivity, WelcomeActivity::class.java))
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun dologoutchanges() {
        val loginSP = applicationContext.getSharedPreferences(Config.SHARED_PREF, 0)
        val loginEditer = loginSP.edit()
        loginEditer.putString("loginsession", "No")
        loginEditer.commit()
    }

    private fun quit() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(false)
            dialog1 .setContentView(R.layout.quit_popup)
            dialog1.window!!.attributes.gravity = Gravity.BOTTOM;
            val btn_Yes = dialog1 .findViewById(R.id.btn_Yes) as Button
            val btn_No = dialog1 .findViewById(R.id.btn_No) as Button
            btn_No.setOnClickListener {
                dialog1 .dismiss()
            }
            btn_Yes.setOnClickListener {
                dialog1.dismiss()
                finish()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    finishAffinity()
                }
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun changeMpin() {
        try {
            val dialog1 = Dialog(this)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog1 .setCancelable(true)
            dialog1 .setContentView(R.layout.changepin_popup)
            val btnreset = dialog1 .findViewById(R.id.btnreset) as Button
            val btnSubmit = dialog1 .findViewById(R.id.btnSubmit) as Button
            val etxt_oldpin = dialog1 .findViewById(R.id.etxt_oldpin) as EditText
            val etxt_newpin = dialog1 .findViewById(R.id.etxt_newpin) as EditText
            val etxt_confirmnewpin = dialog1 .findViewById(R.id.etxt_confirmnewpin) as EditText
            btnSubmit.setOnClickListener {
                if (etxt_oldpin!!.text.toString() == null || etxt_oldpin!!.text.toString().isEmpty()) {
                    etxt_oldpin!!.setError("Please Enter Your Old mPin.")
                }
                else if (etxt_newpin!!.text.toString() == null || etxt_newpin!!.text.toString().isEmpty()) {
                    etxt_newpin!!.setError("Please Enter Your New mPin.")
                }
                else if (etxt_confirmnewpin!!.text.toString() == null || etxt_confirmnewpin!!.text.toString().isEmpty()) {
                    etxt_confirmnewpin!!.setError("Please Confirm Your New mPin.")
                }
                else if (etxt_newpin!!.text.toString() != etxt_confirmnewpin!!.text.toString()) {
                    val dialog: AlertDialog.Builder = AlertDialog.Builder(this)
                    dialog.setMessage("New & Confirm mPin doesn't match")
                    dialog.setPositiveButton("Ok",
                        DialogInterface.OnClickListener { dialog, which ->
                        })
                    val alertDialog: AlertDialog = dialog.create()
                    alertDialog.show()
                }
                else if (etxt_oldpin!!.text.toString().isNotEmpty() && etxt_oldpin!!.text.toString().length!=6) {
                    etxt_oldpin.setError("Please Enter 6 digit mPin")
                }
                else if (etxt_newpin!!.text.toString().isNotEmpty() && etxt_newpin!!.text.toString().length!=6) {
                    etxt_newpin.setError("Please Enter 6 digit mPin")
                }
                else if (etxt_confirmnewpin!!.text.toString().isNotEmpty() && etxt_confirmnewpin!!.text.toString().length!=6) {
                    etxt_confirmnewpin.setError("Please Enter 6 digit mPin")
                }else{
                    dialog1 .dismiss()
                }
            }
            btnreset.setOnClickListener {
                etxt_oldpin.setText("")
                etxt_newpin.setText("")
                etxt_confirmnewpin.setText("")
            }
            dialog1.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        quit()
    }

}