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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.CommonSearchListAdapter
import com.perfect.prodsuit.View.Adapter.SearchModuleAdapter
import com.perfect.prodsuit.Viewmodel.CommonSearchListViewModel
import com.perfect.prodsuit.Viewmodel.SearchModuleViewModel
import org.json.JSONArray
import org.json.JSONObject

class CommonSearchActivity : AppCompatActivity(), View.OnClickListener, ItemClickListener {

    val TAG : String = "CommonSearchActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var til_Module: TextInputLayout? = null
    var til_Number: TextInputLayout? = null
    var til_Mobile: TextInputLayout? = null

    var tie_Module: TextInputEditText? = null
    var tie_Number: TextInputEditText? = null
    var tie_Mobile: TextInputEditText? = null

    var btn_Search: Button? = null
    var recycCommonSearch: RecyclerView? = null

    var commonSearchCount = 0
    lateinit var commonSearchListViewModel: CommonSearchListViewModel
    lateinit var commonSearchArrayList: JSONArray
    lateinit var commonSearchListAdapter : CommonSearchListAdapter

    var searchModuleCount = 0
    lateinit var searchModuleViewModel: SearchModuleViewModel
    lateinit var searchModuleArrayList: JSONArray
    lateinit var searchModuleAdapter : SearchModuleAdapter
    private var dialogModuleSheet : Dialog? = null

    var ID_Module = ""
    private lateinit var networkChangeReceiver: NetworkChangeReceiver


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_common_search)
        context = this@CommonSearchActivity
        commonSearchListViewModel = ViewModelProvider(this).get(CommonSearchListViewModel::class.java)
        searchModuleViewModel = ViewModelProvider(this).get(SearchModuleViewModel::class.java)
        setRegViews()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        til_Module = findViewById<TextInputLayout>(R.id.til_Module)
        til_Number = findViewById<TextInputLayout>(R.id.til_Number)
        til_Mobile = findViewById<TextInputLayout>(R.id.til_Mobile)

        tie_Module = findViewById<TextInputEditText>(R.id.tie_Module)
        tie_Number = findViewById<TextInputEditText>(R.id.tie_Number)
        tie_Mobile = findViewById<TextInputEditText>(R.id.tie_Mobile)

        recycCommonSearch = findViewById<RecyclerView>(R.id.recycCommonSearch)

        btn_Search = findViewById<Button>(R.id.btn_Search)
        tie_Module!!.setOnClickListener(this)
        btn_Search!!.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tie_Module->{
                Config.disableClick(v)
                searchModuleCount = 0
                getSearchModule()
            }

            R.id.btn_Search->{

                if (ID_Module.equals("")){
                    Config.snackBarWarning(context,v,"Select Module")
                }
                else{
                    validation(v)
                }

            }



        }
    }

    override fun onRestart() {
        super.onRestart()
        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }


    private fun validation(v: View) {

        commonSearchCount = 0
        getsearchData()
    }

    private fun getsearchData() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                commonSearchListViewModel.getCommonSearchList(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (commonSearchCount == 0) {
                                    commonSearchCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   120000   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("CommonSearchData")
                                        commonSearchArrayList = jobjt.getJSONArray("CommonSearchListData")

                                        Log.e(TAG, "commonSearchArrayList   120000   " + commonSearchArrayList)

                                        if (commonSearchArrayList.length()> 0){

                                            val lLayout = GridLayoutManager(this@CommonSearchActivity, 1)
                                            recycCommonSearch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                                            commonSearchListAdapter = CommonSearchListAdapter(this@CommonSearchActivity, commonSearchArrayList)
                                            recycCommonSearch!!.adapter = commonSearchListAdapter
                                           // commonSearchListAdapter.setClickListener(this@CommonSearchActivity)
                                        }

                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@CommonSearchActivity,
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

    private fun getSearchModule() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {

                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                searchModuleViewModel.getSearchModule(this)!!.observe(
                    this,
                    Observer { serviceSetterGetter ->

                        try {
                            val msg = serviceSetterGetter.message
                            if (msg!!.length > 0) {
                                if (searchModuleCount == 0) {
                                    searchModuleCount++
                                    val jObject = JSONObject(msg)
                                    Log.e(TAG, "msg   2120000   " + msg)
                                    if (jObject.getString("StatusCode") == "0") {

                                        val jobjt = jObject.getJSONObject("SearchModuleData")
                                        searchModuleArrayList = jobjt.getJSONArray("SearchModuleDataList")

                                        Log.e(TAG, "searchModuleArrayList   21200001   " + searchModuleArrayList)

                                        if (searchModuleArrayList.length()> 0){

                                            modulePopupSheet(searchModuleArrayList)

//                                            val lLayout = GridLayoutManager(this@CommonSearchActivity, 1)
//                                            recycCommonSearch!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                            commonSearchListAdapter = CommonSearchListAdapter(this@CommonSearchActivity, commonSearchArrayList)
//                                            recycCommonSearch!!.adapter = commonSearchListAdapter
//                                            // commonSearchListAdapter.setClickListener(this@CommonSearchActivity)
                                        }

                                    }
                                    else if (jObject.getString("StatusCode") == "105"){
                                        Config.logoutTokenMismatch(context,jObject)
                                    }
                                    else {
                                        val builder = AlertDialog.Builder(
                                            this@CommonSearchActivity,
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

    private fun modulePopupSheet(searchModuleArrayList : JSONArray) {

        try {
            dialogModuleSheet = Dialog(this)
            dialogModuleSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogModuleSheet!! .setContentView(R.layout.search_module_popup)
            dialogModuleSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL

            val window: Window? = dialogModuleSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            var recycModule = dialogModuleSheet!! .findViewById(R.id.recycModule) as RecyclerView
            val lLayout = GridLayoutManager(this@CommonSearchActivity, 1)
            recycModule!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            searchModuleAdapter = SearchModuleAdapter(this@CommonSearchActivity, searchModuleArrayList)
            recycModule!!.adapter = searchModuleAdapter
            searchModuleAdapter.setClickListener(this@CommonSearchActivity)

            dialogModuleSheet!!.show()

        }catch (e : Exception){

        }

    }

    override fun onClick(position: Int, data: String) {

        if (data.equals("moduleClicks")) {
            dialogModuleSheet!!.dismiss()
            val jsonObject = searchModuleArrayList.getJSONObject(position)

            Log.e(TAG,"Module  2999  "+jsonObject.getString("Module"))
            ID_Module = jsonObject.getString("ID_Module")
            tie_Module!!.setText(jsonObject.getString("Module"))
            tie_Number!!.setText("")
            tie_Mobile!!.setText("")
        }

    }

}