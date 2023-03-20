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
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.PickUpAndDeliveryListActivity
import com.perfect.prodsuit.View.Activity.PickUpAndDeliveryUpdateActivity
import org.json.JSONArray
import org.json.JSONObject

class ProdInformationAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    internal val TAG : String = "ProdInformationAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    var strStandByAmount: String? = "0.00"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adpate_prod_information, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                holder.setIsRecyclable(false)
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                //checkbox
                if (jsonObject!!.getString("isSelected").equals("0")){
                    holder.checkbox.isChecked = false
                }else{
                    holder.checkbox.isChecked = true
                }

                //  swOnOff
                if (jsonObject!!.getString("isStatndBy").equals("0")){
                    Log.e(TAG,"standByAmount  68111    "+position)
                    holder.swOnOff.isChecked = false
                    holder.tie_StandByAmount.isEnabled = false
                    holder.tie_StandByAmount.setText("")
                }else{
                    Log.e(TAG,"standByAmount  68112    "+position)
                    holder.swOnOff.isChecked = true
                    holder.tie_StandByAmount.isEnabled = true
                    holder.tie_StandByAmount.setText("0.00")
                }

                Log.e(TAG,"standByAmount  6812    "+jsonObject!!.getString("standByAmount"))

                holder.tv_ProductName.text        = jsonObject!!.getString("prodName")
                holder.tie_Quantity.setText(jsonObject!!.getString("prodQuantity"))
                holder.tie_StandByProduct.setText(jsonObject!!.getString("standByProduct"))
                holder.tie_StandByQuantity.setText(jsonObject!!.getString("standByQuantity"))
                holder.tie_StandByAmount.setText(jsonObject!!.getString("standByAmount"))
                holder.tie_Remarks.setText(jsonObject!!.getString("remarks"))
                DecimelFormatters.setDecimelPlace(holder.tie_StandByAmount!!)

                holder.tie_Quantity.setTag(position)
                holder.tie_Quantity.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("prodQuantity",s.toString())
                    }

                })


                holder.tie_StandByProduct.setTag(position)
                holder.tie_StandByProduct.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("standByProduct",s.toString())
                    }

                })

                holder.tie_StandByQuantity.setTag(position)
                holder.tie_StandByQuantity.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("standByQuantity",s.toString())
                    }

                })

                holder.tie_StandByAmount.setTag(position)
                holder.tie_StandByAmount.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("standByAmount",s.toString())
                        clickListener!!.onClick(position, "changeAmount")
                    }

                })

                holder.tie_Remarks.setTag(position)
                holder.tie_Remarks.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("remarks",s.toString())
                    }

                })


                holder.checkbox!!.setTag(position)
                holder.checkbox!!.setOnClickListener(View.OnClickListener {
                    if (holder.checkbox.isChecked){
                        Log.e(TAG,"82 checkbox   on")
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("isSelected","1")
                        clickListener!!.onClick(position, "changeAmount")

                    }else{
                        Log.e(TAG,"82 checkbox   off")
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("isSelected","0")
                        clickListener!!.onClick(position, "changeAmount")
                    }
                })


                holder.swOnOff!!.setTag(position)
                holder.swOnOff!!.setOnClickListener(View.OnClickListener {
                    if (holder.swOnOff.isChecked){
                        Log.e(TAG,"82 swOnOff   on")
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("isStatndBy","1")
                        holder.tie_StandByAmount.isEnabled = true
                        holder.tie_StandByAmount.setText("0.00")
                        clickListener!!.onClick(position, "changeAmount")
                    }else{
                        Log.e(TAG,"82 swOnOff   off")
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("isStatndBy","0")
                        holder.tie_StandByAmount.isEnabled = false
                        holder.tie_StandByAmount.setText("")
                        clickListener!!.onClick(position, "changeAmount")

                    }
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
        return position %2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_ProductName          : TextView
        internal var tie_Quantity            : TextInputEditText
        internal var tie_StandByQuantity     : TextInputEditText
        internal var tie_StandByProduct      : TextInputEditText
        internal var tie_StandByAmount       : TextInputEditText
        internal var tie_Remarks             : TextInputEditText
        internal var swOnOff                 : SwitchCompat
        internal var checkbox                 : CheckBox
//        internal var txtGridNotification  : TextView
//        internal var image                : ImageView
//        internal var ll_homeGrid          : LinearLayout

        init {
            tv_ProductName         = v.findViewById<View>(R.id.tv_ProductName) as TextView
            tie_Quantity           = v.findViewById<View>(R.id.tie_Quantity) as TextInputEditText
            tie_StandByQuantity    = v.findViewById<View>(R.id.tie_StandByQuantity) as TextInputEditText
            tie_StandByProduct     = v.findViewById<View>(R.id.tie_StandByProduct) as TextInputEditText
            tie_StandByAmount      = v.findViewById<View>(R.id.tie_StandByAmount) as TextInputEditText
            tie_Remarks            = v.findViewById<View>(R.id.tie_Remarks) as TextInputEditText
            swOnOff                = v.findViewById<View>(R.id.swOnOff) as SwitchCompat
            checkbox                = v.findViewById<View>(R.id.checkbox) as CheckBox

//            txtGridNotification   = v.findViewById<View>(R.id.txtGridNotification) as TextView
//            image                 = v.findViewById<View>(R.id.image) as ImageView
//            ll_homeGrid           = v.findViewById<View>(R.id.ll_homeGrid) as LinearLayout

        }
    }


    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}