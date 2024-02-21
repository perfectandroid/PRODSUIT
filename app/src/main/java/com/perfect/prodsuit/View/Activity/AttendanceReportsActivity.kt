package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog

import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.View.Adapter.AttendanceReportAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AttendanceReportsActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {


    var TAG  ="AttendanceReportListActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    lateinit var empByBranchViewModel: EmpByBranchViewModel
    private var dialogEmployeeAll: Dialog? = null
    var dateMode = 0
    lateinit var employeeAllSort: JSONArray
    var recyEmployeeAll: RecyclerView? = null

    lateinit var serviceListViewModel: ServiceListViewModel
    lateinit var attendancefirstpunchArrayList: JSONArray
    lateinit var attendancelastpunchArrayList: JSONArray
    lateinit var serviceListSort: JSONArray
    lateinit var outPunchSort: JSONArray
    var empUseBranch = 0
    lateinit var employeeAllArrayList: JSONArray
    var recyServiceList: RecyclerView? = null
    private var tv_listCount: TextView? = null
    private var txtv_headlabel: TextView? = null
    private var imgv_filter: ImageView? = null

    var serviceList = 0
    var empname : String?= ""
    var SubMode : String?= ""
    var ID_Branch : String?= ""
    var FK_Area : String?= ""
    var ID_Employee : String?= ""
    var strFromDate : String?= ""
    var strToDate : String?= ""
    var strCustomer : String?= ""
    var strMobile : String?= ""
    var strTicketNo : String?= ""
    var strDueDays : String?= ""

    private var til_Status: TextInputLayout? = null
    private var til_AttendedBy: TextInputLayout? = null
    private var crdv_empdetail: CardView? = null




    var exmessage : String? = ""


    var statusCount = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null
    var attendCount = 0
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null

    var ReqMode: String? = ""
    var ID_Status: String? = ""
    var ID_AttendedBy: String? = ""

    var serAssignCount = 0
    lateinit var punchReportViewModel: PunchReportViewModel
    var ID_CustomerServiceRegister: String? = ""
    var FK_CustomerserviceregisterProductDetails: String? = ""
    var TicketStatus: String? = ""


    var ID_Priority: String? = ""


    var strEditTicket : String?= ""
    var strEditProductComplaint : String?= ""
    var strEditCustomer : String?= ""
    var strEditProductname : String?= ""

    lateinit var serviceEditUpdateViewModel: ServiceEditUpdateViewModel
    var serUpdateCount = 0
    var strVisitDate : String?= ""
    var saveAttendanceMark = false
    val jsons= JSONObject()
    private var tie_emp: TextInputEditText? = null
    private var tie_Date: TextInputEditText? = null
    private var til_Date: TextInputLayout? = null
    private var til_Emp: TextInputLayout? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_reports_list1)
        context = this@AttendanceReportsActivity

        punchReportViewModel = ViewModelProvider(this).get(PunchReportViewModel::class.java)

        setRegViews()


        //  tie_emp!!.setOnClickListener(this)


        //  Log.e(TAG,"DET"+isAdmin+"\n"+IsManager)


        //  tie_AssignedTo!!.setOnClickListener(this)
        // tie_CollectedBy!!.setOnClickListener(this)`
        /*   label   = intent.getStringExtra("label")
           SubMode   = intent.getStringExtra("SubMode")
           ID_Branch   = intent.getStringExtra("ID_Branch")
           FK_Area     = intent.getStringExtra("FK_Area")
           ID_Employee = intent.getStringExtra("ID_Employee")
           strFromDate = intent.getStringExtra("strFromDate")
           strToDate   = intent.getStringExtra("strToDate")
           strCustomer = intent.getStringExtra("strCustomer")
           strMobile   = intent.getStringExtra("strMobile")
           strTicketNo = intent.getStringExtra("strTicketNo")
           strDueDays  = intent.getStringExtra("strDueDays")

           txtv_headlabel!!.setText(label)
   */




        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate = sdf.format(Date())
        System.out.println(" C DATE is  "+currentDate)

        tie_Date!!.setText(currentDate)



        serviceList = 0


        val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
        val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")



        var dates = tie_Date!!.text.toString()
        val dateFrom = inputFormat.parse(dates)
        // val dateFrom = inputFormat.parse("08-04-2022")
        val strDate = outputFormat.format(dateFrom)




    //    strToDate=tie_Date!!.text.toString()
        getPunchReport(strDate!!)


     /*   if(strToDate!!.equals("Date")||strToDate!!.equals("")||strToDate!!.equals(null))
        {
            til_Date!!.setError("Please select a Date")
            til_Date!!.setErrorIconDrawable(null)

            Log.i(TAG,"Date : "+strToDate)
        }
        else {







        }
*/

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)






        //  txtv_emp = findViewById<TextView>(R.id.txtv_emp)
        //   txtv_ph = findViewById<TextView>(R.id.txtv_ph)

        //   til_Emp = findViewById<TextInputLayout>(R.id.til_Emp)


        //txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        recyServiceList = findViewById<RecyclerView>(R.id.recyServiceList)
        recyServiceList!!.adapter = null
        //     tv_listCount = findViewById<TextView>(R.id.tv_listCount)

        tie_emp= findViewById<TextInputEditText>(R.id.tie_emp)
        tie_Date = findViewById<TextInputEditText>(R.id.tie_Date)
        til_Date= findViewById<TextInputLayout>(R.id.til_Date)

        tie_Date!!.setOnClickListener(this)


        /*  val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
          val emp = EntrBySP.getString("UserCode", null)
          tie_emp!!.setText(emp)*/



    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tie_Date->{
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
                til_Date!!.setError("")





                //   dateMode==1


            }
            /*  R.id.tie_emp -> {
                  empUseBranch = 0
                  getEmpByBranch(0)


              }*/

        }
    }



    private fun getPunchReport(date: String) {
        Log.i("Dates",date)

        recyServiceList!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                punchReportViewModel.getPunchReprt(this,date!!)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (serviceList == 0) {
                                    serviceList++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   punchreport   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {
                                        //llback!!.visibility=View.VISIBLE
                                        //   crdv_empdetail!!.visibility=View.GONE


                                        val jobjt = jObject.getJSONObject("AttendancePunchDetails")




                                        attendancefirstpunchArrayList = jobjt.getJSONArray("AttendanceFirstPunchdetails")
                                        attendancelastpunchArrayList = jobjt.getJSONArray("AttendanceLastPunchdetails")
                                        if (attendancefirstpunchArrayList.length() > 0) {
                                            //  imgv_filter!!.visibility  =View.VISIBLE

                                            serviceListSort = JSONArray()
                                            outPunchSort = JSONArray()

                                            for (k in 0 until attendancefirstpunchArrayList.length()) {
                                                val jsonObject = attendancefirstpunchArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                serviceListSort.put(jsonObject)
                                            }
                                            Log.e(TAG, "msg   Array   " + serviceListSort.toString())

                                            for (k in 0 until attendancelastpunchArrayList.length()) {
                                                val jsonObject = attendancelastpunchArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                outPunchSort.put(jsonObject)
                                            }
                                            Log.e(TAG, "msg   punchinArray   " + serviceListSort.toString())
                                            Log.e(TAG, "msg   punchoutArray   " + outPunchSort.toString())


                                            //   tv_listCount!!.setText(""+serviceListSort.length())
                                            val lLayout = GridLayoutManager(this@AttendanceReportsActivity, 1)
                                            recyServiceList!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = AttendanceReportAdapter(this@AttendanceReportsActivity, serviceListSort,outPunchSort,date!!)
                                            recyServiceList!!.adapter = adapter
                                            adapter.setClickListener(this@AttendanceReportsActivity)

                                        }
                                    } else {
                                        // llback!!.visibility=View.GONE
                                        Log.i("ExMessage",exmessage.toString())
                                        val builder = AlertDialog.Builder(
                                            this@AttendanceReportsActivity,
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
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    override fun onClick(position: Int, data: String) {


        /*  if (data.equals("Service_timeLine"))
          {
              val jsonObject = serviceListArrayList.getJSONObject(position)

              TicketDate = jsonObject.getString("TicketDate")
              TicketStatus = jsonObject.getString("TicketStatus")
              ID_Master = jsonObject.getString("ID_Master")
              TransMode = jsonObject.getString("TransMode")

              Log.e(TAG, "time Flow 4545456 transmode  "+TransMode)
              Log.e(TAG, "time Flow 4545456 idcus  "+ID_Master )
              Log.e(TAG, "time Flow 4545456  status "+TicketStatus )


              val i = Intent(this@AttendanceReportListActivity, TimeFlowServiceActivity::class.java)
              i.putExtra("TicketDate",TicketDate)
              i.putExtra("TicketStatus",TicketStatus)
              i.putExtra("ID_Master",ID_Master)
              i.putExtra("TransMode",TransMode)
              startActivity(i)

              startActivity(i)
          }*/




    }



    override fun onRestart() {
        super.onRestart()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        /*  serviceList = 0

          val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
          val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")



          var dates = tie_Date!!.text.toString()
          val dateFrom = inputFormat.parse(dates)
          // val dateFrom = inputFormat.parse("08-04-2022")
          val strDate = outputFormat.format(dateFrom)


          Log.e(TAG,"org3   "+strDate)
  */
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = sdf.format(Date())
        System.out.println(" C DATE is  "+currentDate)
        tie_Date!!.setText(currentDate)
        serviceList = 0
        strToDate=currentDate


        serviceList=0
        getPunchReport(strToDate!!)
    }
    private fun openBottomDate() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        if (dateMode == 0){
            date_Picker1.maxDate = System.currentTimeMillis()
        }else if (dateMode == 1){

            date_Picker1.minDate = System.currentTimeMillis()
        }
        else if (dateMode == 2){
            date_Picker1.minDate = System.currentTimeMillis()
        }

        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {

            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon+1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1){
                    strDay ="0"+day
                }
                if (strMonth.length == 1){
                    strMonth ="0"+strMonth
                }

                if (dateMode == 0){


                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    checkCurrDate(""+strDay+"-"+strMonth+"-"+strYear)







                }else if (dateMode == 1){

                    tie_Date!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                    checkCurrDate(""+strDay+"-"+strMonth+"-"+strYear)




                    //     Log.e(TAG,"org1   "+strDate)


                    //getAttendanceReport(strDate)
                }

                /* else if (dateMode == 2){
                     tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                 }*/



            }
            catch (e: Exception){
                Log.e(TAG,"Exception   428   "+e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }
    private fun checkCurrDate(curDate : String) {
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
        val currentDate = sdf.format(Date())

        try {


            val newDate: Date = sdf.parse(currentDate)
            Log.e(TAG,"newDate  196  "+newDate)
            val sdfDate1 = SimpleDateFormat("dd-MM-yyyy")
            val sdfTime1 = SimpleDateFormat("hh:mm aa")
            val sdfTime2 = SimpleDateFormat("HH:mm", Locale.US)

            if (sdfDate1.format(newDate).equals(curDate)){
                Log.e(TAG,"Change date 2196   "+curDate)
                val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")



                var dates = curDate
                val dateFrom = inputFormat.parse(dates)
                // val dateFrom = inputFormat.parse("08-04-2022")
                val strDate = outputFormat.format(dateFrom)


                //  Log.e(TAG,"org1   "+strDate)
                //  ll_reclatndreprt!!.visibility=View.VISIBLE
                //  getPunchReport(strDate)
                //  tie_Time!!.setText(""+sdfTime1.format(newDate))
            }
            else
            {


                //  ll_reclatndreprt!!.visibility=View.VISIBLE
            }


//            tie_Date!!.setText(""+sdfDate1.format(newDate))
//            tie_FromDate!!.setText(""+sdfDate1.format(newDate))
//            //  strVisitDate = sdfDate2.format(newDate)
//
//            tie_Time!!.setText(""+sdfTime1.format(newDate))
//            //  strVisitTime = sdfTime2.format(newDate)

            val inputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")
            val outputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")



            var dates = tie_Date!!.text.toString()
            val dateFrom = inputFormat.parse(dates)
            // val dateFrom = inputFormat.parse("08-04-2022")
            val strDate = outputFormat.format(dateFrom)

            // empname=tie_emp!!.text.toString()



            Log.e(TAG,"org1   "+strDate)
            serviceList = 0
            getPunchReport(strDate)




        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }



}