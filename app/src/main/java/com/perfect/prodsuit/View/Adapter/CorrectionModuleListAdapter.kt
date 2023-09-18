package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class CorrectionModuleListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "CorrectionModuleListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_correction_module_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.i("array", jsonArray.toString()+"\n"+"Test")
                jsonObject = jsonArray.getJSONObject(position)
                Log.e(TAG,"onBindViewHolder   36   ")
                val pos = position+1

//                holder.txtEnquiryAbout.text        = jsonObject!!.getString("EnquiryAbout")
//                holder.txtAction.text        = jsonObject!!.getString("Action")
//                holder.txtActionType.text        = jsonObject!!.getString("ActionType")
//                holder.txtActionDate.text        = jsonObject!!.getString("ActionDate")
//                holder.txtStatus.text        = jsonObject!!.getString("Status")
//                holder.txtAgentRemarks.text        = jsonObject!!.getString("Agentremarks")
//                holder.txtFollowedBy.text        = jsonObject!!.getString("FollowedBy")

            }


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

//        internal var txtEnquiryAbout    : TextView
//        internal var txtAction          : TextView
//        internal var txtActionType      : TextView
//        internal var txtActionDate      : TextView
//        internal var txtStatus          : TextView
//        internal var txtAgentRemarks    : TextView
        internal var tvv_text           : TextView
        internal var img_module         : ImageView


        init {

//            txtEnquiryAbout      = v.findViewById<View>(R.id.txtEnquiryAbout) as TextView
//            txtAction            = v.findViewById<View>(R.id.txtAction) as TextView
//            txtActionType        = v.findViewById<View>(R.id.txtActionType) as TextView
//            txtActionDate        = v.findViewById<View>(R.id.txtActionDate) as TextView
//            txtStatus            = v.findViewById<View>(R.id.txtStatus) as TextView
//            txtAgentRemarks      = v.findViewById<View>(R.id.txtAgentRemarks) as TextView
            tvv_text             = v.findViewById<View>(R.id.tvv_text) as TextView
            img_module           = v.findViewById<View>(R.id.img_module) as ImageView

        }
    }

    override fun getItemCount(): Int {
        return  jsonArray.length()
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}