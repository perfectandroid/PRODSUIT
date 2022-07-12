package com.perfect.prodsuit.View.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.AgendaActionTypeAdapter
import com.perfect.prodsuit.View.Adapter.RemarkActionTypeAdapter
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class AddRemarkMultipleActivity : AppCompatActivity(), View.OnClickListener , ItemClickListener {

    val TAG : String = "AddRemarkMultipleActivity"
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context

    var tv_actionType: TextView? = null

    var tie_ActionType: TextInputEditText? = null
    var til_Date: TextInputEditText? = null
    var tie_Time: TextInputEditText? = null
    var tie_CallStatus: TextInputEditText? = null
    var tie_CallDuration: TextInputEditText? = null
    var tie_RiskType: TextInputEditText? = null
    var tie_AgentNote: TextInputEditText? = null
    var tie_CustomerNote: TextInputEditText? = null
    var tie_FollowType: TextInputEditText? = null
    var tie_Status: TextInputEditText? = null
    var tie_CustomerMentionDate: TextInputEditText? = null
    var tie_Latitude: TextInputEditText? = null
    var tie_Longitude: TextInputEditText? = null

    var til_CallStatus: TextInputLayout? = null
    var til_CallDuration: TextInputLayout? = null
    var til_FollowType: TextInputLayout? = null
    var til_Status: TextInputLayout? = null

    var ll_location: LinearLayout? = null
    var ll_images: LinearLayout? = null
    var ll_acknowledge: LinearLayout? = null
    var ll_need: LinearLayout? = null

    var dialogRemarkAction : Dialog? = null
    var recyActionType: RecyclerView? = null
    lateinit var remarkActionArrayList : JSONArray
    private var ActionType = ""
    var action_id:String?=""

    var rltv_addremark: RelativeLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_add_remark_multiple)
        context = this@AddRemarkMultipleActivity

        setRegViews()

       // getActiontype()

    }





    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)

        tie_ActionType = findViewById(R.id.tie_ActionType);

        til_CallStatus = findViewById(R.id.til_CallStatus);
        til_CallDuration = findViewById(R.id.til_CallDuration);
        til_FollowType = findViewById(R.id.til_FollowType);
        til_Status = findViewById(R.id.til_Status);

        ll_location = findViewById(R.id.ll_location);
        ll_images = findViewById(R.id.ll_images);
        ll_acknowledge = findViewById(R.id.ll_acknowledge);
        ll_need = findViewById(R.id.ll_need);

        rltv_addremark = findViewById(R.id.rltv_addremark);

        tie_ActionType!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tie_ActionType->{
                getActiontype()
            }
        }
    }

    private fun getActiontype() {
        ActionType = Config.getActionTypes()

        Log.e(TAG,"ActionType   44  "+ActionType)
        actiontypePopup(ActionType)
    }

    private fun actiontypePopup(actionType: String) {
        val jObject = JSONObject(actionType)
        val jobjt = jObject.getJSONObject("actionType")
        remarkActionArrayList  = jobjt.getJSONArray("actionTypeDetails")
        try {

            dialogRemarkAction = Dialog(this)
            dialogRemarkAction!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogRemarkAction!! .setContentView(R.layout.remark_action_popup)
            dialogRemarkAction!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
            recyActionType = dialogRemarkAction!! .findViewById(R.id.recyActionType) as RecyclerView
            val etsearch = dialogRemarkAction!! .findViewById(R.id.etsearch) as EditText

//            agendaActionSort = JSONArray()
//            for (k in 0 until agendaActionArrayList.length()) {
//                val jsonObject = agendaActionArrayList.getJSONObject(k)
//                // reportNamesort.put(k,jsonObject)
//                agendaActionSort.put(jsonObject)
//            }

            val lLayout = GridLayoutManager(this@AddRemarkMultipleActivity, 1)
            recyActionType!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = RemarkActionTypeAdapter(this@AddRemarkMultipleActivity, remarkActionArrayList)
            recyActionType!!.adapter = adapter
            adapter.setClickListener(this@AddRemarkMultipleActivity)


            dialogRemarkAction!!.show()
            dialogRemarkAction!!.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onClick(position: Int, data: String) {
        if (data.equals("remarkactiontype")){
            dialogRemarkAction!!.dismiss()
            rltv_addremark!!.visibility = View.GONE

            val jsonObject = remarkActionArrayList.getJSONObject(position)
            action_id = jsonObject.getString("action_id")
            tie_ActionType!!.setText(jsonObject.getString("action"))
            if (action_id.equals("1") || action_id.equals("2")){
                rltv_addremark!!.visibility = View.VISIBLE
            }
            usingActionId(action_id!!)

        }
    }

    private fun usingActionId(actionId: String) {
        if (action_id.equals("1")){
            // ADD REMARK
            til_CallStatus!!.visibility = View.VISIBLE
            til_CallDuration!!.visibility = View.VISIBLE
            ll_acknowledge!!.visibility = View.VISIBLE
            ll_need!!.visibility = View.VISIBLE

            til_FollowType!!.visibility = View.GONE
            til_Status!!.visibility = View.GONE
            ll_location!!.visibility = View.GONE
            ll_images!!.visibility = View.GONE

        }
        else if (action_id.equals("2")){
            // SITE VISIT
            til_CallStatus!!.visibility = View.GONE
            til_CallDuration!!.visibility = View.GONE
            ll_acknowledge!!.visibility = View.GONE
            ll_need!!.visibility = View.GONE

            til_FollowType!!.visibility = View.VISIBLE
            til_Status!!.visibility = View.VISIBLE
            ll_location!!.visibility = View.VISIBLE
            ll_images!!.visibility = View.VISIBLE

        }
    }
}