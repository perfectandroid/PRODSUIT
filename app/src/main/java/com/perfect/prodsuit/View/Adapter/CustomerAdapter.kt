package com.perfect.prodsuit.View.Adapter

import android.R.attr
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
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.R.attr.shape

import android.graphics.drawable.ShapeDrawable
import android.R.attr.shape
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.shapes.OvalShape

class CustomerAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "CustomerAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_customer_details, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                holder.txtName.text        = jsonObject!!.getString("CusName")
                val name = jsonObject!!.getString("CusName")
                val first = name[0]
                Log.e(TAG,"first   1051   "+first)
                holder.txtFirst.text = first!!.toString()
//                if (first.equals("")){
//                    holder.txtFirst.text = second!!.toString()
//                }

                holder.txtEmail!!.setTag(position)
                Log.e(TAG,"onBindViewHolder   1051   "+jsonObject!!.getString("CusEmail")+"   "+jsonObject!!.getString("CusName")+"   "+jsonObject!!.getString("CusEmail").length)
                if (jsonObject!!.getString("CusEmail").length==0){
                      holder.txtEmail!!.visibility = View.GONE
                }else{

                    holder.txtEmail.text        = jsonObject!!.getString("Email")
                }

//                if (position%2 == 0){
//                    holder.llleftlay.setBackgroundColor(context.getColor(R.color.cust_1))
//                }else{
//                    holder.llleftlay.setBackgroundColor(context.getColor(R.color.cust_2))
//                }


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
//                if (position%3 == 0){
//                    //holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color3))
//                    val biggerCircle = ShapeDrawable(OvalShape())
//                    biggerCircle.intrinsicHeight = 60
//                    biggerCircle.intrinsicWidth = 60
//                    biggerCircle.bounds = Rect(30, 30, 30, 30)
//                    biggerCircle.paint.color = context.getColor(R.color.cust_color3)
//                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
//                }
//                if (position%4 == 0){
//                    //holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color4))
//                    val biggerCircle = ShapeDrawable(OvalShape())
//                    biggerCircle.intrinsicHeight = 60
//                    biggerCircle.intrinsicWidth = 60
//                    biggerCircle.bounds = Rect(30, 30, 30, 30)
//                    biggerCircle.paint.color = context.getColor(R.color.cust_color4)
//                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
//                }
//                if (position%5 == 0){
//                   // holder.llleftlay.setBackgroundColor(context.getColor(R.color.leadstatus_color5))
//
//                    val biggerCircle = ShapeDrawable(OvalShape())
//                    biggerCircle.intrinsicHeight = 60
//                    biggerCircle.intrinsicWidth = 60
//                    biggerCircle.bounds = Rect(30, 30, 30, 30)
//                    biggerCircle.paint.color = context.getColor(R.color.cust_color5)
//                    holder.txtFirst.setBackgroundDrawable(biggerCircle)
//                }


                holder.txtEmail.text        = jsonObject!!.getString("CusEmail")
                holder.txtMobile.text     = jsonObject!!.getString("CusPhnNo")
                holder.txtAddress.text      = jsonObject!!.getString("CusAddress1")
                holder.lladpcustomer!!.setTag(position)
                holder.lladpcustomer!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "customer")
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
        internal var txtFirst         : TextView
        internal var txtName          : TextView
        internal var txtEmail         : TextView
        internal var txtMobile        : TextView
        internal var txtAddress       : TextView
        internal var lladpcustomer    : LinearLayout
        internal var llleftlay        : LinearLayout
        init {
            txtFirst       = v.findViewById<View>(R.id.txtFirst) as TextView
            txtName        = v.findViewById<View>(R.id.txtName) as TextView
            txtEmail       = v.findViewById<View>(R.id.txtEmail) as TextView
            txtMobile      = v.findViewById<View>(R.id.txtMobile) as TextView
            txtAddress     = v.findViewById<View>(R.id.txtAddress) as TextView
            lladpcustomer  = v.findViewById<View>(R.id.lladpcustomer) as LinearLayout
            llleftlay      = v.findViewById<View>(R.id.llleftlay) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}