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
import com.google.gson.reflect.TypeToken.getArray
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat


class ServiceCostAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val serviceCostArrayList: ArrayList<ServiceCostModelMain>,
    val jsonArrayServiceType: JSONArray
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "HistoryActMeetingAdapter"
    internal var jsonObject: JSONObject? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_cost_followup, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        try {
        Log.v("sds3rdfdddd", "insdssss")
        jsonObject = jsonArray.getJSONObject(position)
        if (holder is MainViewHolder) {
            var isChecked = ""
            val pos = position
            val df = DecimalFormat("#.##")
            holder.tv_component.text = jsonObject!!.getString("Components")
            holder.tv_service_name.text = jsonObject!!.getString("Service")
            try {
                holder.edtServiceCost.setText(
                    df.format(
                        jsonObject!!.getString("ServiceCost").toFloat()
                    )
                )
                holder.edtTaxAmount.setText(
                    df.format(
                        jsonObject!!.getString("ServiceTaxAmount").toFloat()
                    )
                )
                holder.edtNetAmount.setText(
                    df.format(
                        jsonObject!!.getString("ServiceNetAmount").toFloat()
                    )
                )
            } catch (e: Exception) {
            }
            holder.edtRemarks.setText(jsonObject!!.getString("Remarks"))
            isChecked = "true"
            var searchType = Array<String>(jsonArrayServiceType.length()) { "" }
            for (i in 0 until jsonArrayServiceType.length()) {
                val objects: JSONObject = jsonArrayServiceType.getJSONObject(i)
                searchType[i] = objects.getString("ServiceTypeName");
            }
            val adapter =
                ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
            holder.edtServiceType!!.setAdapter(adapter)
            holder.edtServiceType!!.setText(searchType.get(0), false)
            holder.edtServiceType!!.setOnClickListener {
                holder.edtServiceType!!.showDropDown()
            }
            holder.edtServiceType!!.setOnItemClickListener { parent, view, position, id ->
                serviceCostArrayList.removeAt(pos)
                serviceCostArrayList.add(
                    pos, ServiceCostModelMain(
                        jsonObject!!.getString("Components"),
                        jsonObject!!.getString("ID_ProductWiseServiceDetails"),
                        jsonObject!!.getString("SubProduct"),
                        jsonObject!!.getString("ID_Product"),
                        jsonObject!!.getString("ID_Services"),
                        jsonObject!!.getString("Service"),
                        holder.edtServiceCost.text.toString(),
                        holder.edtTaxAmount.text.toString(),
                        holder.edtNetAmount.text.toString(),
                        holder.edtRemarks.text.toString(),
                        isChecked,
                        holder.edtServiceType.text.toString()
                    )
                )
            }
            holder.edtServiceCost.addTextChangedListener(object : TextWatcher {

                override fun afterTextChanged(s: Editable) {
                    var serviceCost = holder.edtServiceCost.text
                    if (serviceCost.isEmpty()) {
                        holder.edtTaxAmount.setText("")
                        holder.edtNetAmount.setText("")
                    } else {
                        val df = DecimalFormat("#.##")
                        val serviceCostDouble: Double = serviceCost.toString().toDouble()
                        val tax = .18 * serviceCostDouble
                        Log.v("dfffgdf44", "tax  " + tax)
                        holder.edtTaxAmount.setText(df.format(tax))
                        val net = serviceCostDouble + tax
                        holder.edtNetAmount.setText(df.format(net))
                    }

                    serviceCostArrayList.removeAt(pos)
                    serviceCostArrayList.add(
                        pos,
                        ServiceCostModelMain(
                            jsonObject!!.getString("Components"),
                            jsonObject!!.getString("ID_ProductWiseServiceDetails"),
                            jsonObject!!.getString("SubProduct"),
                            jsonObject!!.getString("ID_Product"),
                            jsonObject!!.getString("ID_Services"),
                            jsonObject!!.getString("Service"),
                            holder.edtServiceCost.text.toString(),
                            holder.edtTaxAmount.text.toString(),
                            holder.edtNetAmount.text.toString(),
                            holder.edtRemarks.text.toString(),
                            isChecked,
                            jsonObject!!.getString("serviceType")
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
            holder.edtServiceType.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    serviceCostArrayList.removeAt(pos)
                    serviceCostArrayList.add(
                        pos,
                        ServiceCostModelMain(
                            jsonObject!!.getString("Components"),
                            jsonObject!!.getString("ID_ProductWiseServiceDetails"),
                            jsonObject!!.getString("SubProduct"),
                            jsonObject!!.getString("ID_Product"),
                            jsonObject!!.getString("ID_Services"),
                            jsonObject!!.getString("Service"),
                            holder.edtServiceCost.text.toString(),
                            holder.edtTaxAmount.text.toString(),
                            holder.edtNetAmount.text.toString(),
                            holder.edtRemarks.text.toString(),
                            isChecked,
                            s.toString()
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
                    serviceCostArrayList.removeAt(pos)
                    serviceCostArrayList.add(
                        pos,
                        ServiceCostModelMain(
                            jsonObject!!.getString("Components"),
                            jsonObject!!.getString("ID_ProductWiseServiceDetails"),
                            jsonObject!!.getString("SubProduct"),
                            jsonObject!!.getString("ID_Product"),
                            jsonObject!!.getString("ID_Services"),
                            jsonObject!!.getString("Service"),
                            holder.edtServiceCost.text.toString(),
                            holder.edtTaxAmount.text.toString(),
                            holder.edtNetAmount.text.toString(),
                            s.toString(),
                            isChecked,
                            jsonObject!!.getString("serviceType")
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
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.v("sadsdsssss", "e  " + e)
//        }
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
        var tv_service_name: TextView
        var edtServiceCost: EditText
        var edtRemarks: EditText
        var edtTaxAmount: EditText
        var edtNetAmount: EditText
        var checkbox: CheckBox
        var edtServiceType: AutoCompleteTextView

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            tv_service_name = v.findViewById(R.id.tv_service_name) as TextView
            edtServiceCost = v.findViewById(R.id.edtServiceCost) as EditText
            edtTaxAmount = v.findViewById(R.id.edtTaxAmount) as EditText
            edtNetAmount = v.findViewById(R.id.edtNetAmount) as EditText
            edtRemarks = v.findViewById(R.id.edtRemarks) as EditText
            edtServiceType = v.findViewById(R.id.edtServiceType) as AutoCompleteTextView
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }

    fun getServiceCost(): ArrayList<ServiceCostModelMain> {
        val serviceCostArrayListFinal = ArrayList<ServiceCostModelMain>()
        for (i in 0..serviceCostArrayList.size - 1) {
            var getList: ServiceCostModelMain = serviceCostArrayList.get(i)
            Log.v("dsad33ffdf", "isChecked " + getList.isChecked)
            if (getList.isChecked.equals("true")) {
                serviceCostArrayListFinal.add(
                    ServiceCostModelMain(
                        getList.Components,
                        getList.ID_ProductWiseServiceDetails,
                        getList.SubProduct,
                        getList.ID_Product,
                        getList.ID_Services,
                        getList.Service,
                        getList.ServiceCost,
                        getList.ServiceTaxAmount,
                        getList.ServiceNetAmount,
                        getList.Remarks,
                        getList.isChecked,
                        getList.serviceType
                    )
                )
            }
        }

        return serviceCostArrayListFinal
    }

}