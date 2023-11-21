package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ProjectProductAdapter(internal var context: Context, internal var jsonArray: JSONArray, internal var mode : String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProjectProductAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_project_product, parent, false
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


                holder.txtsino.text        = pos.toString()
                holder.txtProduct.text        = jsonObject!!.getString("Name")
                holder.txtSalesPrice.text        = Config.changeTwoDecimel(jsonObject!!.getString("SalesPrice"))


                if (mode.equals("0")){
                    holder.tv_labelStock.text        = "Available Stock"
                    holder.txtAvailableStock.text        = Config.changeTwoDecimel(jsonObject!!.getString("AvailableStock"))
                    holder.txtAssignedStock.text        = Config.changeTwoDecimel(jsonObject!!.getString("AssignedStock"))
                }
                if (mode.equals("1")){
                    holder.tv_labelStock.text        = "Current Stock"
                    holder.txtAvailableStock.text        = Config.changeTwoDecimel(jsonObject!!.getString("CurrentStock"))
                    holder.ll_Assigned.visibility = View.GONE
                }

                holder.llProduct!!.setTag(position)
                holder.llProduct!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "productClick"
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
        internal var txtProduct   : TextView
        internal var txtsino         : TextView
        internal var txtAvailableStock         : TextView
        internal var txtAssignedStock         : TextView
        internal var txtSalesPrice         : TextView
        internal var tv_labelStock         : TextView
        internal var ll_Assigned    : LinearLayout
        internal var llProduct    : LinearLayout
        init {
            txtProduct          = v.findViewById<View>(R.id.txtProduct) as TextView
            txtsino                = v.findViewById<View>(R.id.txtsino) as TextView
            txtAvailableStock                = v.findViewById<View>(R.id.txtAvailableStock) as TextView
            txtAssignedStock                = v.findViewById<View>(R.id.txtAssignedStock) as TextView
            txtSalesPrice                = v.findViewById<View>(R.id.txtSalesPrice) as TextView
            tv_labelStock                = v.findViewById<View>(R.id.tv_labelStock) as TextView
            llProduct           = v.findViewById<View>(R.id.llProduct) as LinearLayout
            ll_Assigned           = v.findViewById<View>(R.id.ll_Assigned) as LinearLayout
        }
    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}