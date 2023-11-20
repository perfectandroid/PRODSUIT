package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ProjectListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        internal val TAG : String = "ProjectListAdapter"
        internal var jsonObject: JSONObject? = null
        private var clickListener: ItemClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val vh: RecyclerView.ViewHolder
            val v = LayoutInflater.from(parent.context).inflate(
                R.layout.adapter_project_list, parent, false
            )
            vh = MainViewHolder(v)
            return vh
        }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.tv_ShortName.text        = jsonObject!!.getString("ShortName")
                holder.tv_CreatedDate.text        = jsonObject!!.getString("CreateDate")
                holder.tv_ProjectName.text        = jsonObject!!.getString("ProjName")

                holder.tv_Customer.text        = jsonObject!!.getString("CustomerName")
                holder.tv_Address.text        = jsonObject!!.getString("Address")
                holder.tv_MobileNumber.text        = jsonObject!!.getString("MobileNumber")
                holder.tv_StartDate.text        = jsonObject!!.getString("StartDate")
                holder.tv_FinishDate.text        = jsonObject!!.getString("FinishDate")
                holder.tv_FinalAmount.text        = jsonObject!!.getString("FinalAmount")

                holder.ll_material_usage!!.setTag(position)
                holder.ll_material_usage!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "materialUsageClick"
                    )
                })

                holder.ll_material_request!!.setTag(position)
                holder.ll_material_request!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "materialRequestClick"
                    )
                })

                holder.ll_project_followup!!.setTag(position)
                holder.ll_project_followup!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "projectFollowupClick"
                    )
                })

                holder.ll_project_transaction!!.setTag(position)
                holder.ll_project_transaction!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "materialTransactionClick"
                    )
                })
            }
        } catch (e: Exception) {
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
        internal var tv_ShortName               : TextView
        internal var tv_CreatedDate             : TextView
        internal var tv_ProjectName             : TextView
        internal var tv_Customer                : TextView
        internal var tv_Address                : TextView
        internal var tv_MobileNumber                : TextView
        internal var tv_StartDate               : TextView
        internal var tv_FinishDate              : TextView
        internal var tv_FinalAmount             : TextView
        internal var ll_material_usage          : LinearLayout
        internal var ll_material_request        : LinearLayout
        internal var ll_project_followup        : LinearLayout
        internal var ll_project_transaction     : LinearLayout
        init {
            tv_ShortName                    = v.findViewById<View>(R.id.tv_ShortName) as TextView
            tv_CreatedDate                  = v.findViewById<View>(R.id.tv_CreatedDate) as TextView
            tv_ProjectName                  = v.findViewById<View>(R.id.tv_ProjectName) as TextView
            tv_Customer                     = v.findViewById<View>(R.id.tv_Customer) as TextView
            tv_Address                     = v.findViewById<View>(R.id.tv_Address) as TextView
            tv_MobileNumber                     = v.findViewById<View>(R.id.tv_MobileNumber) as TextView
            tv_StartDate                    = v.findViewById<View>(R.id.tv_StartDate) as TextView
            tv_FinishDate                   = v.findViewById<View>(R.id.tv_FinishDate) as TextView
            tv_FinalAmount                  = v.findViewById<View>(R.id.tv_FinalAmount) as TextView
            ll_material_usage               = v.findViewById<View>(R.id.ll_material_usage) as LinearLayout
            ll_material_request             = v.findViewById<View>(R.id.ll_material_request) as LinearLayout
            ll_project_followup             = v.findViewById<View>(R.id.ll_project_followup) as LinearLayout
            ll_project_transaction          = v.findViewById<View>(R.id.ll_project_transaction) as LinearLayout
        }
    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}


