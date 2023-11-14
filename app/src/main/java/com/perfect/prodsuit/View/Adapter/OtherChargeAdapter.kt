package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelOtherCharges
import com.perfect.prodsuit.Model.ServiceDetailsFullListModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class OtherChargeAdapter(internal var context: Context, internal var mList: List<ModelOtherCharges>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "OtherChargeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
//    internal var edt_TransType: AutoCompleteTextView? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_project_othercharges, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {

            if (holder is MainViewHolder) {
                val empModel = mList[position]
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1

                DecimelFormatters.setDecimelPlace(holder.edt_Amount!!)
                DecimelFormatters.setDecimelPlace(holder.edt_Tax_Amount!!)

                holder.edt_Amount!!.setSelection(holder.edt_Amount!!.text.length)
                holder.edt_Amount!!.setText(Config.changeTwoDecimel(empModel!!.OctyAmount))
                holder.edt_Tax_Amount!!.setText(Config.changeTwoDecimel(empModel!!.OctyTaxAmount))

                holder.tv_Type.text        = empModel!!.OctyName
                holder.edt_TransType!!.setText(empModel!!.TransType_Name)

                if (empModel!!.isCalculate){
                    holder.tv_calculate!!.setBackgroundResource(R.drawable.svg_checked)
                    holder.edt_TransType!!.isEnabled = false
                    holder.edt_Amount!!.isEnabled = false
                    holder.edt_Tax_Amount!!.isEnabled = false
                    holder.chk_inclTax!!.isEnabled = false

                }else{
                    holder.tv_calculate!!.setBackgroundResource(R.drawable.svg_unchecked)
                    holder.edt_TransType!!.isEnabled = true
                    holder.edt_Amount!!.isEnabled = true
                    holder.edt_Tax_Amount!!.isEnabled = true
                    holder.chk_inclTax!!.isEnabled = true
                }

                if (empModel.OctyIncludeTaxAmount){
                    holder.chk_inclTax.isChecked = true
                }else{
                    holder.chk_inclTax.isChecked = false
                }


                holder.edt_TransType!!.setOnClickListener {
                    Log.e(TAG,"gridList  58881   "+empModel!!.OctyTransTypeActive)
                  if (empModel!!.OctyTransTypeActive.equals("0")){
                    //  showTransType()
                      var gridList = Config.getTransType(context)
                      val jObject1 = JSONObject(gridList)
                      Log.e(TAG,"gridList  58882   "+gridList)
                      val jobjt1 = jObject1.getJSONObject("TransType")
                      var typeArrayList = jobjt1.getJSONArray("TransTypeDetails")

                      var transType = Array<String>(typeArrayList.length()) { "" }
                      var transTypeID = Array<String>(typeArrayList.length()) { "" }

                      for (i in 0 until typeArrayList.length()) {
                          val objects: JSONObject = typeArrayList.getJSONObject(i)

                          transType[i] = objects.getString("TransType_Name");
                          transTypeID[i] = objects.getString("ID_TransType");
                      }

                      val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, transType)
                      holder.edt_TransType!!.setAdapter(adapter)
                      holder.edt_TransType!!.showDropDown()

                      holder.edt_TransType!!.setOnItemClickListener { parent, view, posit, id ->
                          empModel.ID_TransType = transTypeID[posit]
                          empModel.TransType_Name = transType[posit]

                          notifyItemChanged(position)

                      }

                  }
                }

                holder.edt_Tax_Amount.setOnClickListener {
                    clickListener!!.onClick(position, "IncludeTaxAmountClick")
                }

                holder.tv_calculate.setOnClickListener {
                   if (empModel.isCalculate){
                       empModel.isCalculate = false
                   }else{
                       empModel.isCalculate = true
                   }

                    clickListener!!.onClick(position, "TaxAmountClaculateClick")
                    notifyItemChanged(position)
                }


                holder.chk_inclTax.setOnClickListener {
                    if (holder.chk_inclTax.isChecked){
                        empModel.OctyIncludeTaxAmount = true
                        clickListener!!.onClick(position, "IncludeTaxClick")
                    }else{
                        empModel.OctyIncludeTaxAmount = false
                        clickListener!!.onClick(position, "IncludeTaxClick")
                    }
                }

                holder.edt_Amount.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        holder.edt_Amount.setSelection(holder.edt_Amount.text!!.length)
                        var amt = holder.edt_Amount.text.toString()
                        if (amt.equals("") || amt.equals(".")){
                            amt = "0.00"
                        }
                        empModel!!.OctyAmount = amt
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

//    private fun showTransType() {
//        var gridList = Config.getTransType(context)
//        val jObject1 = JSONObject(gridList)
//        Log.e(TAG,"gridList  2111   "+gridList)
//        val jobjt1 = jObject1.getJSONObject("TransType")
//        var typeArrayList = jobjt1.getJSONArray("TransTypeDetails")
//
//        var transType = Array<String>(typeArrayList.length()) { "" }
//        var transTypeID = Array<String>(typeArrayList.length()) { "" }
//
//        for (i in 0 until typeArrayList.length()) {
//            val objects: JSONObject = typeArrayList.getJSONObject(i)
//
//            transType[i] = objects.getString("TransType_Name");
//            transTypeID[i] = objects.getString("ID_TransType");
//        }
//
//        val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, transType)
//        edt_TransType!!.adapter = adapter
//
//    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_Type   : TextView
        internal var tv_calculate   : TextView

        internal var edt_Amount    : EditText
        internal var edt_Tax_Amount    : EditText
        internal var chk_inclTax    : CheckBox
        internal var edt_TransType: AutoCompleteTextView? =null
        init {
            tv_Type          = v.findViewById<View>(R.id.tv_Type) as TextView
            tv_calculate          = v.findViewById<View>(R.id.tv_calculate) as TextView
            edt_TransType                = v.findViewById<View>(R.id.edt_TransType) as AutoCompleteTextView
            edt_Amount           = v.findViewById<View>(R.id.edt_Amount) as EditText
            edt_Tax_Amount           = v.findViewById<View>(R.id.edt_Tax_Amount) as EditText

            chk_inclTax           = v.findViewById<View>(R.id.chk_inclTax) as CheckBox
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}