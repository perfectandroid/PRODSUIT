package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.ChangeMpinViewModel
import org.json.JSONObject

class HomeActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    lateinit var context: Context
    lateinit var changempinViewModel: ChangeMpinViewModel
    private var progressDialog: ProgressDialog? = null
    private var drawer_layout: DrawerLayout? = null
    private var nav_view: NavigationView? = null
    private var btn_menu: ImageView? = null
    private var llservice: LinearLayout? = null
    private var ll_dashboard: LinearLayout? = null
    private var lllead: LinearLayout? = null
    private var chipNavigationBar: ChipNavigationBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homemain)
        setRegViews()
        bottombarnav()
    }

    private fun bottombarnav() {
        chipNavigationBar = findViewById(R.id.chipNavigation)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener{
            override fun onItemSelected(i: Int) {
                when (i) {
                    R.id.home -> {
                        val i = Intent(this@HomeActivity, HomeActivity::class.java)
                        startActivity(i)
                    }
                    R.id.profile -> {
                        val i = Intent(this@HomeActivity, ProfileActivity::class.java)
                        startActivity(i)
                    }
                    R.id.logout -> {
                        doLogout()
                    }
                    R.id.quit -> {
                        quit()
                    }
                }
            }
        })
    }

    private fun setRegViews() {
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        btn_menu = findViewById(R.id.btn_menu)
        lllead = findViewById(R.id.lllead)
        llservice = findViewById(R.id.llservice)
        ll_dashboard = findViewById(R.id.ll_dashboard)
        btn_menu!!.setOnClickListener(this)
        lllead!!.setOnClickListener(this)
        llservice!!.setOnClickListener(this)
        ll_dashboard!!.setOnClickListener(this)
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
            R.id.llservice -> {
                val i = Intent(this@HomeActivity, ServiceActivity::class.java)
                startActivity(i)
            }
            R.id.lllead -> {
                val i = Intent(this@HomeActivity, LeadActivity::class.java)
                startActivity(i)
            }
            R.id.ll_dashboard -> {
//                https://github.com/PhilJay/MPAndroidChart
                val i = Intent(this@HomeActivity, DashBoardActivity::class.java)
                startActivity(i)
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
        val loginmobileSP = applicationContext.getSharedPreferences(Config.SHARED_PREF14, 0)
        val loginmobileEditer = loginmobileSP.edit()
        loginmobileEditer.putString("Loginmobilenumber", "")
        loginmobileEditer.commit()
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
                    changempinverficationcode(etxt_oldpin!!.text.toString(),etxt_newpin!!.text.toString())
                }
            }
            btnreset.setOnClickListener {
                etxt_oldpin.setText("")
                etxt_newpin.setText("")
                etxt_confirmnewpin.setText("")
            }
            dialog1.show()
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        var strOldMPIN= ""
        var strNewMPIN= ""
    }

    private fun changempinverficationcode(oldPin: String, newPin: String) {
        context = this@HomeActivity
        changempinViewModel = ViewModelProvider(this).get(ChangeMpinViewModel::class.java)
        strOldMPIN = oldPin
        strNewMPIN = newPin
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                changempinViewModel.changeMpin(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            if (jObject.getString("StatusCode") == "0") {
                                var jobj = jObject.getJSONObject("MPINDetails")

                                val builder = AlertDialog.Builder(
                                    this@HomeActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jobj.getString("ResponseMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@HomeActivity,
                                    R.style.MyDialogTheme
                                )
                                builder.setMessage(jObject.getString("EXMessage"))
                                builder.setPositiveButton("Ok") { dialogInterface, which ->
                                }
                                val alertDialog: AlertDialog = builder.create()
                                alertDialog.setCancelable(false)
                                alertDialog.show()
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
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

    override fun onBackPressed() {
        quit()
    }

}


