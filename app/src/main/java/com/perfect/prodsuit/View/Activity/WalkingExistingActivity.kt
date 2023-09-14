package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class WalkingExistingActivity : AppCompatActivity() , View.OnClickListener {

    var TAG = "WalkingExistingActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var jsonObj: JSONObject? = null
    internal var recyDetail: RecyclerView? = null
    lateinit var walkExistList : JSONArray


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walking_existing)
        context = this@WalkingExistingActivity

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
        val jobjt = jsonObj.getJSONObject("ExistCustomerDetails")
        walkExistList = jobjt.getJSONArray("ExistCustomerDetailList")

        Log.e(TAG,"jsonObj  377771   "+jsonObj)

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        recyDetail = findViewById(R.id.recyDetail)

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }


}