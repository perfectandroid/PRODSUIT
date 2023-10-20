package com.perfect.prodsuit.View.Activity

import android.app.*
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.AssignedTicketListViewModel
import org.json.JSONArray
import org.json.JSONObject
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.perfect.prodsuit.View.Adapter.AssignedTicketsAdapter

class AssignedTicketsActivity :AppCompatActivity() , View.OnClickListener, ItemClickListener {

    var TAG  ="AssignedTicketsActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var assignedTicketListViewModel: AssignedTicketListViewModel
    lateinit var asgnedtktListArrayList: JSONArray
    lateinit var asgnedtktListSort: JSONArray
    var rclrvw_asgndtkts: RecyclerView? = null
    var imgv_filter: ImageView? = null
    var txtv_headlabel: TextView? = null
    var tv_listCount: TextView? = null

    var date :String = ""
    var FK_Emp :String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity__assignedtkts)
        context = this@AssignedTicketsActivity
        assignedTicketListViewModel = ViewModelProvider(this).get(AssignedTicketListViewModel::class.java)


        setRegViews()
        FK_Emp = intent.getStringExtra("FK_Employee")!!
        date = intent.getStringExtra("Date")!!
        Log.e(TAG,"Parameters    "+FK_Emp+"\n"+date)
        getAssignedTicketList()

    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
        imgv_filter!!.setOnClickListener(this)

        txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

        rclrvw_asgndtkts = findViewById<RecyclerView>(R.id.rclrvw_asgndtkts)
        rclrvw_asgndtkts!!.adapter = null
        tv_listCount = findViewById<TextView>(R.id.tv_listCount)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.imgv_filter->{
                /*Config.disableClick(v)
                filterBottomSheet()*/

            }


        }
    }





    override fun onClick(position: Int, data: String) {

        if (data.equals("ServiceList")) {
            /*  val jsonObject = serviceListArrayList.getJSONObject(position)
              ID_CustomerServiceRegister = jsonObject.getString("ID_CustomerServiceRegister")
              FK_CustomerserviceregisterProductDetails = jsonObject.getString("ID_CustomerServiceRegisterProductDetails")
              TicketDate = jsonObject.getString("TicketDate")
              TicketStatus = jsonObject.getString("TicketStatus")

              Log.i("FKK",FK_CustomerserviceregisterProductDetails.toString())
              val i = Intent(this@ServiceHistoryActivity, ServiceAssignActivity::class.java)
              i.putExtra("ID_CustomerServiceRegister",ID_CustomerServiceRegister)
              i.putExtra("FK_CustomerserviceregisterProductDetails",FK_CustomerserviceregisterProductDetails)
              i.putExtra("TicketStatus",TicketStatus)
              i.putExtra("TicketDate",TicketDate)
              startActivity(i)*/
        }





    }

    private fun getAssignedTicketList() {
//        recyServiceList!!.adapter = null
//        tv_listCount!!.setText("0")
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                assignedTicketListViewModel.getAssignedTickets(this,FK_Emp,date)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {


                                val jObject = JSONObject(msg)
                                Log.e(TAG, "msg   assignedtickets   " + msg)
                                if (jObject.getString("StatusCode") == "0") {
                                   val jobjt = jObject.getJSONObject("ServiceAssignedWork")
                                    asgnedtktListArrayList = jobjt.getJSONArray("ServiceAssignedWorkList")
                                    if (asgnedtktListArrayList.length() > 0) {
                                        // imgv_filter!!.visibility  =View.VISIBLE

                                        asgnedtktListSort = JSONArray()
                                        for (k in 0 until asgnedtktListArrayList.length()) {
                                            val jsonObject = asgnedtktListArrayList.getJSONObject(k)
                                            // reportNamesort.put(k,jsonObject)
                                            asgnedtktListSort.put(jsonObject)
                                        }

                                        tv_listCount!!.setText(""+asgnedtktListSort.length())
                                        val lLayout = GridLayoutManager(this@AssignedTicketsActivity, 1)
                                        rclrvw_asgndtkts!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                        //  val adapter = ServiceListAdapter(this@ServiceAssignListActivity, serviceListArrayList,SubMode!!)
                                        val adapter = AssignedTicketsAdapter(this@AssignedTicketsActivity,asgnedtktListArrayList!!)
                                        rclrvw_asgndtkts!!.adapter = adapter
                                        adapter.setClickListener(this@AssignedTicketsActivity)

                                    }
                                } else {
                                    val builder = AlertDialog.Builder(
                                        this@AssignedTicketsActivity,
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

    override fun onRestart() {
        super.onRestart()
        //serviceList = 0
        getAssignedTicketList()
    }


}