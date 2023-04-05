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
import com.perfect.prodsuit.Model.ReplacedProductCostModel
import com.perfect.prodsuit.Model.ReplacedProductCostModelFinal
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ReplacedProductCostAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val replacedProductCostArrayList: ArrayList<ReplacedProductCostModelFinal>,
    val jsonArrayChangeMode: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "HistoryActMeetingAdapter"
//    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_replaced_product_cost_followup, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   ")
                var isChecked = "true"
                val pos = position
                holder.tv_component.text = jsonObject!!.getString("Component")
                holder.edt_qty.setText(jsonObject!!.getString("Quantity"))
//                holder.edtChangeMode.setText(jsonObject!!.getString("changeMode"))
                holder.edtBuyBackAmount.setText(jsonObject!!.getString("buyBackAmount"))
                holder.edtRemarks.setText(jsonObject!!.getString("remarks"))
                var searchType = Array<String>(jsonArrayChangeMode.length()) { "" }
                for (i in 0 until jsonArrayChangeMode.length()) {
                    val objects: JSONObject = jsonArrayChangeMode.getJSONObject(i)
                    searchType[i] = objects.getString("ModeName");
                }
                val adapter =ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
                holder.edtChangeMode!!.setAdapter(adapter)
                holder.edtChangeMode!!.setText(searchType.get(0), false)
                holder.edtChangeMode!!.setOnClickListener {
                    holder.edtChangeMode!!.showDropDown()
                }
                holder.edtChangeMode!!.setOnItemClickListener { parent, view, position, id ->
                    replacedProductCostArrayList.removeAt(pos)
                    replacedProductCostArrayList.add(
                        pos,
                        ReplacedProductCostModelFinal(
                            jsonObject!!.getString("id"),
                            jsonObject!!.getString("Component"),
                            holder.edt_qty.text.toString(),
                            holder.edtChangeMode.text.toString(),
                            holder.edtBuyBackAmount.text.toString(),
                            holder.edtproduct.text.toString(),
                            holder.edtReplaceQuantity.text.toString(),
                            holder.edtreplaceamount.text.toString(),
                            holder.edtRemarks.text.toString(),
                            isChecked
                        )
                    )
                }
                if (jsonObject!!.getString("isChecked").equals("true")) {
                    holder.checkbox.isChecked = true
                    isChecked = "true"
                } else {
                    holder.checkbox.isChecked = false
                    isChecked = "false"
                }
              /*  holder.checkbox.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { compoundButton, b ->
                    if (b) {
                        isChecked = "true"
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                holder.edtAmount.text.toString(),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
                                holder.edtRemarks.text.toString(),
                                "true"
                            )
                        )
                    } else {
                        isChecked = "false"
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                holder.edtAmount.text.toString(),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
                                holder.edtRemarks.text.toString(),
                                "false"
                            )
                        )
                    }
                })*/

                holder.edt_qty.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModelFinal(
                                jsonObject!!.getString("id"),
                                jsonObject!!.getString("Component"),
                                s.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
                                holder.edtproduct.text.toString(),
                                holder.edtReplaceQuantity.text.toString(),
                                holder.edtreplaceamount.text.toString(),
                                holder.edtRemarks.text.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                holder.edtChangeMode.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModelFinal(
                                jsonObject!!.getString("id"),
                                jsonObject!!.getString("Component"),
                                holder.edt_qty.text.toString(),
                                s.toString(),
                                holder.edtBuyBackAmount.text.toString(),
                                holder.edtproduct.text.toString(),
                                holder.edtReplaceQuantity.text.toString(),
                                holder.edtreplaceamount.text.toString(),
                                holder.edtRemarks.text.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                holder.edtBuyBackAmount.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModelFinal(
                                jsonObject!!.getString("id"),
                                jsonObject!!.getString("Component"),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                s.toString(),
                                holder.edtproduct.text.toString(),
                                holder.edtReplaceQuantity.text.toString(),
                                holder.edtreplaceamount.text.toString(),
                                holder.edtRemarks.text.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                holder.edtproduct.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModelFinal(
                                jsonObject!!.getString("id"),
                                jsonObject!!.getString("Component"),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
                                s.toString(),
                                holder.edtReplaceQuantity.text.toString(),
                                holder.edtreplaceamount.text.toString(),
                                holder.edtRemarks.text.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
                    }
                })
                holder.edtRemarks.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModelFinal(
                                jsonObject!!.getString("id"),
                                jsonObject!!.getString("Component"),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
                                holder.edtproduct.text.toString(),
                                holder.edtReplaceQuantity.text.toString(),
                                holder.edtreplaceamount.text.toString(),
                                s.toString(),
                                isChecked
                            )
                        )
                    }

                    override fun beforeTextChanged(
                        s: CharSequence, start: Int,
                        count: Int, after: Int
                    ) {

                    }

                    override fun onTextChanged(
                        s: CharSequence, start: Int,
                        before: Int, count: Int
                    ) {
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
        var edt_qty: EditText
        var edtChangeMode: AutoCompleteTextView
        var edtBuyBackAmount: EditText
        var edtproduct: EditText
        var edtRemarks: EditText
        var edtreplaceamount: EditText
        var checkbox: CheckBox
        var edtReplaceQuantity: EditText

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            edt_qty = v.findViewById(R.id.edt_qty) as EditText
            edtChangeMode = v.findViewById(R.id.edtChangeMode) as AutoCompleteTextView
            edtBuyBackAmount = v.findViewById(R.id.edtBuyBackAmount) as EditText
            edtproduct = v.findViewById(R.id.edtproduct) as EditText
            edtRemarks = v.findViewById(R.id.edtRemarks) as EditText
            edtreplaceamount = v.findViewById(R.id.edtreplaceamount) as EditText
            edtReplaceQuantity = v.findViewById(R.id.edtReplaceQuantity) as EditText
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }

    fun getReplaceProductCost(): ArrayList<ReplacedProductCostModel>? {
        val replacedProductCostArrayListFinal = ArrayList<ReplacedProductCostModel>()
        for (i in 0..replacedProductCostArrayList.size - 1) {
            var getList: ReplacedProductCostModelFinal = replacedProductCostArrayList.get(i)
            Log.v("dsad33ffdf", "isChecked " + getList.isChecked)
            if (getList.isChecked.equals("true")) {
                var id = getList.id
                var Component = getList.Component
                var Quantity = getList.Quantity
                var changeMode = getList.changeMode
                var buyBackAmount = getList.buyBackAmount
                var replacedProduct = getList.replacedProduct
                var replacedQuantity = getList.replacedQuantity
                var replacedAmount = getList.replacedAmount
                var isChecked = getList.isChecked
                replacedProductCostArrayListFinal.add(
                    ReplacedProductCostModel(
                        id,
                        Component,
                        Quantity,
                        changeMode,
                        buyBackAmount,
                        replacedProduct,
                        replacedQuantity,
                        replacedAmount,
                        isChecked
                    )
                )
            }
        }

        return replacedProductCostArrayListFinal

    }

}