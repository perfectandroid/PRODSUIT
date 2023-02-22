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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class CustomerListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "CustomerListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_customer_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {

                val pos = position+1
                holder.txtsino.text        = pos.toString()

                Log.e(TAG,"onBindViewHolder   1051   ")

                holder.txtName.text      = jsonObject!!.getString("Name")
                holder.txtAddress.text      = jsonObject!!.getString("Address")
                holder.txtMobile.text      = jsonObject!!.getString("Mobile")
                holder.lladpcustomer!!.setTag(position)
                holder.lladpcustomer!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "customerList")
                    System.out.println("FK_Customer"+jsonObject!!.getString("Customer_ID"))
                    System.out.println("FK_CustomerOther"+jsonObject!!.getString("CusMode"))


                    val FK_Customer = context.getSharedPreferences(Config.SHARED_PREF47, 0)
                    val FK_CustomerEditer = FK_Customer.edit()
                    FK_CustomerEditer.putString("Customerid", jsonObject!!.getString("Customer_ID"))
                    FK_CustomerEditer.commit()


                    val FK_CustomerOther = context.getSharedPreferences(Config.SHARED_PREF48, 0)
                    val FK_CustomerOtherEditer = FK_CustomerOther.edit()
                    FK_CustomerOtherEditer.putString("CusMode", jsonObject!!.getString("CusMode"))
                    FK_CustomerOtherEditer.commit()
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
        internal var txtsino         : TextView
        internal var txtName          : TextView
        internal var txtAddress       : TextView
        internal var txtMobile       : TextView
        internal var lladpcustomer    : LinearLayout

        init {
            txtsino       = v.findViewById<View>(R.id.txtsino) as TextView
            txtName        = v.findViewById<View>(R.id.txtName) as TextView
            txtAddress     = v.findViewById<View>(R.id.txtAddress) as TextView
            txtMobile     = v.findViewById<View>(R.id.txtMobile) as TextView
            lladpcustomer  = v.findViewById<View>(R.id.lladpcustomer) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}