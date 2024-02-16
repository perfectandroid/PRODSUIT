package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
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
import com.perfect.prodsuit.Helper.ProdsuitApplication
import com.perfect.prodsuit.View.Adapter.AttendanceReportAdapter
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class AttendanceReportActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG  ="AttendanceReportActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var attendanceReportViewModel: AttendanceReportViewModel
    lateinit var attendancereportListArrayList: JSONArray
    lateinit var attendancereportSort: JSONArray

    var rclrvw_attendncereport: RecyclerView? = null
    private var tv_listCount: TextView? = null
    private var txtv_headlabel: TextView? = null
    private var imgv_filter: ImageView? = null
    private var tie_emp: TextInputEditText? = null
    private var tie_Date: TextInputEditText? = null
    private var til_Date: TextInputLayout? = null
    private var ll_reclatndreprt: LinearLayout? = null


    var serviceList = 0
    var label : String?= ""
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

    private var tie_Status: TextInputEditText? = null
    private var tie_AttendedBy: TextInputEditText? = null

    private var tv_Ticket: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_Name: TextView? = null
    private var tv_Complaint: TextView? = null
    private var txtReset: TextView? = null
    private var txtUpdate: TextView? = null

    private var tie_TicketNumber: TextInputEditText? = null
    private var tie_Branch: TextInputEditText? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_DueDays: TextInputEditText? = null

    private var txtFilterReset: TextView? = null
    private var txtFilterSearch: TextView? = null

    var filterTicketNumber: String? = ""
    var filterBranch : String? = ""
    var filterCustomer : String? = ""
    var filterMobile : String? = ""
    var filterArea   : String? = ""
    var filterDueDays : String? = ""
    var exmessage : String? = ""


    var statusCount = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null
    var dateMode = 0
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
    lateinit var attendanceReportViewMode: AttendanceReportViewModel
    var ID_CustomerServiceRegister: String? = ""
    var FK_CustomerserviceregisterProductDetails: String? = ""
    var TicketStatus: String? = ""
    var TicketDate: String? = ""

    var ID_Priority: String? = ""


    var strEditTicket : String?= ""
    var strEditProductComplaint : String?= ""
    var strEditCustomer : String?= ""
    var strEditProductname : String?= ""


    var serUpdateCount = 0
    var strVisitDate : String?= ""
    var saveAttendanceMark = false
    val jsons= JSONObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_attendance_report)
        context = this@AttendanceReportActivity
        attendanceReportViewModel = ViewModelProvider(this).get(AttendanceReportViewModel::class.java)

        setRegViews()



         //   getAttedanceReport(strToDate)


      /*  if (tie_Date!!.text.toString().equals("Date")) {
            //  Config.snackBars(context, v, "Select Date")
        }
        else
        {
            getAttedanceReport(tie_Date!!.text.toString())
        }
*/








        /*label   = intent.getStringExtra("label")
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


        serviceList = 0
        getServiceNewList()*/


    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_emp= findViewById<TextInputEditText>(R.id.tie_emp)
        tie_Date = findViewById<TextInputEditText>(R.id.tie_Date)
        til_Date= findViewById<TextInputLayout>(R.id.til_Date)
      //  ll_reclatndreprt= findViewById<TextInputLayout>(R.id.ll_reclatndreprt)


        tie_Date!!.setOnClickListener(this)


        val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF36, 0)
        val emp = EntrBySP.getString("UserCode", null)
        tie_emp!!.setText(emp)





        if(strToDate!!.equals("Date")||strToDate!!.equals("")||strToDate!!.equals(null))
        {
            til_Date!!.setError("Please select a Date")
            til_Date!!.setErrorIconDrawable(null)

            Log.i(TAG,"Date : "+strToDate)
        }


        rclrvw_attendncereport= findViewById<RecyclerView>(R.id.rclrvw_attendncereport)
       /* imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        recyServiceList = findViewById<RecyclerView>(R.id.recyServiceList)
        recyServiceList!!.adapter = null
        tv_listCount = findViewById<TextView>(R.id.tv_listCount)*/

    }

    override fun onClick(v: View) {
        when(v.id){
           R.id.imback->{
                finish()
            }
            /*  R.id.tie_Status->{
                 Config.disableClick(v)
                 statusCount = 0
                 ReqMode = "17"
                 getStatus("2")
             }

             R.id.tie_AttendedBy->{
                 Config.disableClick(v)
                 attendCount = 0
                 getChannelEmp()
             }
             R.id.imgv_filter->{
                 Config.disableClick(v)
                 filterBottomSheet()

             }*/
            R.id.tie_Date->{
                Config.disableClick(v)
                dateMode = 0
                openBottomDate()
                til_Date!!.setError("")

                val EntrBySP = context.getSharedPreferences(Config.SHARED_PREF83, 0)
                val atndncedte = EntrBySP.getString("attendance", null)


                Log.e(TAG,"ATTENDANCE"+tie_Date!!.text.toString())



            }

        }
    }

    private fun getAttedanceReport(strToDate: String?) {
        rclrvw_attendncereport!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                attendanceReportViewModel.getAttendanceReprt(this,strToDate)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {

                                if (serviceList == 0) {
                                    serviceList++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   attendacereprt   " + msg)

                                    if (jObject.getString("StatusCode") == "0") {
                                        val jobjt = jObject.getJSONObject("AttendanceDetails")
                                     //   Log.e(TAG, "msg   attendacereprt   " + c)



                                        attendancereportListArrayList = jobjt.getJSONArray("AttendanceDetailsList")

                                        if (attendancereportListArrayList.length() > 0) {
                                            imgv_filter!!.visibility  =View.VISIBLE

                                            attendancereportSort = JSONArray()

                                            for (k in 0 until attendancereportListArrayList.length()) {
                                                val jsonObject = attendancereportListArrayList.getJSONObject(k)
                                                // reportNamesort.put(k,jsonObject)
                                                attendancereportSort.put(jsonObject)
                                            }

                                           // tv_listCount!!.setText(""+serviceListSort.length())
                                           /* val lLayout = GridLayoutManager(this@AttendanceReportActivity, 1)
                                            rclrvw_attendncereport!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                          //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                            val adapter = AttendanceReportAdapter(this@AttendanceReportActivity, attendancereportSort!!)
                                            rclrvw_attendncereport!!.adapter = adapter
                                            adapter.setClickListener(this@AttendanceReportActivity)*/

                                        }
                                    } else {

                                        Log.i("ExMessage",exmessage.toString())
                                        val builder = AlertDialog.Builder(
                                            this@AttendanceReportActivity,
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

       /* if (data.equals("ServiceList")) {
            val jsonObject = serviceListArrayList.getJSONObject(position)
         //   ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
          //  FK_CustomerserviceregisterProductDetails = jsonObject.getString("ID_CustomerServiceRegisterProductDetails")
            TicketDate = jsonObject.getString("TicketDate")
            TicketStatus = jsonObject.getString("TicketStatus")


            if(!TicketStatus.equals("3"))
            {
                val idcustservceregstSP = context.getSharedPreferences(Config.SHARED_PREF72, 0)
                ID_CustomerServiceRegister = idcustservceregstSP.getString("idcustsrvceregist","")

                val idcustservceregstprdctdetlSP = context.getSharedPreferences(Config.SHARED_PREF73, 0)
                FK_CustomerserviceregisterProductDetails = idcustservceregstprdctdetlSP.getString("idcustsrvceregistproductdetail","")


                Log.i("FKK",FK_CustomerserviceregisterProductDetails.toString()+"\n"+ID_CustomerServiceRegister)
                val i = Intent(this@AttendanceReportActivity, ServiceAssignActivity::class.java)
                i.putExtra("ID_CustomerServiceRegister",ID_CustomerServiceRegister)
                i.putExtra("FK_CustomerserviceregisterProductDetails",FK_CustomerserviceregisterProductDetails)
                i.putExtra("TicketStatus",TicketStatus)
                i.putExtra("TicketDate",TicketDate)
                startActivity(i)
            }

        }
        if (data.equals("ServiceEdit")) {

//            val i = Intent(this@ServiceAssignListActivity, ServiceAssignActivity::class.java)
//            startActivity(i)

           // serviceEditBottom()
            checkAttendance()
            if (saveAttendanceMark){
                val jsonObject = serviceListArrayList.getJSONObject(position)
                ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
                serAssignCount = 0
                getServiceAssignDetails()
            }


        }*/





    }


    override fun onRestart() {
        super.onRestart()
        serviceList = 0
        getAttedanceReport(strToDate)
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

                }

               /* else if (dateMode == 2){
                    tie_ToDate!!.setText(""+strDay+"-"+strMonth+"-"+strYear)
                }*/

                val attendSP = context.getSharedPreferences(Config.SHARED_PREF83, 0)
                val attendSPEditer = attendSP.edit()
                attendSPEditer.putString("attendance", "")
                attendSPEditer.commit()


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


                Log.e(TAG,"org1   "+strDate)
              //  ll_reclatndreprt!!.visibility=View.VISIBLE
                getAttedanceReport(strDate)
              //  tie_Time!!.setText(""+sdfTime1.format(newDate))
            }
            else
            {
                val inputFormat1: DateFormat = SimpleDateFormat("dd-MM-yyyy")
                val outputFormat1: DateFormat = SimpleDateFormat("yyyy-MM-dd")


                val dateFrom = inputFormat1.parse(tie_Date!!.text.toString())
                // val dateFrom = inputFormat.parse("08-04-2022")
                val strDate2 = outputFormat1.format(dateFrom)

                Log.e(TAG,"org2   "+strDate2)
                getAttedanceReport(strDate2)
              //  ll_reclatndreprt!!.visibility=View.VISIBLE
            }


//            tie_Date!!.setText(""+sdfDate1.format(newDate))
//            tie_FromDate!!.setText(""+sdfDate1.format(newDate))
//            //  strVisitDate = sdfDate2.format(newDate)
//
//            tie_Time!!.setText(""+sdfTime1.format(newDate))
//            //  strVisitTime = sdfTime2.format(newDate)


        }catch (e: Exception){

            Log.e(TAG,"Exception 196  "+e.toString())
        }
    }


}