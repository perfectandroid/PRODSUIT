package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.lifecycle.Observer
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.BranchTypeAdapter
import com.perfect.prodsuit.View.Adapter.FollowupActionAdapter
import com.perfect.prodsuit.View.Adapter.FollowupTypeAdapter
import com.perfect.prodsuit.Viewmodel.BranchTypeViewModel
import com.perfect.prodsuit.Viewmodel.FollowUpActionViewModel
import com.perfect.prodsuit.Viewmodel.FollowUpTypeViewModel
import org.json.JSONArray
import org.json.JSONObject

class FollowUpActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {
    val TAG : String = "FollowUpActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var tie_Action: TextInputEditText? = null
    var tie_ActionType: TextInputEditText? = null
    var tie_FollowUpDate: TextInputEditText? = null
    var tie_BranchType: TextInputEditText? = null
    var tie_Branch: TextInputEditText? = null
    var tie_Department: TextInputEditText? = null
    var tie_Employee: TextInputEditText? = null

    var switchTransfer: Switch? = null
    var llNeedTransfer: LinearLayout? = null


    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    lateinit var followUpTypeViewModel: FollowUpTypeViewModel
    lateinit var followUpTypeArrayList : JSONArray
    private var dialogFollowupType : Dialog? = null
    var recyFollowupType: RecyclerView? = null

    lateinit var branchTypeViewModel: BranchTypeViewModel
    lateinit var branchTypeArrayList : JSONArray
    private var dialogBranchType : Dialog? = null
    var recyBranchType: RecyclerView? = null


    private var ID_NextAction:String = ""
    private var ID_ActionType:String = ""
    var strNeedCheck : String = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_follow_up)
        context = this@FollowUpActivity

        followUpActionViewModel = ViewModelProvider(this).get(FollowUpActionViewModel::class.java)
        followUpTypeViewModel = ViewModelProvider(this).get(FollowUpTypeViewModel::class.java)
        branchTypeViewModel = ViewModelProvider(this).get(BranchTypeViewModel::class.java)

        setRegViews()


        switchTransfer!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                llNeedTransfer!!.visibility = View.VISIBLE
