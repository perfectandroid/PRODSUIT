package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ServiceConstants
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class OtherChargesAdapterAdapter(
    internal var context: Context,
    internal var mList: ArrayList<OtherChargesMainModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "ServiceDetailsAdapter"

    //    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_other_charges, parent, false
        )
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {

            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   105100   ")
                val empModel = mList[position]
                holder.chargename.text = empModel.OctyName
                holder.edt_charge.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // This method is called before the text changes.
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        // This method is called as the text changes.
                        val newText = s.toString()
                        // You can perform actions here based on the new text.
                    }

                    override fun afterTextChanged(s: Editable?) {
                        var empModel = mList[position]
                        empModel.OctyAmount = s.toString()
                    }
                })

            }


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
        }
    }


    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    fun getotherCharges(position: Int): List<OtherChargesMainModel> {
        return mList
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var chargename: TextView
        internal var edt_charge: TextInputEditText


        init {
            chargename = v.findViewById<View>(R.id.chargename) as TextView
            edt_charge = v.findViewById<View>(R.id.edt_charge) as TextInputEditText


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}