package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
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

class LeadEditDetailAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "LeadEditDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adpate_leadedit_detail, parent, false
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
                holder.txtCustName.text        = jsonObject!!.getString("CustomerName")
                val name                       = jsonObject!!.getString("CustomerName")
                val first                      = name[0]
                holder.txtFirst.text           = first!!.toString()
                holder.txtCustAddress.text     = jsonObject!!.getString("Address")
                holder.txtProdName.text        = jsonObject!!.getString("product")
                holder.txtNextActionDate.text  = jsonObject!!.getString("NextActionDate")


                if (position%1 == 0){
                    val biggerCircle = ShapeDrawable(OvalShape())
                    biggerCircle.intrinsicHeight = 60
                    biggerCircle.intrinsicWidth = 60
                    biggerCircle.bounds = Rect(30, 30, 30, 30)
                    biggerCircle.paint.color = context.getColor(R.color.cust_color1)
                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
                    // holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color1))
                }
                if (position%2 == 0){
                    //  holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color2))
                    val biggerCircle = ShapeDrawable(OvalShape())
                    biggerCircle.intrinsicHeight = 60
                    biggerCircle.intrinsicWidth = 60
                    biggerCircle.bounds = Rect(30, 30, 30, 30)
                    biggerCircle.paint.color = context.getColor(R.color.cust_color2)
                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
                }
                if (position%3 == 0){
                    //holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color3))
                    val biggerCircle = ShapeDrawable(OvalShape())
                    biggerCircle.intrinsicHeight = 60
                    biggerCircle.intrinsicWidth = 60
                    biggerCircle.bounds = Rect(30, 30, 30, 30)
                    biggerCircle.paint.color = context.getColor(R.color.cust_color3)
                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
                }
                if (position%4 == 0){
                    //holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color4))
                    val biggerCircle = ShapeDrawable(OvalShape())
                    biggerCircle.intrinsicHeight = 60
                    biggerCircle.intrinsicWidth = 60
                    biggerCircle.bounds = Rect(30, 30, 30, 30)
                    biggerCircle.paint.color = context.getColor(R.color.cust_color4)
                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
                }
                if (position%5 == 0){
                    // holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color5))

                    val biggerCircle = ShapeDrawable(OvalShape())
                    biggerCircle.intrinsicHeight = 60
                    biggerCircle.intrinsicWidth = 60
                    biggerCircle.bounds = Rect(30, 30, 30, 30)
                    biggerCircle.paint.color = context.getColor(R.color.cust_color5)
                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
                }
                holder.llleadEdit!!.setTag(position)
                holder.llleadEdit!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "leadedit"
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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtCustName        : TextView
        internal var txtFirst        : TextView
        internal var txtCustAddress     : TextView
        internal var txtProdName        : TextView
        internal var txtNextActionDate  : TextView
        internal var llleadEdit         : LinearLayout
        init {
            txtFirst          = v.findViewById<View>(R.id.txtFirst) as TextView
            txtCustName          = v.findViewById<View>(R.id.txtCustName) as TextView
            txtCustAddress       = v.findViewById<View>(R.id.txtCustAddress) as TextView
            txtProdName          = v.findViewById<View>(R.id.txtProdName) as TextView
            txtNextActionDate    = v.findViewById<View>(R.id.txtNextActionDate) as TextView
            llleadEdit           = v.findViewById<View>(R.id.llleadEdit) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}