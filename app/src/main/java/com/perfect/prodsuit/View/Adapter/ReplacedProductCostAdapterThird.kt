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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Model.MoreReplacedProductCostModel
import com.perfect.prodsuit.Model.ReplacedProductCostModel
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ReplacedProductCostAdapterThird(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val replacedProductCostArrayList: ArrayList<MoreReplacedProductCostModel>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "HistoryActMeetingAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_replaced_product_cost_followup_secondary, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            var getList = replacedProductCostArrayList.get(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                var isChecked = ""
                val pos = position
                holder.tv_component.text = jsonObject!!.getString("Name")
                holder.edtAmount.setText(jsonObject!!.getString("MRPs"))
                holder.edtBuyBackAmount.setText(jsonObject!!.getString("MRP1R"))
                try {
                    if (getList.isChecked.equals("true")) {
                        holder.checkbox.isChecked = true
                        isChecked = "true"
                    } else {
                        holder.checkbox.isChecked = false
                        isChecked = "false"
                    }
                } catch (e: Exception) {
                    holder.checkbox.isChecked = false
                    isChecked = "false"
                }
                holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                    if (b) {
                        isChecked = "true"
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            MoreReplacedProductCostModel(
                                jsonObject!!.getString("ID_Product"),
                                jsonObject!!.getString("Code"),
                                jsonObject!!.getString("Name"),
                                jsonObject!!.getString("MRPs"),
                                jsonObject!!.getString("MRP1R"),
                                jsonObject!!.getString("SalesPrice1R"),
                                jsonObject!!.getString("SalePrice"),
                                jsonObject!!.getString("CurrentStock1R"),
                                jsonObject!!.getString("StockId"),
                                jsonObject!!.getString("TaxAmount"),
                                jsonObject!!.getString("StandbyStock"),
                                jsonObject!!.getString("TotalCount"),
                                "true"
                            )
                        )
                    } else {
                        isChecked = "false"
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            MoreReplacedProductCostModel(
                                jsonObject!!.getString("ID_Product"),
                                jsonObject!!.getString("Code"),
                                jsonObject!!.getString("Name"),
                                jsonObject!!.getString("MRPs"),
                                jsonObject!!.getString("MRP1R"),
                                jsonObject!!.getString("SalesPrice1R"),
                                jsonObject!!.getString("SalePrice"),
                                jsonObject!!.getString("CurrentStock1R"),
                                jsonObject!!.getString("StockId"),
                                jsonObject!!.getString("TaxAmount"),
                                jsonObject!!.getString("StandbyStock"),
                                jsonObject!!.getString("TotalCount"),
                                "false"
                            )
                        )
                    }
                })
            }
        } catch (e: Exception) {
            e.printStackTrace()
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
        var tv_component: TextView
        var edtAmount: EditText
        var edt_qty: EditText
        var edtChangeMode: AutoCompleteTextView
        var edtBuyBackAmount: EditText
        var edtRemarks: EditText
        var checkbox: CheckBox

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            edtAmount = v.findViewById(R.id.edtAmount) as EditText
            edt_qty = v.findViewById(R.id.edt_qty) as EditText
            edtChangeMode = v.findViewById(R.id.edtChangeMode) as AutoCompleteTextView
            edtBuyBackAmount = v.findViewById(R.id.edtBuyBackAmount) as EditText
            edtRemarks = v.findViewById(R.id.edtRemarks) as EditText
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }

    fun getReplaceProductCost(): ArrayList<MoreReplacedProductCostModel> {

        val replacedProductCostArrayListFinal = ArrayList<MoreReplacedProductCostModel>()
        for (i in 0..replacedProductCostArrayList.size - 1) {
            var getList: MoreReplacedProductCostModel = replacedProductCostArrayList.get(i)
            Log.v("adasdse333fggg", "isChecked " + getList.isChecked)
            if (getList.isChecked.equals("true")) {
                var ID_Product = getList.ID_Product
                var Code = getList.Code
                var Name = getList.Name
                var MRPs = getList.MRPs
                var MRP1R = getList.MRP1R
                var SalesPrice1R = getList.SalesPrice1R
                var SalePrice = getList.SalePrice
                var CurrentStock1R = getList.CurrentStock1R
                var StockId = getList.StockId
                var TaxAmount = getList.TaxAmount
                var StandbyStock = getList.StandbyStock
                var TotalCount = getList.TotalCount
                var isChecked = getList.isChecked
                replacedProductCostArrayListFinal.add(
                    MoreReplacedProductCostModel(
                        ID_Product,
                        Code,
                        Name,
                        MRPs,
                        MRP1R,
                        SalesPrice1R,
                        SalePrice,
                        CurrentStock1R,
                        StockId,
                        TaxAmount,
                        StandbyStock,
                        TotalCount,
                        isChecked
                    )
                )
            }
        }

        return replacedProductCostArrayListFinal

    }

    fun getReplaceProductCostJson(): JSONArray {
        val gson = Gson()
        val listString = gson.toJson(
            replacedProductCostArrayList,
            object : TypeToken<ArrayList<ReplacedProductCostModel?>?>() {}.type
        )
        var jsonArray = JSONArray(listString)
        return jsonArray
    }

}