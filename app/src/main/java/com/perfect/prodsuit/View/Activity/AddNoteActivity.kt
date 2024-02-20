package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.AddNoteViewModel
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class AddNoteActivity : AppCompatActivity(), View.OnClickListener{

    var lldate: LinearLayout? = null
    var llNextactndate: LinearLayout? = null
    var llcstmr_remark: LinearLayout? = null
    var llAgent_remrk: LinearLayout? = null
    var imFromDate: ImageView? = null
    var imFromDate1: ImageView? = null
    private var progressDialog: ProgressDialog? = null
    var btnReset: Button? = null
    var btnSubmit: Button? = null
    lateinit var addNoteViewModel: AddNoteViewModel
    var llFromDatePick: LinearLayout? = null
    var llFromDatePick1: LinearLayout? = null
    var txtFromSubmit: TextView? = null
    var txtFromSubmit1: TextView? = null
    var etxt_custremrk: EditText? = null
    var etxt_agentremrk: EditText? = null
    var txtv_Date: TextView? = null
    lateinit var context: Context
    var fromDateMode : String?= "1"
    var fromDateMode1 : String?= "1"
    var datePickerFrom: DatePicker? = null
    var datePickerFrom1: DatePicker? = null
    var txtnxt_actndte: TextView? = null
    var strDate = ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    companion object{
        var date1= ""
        var custnote= ""
        var agentnote= ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_note)
        context = this@AddNoteActivity
        setRegViews()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        lldate = findViewById<LinearLayout>(R.id.lldate)
        txtnxt_actndte = findViewById<TextView>(R.id.txtnxt_actndte)
        lldate!!.setOnClickListener(this)
        llNextactndate = findViewById<LinearLayout>(R.id.llNextactndate)
        llNextactndate!!.setOnClickListener(this)
        llcstmr_remark = findViewById<LinearLayout>(R.id.llcstmr_remark)
        llcstmr_remark!!.setOnClickListener(this)
        llAgent_remrk = findViewById<LinearLayout>(R.id.llAgent_remrk)
        llAgent_remrk!!.setOnClickListener(this)

        datePickerFrom = findViewById(R.id.datePickerFrom) as DatePicker
        datePickerFrom1 = findViewById(R.id.datePickerFrom1) as DatePicker

        imFromDate = findViewById(R.id.imFromDate) as ImageView
        imFromDate1 = findViewById(R.id.imFromDate1) as ImageView

        etxt_custremrk = findViewById<EditText>(R.id.etxt_custremrk)
        etxt_agentremrk = findViewById<EditText>(R.id.etxt_agentremrk)

        btnReset = findViewById(R.id.btnReset) as Button
        btnSubmit = findViewById(R.id.btnSubmit) as Button

        btnReset!!.setOnClickListener(this)
        btnSubmit!!.setOnClickListener(this)

        llFromDatePick = findViewById<LinearLayout>(R.id.llFromDatePick)
        llFromDatePick1 = findViewById<LinearLayout>(R.id.llFromDatePick1)

        txtFromSubmit = findViewById<TextView>(R.id.txtFromSubmit)
        txtFromSubmit!!.setOnClickListener(this)

        txtFromSubmit1 = findViewById<TextView>(R.id.txtFromSubmit1)
        txtFromSubmit1!!.setOnClickListener(this)

        txtv_Date= findViewById<TextView>(R.id.txtv_Date)
        txtv_Date!!.setOnClickListener(this)

        imFromDate!!.setOnClickListener(this)
        imFromDate1!!.setOnClickListener(this)
    }

    private fun getAddNote() {
        date1 = txtv_Date!!.text.toString()
        custnote=etxt_custremrk!!.text.toString()
        agentnote=etxt_agentremrk!!.text.toString()
        Log.i("Details",date1+"\n"+ custnote+"\n"+ agentnote)


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
                                val jobjt = jObject.getJSONObject("AddAgentCustomerRemarks")
                                if (jObject.getString("StatusCode") == "0") {

                                    var responsemessage = jobjt.getString("ResponseMessage")
                                    val jobjt = jObject.getJSONObject("AddAgentCustomerRemarks")
                                    var responsemsg = jobjt!!.getString("ResponseMessage")

                                    Log.i("Result",responsemsg)
                                    val builder = AlertDialog.Builder(
                                            this@AddNoteActivity,
                                            R.style.MyDialogTheme
                                    )
                                    builder.setMessage(responsemsg)
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                      //  val i = Intent(this@AddNoteActivity, AccountDetailsActivity::class.java)
                                       // startActivity(i)
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.btnSubmit->{

                validations(v)


            }
            R.id.lldate->{
                if (fromDateMode.equals("0")){
                    llFromDatePick!!.visibility = View.GONE
                    fromDateMode = "1"
                }else{
                    llFromDatePick!!.visibility = View.VISIBLE
                    fromDateMode = "0"
                }
            }
            R.id.llNextactndate->{
                if (fromDateMode1.equals("0")){
                    llFromDatePick1!!.visibility = View.GONE
                    fromDateMode1 = "1"
                }else{
                    llFromDatePick1!!.visibility = View.VISIBLE
                    fromDateMode1 = "0"
                }
            }
            R.id.imFromDate->{
                llFromDatePick!!.visibility = View.GONE
                fromDateMode = "1"
            }
            R.id.imFromDate1->{
                llFromDatePick1!!.visibility = View.GONE
                fromDateMode1 = "1"
            }
            R.id.imFromDate1->{
                llFromDatePick1!!.visibility = View.GONE
                fromDateMode1 = "1"
            }
            R.id.btnReset->{
                ResetData()
            }
            R.id.txtFromSubmit->{


                try {
                    datePickerFrom!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = datePickerFrom!!.getDayOfMonth()
                    val mon: Int = datePickerFrom!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = datePickerFrom!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtv_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llFromDatePick!!.visibility=View.GONE
                    fromDateMode = "1"
                    strDate = strDay+"-"+strMonth+"-"+strYear


                }
                catch (e: Exception){
                    Log.e("TAG","Exception   428   "+e.toString())
                }
            }
            R.id.txtFromSubmit1->{


                try {
                    datePickerFrom1!!.minDate = Calendar.getInstance().timeInMillis
                    val day: Int = datePickerFrom1!!.getDayOfMonth()
                    val mon: Int = datePickerFrom1!!.getMonth()
                    val month: Int = mon+1
                    val year: Int = datePickerFrom1!!.getYear()
                    var strDay = day.toString()
                    var strMonth = month.toString()
                    var strYear = year.toString()
                    if (strDay.length == 1){
                        strDay ="0"+day
                    }
                    if (strMonth.length == 1){
                        strMonth ="0"+strMonth
                    }
                    txtnxt_actndte!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    llFromDatePick1!!.visibility=View.GONE
                    fromDateMode1 = "1"
                    strDate = strDay+"-"+strMonth+"-"+strYear
                }
                catch (e: Exception){
                    Log.e("TAG","Exception   428   "+e.toString())
                }
            }

        }

    }

    private fun ResetData() {
        txtv_Date!!.setText("")
        txtnxt_actndte!!.setText("")
        etxt_custremrk!!.setText("")
        etxt_agentremrk!!.setText("")

    }

    private fun validations(v: View) {

        if (txtv_Date!!.text.toString().equals("")){
            Config.snackBars(context,v,"Please Select Date")
        }
        else if (txtnxt_actndte!!.text.toString().equals("")){
            Config.snackBars(context,v,"Please Select Next Action Date")
        }
        else if (etxt_custremrk!!.text.toString().equals("")){
            Config.snackBars(context,v,"Please Enter Customer Remark.")
        }
        else if (etxt_agentremrk!!.text.toString().equals("")){
            Config.snackBars(context,v,"Please Enter Agent Remark.")
        }

        else
        {
            getAddNote()
        }
    }


}
