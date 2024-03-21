package com.perfect.prodsuit.View.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.MyActivitysActionCountModelList
import com.perfect.prodsuit.Model.MyActivitysActionDetailsModelList
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.MyActivitysActionCountAdapter
import com.perfect.prodsuit.View.Adapter.MyActivitysActionDetailsAdapter
import com.perfect.prodsuit.View.Adapter.MyActivitysCountAdapter
import com.perfect.prodsuit.View.Adapter.MyActivitysFlitersAdapter
import com.perfect.prodsuit.Viewmodel.MyActivityCountViewModel
import com.perfect.prodsuit.Viewmodel.MyActivitysActionCountViewModel
import com.perfect.prodsuit.Viewmodel.MyActivitysActionDetailsViewModel
import com.perfect.prodsuit.Viewmodel.MyActivitysFlitersViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class MyActivity : AppCompatActivity()  , View.OnClickListener, ItemClickListener {

    var TAG ="MyActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null
    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    var dialogFilter: BottomSheetDialog? = null

    private var imgv_filterManage: ImageView? = null
    private var ll_filterlabel: LinearLayout? = null
    private var tv_filterlabel: TextView? = null

    var ID_ActionType : String = "0"
    var IdFliter : String = "0"
    var SubMode : String = "0"

    var myActivitysFliters = 0
    lateinit var myActivitysFlitersViewModel: MyActivitysFlitersViewModel
    lateinit var myActivitysFlitersArrayList : JSONArray
    var recy_filter: RecyclerView? = null
    var myActivitysFlitersAdapter  : MyActivitysFlitersAdapter? = null


    var myActivityCount = 0
    lateinit var myActivityCountViewModel: MyActivityCountViewModel
    var recy_activitycount: RecyclerView? = null
    lateinit var activityCountArrayList : JSONArray
    var myActivitysCountAdapter  : MyActivitysCountAdapter? = null
    var myActivityCountMode = 0



    var myActivityActionCount = 0
    lateinit var myActivityActionCountViewModel: MyActivitysActionCountViewModel
    var recy_actioncount: RecyclerView? = null
    var myActivitysActionCountModelList = ArrayList<MyActivitysActionCountModelList>()
    var myActivitysActionCountAdapter  : MyActivitysActionCountAdapter? = null
    var myActivityActionCountMode = 0

    var myActivitysActionDetailsCount = 0
    lateinit var myActivitysActionDetailsViewModel: MyActivitysActionDetailsViewModel
    var recy_actiondetails: RecyclerView? = null
    var myActivitysActionDetailsModelList = ArrayList<MyActivitysActionDetailsModelList>()
    var myActivitysActionDetailsAdapter  : MyActivitysActionDetailsAdapter? = null
    lateinit var myActivitysActionDetailsArrayList : JSONArray






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_my)
        context = this@MyActivity

        myActivitysFlitersViewModel = ViewModelProvider(this).get(MyActivitysFlitersViewModel::class.java)
        myActivityCountViewModel = ViewModelProvider(this).get(MyActivityCountViewModel::class.java)
        myActivityActionCountViewModel = ViewModelProvider(this).get(MyActivitysActionCountViewModel::class.java)
        myActivitysActionDetailsViewModel = ViewModelProvider(this).get(MyActivitysActionDetailsViewModel::class.java)

        setRegViews()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

        myActivitysFliters = 0
        getMyActivityFilter()

//        myActivityCount = 0
//        getMyActivityCount()

