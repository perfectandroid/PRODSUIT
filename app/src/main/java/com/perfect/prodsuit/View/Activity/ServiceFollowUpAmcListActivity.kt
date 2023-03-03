package com.perfect.prodsuit.View.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.ItemClickListenerData
import com.perfect.prodsuit.Model.AmcFollowUpListModel
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.Model.ServiceFollowUpListModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AmcFollowUpListAdapter
import com.perfect.prodsuit.View.Adapter.ServiceFollowUpListAdapter
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class ServiceFollowUpAmcListActivity : AppCompatActivity(), View.OnClickListener,
    ItemClickListenerData {
    lateinit var imback: ImageView
    lateinit var jsonArray: JSONArray
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_service_follow_up_amc_list)
        setId()
        setListners()
        loadData()
    }

    private fun setId() {
        recyclerView = findViewById(R.id.recycler)
        imback = findViewById(R.id.imback)
    }

    private fun setListners() {
        imback.setOnClickListener(this)
    }

    private fun loadData() {
        val amcFollowArrayList = ArrayList<AmcFollowUpListModel>()
        val e1 =
            AmcFollowUpListModel(
                "SALEBILL-230",
                "21/07/2022",
                "Hi -Lite Branch"
            )
        val e2 =
            AmcFollowUpListModel(
                "SALEBILL-4756",
                "06/02/2023",
                "Hi -Lite Branch"
            )
        val e3 =
            AmcFollowUpListModel(
                "SALEBILL-478",
                "31/07/2022",
                "John"
            )
        val e4 =
            AmcFollowUpListModel(
                "TKT145874",
                "21/15/2025",
                "Hi -Lite Branch"
            )
        amcFollowArrayList.add(e1)
        amcFollowArrayList.add(e2)
        amcFollowArrayList.add(e3)
        amcFollowArrayList.add(e4)
        val gson = Gson()
        val listString = gson.toJson(
            amcFollowArrayList,
            object : TypeToken<ArrayList<AmcFollowUpListModel?>?>() {}.type
        )
        jsonArray = JSONArray(listString)

        setServiceFollowRecycler()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.imback -> {
                onBackPressed()
            }
        }
    }

    private fun setServiceFollowRecycler() {
        recyclerView!!.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
            )
        )
        var adapter = AmcFollowUpListAdapter(this, jsonArray)
        recyclerView!!.adapter = adapter
        adapter.addItemClickListener(this)
    }

    override fun onClick(position: Int, data: String, jsonObject: JSONObject) {
        if (data == "amc") {
            val intent = Intent(this, ServiceFollowUpAmcActivity::class.java)
            val invoice = jsonObject!!.getString("invoiceNo")
            intent.putExtra("invoice", invoice)
            startActivity(intent)
        }
    }
}