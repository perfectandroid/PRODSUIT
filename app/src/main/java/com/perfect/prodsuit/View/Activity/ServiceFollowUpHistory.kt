package com.perfect.prodsuit.View.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Model.AttendanceFollowUpModel
import com.perfect.prodsuit.Model.HistoryFollowUpModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.HistoryServiceFollowUpAdapter
import org.json.JSONArray

class ServiceFollowUpHistory : AppCompatActivity(),View.OnClickListener {
    lateinit var recycleView_history: RecyclerView
    lateinit var jsonArrayHistory: JSONArray
    lateinit var imback:ImageView
    var adapterHistoryServiceFollowUp: HistoryServiceFollowUpAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_service_follow_up_history)
        setId()
        loadArray()
        SetDataToRecycler(jsonArrayHistory)
    }

    private fun setId() {
        imback=findViewById(R.id.imback)
        recycleView_history=findViewById(R.id.recycleView_history)
        imback.setOnClickListener(this)
    }

    private fun loadArray() {
        val historyFollowUpArrayList = ArrayList<HistoryFollowUpModel>()
        val e1 = HistoryFollowUpModel(
            "12458",
            "Battery Check",
            "Loading issue",
            "Pending",
            "26/07/2022",
            "sdsdsdsdsdsds"
        )
        val e2 = HistoryFollowUpModel(
            "47821",
            "Starting Motor",
            "Starting issue",
            "Closed",
            "26/07/2022",
            "dfdfsdfdfddd"
        )
        val e3 = HistoryFollowUpModel(
            "12458",
            "Battery Check",
            "Loading issue",
            "Closed",
            "26/07/2022",
            "sdsdsdsdsdsds"
        )
        historyFollowUpArrayList.add(e1)
        historyFollowUpArrayList.add(e2)
        historyFollowUpArrayList.add(e3)
        val gson = Gson()
        val listString = gson.toJson(
            historyFollowUpArrayList,
            object : TypeToken<ArrayList<AttendanceFollowUpModel?>?>() {}.type
        )
        jsonArrayHistory = JSONArray(listString)
        SetDataToRecycler(jsonArrayHistory)
    }

    private fun SetDataToRecycler(jsonArrayHistoryData: JSONArray) {
        recycleView_history!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        adapterHistoryServiceFollowUp = HistoryServiceFollowUpAdapter(this, jsonArrayHistoryData)
        recycleView_history!!.adapter = adapterHistoryServiceFollowUp
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
           R.id.imback->
           {
               onBackPressed()
           }
        }
    }
}