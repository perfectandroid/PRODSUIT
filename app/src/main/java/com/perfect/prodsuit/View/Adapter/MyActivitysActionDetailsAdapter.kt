package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.MyActivitysActionDetailsModelList
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class MyActivitysActionDetailsAdapter(internal var context: Context, internal var jsonArray: JSONArray) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "MyActivitysActionDetailsAdapter"
    private var clickListener: ItemClickListener? = null
    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_activity_actiondetail, parent, false)
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   213331   ")
                val pos = position+1

              //  val empModel = actionCount[position]
                jsonObject = jsonArray.getJSONObject(position)

                if (!jsonObject!!.getString("Product").equals("") || jsonObject!!.getString("Product")!= null){
                    holder.ll_Product.visibility = View.VISIBLE
                }

                if (!jsonObject!!.getString("Agentremarks").equals("") || jsonObject!!.getString("Agentremarks") != null){
                    holder.ll_Agentremarks.visibility = View.VISIBLE
                }
                if (!jsonObject!!.getString("CustomerRemarks").equals("") || jsonObject!!.getString("CustomerRemarks") != null){
                    holder.ll_Customerremarks.visibility = View.VISIBLE
                }

//                holder.tv_Product.text              = empModel.Product
//                holder.tv_EnquiryAbout.text         = empModel.EnquiryAbout
//                holder.tv_ActionType.text           = empModel.ActionType
//                holder.tv_Action.text               = empModel.Action
//                holder.tv_ActionDate.text           = empModel.ActionDate
//                holder.tv_Status.text               = empModel.Status
//                holder.tv_Followed.text             = empModel.FollowedBy
//                holder.tv_Agentremarks.text         = empModel.Agentremarks

                holder.tv_Customer.setText(jsonObject!!.getString("CustomerName"))
                holder.tv_Product.setText(jsonObject!!.getString("Product"))
                holder.tv_EnquiryAbout.setText(jsonObject!!.getString("EnquiryAbout"))
                holder.tv_ActionType.setText(jsonObject!!.getString("ActionType"))
                holder.tv_Action.setText(jsonObject!!.getString("Action"))
                holder.tv_ActionDate.setText(jsonObject!!.getString("ActionDate"))
                holder.tv_Status.setText(jsonObject!!.getString("Status"))
                holder.tv_Followed.setText(jsonObject!!.getString("FollowedBy"))
                holder.tv_Agentremarks.setText(jsonObject!!.getString("Agentremarks"))
                holder.tv_Customerremarks.setText(jsonObject!!.getString("CustomerRemarks"))

//                if (myActivityActionCountMode == position){
//                    holder.tv_ActionName.setTextColor(Color.GREEN)
//                }else{
//                    holder.tv_ActionName.setTextColor(Color.WHITE)
//                }

//                holder.ll_actiondetails.setTag(position)
//                holder.ll_actiondetails.setOnClickListener {
//                    Log.e(TAG,"213332   "+position)
//                    myActivityActionCountMode = position
//                    notifyDataSetChanged()
//                    clickListener!!.onClick(position, "actionCountDetailClick")
//                }


            }
        }
        catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var tv_ActionType      :    TextView
        internal var tv_ActionDate      :    TextView
        internal var tv_Customer        :    TextView
        internal var tv_Product         :    TextView
        internal var tv_EnquiryAbout    :    TextView
        internal var tv_Action          :    TextView
        internal var tv_Status          :    TextView
        internal var tv_Followed        :    TextView
        internal var tv_Agentremarks    :    TextView
        internal var tv_Customerremarks :    TextView

        internal var ll_Customer         : LinearLayout
        internal var ll_Product          : LinearLayout
        internal var ll_Agentremarks     : LinearLayout
        internal var ll_Customerremarks  : LinearLayout
//        internal var tv_MeasurementType: TextView

        init {
            tv_ActionType       = v.findViewById<View>(R.id.tv_ActionType) as TextView
            tv_ActionDate       = v.findViewById<View>(R.id.tv_ActionDate) as TextView
            tv_Customer         = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Product          = v.findViewById<View>(R.id.tv_Product) as TextView
            tv_EnquiryAbout     = v.findViewById<View>(R.id.tv_EnquiryAbout) as TextView
            tv_Action           = v.findViewById<View>(R.id.tv_Action) as TextView
            tv_Status           = v.findViewById<View>(R.id.tv_Status) as TextView
            tv_Followed         = v.findViewById<View>(R.id.tv_Followed) as TextView
            tv_Agentremarks     = v.findViewById<View>(R.id.tv_Agentremarks) as TextView
            tv_Customerremarks  = v.findViewById<View>(R.id.tv_Customerremarks) as TextView


            ll_Customer         = v.findViewById<View>(R.id.ll_Customer) as LinearLayout
            ll_Product          = v.findViewById<View>(R.id.ll_Product) as LinearLayout
            ll_Agentremarks     = v.findViewById<View>(R.id.ll_Agentremarks) as LinearLayout
            ll_Customerremarks  = v.findViewById<View>(R.id.ll_Customerremarks) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}