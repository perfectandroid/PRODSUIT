package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.*
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ApproveAdapter
import com.perfect.prodsuit.View.Adapter.ApproveListAdapter
import com.perfect.prodsuit.View.Adapter.LeadByAdapter
import com.perfect.prodsuit.View.Adapter.ReasonAuthAdapter
import com.perfect.prodsuit.Viewmodel.ApprovalListViewModel
import com.perfect.prodsuit.Viewmodel.ApprovalViewModel
import com.perfect.prodsuit.Viewmodel.ReasonViewModel
import org.json.JSONArray
import org.json.JSONObject

class ApprovalListActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {


    val TAG : String = "ApprovalListActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    lateinit var approvalListViewModel: ApprovalListViewModel
    lateinit var approvalArrayList: JSONArray
    internal var recyAprrove: RecyclerView? = null
    var approveCount = 0

    lateinit var reasonViewModel: ReasonViewModel
    lateinit var reasonArrayList: JSONArray
    lateinit var reasonSort: JSONArray
    internal var recyReason: RecyclerView? = null
    var dialogReason: Dialog? = null
    var reasonCount = 0

    private var txtRjtCancel: TextView? = null
    private var txtRjtSubmit: TextView? = null

    private var tie_Reason: TextInputEditText? = null
    private var tie_Remarks: TextInputEditText? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_approval_list)

        context = this@ApprovalListActivity

        approvalListViewModel = ViewModelProvider(this).get(ApprovalListViewModel::class.java)
        reasonViewModel = ViewModelProvider(this).get(ReasonViewModel::class.java)

        setRegViews()

        approveCount = 0
        getAppoval()

    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyAprrove = findViewById(R.id.recyAprrove)
    }

    private fun getAppoval() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                approvalListViewModel.getApprovalList(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (approveCount == 0) {
                                    approveCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ApprovalDetails")
                                        approvalArrayList = jobjt.getJSONArray("ApprovalDetailList")

                                        Log.e(TAG, "approvalArrayList   999101   " + approvalArrayList)
                                        if (approvalArrayList.length()> 0){
                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
                                            recyAprrove!!.adapter = adapter
                                            adapter.setClickListener(this@ApprovalListActivity)
                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListActivity,
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

    private fun rejectBottomSheet() {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.authorization_reject, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCanceledOnTouchOutside(false)

            txtRjtSubmit = view.findViewById<TextView>(R.id.txtRjtSubmit)
            txtRjtCancel = view.findViewById<TextView>(R.id.txtRjtCancel)

            tie_Reason = view.findViewById<TextInputEditText>(R.id.tie_Reason)
            tie_Remarks = view.findViewById<TextInputEditText>(R.id.tie_Remarks)

            tie_Reason!!.setOnClickListener {
                Config.disableClick(it)
                reasonCount = 0
                getReason()
            }

            txtRjtCancel!!.setOnClickListener {
                dialog1.dismiss()
            }

            txtRjtSubmit!!.setOnClickListener {

            }



            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
    }

    private fun getReason() {

        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                reasonViewModel.getReason(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (reasonCount == 0) {
                                    reasonCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   999101   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("ReasonDetails")
                                        reasonArrayList = jobjt.getJSONArray("ReasonDetailList")

                                        Log.e(TAG, "reasonArrayList   999101   " + reasonArrayList)
                                        if (reasonArrayList.length()> 0){
//                                            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
//                                            recyAprrove!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            val adapter = ApproveListAdapter(this@ApprovalListActivity, approvalArrayList)
//                                            recyAprrove!!.adapter = adapter
//                                            adapter.setClickListener(this@ApprovalListActivity)

                                            reasonpopup(reasonArrayList)


                                        }


                                    } else {
                                        val builder = AlertDialog.Builder(
                                            this@ApprovalListActivity,
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

    private fun reasonpopup(reasonArrayList: JSONArray) {
        try {

            dialogReason = Dialog(this)
            dialogReason!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReason!! .setContentView(R.layout.popup_reason)
            dialogReason!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            val window: Window? = dialogReason!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            recyReason = dialogReason!!.findViewById(R.id.recyReason) as RecyclerView

            reasonSort = JSONArray()
            for (k in 0 until reasonArrayList.length()) {
                val jsonObject = reasonArrayList.getJSONObject(k)
                reasonSort.put(jsonObject)
            }

            val lLayout = GridLayoutManager(this@ApprovalListActivity, 1)
            recyReason!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = ReasonAuthAdapter(this@ApprovalListActivity, reasonSort)
            recyReason!!.adapter = adapter
            adapter.setClickListener(this@ApprovalListActivity)

            dialogReason!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("eeee", "rreeeerree " + e)
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("approveListClick")){
            Log.e(TAG,"Position   "+position)

            val i = Intent(this@ApprovalListActivity, ApprovalListDetailActivity::class.java)
            startActivity(i)
        }
        if (data.equals("approveClick")){
            val jsonObject = approvalArrayList.getJSONObject(position)
            Log.e(TAG,"15777  approve "+jsonObject.getString("Transaction No"))

        }
        if (data.equals("rejectClick")){
            val jsonObject = approvalArrayList.getJSONObject(position)
            Log.e(TAG,"15777  reject "+jsonObject.getString("Transaction No"))

            rejectBottomSheet()
        }

        if (data.equals("reasonClick")){
            dialogReason!!.dismiss()
            val jsonObject = reasonSort.getJSONObject(position)
            tie_Reason!!.setText(jsonObject.getString("Reason"))
        }

    }



}