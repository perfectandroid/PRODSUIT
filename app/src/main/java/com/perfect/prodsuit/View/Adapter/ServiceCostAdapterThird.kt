package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceCostAdapterThird(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    val serviceCostArrayList: ArrayList<ServiceCostModelMain>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "HistoryActMeetingAdapter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_cost_followup_third, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        try {
            var jsonObject = jsonArray.getJSONObject(position)
            var getList = serviceCostArrayList.get(position)
            Log.v("dsfsdfdsddddd", "position " + position)
            Log.v("dsfsdfdsddddd", "isChecked1 " + getList.isChecked)
            if (holder is MainViewHolder) {
                var isChecked = ""
                Log.e(TAG, "onBindViewHolder   1051   ")
                val pos = position
                // holder.tv_component.text = jsonObject!!.getString("Components")
                holder.tv_service_name.text = jsonObject!!.getString("Service")
                // holder.edtServiceCost.setText(jsonObject!!.getString("ServiceCost"))
                // holder.tv_service_cost.text = "\u20B9"+jsonObject!!.getString("ServiceCost")
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
                    if (compoundButton.isPressed()) {
                        if (b) {
                            isChecked = "true"
                            serviceCostArrayList.removeAt(pos)
                            serviceCostArrayList.add(
                                pos,
                                ServiceCostModelMain(
                                    "",
                                    "",
                                    "",
                                    "",
                                    jsonObject!!.getString("ID_Services"),
                                    jsonObject!!.getString("Service"),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "true",
                                    ""
                                )
                            )
                        } else {
                            isChecked = "false"
                            serviceCostArrayList.removeAt(pos)
                            serviceCostArrayList.add(
                                pos,
                                ServiceCostModelMain(
                                    "",
                                    "",
                                    "",
                                    "",
                                    jsonObject!!.getString("ID_Services"),
                                    jsonObject!!.getString("Service"),
                                    "",
                                    "",
                                    "",
                                    "",
                                    "false",
                                    ""
                                )
                            )
                        }
                    }
                })
//                holder.edtServiceCost.addTextChangedListener(object : TextWatcher {
//
//                    override fun afterTextChanged(s: Editable) {
//                        serviceCostArrayList.removeAt(pos)
//                        ServiceCostModelMain("",
//                            "",
//                            "",
//                            "",
//                            jsonObject!!.getString("ID_Services"),
//                            jsonObject!!.getString("Service"),
//                            s.toString(),
//                            "",
//                            "",
//                            "",
//                            "",
//                           ""
//                        )
//                    }
//
//                    override fun beforeTextChanged(s: CharSequence, start: Int,
//                                                   count: Int, after: Int) {
//
//                    }
//
//                    override fun onTextChanged(s: CharSequence, start: Int,
//                                               before: Int, count: Int) {
//                    }
//                })

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

        //        var tv_service_cost: TextView
        var tv_service_name: TextView

        // var edtServiceCost: EditText
        var checkbox: CheckBox

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            tv_service_name = v.findViewById(R.id.tv_service_name) as TextView
//            tv_service_cost = v.findViewById(R.id.tv_service_cost) as TextView
            // edtServiceCost = v.findViewById(R.id.edtServiceCost) as EditText
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }

    fun getServiceCost(): ArrayList<ServiceCostModelMain> {
        val serviceCostArrayListFinal = ArrayList<ServiceCostModelMain>()
        for (i in 0..serviceCostArrayList.size - 1) {
            var getList: ServiceCostModelMain = serviceCostArrayList.get(i)
            Log.v("dsad33ffdf", "isChecked " + getList.isChecked)
            Log.v("dsad33ffdf", "ServiceName " + getList.Service)
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

    fun getServiceCostJson(): JSONArray {
        val gson = Gson()
        val listString = gson.toJson(
            serviceCostArrayList,
            object : TypeToken<ArrayList<ServiceCostModelMain?>?>() {}.type
        )
        var jsonArray = JSONArray(listString)
        return jsonArray
    }

}