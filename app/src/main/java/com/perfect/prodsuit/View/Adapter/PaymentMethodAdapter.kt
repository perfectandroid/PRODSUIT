package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ReplacedProductCostModel
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class PaymentMethodAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "PaymentMethodAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
class PaymentMethodAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_payment_method, parent, false
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
                holder.txtPayMethod.text        = jsonObject!!.getString("CategoryName")

                holder.llPaymentMethod!!.setTag(position)
                holder.llPaymentMethod!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "paymentMethod")
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
            Log.v("sadsdsssss", "e  " + e)
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
        internal var txtsino   : TextView
        internal var txtPayMethod   : TextView
        internal var llPaymentMethod    : LinearLayout
        init {
            txtsino          = v.findViewById<View>(R.id.txtsino) as TextView
            txtPayMethod          = v.findViewById<View>(R.id.txtPayMethod) as TextView
            llPaymentMethod           = v.findViewById<View>(R.id.llPaymentMethod) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
        lateinit var txt_method: TextView
        lateinit var txt_reff: TextView
        lateinit var txt_amount: TextView
        lateinit var img_delete: ImageView
        lateinit var img_edit: ImageView

        init {
            txt_method = v.findViewById(R.id.txt_method) as TextView
            txt_reff = v.findViewById(R.id.txt_reff) as TextView
            txt_amount = v.findViewById(R.id.txt_amount) as TextView
            img_delete = v.findViewById(R.id.img_delete) as ImageView
            img_edit = v.findViewById(R.id.img_edit) as ImageView
        }
    }

    fun addItemClickListener(listener: ItemClickListener) {
        mItemClickListener = listener
    }

}