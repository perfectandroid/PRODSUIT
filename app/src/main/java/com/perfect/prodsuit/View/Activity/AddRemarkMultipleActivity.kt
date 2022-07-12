package com.perfect.prodsuit.View.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    var dialogRemarkAction : Dialog? = null
    var recyActionType: RecyclerView? = null
    lateinit var remarkActionArrayList : JSONArray
    private var ActionType = ""

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

        tv_actionType = findViewById(R.id.tv_actionType);

        tv_actionType!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }

            R.id.tv_actionType->{
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
            val jsonObject = remarkActionArrayList.getJSONObject(position)

            tv_actionType!!.setText(jsonObject.getString("action"))

        }
    }
}