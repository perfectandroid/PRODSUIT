package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelLeadCorrectionDetails
import com.perfect.prodsuit.Model.ModelWalkingExist
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class CorrectionProductAdapter (internal var context: Context, internal var modelLeadCorrectionDetails: ArrayList<ModelLeadCorrectionDetails>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "CorrectionProductAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.correction_product_adapter, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            Log.e(TAG, "Exception   49731   ")

            Log.e(TAG, "jsonObject   497310   "+jsonObject)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                val ItemsModel = modelLeadCorrectionDetails[position]
                val pos = position + 1

                DecimelFormatters.setDecimelPlace(holder.edt_offerpriz!!)

                holder.tvv_tvCategory.text = ItemsModel.CategoryName
                holder.tvv_Product.text = ItemsModel.ProdName
              //  holder.tvv_mrp.text = ItemsModel.LgpMRP


                holder.tvv_mrp.text  = Config.changeTwoDecimel(ItemsModel.LgpMRP)
                holder.edt_offerpriz.setText(ItemsModel.LgpSalesPrice)

                holder.edt_offerpriz.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                        var MRRP = holder.tvv_mrp.text.toString()
                        var offer = holder.edt_offerpriz.text.toString()



                        if (MRRP!!.equals("")) {
                            MRRP = "0"
                        }

                        if (offer!!.equals("")) {
                            offer = "0"
                        }
                        Log.e(TAG,"70001  MRRP      "+MRRP)
                        Log.e(TAG,"70001  MRRP      "+MRRP.toFloat())
                        Log.e(TAG,"70002  offer     "+offer)

                        if ((MRRP.toFloat() != "0".toFloat()) && (offer.toFloat() > MRRP.toFloat())) {

                          //  Toast.makeText(context,"Offer Price Should be less than or Equal to MRP",Toast.LENGTH_SHORT).show()
                              Config.showCustomToast1("Offer Price Should be less than or Equal to MRP",context)
                            modelLeadCorrectionDetails[position].LgpSalesPrice = Config.changeTwoDecimel(offer)
                            holder.edt_offerpriz.setBackgroundDrawable(context.resources.getDrawable(R.drawable.shape_bg_red))
                        }
                        else{
                            modelLeadCorrectionDetails[position].LgpSalesPrice = Config.changeTwoDecimel(offer)
                            holder.edt_offerpriz.setBackgroundDrawable(context.resources.getDrawable(R.drawable.shape_bg))
                        }
                    }
                })


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   4973   " + e.toString())
        }
    }

    override fun getItemCount(): Int {
        return modelLeadCorrectionDetails.size
    }
     fun returnlist(): ArrayList<ModelLeadCorrectionDetails> {
        return modelLeadCorrectionDetails
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var tvv_tvCategory: TextView
        internal var tvv_Product: TextView
        internal var tvv_product_name: TextView
        internal var tvv_mrp: TextView
        internal var edt_offerpriz: EditText

        init {

            tvv_tvCategory = v.findViewById<View>(R.id.tvv_tvCategory) as TextView
            tvv_Product = v.findViewById<View>(R.id.tvv_Product) as TextView
            tvv_product_name = v.findViewById<View>(R.id.tvv_product_name) as TextView
            tvv_mrp = v.findViewById<View>(R.id.tvv_mrp) as TextView
            edt_offerpriz = v.findViewById<View>(R.id.edt_offerpriz) as EditText
//            category_prod = v.findViewById<View>(R.id.category_prod) as TextView


        }


    }
    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}