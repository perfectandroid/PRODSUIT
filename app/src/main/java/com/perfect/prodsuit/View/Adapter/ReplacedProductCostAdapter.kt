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
import com.perfect.prodsuit.Model.ReplacedProductCostModel
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ReplacedProductCostAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val replacedProductCostArrayList: ArrayList<ReplacedProductCostModel>
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
                holder.tv_component.text = jsonObject!!.getString("components")
                holder.edtAmount.setText(jsonObject!!.getString("amount"))
                holder.edt_qty.setText(jsonObject!!.getString("quantity"))
//                holder.edtChangeMode.setText(jsonObject!!.getString("changeMode"))
                holder.edtBuyBackAmount.setText(jsonObject!!.getString("buyBackAmount"))
                holder.edtRemarks.setText(jsonObject!!.getString("remark"))
                val searchTypeMain =
                    arrayOf<String>("BuyBack", "StandBy", "PickUp", "Replace")
                var searchType = Array<String>(4){""}
                searchType[0]=jsonObject!!.getString("changeMode")
                Log.v("dfdfddddd","searchType[0]  "+searchType[0])
                var i=1;
                for (x in searchTypeMain) {
                    Log.v("dfdfddddd","value  "+x)
                    if(searchType.contains(x))
                    {
                        Log.v("dfdfddddd","yes  ")
                    }
                    else
                    {
                        Log.v("dfdfddddd","no  ")
                        searchType[i]=x
                        i=i+1
                    }
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
                        ReplacedProductCostModel(
                            jsonObject!!.getString("components"),
                            holder.edtAmount.text.toString(),
                            holder.edt_qty.text.toString(),
                            holder.edtChangeMode.text.toString(),
                            holder.edtBuyBackAmount.text.toString(),
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
                holder.edtAmount.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                s.toString(),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
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
                holder.edt_qty.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable) {
                        replacedProductCostArrayList.removeAt(pos)
                        replacedProductCostArrayList.add(
                            pos,
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                holder.edtAmount.text.toString(),
                                s.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
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
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                holder.edtAmount.text.toString(),
                                holder.edt_qty.text.toString(),
                                s.toString(),
                                holder.edtBuyBackAmount.text.toString(),
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
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                holder.edtAmount.text.toString(),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                s.toString(),
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
                            ReplacedProductCostModel(
                                jsonObject!!.getString("components"),
                                holder.edtAmount.text.toString(),
                                holder.edt_qty.text.toString(),
                                holder.edtChangeMode.text.toString(),
                                holder.edtBuyBackAmount.text.toString(),
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

    fun getReplaceProductCost(): ArrayList<ReplacedProductCostModel> {

        val replacedProductCostArrayListFinal = ArrayList<ReplacedProductCostModel>()
        for (i in 0..replacedProductCostArrayList.size - 1) {
            var getList: ReplacedProductCostModel = replacedProductCostArrayList.get(i)
            Log.v("dsad33ffdf", "isChecked " + getList.isChecked)
            if (getList.isChecked.equals("true")) {
                var Components = getList.components
                var amount = getList.amount
                var quantity = getList.quantity
                var changeMode = getList.changeMode
                var buyBackAmount = getList.buyBackAmount
                var remark = getList.remark
                var isChecked = getList.isChecked
                replacedProductCostArrayListFinal.add(
                    ReplacedProductCostModel(
                        Components,
                        amount,
                        quantity,
                        changeMode,
                        buyBackAmount,
                        remark,
                        isChecked
                    )
                )
            }
        }

        return replacedProductCostArrayListFinal

    }

}