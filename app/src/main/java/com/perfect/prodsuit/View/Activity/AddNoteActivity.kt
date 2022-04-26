package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.AddNoteViewModel
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject

class AddNoteActivity : AppCompatActivity(), View.OnClickListener{

    var lldate: LinearLayout? = null
    var llNextactndate: LinearLayout? = null
    var llcstmr_remark: LinearLayout? = null
    var llAgent_remrk: LinearLayout? = null
    private var progressDialog: ProgressDialog? = null
    var btnReset: Button? = null
    var btnSubmit: Button? = null
    lateinit var addNoteViewModel: AddNoteViewModel
    var llFromDatePick: LinearLayout? = null
    var txtFromSubmit: TextView? = null
    var txtv_Date: TextView? = null
    lateinit var context: Context
    var fromDateMode : String?= "1"
    var datePickerFrom: DatePicker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        setRegViews()
        getAddNote()
    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        lldate = findViewById<LinearLayout>(R.id.lldate)
        lldate!!.setOnClickListener(this)
        llNextactndate = findViewById<LinearLayout>(R.id.llNextactndate)
        llNextactndate!!.setOnClickListener(this)
        llcstmr_remark = findViewById<LinearLayout>(R.id.llcstmr_remark)
        llcstmr_remark!!.setOnClickListener(this)
        llAgent_remrk = findViewById<LinearLayout>(R.id.llAgent_remrk)
        llAgent_remrk!!.setOnClickListener(this)

        btnReset = findViewById(R.id.btnReset) as Button
        btnSubmit = findViewById(R.id.btnSubmit) as Button

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        llFromDatePick = findViewById<LinearLayout>(R.id.llFromDatePick)
        txtFromSubmit = findViewById<TextView>(R.id.txtFromSubmit)
        txtFromSubmit!!.setOnClickListener(this)
        txtv_Date= findViewById<TextView>(R.id.txtv_Date)
        txtv_Date!!.setOnClickListener(this)

    }

    private fun getAddNote() {
        context = this@AddNoteActivity
        addNoteViewModel = ViewModelProvider(this).get(AddNoteViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                addNoteViewModel.getAddnotelist(this)!!.observe(
                        this,
                        Observer { addnoteSetterGetter ->
                            val msg = addnoteSetterGetter.message
                            if (msg!!.length > 0) {
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode") == "0") {
                                 /*   val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                    todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                    val lLayout = GridLayoutManager(this@TodoListActivity, 1)
                                    rv_todolist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                    rv_todolist!!.setHasFixedSize(true)
                                    val adapter = TodoListAdapter(applicationContext, todoArrayList)
                                    rv_todolist!!.adapter = adapter
                                    adapter.setClickListener(this@TodoListActivity)*/
                                } else {
                                    val builder = AlertDialog.Builder(
                                            this@AddNoteActivity,
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

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.btnSubmit->{
                getAddNote()
            }
        }
    }



}
