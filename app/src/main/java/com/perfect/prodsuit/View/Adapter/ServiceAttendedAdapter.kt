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
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Model.ModelServiceAttended
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

class ServiceAttendedAdapter (internal var context: Context, internal var mList: List<ModelServiceAttended>, internal var jsonArrayServiceType: JSONArray,):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceAttendedAdapter"
    internal var jsonObject: JSONObject? = null
   // private var clickListener: ItemClickListener? = null
   val data = ArrayList<ModelServiceAttended>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_attended, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position
                val df = DecimalFormat("#.##")

                val ItemsModel = mList[position]


                holder.tv_component.text = ItemsModel.SubProduct
                holder.tv_service_name.text = ItemsModel.Service

                try {

//                    Log.e(TAG,"onBindViewHolder   1051   "+ItemsModel.ServiceCost.toFloat())

                    if (!ItemsModel.ServiceCost.equals("")){
                        holder.edtServiceCost.setText(df.format(ItemsModel.ServiceCost.toFloat()))
                    }
                    else{
                        holder.edtServiceCost.setText("0")
                        ItemsModel.ServiceCost = "0"
                    }
                    if (!ItemsModel.ServiceTaxAmount.equals("")){
                        holder.edtTaxAmount.setText(df.format(ItemsModel.ServiceTaxAmount.toFloat()))
                    }
                    else{
                        holder.edtTaxAmount.setText("0")
                        ItemsModel.ServiceTaxAmount = "0"
                    }
                    if (!ItemsModel.ServiceNetAmount.equals("")){
                        holder.edtNetAmount.setText(df.format(ItemsModel.ServiceNetAmount.toFloat()))
                    }
                    else{
                        holder.edtNetAmount.setText("0")
                        ItemsModel.ServiceNetAmount = "0"
                    }


                    holder.edtRemarks.setText(ItemsModel.Remarks)
                    holder.edtServiceType!!.setText(ItemsModel.ServiceTypeName)
                    if (ItemsModel.isChecked.equals("1")){
                        holder.chkbox_service_attend.isChecked   = true
                    }else{
                        holder.chkbox_service_attend.isChecked   = false
                    }
                    var searchType = Array<String>(jsonArrayServiceType.length()) { "" }
                    for (i in 0 until jsonArrayServiceType.length()) {
                        val objects: JSONObject = jsonArrayServiceType.getJSONObject(i)
                        searchType[i] = objects.getString("ServiceTypeName");
                    }
                    if (ItemsModel.ServiceTypeId.equals("")){
                        jsonObject = jsonArrayServiceType.getJSONObject(0)
                        holder.edtServiceType.setText(jsonObject!!.getString("ServiceTypeName"))
                        ItemsModel.ServiceTypeId = jsonObject!!.getString("ServiceTypeId")
                        ItemsModel.ServiceTypeName = jsonObject!!.getString("ServiceTypeName")
                        if (jsonObject!!.getString("ServiceTypeId").equals("1")){
                            holder.edtServiceCost.isEnabled = true
                          //  holder.edtServiceCost.setText("0")
                        }else{
                            holder.edtServiceCost.isEnabled = false
                          //  holder.edtServiceCost.setText("0")
                        }
                    }
                    else{
                        holder.edtServiceType.setText(ItemsModel.ServiceTypeName)
                        if (ItemsModel.ServiceTypeId.equals("1")){
                            holder.edtServiceCost.isEnabled = true
                          //  holder.edtServiceCost.setText("0")
                        }else{
                            holder.edtServiceCost.isEnabled = false
                           // holder.edtServiceCost.setText("0")
                        }
                    }
               //     holder.edtServiceType!!.setText(searchType.get(0), false)
                    holder.edtServiceType.setTag(position)
                    holder.edtServiceType.setOnClickListener {
                        Log.e(TAG,"searchType   651   "+searchType)
                        val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
                        holder.edtServiceType!!.setAdapter(adapter)
                        holder.edtServiceType!!.showDropDown()
                    }
                    holder.edtServiceType!!.setOnItemClickListener { parent, view, pos, id ->
                        jsonObject = jsonArrayServiceType.getJSONObject(pos)
                        Log.e(TAG,"698   "+jsonObject!!.getString("ServiceTypeName"))
                        ItemsModel.ServiceTypeId = jsonObject!!.getString("ServiceTypeId")
                        ItemsModel.ServiceTypeName = jsonObject!!.getString("ServiceTypeName")

                        if (jsonObject!!.getString("ServiceTypeId").equals("1")){

                            holder.edtServiceCost.isEnabled = true
                            holder.edtServiceCost.setText("0")
                        }else{
                            holder.edtServiceCost.isEnabled = false
                            holder.edtServiceCost.setText("0")
                        }


                    }

                    holder.chkbox_service_attend.setOnClickListener {
                        if (holder.chkbox_service_attend.isChecked){
                            ItemsModel.isChecked = "1"
                        }else{
                            ItemsModel.isChecked = "0"
                        }
                    }

                    holder.edtServiceCost.addTextChangedListener(object : TextWatcher {

                        override fun afterTextChanged(s: Editable) {

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
                            var serviceCost = holder.edtServiceCost.text
                            if (serviceCost.isEmpty()) {
//                                holder.edtTaxAmount.setText("")
//                                holder.edtNetAmount.setText("")
                                ItemsModel.ServiceCost = "0"
                            } else {
//                                val df = DecimalFormat("#.##")
//                                val serviceCostDouble: Double = serviceCost.toString().toDouble()
//                                val tax = .18 * serviceCostDouble
//                                Log.v("dfffgdf44", "tax  " + tax)
//                                holder.edtTaxAmount.setText(df.format(tax))
//                                val net = serviceCostDouble + tax
//                                holder.edtNetAmount.setText(df.format(net))

                                ItemsModel.ServiceCost = serviceCost.toString()
                            }
                        }
                    })

                    holder.edtRemarks.addTextChangedListener(object : TextWatcher {

                        override fun afterTextChanged(s: Editable) {

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
                            var serviceRemarks = holder.edtRemarks.text
                            ItemsModel.Remarks = serviceRemarks.toString()

                        }
                    })



                } catch (e: Exception) {
                    Log.e(TAG,"6512 Exception  "+e.toString())
                }