//                edtbarnchtype!!.setText("")
//                edtbranch!!.setText("")
//                edtdepartment!!.setText("")
//                edtEmployee!!.setText("")
                strNeedCheck = "1"
            } else {
                llNeedTransfer!!.visibility = View.GONE
//                edtbarnchtype!!.setText("")
//                edtbranch!!.setText("")
//                edtdepartment!!.setText("")
//                edtEmployee!!.setText("")
                strNeedCheck = "0"
            }
        }

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_Action  = findViewById(R.id.tie_Action)
        tie_ActionType  = findViewById(R.id.tie_ActionType)
        tie_FollowUpDate  = findViewById(R.id.tie_FollowUpDate)
        tie_BranchType  = findViewById(R.id.tie_BranchType)
        tie_Branch  = findViewById(R.id.tie_Branch)
        tie_Department  = findViewById(R.id.tie_Department)
        tie_Employee  = findViewById(R.id.tie_Employee)


        switchTransfer  = findViewById(R.id.switchTransfer)
        llNeedTransfer  = findViewById(R.id.llNeedTransfer)

        tie_Action!!.setOnClickListener(this)
        tie_ActionType!!.setOnClickListener(this)
        tie_FollowUpDate!!.setOnClickListener(this)
        tie_BranchType!!.setOnClickListener(this)
        tie_Branch!!.setOnClickListener(this)
        tie_Department!!.setOnClickListener(this)
        tie_Employee!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.tie_Action->{
                getFollowupAction()
            }
            R.id.tie_ActionType->{
                getFollowupType()
            }
            R.id.tie_FollowUpDate->{

            }
            R.id.tie_BranchType->{
                getBranchType()
            }
            R.id.tie_Branch->{

            }
            R.id.tie_Department->{

            }
            R.id.tie_EmployeeName->{

            }
        }
    }

    private fun getFollowupAction() {
        var followUpAction = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpActionViewModel.getFollowupAction(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpActionDetails")
                                followUpActionArrayList = jobjt.getJSONArray("FollowUpActionDetailsList")
                                if (followUpActionArrayList.length()>0){
                                    if (followUpAction == 0){
                                        followUpAction++
                                        followUpActionPopup(followUpActionArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@FollowUpActivity,
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

    private fun followUpActionPopup(followUpActionArrayList: JSONArray) {

        try {

            dialogFollowupAction = Dialog(this)
            dialogFollowupAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupAction!! .setContentView(R.layout.followup_action)
            dialogFollowupAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupAction = dialogFollowupAction!! .findViewById(R.id.recyFollowupAction) as RecyclerView

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyFollowupAction!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = FollowupActionAdapter(this@FollowUpActivity, followUpActionArrayList)
            recyFollowupAction!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

            dialogFollowupAction!!.show()
            dialogFollowupAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getFollowupType() {
        var followUpType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                followUpTypeViewModel.getFollowupType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   82   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("FollowUpTypeDetails")
                                followUpTypeArrayList = jobjt.getJSONArray("FollowUpTypeDetailsList")
                                if (followUpTypeArrayList.length()>0){
                                    if (followUpType == 0){
                                        followUpType++
                                        followupTypePopup(followUpTypeArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@FollowUpActivity,
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

    private fun followupTypePopup(followUpTypeArrayList: JSONArray) {

        try {

            dialogFollowupType = Dialog(this)
            dialogFollowupType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogFollowupType!! .setContentView(R.layout.followup_type_popup)
            dialogFollowupType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyFollowupType = dialogFollowupType!! .findViewById(R.id.recyFollowupType) as RecyclerView

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyFollowupType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = FollowupTypeAdapter(this@FollowUpActivity, followUpTypeArrayList)
            recyFollowupType!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

            dialogFollowupType!!.show()
            dialogFollowupType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getBranchType() {
        var branchType = 0
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                branchTypeViewModel.getBranchType(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                            Log.e(TAG,"msg   979   "+msg)
                            if (jObject.getString("StatusCode") == "0") {
                                val jobjt = jObject.getJSONObject("BranchTypeDetails")
                                branchTypeArrayList = jobjt.getJSONArray("BranchTypeDetailsList")
                                if (branchTypeArrayList.length()>0){
                                    if (branchType == 0){
                                        branchType++
                                        branchTypePopup(branchTypeArrayList)
                                    }

                                }
                            } else {
                                val builder = AlertDialog.Builder(
                                    this@FollowUpActivity,
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

    private fun branchTypePopup(branchTypeArrayList: JSONArray) {

        try {

            dialogBranchType = Dialog(this)
            dialogBranchType!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBranchType!! .setContentView(R.layout.branchtype_popup)
            dialogBranchType!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyBranchType = dialogBranchType!! .findViewById(R.id.recyBranchType) as RecyclerView

            val lLayout = GridLayoutManager(this@FollowUpActivity, 1)
            recyBranchType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//            recyCustomer!!.setHasFixedSize(true)
            val adapter = BranchTypeAdapter(this@FollowUpActivity, branchTypeArrayList)
            recyBranchType!!.adapter = adapter
            adapter.setClickListener(this@FollowUpActivity)

            dialogBranchType!!.show()
            dialogBranchType!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun onClick(position: Int, data: String) {
        if (data.equals("followupaction")){
            dialogFollowupAction!!.dismiss()
            val jsonObject = followUpActionArrayList.getJSONObject(position)
            Log.e(TAG,"ID_NextAction   "+jsonObject.getString("ID_NextAction"))
            ID_NextAction = jsonObject.getString("ID_NextAction")
            tie_Action!!.setText(jsonObject.getString("NxtActnName"))


        }

        if (data.equals("followuptype")){
            dialogFollowupType!!.dismiss()
            val jsonObject = followUpTypeArrayList.getJSONObject(position)
            Log.e(TAG,"ID_ActionType   "+jsonObject.getString("ID_ActionType"))
            ID_ActionType = jsonObject.getString("ID_ActionType")
            tie_ActionType!!.setText(jsonObject.getString("ActnTypeName"))


        }
    }
}