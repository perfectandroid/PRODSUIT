package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelServiceAttended
import com.perfect.prodsuit.Model.ModelWalkingExist
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AssignedToAdapter
import com.perfect.prodsuit.View.Adapter.ServiceAttendedAdapter
import com.perfect.prodsuit.View.Adapter.WalkingExistingAdapter
import com.perfect.prodsuit.Viewmodel.AssignedToWalkingViewModel
import com.perfect.prodsuit.Viewmodel.WalkingUpdateViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class WalkingExistingActivity : AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG = "WalkingExistingActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var jsonObj: JSONObject? = null
    internal var recyDetail: RecyclerView? = null
    lateinit var walkExistList : JSONArray
    val modelWalkingExist = ArrayList<ModelWalkingExist>()
    var walkingExistingAdapter: WalkingExistingAdapter? = null

    private var tie_AssignedTo: TextInputEditText? = null
    private var tie_AssignedDate: TextInputEditText? = null

    private var btnSubmit: Button? = null

    var dateSelectMode: Int = 0

    var assignedToCount: Int = 0
    lateinit var assignedToWalkingViewModel: AssignedToWalkingViewModel
    lateinit var assignedToList : JSONArray
    lateinit var assignedToSortList : JSONArray
    private var dialogassignedTo : Dialog? = null
    var recyassignedTo: RecyclerView? = null

    lateinit var walkingUpdateViewModel: WalkingUpdateViewModel


    var ID_AssignedTo: String? = ""
    var strAssignedDate: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walking_existing)
        context = this@WalkingExistingActivity

        assignedToWalkingViewModel = ViewModelProvider(this).get(AssignedToWalkingViewModel::class.java)
        walkingUpdateViewModel = ViewModelProvider(this).get(WalkingUpdateViewModel::class.java)

        setRegViews()

        try {
            var jsonObject: String? = intent.getStringExtra("jsonObject")
            jsonObj = JSONObject(jsonObject)
            Log.e(TAG,"jsonObj  37777   "+jsonObj)
            showList(jsonObj!!)
        }catch (e: Exception){

        }

    }

    private fun showList(jsonObj: JSONObject) {
        val jobjt = jsonObj.getJSONObject("WalkingCustomerList")
        walkExistList = jobjt.getJSONArray("WalkingCustomerDetails")
        Log.e(TAG,"jsonObj  377771   "+jsonObj)
        modelWalkingExist.clear()
        for (i in 0 until walkExistList.length()) {

            var jsonObject = walkExistList.getJSONObject(i)
            modelWalkingExist!!.add(ModelWalkingExist(jsonObject.getString("Customer"),jsonObject.getString("Mobile")
                ,jsonObject.getString("Product"),jsonObject.getString("Action"),jsonObject.getString("AssignedTo"),jsonObject.getString("ID_LeadGenerateProduct")
                ,jsonObject.getString("FollowUpDate"),jsonObject.getString("FK_Employee"),jsonObject.getString("ID_Users"),jsonObject.getString("LeadNo")))

        }

        if (modelWalkingExist.size>0){
            Log.e(TAG,"modelWalkingExist  377772   "+modelWalkingExist[0].FollowUpDate)
            recyDetail!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
            walkingExistingAdapter = WalkingExistingAdapter(this@WalkingExistingActivity, modelWalkingExist)
            recyDetail!!.adapter = walkingExistingAdapter
            walkingExistingAdapter!!.setClickListener(this@WalkingExistingActivity)
        }


    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyDetail = findViewById(R.id.recyDetail)
        btnSubmit = findViewById(R.id.btnSubmit)

        btnSubmit!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.btnSubmit->{
               Config.disableClick(v)
                saveData()
            }
        }
    }

    private fun saveData() {
        for (k in 0 until modelWalkingExist.size) {

            Log.e(TAG,"14777"+k+"    Customer:  "+modelWalkingExist[k].Customer)
            Log.e(TAG,"14777"+k+"    Mobile:  "+modelWalkingExist[k].Mobile)
            Log.e(TAG,"14777"+k+"    Product:  "+modelWalkingExist[k].Product)
            Log.e(TAG,"14777"+k+"    Action:  "+modelWalkingExist[k].Action)
            Log.e(TAG,"14777"+k+"    AssignedTo:  "+modelWalkingExist[k].AssignedTo)
            Log.e(TAG,"14777"+k+"    ID_LeadGenerateProduct:  "+modelWalkingExist[k].ID_LeadGenerateProduct)
            Log.e(TAG,"14777"+k+"    FollowUpDate:  "+modelWalkingExist[k].FollowUpDate)
            Log.e(TAG,"14777"+k+"    FK_Employee:  "+modelWalkingExist[k].FK_Employee)
            Log.e(TAG,"14777"+k+"    ID_Users:  "+modelWalkingExist[k].ID_Users)
            Log.e(TAG,"14777"+k+"    LeadNo:  "+modelWalkingExist[k].LeadNo)

        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("clickLead")){
            Log.e(TAG,"clickLead  10000   "+modelWalkingExist[position].FollowUpDate)
            updateBottomSheet(position,modelWalkingExist)
        }

        if (data.equals("AssignedToClick")) {
            dialogassignedTo!!.dismiss()
            val jsonObject = assignedToSortList.getJSONObject(position)

            tie_AssignedTo!!.setText(jsonObject!!.getString("EmpName"))
            ID_AssignedTo = jsonObject.getString("ID_Employee")
        }
    }

    private fun updateBottomSheet(position: Int, modelWalkingExist: ArrayList<ModelWalkingExist>) {
        try {
            val dialog = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.walking_edit_bottomsheet, null)

            tie_AssignedTo = view.findViewById<TextInputEditText>(R.id.tie_AssignedTo)
            tie_AssignedDate = view.findViewById<TextInputEditText>(R.id.tie_AssignedDate)

            val btnCancel = view.findViewById<Button>(R.id.btnCancel)
            val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)

            val dateFormat = SimpleDateFormat("dd-MM-yyyy")

            val currentDate1 = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())


            val date1 = dateFormat.parse(currentDate1)
            val date2 = dateFormat.parse(modelWalkingExist[position].FollowUpDate)

            val timeInMillis1 = date1?.time ?: 0
            val timeInMillis2 = date2?.time ?: 0

            tie_AssignedTo!!.setText(modelWalkingExist[position].AssignedTo)
            ID_AssignedTo = modelWalkingExist[position].FK_Employee
            Log.e(TAG,"178883   "+timeInMillis1+"   :  "+timeInMillis2)
            Log.e(TAG,"178883   "+currentDate1+"   :  "+modelWalkingExist[position].FollowUpDate)
            if (timeInMillis1 >= timeInMillis2) {
                // date1 is greater than or equal to date2
                // Your code here
                    Log.e(TAG,"178881   "+currentDate1)
                tie_AssignedDate!!.setText(currentDate1)
                strAssignedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            } else {
                // date1 is less than date2
                // Your code here
                Log.e(TAG,"178882   "+modelWalkingExist[position].FollowUpDate)

                tie_AssignedDate!!.setText(modelWalkingExist[position].FollowUpDate)
                strAssignedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(modelWalkingExist[position].FollowUpDate)
            }




            tie_AssignedTo!!.setOnClickListener {
                Config.disableClick(it)
                assignedToCount = 0
                getAssignedTo()
            }

            tie_AssignedDate!!.setOnClickListener {
                dateSelectMode = 0
                openBottomSheet()
            }

            btnCancel.setOnClickListener {
                dialog .dismiss()
                ID_AssignedTo = ""
                strAssignedDate = ""
                tie_AssignedDate!!.setText("")
                tie_AssignedTo!!.setText("")
            }
            btnUpdate.setOnClickListener {
                dialog.dismiss()

                var assignDate = tie_AssignedDate!!.text.toString()
                var assignTo = tie_AssignedTo!!.text.toString()
                modelWalkingExist[position].FollowUpDate =  assignDate
                modelWalkingExist[position].AssignedTo =  assignTo
                modelWalkingExist[position].FK_Employee =  ID_AssignedTo!!
                walkingExistingAdapter!!.notifyItemChanged(position)
                //ApprovalListDetailActivity
             //   successBottomSheet()

            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog!!.setContentView(view)

            dialog.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
    }

    private fun successBottomSheet() {
        try {
            val dialog1 = BottomSheetDialog(this,R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.succes_bottomsheet, null)
            dialog1 .requestWindowFeature(Window.FEATURE_NO_TITLE)
            val window: Window? = dialog1.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog1!!.setCancelable(false)
            dialog1!!.setCanceledOnTouchOutside(false)
            dialog1!!.getBehavior().setDraggable(false);

            var tv_succesmsg = view.findViewById<TextView>(R.id.tv_succesmsg)
            var tv_gotit = view.findViewById<TextView>(R.id.tv_gotit)

            tv_succesmsg!!.setText("Success")
            tv_gotit!!.setOnClickListener {
                dialog1.dismiss()
            }


            dialog1!!.setContentView(view)
            dialog1.show()

        }catch (e: Exception){
            Log.e(TAG,"777  Exception   "+e.toString())
        }
    }


    private fun openBottomSheet() {
        // BottomSheet

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.bottomsheet_remark, null)

        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val txtSubmit = view.findViewById<TextView>(R.id.txtSubmit)
        val date_Picker1 = view.findViewById<DatePicker>(R.id.date_Picker1)

        date_Picker1.setMinDate(System.currentTimeMillis());
        date_Picker1.minDate = System.currentTimeMillis()


        txtCancel.setOnClickListener {
            dialog.dismiss()
        }
        txtSubmit.setOnClickListener {
            dialog.dismiss()
            try {
                //   date_Picker1!!.minDate = Calendar.getInstance().timeInMillis
                val day: Int = date_Picker1!!.getDayOfMonth()
                val mon: Int = date_Picker1!!.getMonth()
                val month: Int = mon + 1
                val year: Int = date_Picker1!!.getYear()
                var strDay = day.toString()
                var strMonth = month.toString()
                var strYear = year.toString()
                if (strDay.length == 1) {
                    strDay = "0" + day
                }
                if (strMonth.length == 1) {
                    strMonth = "0" + strMonth
                }

                tie_AssignedDate!!.setText("" + strDay + "-" + strMonth + "-" + strYear)
                strAssignedDate =  strYear + "-" + strMonth + "-" + strDay

            } catch (e: Exception) {
                Log.e(TAG, "Exception   428   " + e.toString())
            }
        }
        dialog.setCancelable(false)
        dialog!!.setContentView(view)

        dialog.show()
    }

    private fun getAssignedTo() {
        var ReqMode = "107"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                assignedToWalkingViewModel.getAssignedToWalking(this,ReqMode)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (assignedToCount == 0){
                                assignedToCount++
                                Log.e(TAG,"msg   2280   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    val jobjt = jObject.getJSONObject("EmployeeDetails")
                                    assignedToList = jobjt.getJSONArray("EmployeeDetailsList")
                                    if (assignedToList.length()>0){

                                        assignedToPopup(assignedToList)
                                    }
                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@WalkingExistingActivity,
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

    private fun assignedToPopup(assignedToList: JSONArray) {

        try {

            dialogassignedTo = Dialog(this)
            dialogassignedTo!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogassignedTo!! .setContentView(R.layout.assignedto_popup)
            dialogassignedTo!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyassignedTo = dialogassignedTo!! .findViewById(R.id.recyassignedTo) as RecyclerView
            val etsearch = dialogassignedTo!! .findViewById(R.id.etsearch) as EditText

            assignedToSortList = JSONArray()
            for (k in 0 until assignedToList.length()) {
                val jsonObject = assignedToList.getJSONObject(k)
                // reportNamesort.put(k,jsonObject)
                assignedToSortList.put(jsonObject)
            }


            val lLayout = GridLayoutManager(this@WalkingExistingActivity, 1)
            recyassignedTo!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = AssignedToAdapter(this@WalkingExistingActivity, assignedToSortList)
            recyassignedTo!!.adapter = adapter
            adapter.setClickListener(this@WalkingExistingActivity)


            etsearch!!.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                }

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    //  list_view!!.setVisibility(View.VISIBLE)
                    val textlength = etsearch!!.text.length
                    assignedToSortList = JSONArray()

                    for (k in 0 until assignedToList.length()) {
                        val jsonObject = assignedToList.getJSONObject(k)
                        if (textlength <= jsonObject.getString("EmpName").length) {
                            if (jsonObject.getString("EmpName")!!.toLowerCase().trim().contains(etsearch!!.text.toString().toLowerCase().trim())){
                                assignedToSortList.put(jsonObject)
                            }

                        }
                    }

                    Log.e(TAG,"assignedToSortList               7103    "+assignedToSortList)
                    val adapter = AssignedToAdapter(this@WalkingExistingActivity, assignedToSortList)
                    recyassignedTo!!.adapter = adapter
                    adapter.setClickListener(this@WalkingExistingActivity)
                }
            })

            dialogassignedTo!!.show()
            dialogassignedTo!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception  1394    "+e.toString())

        }
    }


}