//                holder.tv_component.text = jsonObject!!.getString("SubProduct")
//                holder.tv_service_name.text = jsonObject!!.getString("Service")
//
//                try {
//
//                    holder.edtServiceCost.setText(df.format(jsonObject!!.getString("ServiceCost").toFloat()))
//                    holder.edtTaxAmount.setText(df.format(jsonObject!!.getString("ServiceTaxAmount").toFloat()))
//                    holder.edtNetAmount.setText(df.format(jsonObject!!.getString("ServiceNetAmount").toFloat()))
//
//                } catch (e: Exception) {
//                }
//                holder.edtRemarks.setText(jsonObject!!.getString("Remarks"))

//                val pos = position+1
//                holder.txtSino.text        = pos.toString()
//                holder.txtCountry.text        = jsonObject!!.getString("Country")
//
//                holder.llCountry!!.setTag(position)
//                holder.llCountry!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "countrydetail")
//                })
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
        var tv_component: TextView
        var tv_service_name: TextView
        var edtServiceCost: EditText
        var edtRemarks: EditText
        var edtTaxAmount: EditText
        var edtNetAmount: EditText
        var chkbox_service_attend: CheckBox
        var edtServiceType: AutoCompleteTextView
        var edtServiceTypeLay: TextInputLayout

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            tv_service_name = v.findViewById(R.id.tv_service_name) as TextView
            edtServiceCost = v.findViewById(R.id.edtServiceCost) as EditText
            edtTaxAmount = v.findViewById(R.id.edtTaxAmount) as EditText
            edtNetAmount = v.findViewById(R.id.edtNetAmount) as EditText
            edtRemarks = v.findViewById(R.id.edtRemarks) as EditText
            edtServiceType = v.findViewById(R.id.edtServiceType) as AutoCompleteTextView
            edtServiceTypeLay = v.findViewById(R.id.edtServiceTypeLay) as TextInputLayout
            chkbox_service_attend = v.findViewById(R.id.chkbox_service_attend) as CheckBox
        }
    }

//    fun setClickListener(itemClickListener: ItemClickListener?) {
//        clickListener = itemClickListener
//    }


}