//        myActivityActionCount = 0
//        getMyActivityActionCount()

    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recy_activitycount = findViewById<RecyclerView>(R.id.recy_activitycount)
        recy_actioncount = findViewById<RecyclerView>(R.id.recy_actioncount)
        recy_actiondetails = findViewById<RecyclerView>(R.id.recy_actiondetails)

        tv_filterlabel = findViewById<TextView>(R.id.tv_filterlabel)
        ll_filterlabel = findViewById<LinearLayout>(R.id.ll_filterlabel)

        imgv_filterManage = findViewById<ImageView>(R.id.imgv_filterManage)
        imgv_filterManage!!.setOnClickListener(this)
    }

    private fun getMyActivityFilter() {
        var ReqMode = "4"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                myActivitysFlitersViewModel.getMyActivitysFliters(this, ReqMode!!)!!.observe(this) { todolistSetterGetter ->
                    try {
                        val msg = todolistSetterGetter.message
                        Log.e("weweqweqwe", "msg=" + msg)

                        if (msg!!.length > 0) {
                            if (myActivityCount == 0) {
                                myActivityCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"156666 jObject     "+jObject)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("MyActivitysFliters")
                                    myActivitysFlitersArrayList = jobjt.getJSONArray("MyActivitysFlitersList")

                                    if (myActivitysFlitersArrayList.length() > 0){
//                                        ll_filterlabel!!.visibility = View.VISIBLE
//                                        val lLayout = GridLayoutManager(this@MyActivity, 1)
//                                        recy_activitycount!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
//                                        myActivitysCountAdapter = MyActivitysCountAdapter(this@MyActivity,activityCountArrayList,myActivityCountMode)
//                                        recy_activitycount!!.adapter = myActivitysCountAdapter
//                                        myActivitysCountAdapter!!.setClickListener(this@MyActivity)
//                                        var jsonObject = activityCountArrayList.getJSONObject(0)
//                                        SubMode = jsonObject.getString("SubMode")
////
//                                        myActivityActionCount = 0
//                                        getMyActivityActionCount()

                                        var jsonObject = myActivitysFlitersArrayList.getJSONObject(0)
                                        IdFliter = jsonObject.getString("IdFliter")
                                        tv_filterlabel!!.setText(jsonObject.getString("FliterType"))

                                        myActivityCount = 0
                                        getMyActivityCount()

                                    }

                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(this@MyActivity, R.style.MyDialogTheme)
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        // dialogInterface.dismiss()
                                        onBackPressed()
                                        //                                               finish()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }

                            }
                        }


                    } catch (e: Exception) {
                        Log.i("responseCatch", "ex=" + e.printStackTrace())
                    }
                }
                progressDialog!!.dismiss()
            }
            else -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun getMyActivityCount() {
        recy_actioncount!!.adapter = null
        recy_actiondetails!!.adapter = null
        var ReqMode = "1"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                myActivityCountViewModel.getMyActivityCount(this, ReqMode!!,IdFliter)!!.observe(this) { todolistSetterGetter ->
                    try {
                        val msg = todolistSetterGetter.message
                        Log.e("weweqweqwe", "msg=" + msg)

                        if (msg!!.length > 0) {
                            if (myActivityCount == 0) {
                                myActivityCount++
                                Log.e("weweqweqwewrwrwe", "msg=" + msg)
                                val jObject = JSONObject(msg)
                                Log.e(TAG,"788888 jObject     "+jObject)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("MyActivityCount")
                                    activityCountArrayList = jobjt.getJSONArray("MyActivityCountList")

                                    if (activityCountArrayList.length() > 0){
                                        ll_filterlabel!!.visibility = View.VISIBLE
                                        val lLayout = GridLayoutManager(this@MyActivity, 1)
                                        recy_activitycount!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
                                        myActivitysCountAdapter = MyActivitysCountAdapter(this@MyActivity,activityCountArrayList,myActivityCountMode)
                                        recy_activitycount!!.adapter = myActivitysCountAdapter
                                        myActivitysCountAdapter!!.setClickListener(this@MyActivity)
                                        var jsonObject = activityCountArrayList.getJSONObject(0)
                                        SubMode = jsonObject.getString("SubMode")
//
                                        myActivityActionCount = 0
                                        getMyActivityActionCount()
                                    }

                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(this@MyActivity, R.style.MyDialogTheme)
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        // dialogInterface.dismiss()
                                        onBackPressed()
                                        //                                               finish()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }

                            }
                        }


                    } catch (e: Exception) {
                        Log.i("responseCatch", "ex=" + e.printStackTrace())
                    }
                }
                progressDialog!!.dismiss()
            }
            else -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    private fun getMyActivityActionCount() {
        var ReqMode = "2"
//        var SubMode1 = "1"
        recy_actioncount!!.adapter = null
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                myActivityActionCountViewModel.getMyActivityActionCount(this, ReqMode!!,SubMode,IdFliter)!!.observe(this) { todolistSetterGetter ->
                    try {
                        val msg = todolistSetterGetter.message

                        if (msg!!.length > 0) {
                            if (myActivityActionCount == 0) {
                                myActivityActionCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"1433333 jObject     "+jObject)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("MyActivitysActionCountDetails")
                                    var myActivitysActionCounArrayList = jobjt.getJSONArray("MyActivitysActionCountList")

                                    val gson = Gson()
                                    myActivitysActionCountModelList = gson.fromJson(myActivitysActionCounArrayList.toString(),Array<MyActivitysActionCountModelList>::class.java).toList() as ArrayList<MyActivitysActionCountModelList>

                                    Log.e(TAG,"143333311   "+myActivitysActionCountModelList.size)
                                    if (myActivitysActionCountModelList.size > 0){
                                        val lLayout = GridLayoutManager(this@MyActivity, 1)
                                        recy_actioncount!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false))
                                        myActivitysActionCountAdapter = MyActivitysActionCountAdapter(this@MyActivity,myActivitysActionCountModelList,myActivityActionCountMode)
                                        recy_actioncount!!.adapter = myActivitysActionCountAdapter
                                        myActivitysActionCountAdapter!!.setClickListener(this@MyActivity)

                                        ID_ActionType = myActivitysActionCountModelList[0].ID_ActionType
                                        myActivitysActionDetailsCount = 0
                                        getMyActivitysActionDetails(ID_ActionType)
                                    }



                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(this@MyActivity, R.style.MyDialogTheme)
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        // dialogInterface.dismiss()
                                        onBackPressed()
                                        //                                               finish()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }

                            }
                        }


                    } catch (e: Exception) {
                        Log.i("responseCatch", "ex=" + e.printStackTrace())
                    }
                }
                progressDialog!!.dismiss()
            }
            else -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }


    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
            R.id.imgv_filterManage->{
                Config.disableClick(v)
                filterDateBottomsheet(v)
            }
        }
    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("activityfiltersClick")) {
            dialogFilter!!.dismiss()
            var jsonObject = myActivitysFlitersArrayList.getJSONObject(position)
            IdFliter = jsonObject.getString("IdFliter")
            tv_filterlabel!!.setText(jsonObject.getString("FliterType"))

            myActivityCount = 0
            getMyActivityCount()

        }

        if (data.equals("activitycountClick")) {

            recy_actioncount!!.adapter = null
            recy_actiondetails!!.adapter = null

            var jsonObject = activityCountArrayList.getJSONObject(position)
            SubMode = jsonObject.getString("SubMode")
//
            myActivityActionCount = 0
            getMyActivityActionCount()
        }

        if (data.equals("actionCountDetailClick")) {

            recy_actiondetails!!.adapter = null
            Log.e(TAG,"213333   "+position)
            ID_ActionType  = myActivitysActionCountModelList.get(position).ID_ActionType

            myActivitysActionDetailsCount = 0
            getMyActivitysActionDetails(ID_ActionType)
        }
    }

    private fun getMyActivitysActionDetails(ID_ActionType: String) {
        recy_actiondetails!!.adapter = null
        Log.e(TAG,"213334   "+ID_ActionType)
        var ReqMode = "3"
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                myActivitysActionDetailsViewModel.getMyActivitysActionDetails(this, ReqMode!!,SubMode,ID_ActionType,IdFliter)!!.observe(this) { todolistSetterGetter ->
                    try {
                        val msg = todolistSetterGetter.message

                        if (msg!!.length > 0) {
                            if (myActivitysActionDetailsCount == 0) {
                                myActivitysActionDetailsCount++

                                val jObject = JSONObject(msg)
                                Log.e(TAG,"2488882 jObject     "+jObject)
                                if (jObject.getString("StatusCode") == "0") {

                                    val jobjt = jObject.getJSONObject("MyActivitysActionDetails")
                                    myActivitysActionDetailsArrayList = jobjt.getJSONArray("MyActivitysActionList")
                                    Log.e(TAG,"24888821 myActivitysActionCounArrayList     "+myActivitysActionDetailsArrayList)

//                                    val gson = Gson()
//                                    myActivitysActionDetailsModelList = gson.fromJson(myActivitysActionCounArrayList1.toString(),Array<MyActivitysActionDetailsModelList>::class.java).toList() as ArrayList<MyActivitysActionDetailsModelList>

//                                    val gson = Gson()
//                                    myActivitysActionDetailsModelList = gson.fromJson(myActivitysActionCounArrayList.toString(),Array<MyActivitysActionDetailsModelList>::class.java).toList() as ArrayList<MyActivitysActionDetailsModelList>
                                    Log.e(TAG,"2488883 myActivitysActionDetailsModelList     "+myActivitysActionDetailsModelList.size)
                                    if (myActivitysActionDetailsArrayList.length()> 0){
                                        val lLayout = GridLayoutManager(this@MyActivity, 1)
                                        recy_actiondetails!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                                        myActivitysActionDetailsAdapter = MyActivitysActionDetailsAdapter(this@MyActivity,myActivitysActionDetailsArrayList)
                                        recy_actiondetails!!.adapter = myActivitysActionDetailsAdapter
                                        myActivitysActionDetailsAdapter!!.setClickListener(this@MyActivity)
                                    }



                                }
                                else if (jObject.getString("StatusCode") == "105"){
                                    Config.logoutTokenMismatch(context,jObject)
                                }
                                else {
                                    val builder = AlertDialog.Builder(this@MyActivity, R.style.MyDialogTheme)
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                        // dialogInterface.dismiss()
                                        onBackPressed()
                                        //                                               finish()
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }

                            }
                        }


                    } catch (e: Exception) {
                        Log.e("24888824  responseCatch", "ex=" + e.toString())
                    }
                }
                progressDialog!!.dismiss()
            }
            else -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun filterDateBottomsheet(v: View) {

        try {

            dialogFilter = BottomSheetDialog(this)
            val view = layoutInflater.inflate(R.layout.filter_activity_bottom, null)
            recy_filter = view.findViewById<RecyclerView>(R.id.recy_filter)

            if (myActivitysFlitersArrayList.length() <= 0){
                myActivitysFliters = 0
                getMyActivityFilter()
            }

            if (myActivitysFlitersArrayList.length()>0){
                recy_filter!!.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false))
                myActivitysFlitersAdapter = MyActivitysFlitersAdapter(this@MyActivity,myActivitysFlitersArrayList)
                recy_filter!!.adapter = myActivitysFlitersAdapter
                myActivitysFlitersAdapter!!.setClickListener(this@MyActivity)
            }


            dialogFilter!!.setCancelable(true)
            dialogFilter!!.setContentView(view)

            dialogFilter!!.show()
        }catch (e: Exception){

        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }


}