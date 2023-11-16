package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelOtherChargesCalculation
import com.perfect.prodsuit.R
import org.json.JSONObject

class TaxDetailAdapter (internal var context: Context, internal var mList: List<ModelOtherChargesCalculation>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "TaxDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_tax_detail, parent, false
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
                DecimelFormatters.setDecimelPlace(holder.edt_Percentage!!)
                holder.edt_Amount!!.setSelection(holder.edt_Amount!!.text.length)
                holder.edt_Amount!!.setText(Config.changeTwoDecimel(empModel!!.Amount!!))
                holder.edt_Percentage!!.setText(Config.changeTwoDecimel(empModel!!.TaxPercentage!!))

                holder.tv_TaxType.text        = empModel!!.TaxTyName

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
                        empModel!!.Amount = amt
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

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
        internal var tv_TaxType   : TextView
        internal var edt_Percentage    : EditText
        internal var edt_Amount    : EditText
        init {
            tv_TaxType          = v.findViewById<View>(R.id.tv_TaxType) as TextView
            edt_Percentage           = v.findViewById<View>(R.id.edt_Percentage) as EditText
            edt_Amount           = v.findViewById<View>(R.id.edt_Amount) as EditText


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}