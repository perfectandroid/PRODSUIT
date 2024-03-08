package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.prodsuit.Helper.ClickListener
import com.perfect.prodsuit.Helper.DecimelFormatters
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ServiceCostModelMain
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.PickUpAndDeliveryListActivity
import com.perfect.prodsuit.View.Activity.PickUpAndDeliveryUpdateActivity
import org.json.JSONArray
import org.json.JSONObject

class ProdInformationAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    internal val TAG                  : String = "ProdInformationAdapter"
    internal var jsonObject           : JSONObject? = null
    private var clickListener         : ItemClickListener? = null
    var ID_Priority: String? = ""
    var ID_ComplaintList: String? = ""
    var ID_Product: String? = ""

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
                Log.e(TAG,"onBindViewHolder   1051111   "+jsonArray)
                Log.e(TAG,"ProvideStandBy   1051111   "+jsonObject!!.getString("ProvideStandBy"))

                val pos = position+1
                Log.e(TAG,"TransMode1212122   "+jsonObject!!.getString("TransMode"))

                Log.e(TAG,"SubMode   "+jsonObject!!.getString("SubMode"))

                if (jsonObject!!.getString("SubMode").equals("1")){       //PickupModule
                    Log.e(TAG,"ll_emi_Pickup 1  "+jsonObject!!.getString("SubMode"))

                    if (jsonObject!!.getString("TransMode").equals("INPDR")){    //emirecovery

                        Log.e(TAG,"ll_emi_Pickup 2  "+jsonObject!!.getString("SubMode"))

                        holder.provideStandBy.visibility      = View.GONE
                        holder.til_StandByProduct.visibility  = View.GONE
                        holder.til_StandByQuantity.visibility = View.GONE
                        holder.til_StandByAmount.visibility   = View.GONE
                        holder.tv_ProductName.visibility      = View.VISIBLE
                        holder.tie_Quantity.visibility        = View.VISIBLE
                        holder.tie_Remarks.visibility         = View.VISIBLE

                        holder.tie_Quantity.isEnabled = false
                        holder.tie_Quantity.isClickable = false
                        holder.tie_Quantity.isFocusable = false

                        ID_Product = jsonObject!!.getString("ID_Product")
                        holder.tv_ProductName.setText(jsonObject!!.getString("ProdName"))
                        holder.tie_Quantity.setText(jsonObject!!.getString("Quantity"))
//                        holder.tie_Remarks.setText(jsonObject!!.getString("Remarks"))

//                        holder.ll_emi_Pickup.visibility = View.VISIBLE
//                        holder.ll_Pickup.visibility = View.GONE
//
//
//                        ID_Product = jsonObject!!.getString("ID_Product")
//                        holder.tv_emi_ProductName.setText(jsonObject!!.getString("ProdName"))
//                        holder.tie_emi_Quantity.setText(jsonObject!!.getString("Quantity"))
//                        holder.tie_emi_Remarks.setText(jsonObject!!.getString("Remarks"))


                    }else{

                        Log.e(TAG,"til_StandByAmount   "+jsonObject!!.getString("SubMode"))

//                        holder.ll_emi_Pickup.visibility = View.GONE
//                        holder.ll_Pickup.visibility = View.VISIBLE
                        holder.provideStandBy.visibility      = View.VISIBLE
                        holder.til_StandByProduct.visibility  = View.VISIBLE
                        holder.til_StandByQuantity.visibility = View.VISIBLE
                        holder.til_StandByAmount.visibility   = View.VISIBLE

                        holder.tv_ProductName.text        = jsonObject!!.getString("ProdName")
                        holder.tie_StandByProduct.setText(jsonObject!!.getString("Product"))
                        holder.tie_Quantity.setText(jsonObject!!.getString("Quantity"))
                        holder.tie_StandByQuantity.setText(jsonObject!!.getString("SPQuantity"))
                        holder.tie_StandByAmount.setText(jsonObject!!.getString("SPAmount"))
                        holder.tie_Remarks.setText(jsonObject!!.getString("Remarks"))
                        DecimelFormatters.setDecimelPlace(holder.tie_StandByAmount!!)



                        holder.tie_StandByAmount.setTag(position)
                        holder.tie_StandByAmount.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                            }

                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("SPAmount",s.toString())
                                clickListener!!.onClick(
                                    position,
                                    "changeAmount"
                                )
                            }

                        })
                    }

                    Log.e(TAG,"isEnable   1051   "+jsonObject!!.getString("isEnable"))

                    if (jsonObject!!.getString("isEnable").equals("0")){
                        holder.tie_StandByAmount.isEnabled = false
                    }else{
                        holder.tie_StandByAmount.isEnabled = true
                    }

                    if (jsonObject!!.getString("isSelected").equals("1") && jsonObject!!.getString("ProvideStandBy").equals("1"))  {
                        holder.tie_StandByAmount.isEnabled = true
                    }else{
                        holder.tie_StandByAmount.isEnabled = false
                    }

                    Log.e(TAG,"isSelected  666111    "+jsonObject!!.getString("isSelected"))


                    holder.checkbox!!.setTag(position)
                    holder.checkbox!!.setOnClickListener(View.OnClickListener {
                        if (holder.checkbox.isChecked){
                            Log.e(TAG,"82 checkbox   on")
                            holder.checkbox!!.isChecked = true
//                        holder.tie_StandByAmount.isEnabled = false
                            val jsonObject1 = jsonArray.getJSONObject(position)
                            jsonObject1.put("isSelected","1")
                            clickListener!!.onClick(position, "changeAmount")

                        }else{
                            Log.e(TAG,"82 checkbox   off")
                            val jsonObject1 = jsonArray.getJSONObject(position)
                            jsonObject1.put("isSelected","0")
                            clickListener!!.onClick(position, "changeAmount")
//                        holder.tie_StandByAmount.isEnabled = false
                            holder.tie_Quantity.isEnabled = false
                            holder.tie_StandByProduct.isEnabled = false
                            holder.tie_StandByQuantity.isEnabled = false
                            holder.tie_StandByAmount.setText("")
                            holder.tie_StandByProduct.setText("")
                            holder.tie_StandByQuantity.setText("")
                        }
                    })

                    holder.swOnOff!!.setTag(position)
                    holder.swOnOff!!.setOnClickListener(View.OnClickListener {
                        if (holder.swOnOff.isChecked && holder.checkbox.isChecked){
                            Log.e(TAG,"82 swOnOff   on")
                            val jsonObject1 = jsonArray.getJSONObject(position)
                            jsonObject1.put("ProvideStandBy","1")
                            holder.tie_StandByAmount.isEnabled = false
                            holder.tie_Quantity.isEnabled = true
                            holder.tie_StandByProduct.isEnabled = true
                            holder.tie_StandByQuantity.isEnabled = true
//                        holder.tie_StandByAmount.setText("0.00")
                            clickListener!!.onClick(
                                position,
                                "changeAmount"
                            )
                        }else{
                            Log.e(TAG,"82 swOnOff   off")
                            val jsonObject1 = jsonArray.getJSONObject(position)
                            jsonObject1.put("ProvideStandBy","0")
                            holder.tie_StandByAmount.isEnabled = false
//                        holder.tie_Quantity.isEnabled = false
                            holder.tie_StandByProduct.isEnabled = false
                            holder.tie_StandByQuantity.isEnabled = false
                            holder.tie_StandByAmount.setText("")
                            holder.tie_StandByProduct.setText("")
                            holder.tie_StandByQuantity.setText("")
                            clickListener!!.onClick(
                                position,
                                "changeAmount"
                            )

                        }
                    })


                    holder.tie_StandByProduct.setOnClickListener(View.OnClickListener {
                        clickListener!!.onClick(
                            position,
                            "ProductName"
                        )

//                        Log.e(TAG,"standByAmount  ddddddd    "+jsonObject!!.getString("ProductName"))
//                        holder.tie_StandByProduct!!.setText(jsonObject!!.getString("Product"))
                    })


                }
                else{
                    Log.e(TAG,"til_StandByAmount else   "+jsonObject!!.getString("SubMode"))

//                    holder.tie_Priority.setText(jsonObject!!.getString("PriorityName"))
//                    holder.tie_Quantity_delivery.setText(jsonObject!!.getString("Quantity"))
//                    holder.tie_Complaint_Type.setText(jsonObject!!.getString("ComplaintName"))
//                    holder.tv_ProductName_Delivery.setText(jsonObject!!.getString("Product"))
//                    holder.tie_ComplaintQty.setText(jsonObject!!.getString("ComplaintQty"))
//                    holder.tie_Description.setText(jsonObject!!.getString("Description"))

                    holder.til_StandByAmount.visibility = View.GONE
                    holder.ll_delivery.visibility = View.GONE
//                    holder.ll_Pickup.visibility = View.GONE
//                    holder.ll_emi_Pickup.visibility = View.GONE


//                    Log.e(TAG, "isSelectedDelivery1231   " + jsonObject!!.getString("isSelectedDelivery"))

                    if (jsonObject!!.getString("TransMode").equals("CUSA")){  //service

                        Log.e(TAG, "TransMode 001   " + jsonObject!!.getString("TransMode"))
                        holder.ll_Pickup.visibility = View.VISIBLE
                        holder.ll_delivery.visibility = View.GONE

                        Log.e(TAG, "service 0011   " + jsonObject!!.getString("Product"))
                        Log.e(TAG, "service 00112  " + jsonObject!!.getString("ProdName"))
//                        Log.e(TAG, "service 0010   " + jsonObject!!.getString("StandByProduct"))
//                        Log.e(TAG, "service 001   " + jsonObject!!.getString("SPQuantity"))
//                        Log.e(TAG, "service 001   " + jsonObject!!.getString("Quantity"))

                        holder.tv_ProductName.text        = jsonObject!!.getString("Product")
                        holder.tie_StandByProduct.setText(jsonObject!!.getString("Product"))
                        holder.tie_Quantity.setText(jsonObject!!.getString("Quantity"))
                        holder.tie_StandByQuantity.setText(jsonObject!!.getString("SPQuantity"))
//                        holder.tie_Remarks.setText(jsonObject!!.getString("Remarks"))


                        holder.checkbox!!.setTag(position)
                        holder.checkbox!!.setOnClickListener(View.OnClickListener {
                            if (holder.checkbox.isChecked){
                                Log.e(TAG,"82 checkbox   on")
                                holder.checkbox!!.isChecked = true
//                        holder.tie_StandByAmount.isEnabled = false
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("isSelected","1")
//                                clickListener!!.onClick(position, "changeAmount")

                            }else{
                                Log.e(TAG,"82 checkbox   off")
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("isSelected","0")
//                                clickListener!!.onClick(position, "changeAmount")
//                        holder.tie_StandByAmount.isEnabled = false
                                holder.tie_Quantity.isEnabled = false
                                holder.tie_StandByProduct.isEnabled = false
                                holder.tie_StandByQuantity.isEnabled = false
//                                holder.tie_StandByAmount.setText("")
//                                holder.tie_StandByProduct.setText("")
                                holder.tie_StandByQuantity.setText("")
                            }
                        })

                        holder.swOnOff!!.setTag(position)
                        holder.swOnOff!!.setOnClickListener(View.OnClickListener {
                            if (holder.swOnOff.isChecked && holder.checkbox.isChecked){
                                Log.e(TAG,"82 swOnOff   on")
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("ProvideStandBy","1")
//                                holder.tie_StandByAmount.isEnabled = false
                                holder.tie_Quantity.isEnabled = true
                                holder.tie_StandByProduct.isEnabled = true
                                holder.tie_StandByQuantity.isEnabled = true
//                        holder.tie_StandByAmount.setText("0.00")
//                                clickListener!!.onClick(
//                                    position,
//                                    "changeAmount"
//                                )
                            }else{
                                Log.e(TAG,"82 swOnOff   off")
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("ProvideStandBy","0")
//                                holder.tie_StandByAmount.isEnabled = false
//                        holder.tie_Quantity.isEnabled = false
                                holder.tie_StandByProduct.isEnabled = false
                                holder.tie_StandByQuantity.isEnabled = false
                                holder.tie_StandByAmount.setText("")
//                                holder.tie_StandByProduct.setText("")
                                holder.tie_StandByQuantity.setText("")
//                                clickListener!!.onClick(
//                                    position,
//                                    "changeAmount"
//                                )

                            }
                        })

                        holder.tie_StandByProduct.setOnClickListener(View.OnClickListener {
                            clickListener!!.onClick(
                                position,
                                "ProductName"
                            )

//                            Log.e(TAG,"standByAmount  ddddddd    "+jsonObject!!.getString("ProductName"))
//                            holder.tie_StandByProduct!!.setText(jsonObject!!.getString("Product"))
                        })


                    }else if(jsonObject!!.getString("TransMode").equals("INSBR")){      //standby recovery

                        Log.e(TAG, "TransMode innn   " + jsonObject!!.getString("TransMode"))
                        holder.ll_Pickup.visibility = View.VISIBLE
                        holder.ll_Productnamepickup.visibility = View.GONE
                        holder.provideStandBy.visibility = View.GONE
                        holder.til_StandByAmount.visibility = View.GONE
                        holder.ll_delivery.visibility = View.GONE

                        holder.tie_Quantity.isEnabled = false
                        holder.tie_Quantity.isClickable = false
                        holder.tie_Quantity.isFocusable = false

                        holder.tie_StandByProduct.isEnabled = false
                        holder.tie_StandByProduct.isClickable = false
                        holder.tie_StandByProduct.isFocusable = false

                        holder.tie_StandByQuantity.isEnabled = false
                        holder.tie_StandByQuantity.isClickable = false
                        holder.tie_StandByQuantity.isFocusable = false

                        holder.tvv_productnameLabel.setText("Replace Product")
                        holder.til_Quantity.setHint("Replace Quantity")


                        holder.tv_ProductName.setText(jsonObject!!.getString("Product"))

                        Log.e(TAG,"sdggdgsgsgd "+jsonObject!!.getString("Product"))
                        Log.e(TAG,"sdggdgsgsgd "+jsonObject!!.getString("SPQuantity"))

                        holder.tie_Quantity.setText(jsonObject!!.getString("Quantity"))
                        holder.tie_StandByProduct.setText(jsonObject!!.getString("StandByProduct"))
                        holder.tie_StandByQuantity.setText(jsonObject!!.getString("SPQuantity"))
//                        holder.tie_Remarks.setText(jsonObject!!.getString("Remarks"))

                    }else if(jsonObject!!.getString("TransMode").equals("INDA")){  //sales

                        Log.e(TAG, "TransMode innn22   " + jsonObject!!.getString("TransMode"))
                        holder.ll_Pickup.visibility = View.GONE
                        holder.ll_delivery.visibility = View.VISIBLE


                        holder.tie_Priority.setText(jsonObject!!.getString("PriorityName"))
                        holder.tie_Quantity_delivery.setText(jsonObject!!.getString("Quantity"))
                        holder.tie_Complaint_Type.setText(jsonObject!!.getString("ComplaintName"))
                        holder.tv_ProductName_Delivery.setText(jsonObject!!.getString("Product"))
                        holder.tie_ComplaintQty.setText(jsonObject!!.getString("ComplaintQty"))
                        holder.tie_Description.setText(jsonObject!!.getString("Description"))

                        Log.e(TAG, "sales innn22   " + jsonObject!!.getString("PriorityName"))
                        Log.e(TAG, "sales innn22   " + jsonObject!!.getString("Quantity"))
                        Log.e(TAG, "sales innn22   " + jsonObject!!.getString("ComplaintName"))
                        Log.e(TAG, "sales innn22   " + jsonObject!!.getString("Product"))
                        Log.e(TAG, "sales innn22   " + jsonObject!!.getString("ComplaintQty"))
                        Log.e(TAG, "sales innn22   " + jsonObject!!.getString("Description"))

                        if (jsonObject!!.getString("isSelectedDelivery").equals("0")) {
                            holder.check_Complaintbox.isChecked = false

                            holder.tie_Priority.isEnabled = false
                            holder.tie_ComplaintQty.isEnabled = false
                            holder.tie_Complaint_Type.isEnabled = false
                            holder.tie_Description.isEnabled = false

                            Log.e(TAG, "isSelectedDelivery off   " + jsonObject!!.getString("isSelectedDelivery"))
                            Log.e(TAG, "isSelectedDelivery  false  ")
                        } else {
                            holder.check_Complaintbox.isChecked = true
                            Log.e(TAG, "isSelectedDelivery On   " + jsonObject!!.getString("isSelectedDelivery"))
                            Log.e(TAG, "isSelectedDelivery  true  ")

                        }

                        holder.check_Complaintbox!!.setTag(position)
                        holder.check_Complaintbox!!.setOnClickListener(View.OnClickListener {
                            if (holder.check_Complaintbox.isChecked){
                                Log.e(TAG,"82 checkbox   on")
                                holder.check_Complaintbox!!.isChecked = true

                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("isSelectedDelivery","1")
                                holder.tie_Priority.isEnabled = true
                                holder.tie_ComplaintQty.isEnabled = true
                                holder.tie_Complaint_Type.isEnabled = true
                                holder.tie_Description.isEnabled = true

                                Log.e(TAG,"adapter 22  "+jsonObject1)
                            }else{
                                Log.e(TAG,"82 checkbox   off")
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("isSelectedDelivery","0")
                                jsonObject1.put("ID_Priority","")
                                jsonObject1.put("ID_ComplaintList","")
                                jsonObject1.put("ComplaintName","")

                                holder.tie_Priority.isEnabled = false
                                holder.tie_ComplaintQty.isEnabled = false
                                holder.tie_Complaint_Type.isEnabled = false
                                holder.tie_Description.isEnabled = false

                                holder.tie_Priority.setText("")
                                holder.tie_ComplaintQty.setText("")
                                holder.tie_Complaint_Type.setText("")
                                holder.tie_Description.setText("")
                                Log.e(TAG,"adapter 11  "+jsonObject1)
                            }

                        })

                        holder.tie_ComplaintQty.setTag(position)
                        holder.tie_ComplaintQty.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                            }

                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("ComplaintQty",s.toString())
                            }

                        })

                        holder.tie_Description.setTag(position)
                        holder.tie_Description.addTextChangedListener(object : TextWatcher {
                            override fun afterTextChanged(s: Editable?) {
                            }

                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                val jsonObject1 = jsonArray.getJSONObject(position)
                                jsonObject1.put("Description",s.toString())
                            }

                        })



                        holder.tie_Priority.setOnClickListener(View.OnClickListener {
                            clickListener!!.onClick(
                                position,
                                "PriorityCheck"
                            )

                            Log.e(TAG,"PriorityCheck    ")
                        })

//                    holder.tie_Complaint_Type.setTag(position)
                        holder.tie_Complaint_Type.setOnClickListener(View.OnClickListener {
                            clickListener!!.onClick(
                                position,
                                "ComplaintTypeclick"
                            )

                            Log.e(TAG,"ComplaintType 111   ")
                        })

                        Log.e(TAG,"PriorityName 0088    "+jsonObject!!.getString("PriorityName"))


                        holder.tie_StandByProduct.setOnClickListener(View.OnClickListener {
                            clickListener!!.onClick(
                                position,
                                "ProductName"
                            )

//                            Log.e(TAG,"standByAmount  ddddddd    "+jsonObject!!.getString("ProductName"))
//                            holder.tie_StandByProduct!!.setText(jsonObject!!.getString("Product"))
                        })

                    }

//                        if (jsonObject!!.getString("isSelectedDelivery").equals("0")) {
//                            holder.check_Complaintbox.isChecked = false
//
//                            holder.tie_Priority.isEnabled = false
//                            holder.tie_ComplaintQty.isEnabled = false
//                            holder.tie_Complaint_Type.isEnabled = false
//                            holder.tie_Description.isEnabled = false
//
//                            Log.e(TAG, "isSelectedDelivery off   " + jsonObject!!.getString("isSelectedDelivery"))
//                            Log.e(TAG, "isSelectedDelivery  false  ")
//                        } else {
//                            holder.check_Complaintbox.isChecked = true
//                            Log.e(TAG, "isSelectedDelivery On   " + jsonObject!!.getString("isSelectedDelivery"))
//                            Log.e(TAG, "isSelectedDelivery  true  ")
//
//                        }
//
//                    holder.check_Complaintbox!!.setTag(position)
//                    holder.check_Complaintbox!!.setOnClickListener(View.OnClickListener {
//                        if (holder.check_Complaintbox.isChecked){
//                            Log.e(TAG,"82 checkbox   on")
//                            holder.check_Complaintbox!!.isChecked = true
//
//                            val jsonObject1 = jsonArray.getJSONObject(position)
//                            jsonObject1.put("isSelectedDelivery","1")
//                            holder.tie_Priority.isEnabled = true
//                            holder.tie_ComplaintQty.isEnabled = true
//                            holder.tie_Complaint_Type.isEnabled = true
//                            holder.tie_Description.isEnabled = true
//
//                            Log.e(TAG,"adapter 22  "+jsonObject1)
//                        }else{
//                            Log.e(TAG,"82 checkbox   off")
//                            val jsonObject1 = jsonArray.getJSONObject(position)
//                            jsonObject1.put("isSelectedDelivery","0")
//                            jsonObject1.put("ID_Priority","")
//                            jsonObject1.put("ID_ComplaintList","")
//                            jsonObject1.put("ComplaintName","")
//
//                            holder.tie_Priority.isEnabled = false
//                            holder.tie_ComplaintQty.isEnabled = false
//                            holder.tie_Complaint_Type.isEnabled = false
//                            holder.tie_Description.isEnabled = false
//
//                            holder.tie_Priority.setText("")
//                            holder.tie_ComplaintQty.setText("")
//                            holder.tie_Complaint_Type.setText("")
//                            holder.tie_Description.setText("")
//                            Log.e(TAG,"adapter 11  "+jsonObject1)
//                        }
//
//                    })
//
//                    holder.tie_ComplaintQty.setTag(position)
//                    holder.tie_ComplaintQty.addTextChangedListener(object : TextWatcher {
//                        override fun afterTextChanged(s: Editable?) {
//                        }
//
//                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//
//                        }
//
//                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                            val jsonObject1 = jsonArray.getJSONObject(position)
//                            jsonObject1.put("ComplaintQty",s.toString())
//                        }
//
//                    })
//
//                    holder.tie_Description.setTag(position)
//                    holder.tie_Description.addTextChangedListener(object : TextWatcher {
//                        override fun afterTextChanged(s: Editable?) {
//                        }
//
//                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//
//                        }
//
//                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                            val jsonObject1 = jsonArray.getJSONObject(position)
//                            jsonObject1.put("Description",s.toString())
//                        }
//
//                    })
//
//
//
//                    holder.tie_Priority.setOnClickListener(View.OnClickListener {
//                        clickListener!!.onClick(
//                            position,
//                            "PriorityCheck"
//                        )
//
//                        Log.e(TAG,"PriorityCheck    ")
//                    })
//
////                    holder.tie_Complaint_Type.setTag(position)
//                    holder.tie_Complaint_Type.setOnClickListener(View.OnClickListener {
//                        clickListener!!.onClick(
//                            position,
//                            "ComplaintTypeclick"
//                        )
//
//                        Log.e(TAG,"ComplaintType 111   ")
//                    })
//
//                    Log.e(TAG,"PriorityName 0088    "+jsonObject!!.getString("PriorityName"))
                }

                //checkbox
                if (jsonObject!!.getString("isSelected").equals("0")){
                    holder.checkbox.isChecked = false
                }else{
                    holder.checkbox.isChecked = true
                }


//                Log.e(TAG,"isEnable   1051   "+jsonObject!!.getString("isEnable"))
//
//                if (jsonObject!!.getString("isEnable").equals("0")){
//                    holder.tie_StandByAmount.isEnabled = false
//                }else{
//                    holder.tie_StandByAmount.isEnabled = true
//                }
//
//                if (jsonObject!!.getString("isSelected").equals("1") && jsonObject!!.getString("ProvideStandBy").equals("1"))  {
//                    holder.tie_StandByAmount.isEnabled = true
//                }else{
//                    holder.tie_StandByAmount.isEnabled = false
//                }
//
//                Log.e(TAG,"isSelected  666111    "+jsonObject!!.getString("isSelected"))

//                holder.checkbox!!.setTag(position)
//                holder.checkbox!!.setOnClickListener(View.OnClickListener {
//                    if (holder.checkbox.isChecked){
//                        Log.e(TAG,"82 checkbox   on")
//                        holder.checkbox!!.isChecked = true
////                        holder.tie_StandByAmount.isEnabled = false
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("isSelected","1")
//                        clickListener!!.onClick(position, "changeAmount")
//
//                    }else{
//                        Log.e(TAG,"82 checkbox   off")
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("isSelected","0")
//                        clickListener!!.onClick(position, "changeAmount")
////                        holder.tie_StandByAmount.isEnabled = false
//                        holder.tie_Quantity.isEnabled = false
//                        holder.tie_StandByProduct.isEnabled = false
//                        holder.tie_StandByQuantity.isEnabled = false
//                        holder.tie_StandByAmount.setText("")
//                        holder.tie_StandByProduct.setText("")
//                        holder.tie_StandByQuantity.setText("")
//                    }
//                })




                Log.e(TAG,"ProvideStandBy   11221  "+jsonObject!!.getString("ProvideStandBy"))
                //  swOnOff
                if (jsonObject!!.getString("ProvideStandBy").equals("0")){
                    Log.e(TAG,"standByAmount  68111    "+position)
                    holder.swOnOff.isChecked = false
                    holder.tie_StandByAmount.isEnabled = false
//                    holder.tie_Quantity.isEnabled = false
                    holder.tie_StandByProduct.isEnabled = false
                    holder.tie_StandByQuantity.isEnabled = false
                    holder.tie_StandByAmount.setText("")
//                    holder.tie_StandByProduct.setText("")
                    holder.tie_StandByQuantity.setText("")
                    Log.e(TAG,"standByAmount  6812    "+jsonObject!!.getString("ProvideStandBy"))
                }else{
                    Log.e(TAG,"standByAmount  68112    "+position)
                    holder.swOnOff.isChecked = true
                    holder.checkbox.isChecked = true
                    holder.tie_StandByAmount.isEnabled = true
                    holder.tie_Quantity.isEnabled = true
                    holder.tie_StandByProduct.isEnabled = true
                    holder.tie_StandByQuantity.isEnabled = true
//                    holder.tie_StandByAmount.setText("0.00")
                    Log.e(TAG,"standByAmount  6812    "+jsonObject!!.getString("ProvideStandBy"))
                }

//                if (jsonObject!!.getString("Product").equals("")){
//                    val jsonObject1 = prodDetailsjsonArray.getJSONObject(position)
//                    jsonObject1.put("ProductName","")
//                    jsonObject1.put("ProductCode","")
//                    clickListener!!.onClick(position, "ProductName")
//                }else{
//                    holder.tie_StandByProduct.setOnClickListener(View.OnClickListener {
//                }

                  //  .......................................................................

//                Log.e(TAG,"standByAmount  6812    "+jsonObject!!.getString("SPAmount"))

//                holder.tv_ProductName.text        = jsonObject!!.getString("ProdName")
//                holder.tv_ProductName.text        = jsonObject!!.getString("Product")
//                holder.tie_StandByProduct.setText(jsonObject!!.getString("Product"))
//                holder.tie_Quantity.setText(jsonObject!!.getString("Quantity"))
//                holder.tie_StandByQuantity.setText(jsonObject!!.getString("SPQuantity"))
//                holder.tie_StandByAmount.setText(jsonObject!!.getString("SPAmount"))
//                holder.tie_Remarks.setText(jsonObject!!.getString("Remarks"))
//                DecimelFormatters.setDecimelPlace(holder.tie_StandByAmount!!)

                 //   .......................................................................

//                holder.tie_StandByProduct.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(
//                        position,
//                        "ProductName"
//                    )
//
//                    Log.e(TAG,"standByAmount  ddddddd    "+jsonObject!!.getString("ProductName"))
//                    holder.tie_StandByProduct!!.setText(jsonObject!!.getString("Product"))
//                })



//                var searchType = Array<String>(prodDetailsjsonArray.length()) { "" }
//                for (i in 0 until prodDetailsjsonArray.length()) {
//                    val objects: JSONObject = prodDetailsjsonArray.getJSONObject(i)
//                    searchType[i] = objects.getString("ServiceTypeName");
//                }
//                val adapter =
//                    ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
//                holder.edtServiceType!!.setAdapter(adapter)
//                holder.edtServiceType!!.setText(searchType.get(0), false)
//                holder.edtServiceType!!.setOnClickListener {
//                    holder.edtServiceType!!.showDropDown()
//                }

//                holder.edtServiceType!!.setOnItemClickListener { parent, view, position, id ->
//                    serviceCostArrayList.removeAt(pos)
//                    serviceCostArrayList.add(
//                        pos, ServiceCostModelMain(
//                            jsonObject!!.getString("Components"),
//                            jsonObject!!.getString("ID_ProductWiseServiceDetails"),
//                            jsonObject!!.getString("SubProduct"),
//                            jsonObject!!.getString("ID_Product"),
//                            jsonObject!!.getString("ID_Services"),
//                            jsonObject!!.getString("Service"),
//                            holder.edtServiceCost.text.toString(),
//                            holder.edtTaxAmount.text.toString(),
//                            holder.edtNetAmount.text.toString(),
//                            holder.edtRemarks.text.toString(),
//                            isChecked,
//                            holder.edtServiceType.text.toString()
//                        )
//                    )
//                }
                Log.e(TAG,"Quantity  1253    "+jsonObject!!.getString("Quantity"))
                holder.tie_Quantity.setTag(position)
                holder.tie_Quantity.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("Quantity",s.toString())
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
                        jsonObject1.put("Product",s.toString())
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
                        jsonObject1.put("SPQuantity",s.toString())
                    }

                })

//                holder.tie_StandByAmount.setTag(position)
//                holder.tie_StandByAmount.addTextChangedListener(object : TextWatcher {
//                    override fun afterTextChanged(s: Editable?) {
//                    }
//
//                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//
//                    }
//
//                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("SPAmount",s.toString())
//                        clickListener!!.onClick(
//                            position,
//                            "changeAmount"
//                        )
//                    }
//
//                })

                holder.tie_Remarks.setTag(position)
                holder.tie_Remarks.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        val jsonObject1 = jsonArray.getJSONObject(position)
                        jsonObject1.put("Remarks",s.toString())
                    }

                })


////                holder.checkbox!!.isChecked = true
//                holder.checkbox!!.setTag(position)
//                holder.checkbox!!.setOnClickListener(View.OnClickListener {
//                    if (holder.checkbox.isChecked){
//                        Log.e(TAG,"82 checkbox   on")
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("isSelected","1")
//                        clickListener!!.onClick(position, "changeAmount")
//
//                    }else{
//                        Log.e(TAG,"82 checkbox   off")
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("isSelected","0")
//                        clickListener!!.onClick(position, "changeAmount")
//                        holder.tie_StandByAmount.setText("")
//                        holder.tie_StandByProduct.setText("")
//                        holder.tie_StandByQuantity.setText("")
//                    }
//                })


//                holder.swOnOff!!.setTag(position)
//                holder.swOnOff!!.setOnClickListener(View.OnClickListener {
//                    if (holder.swOnOff.isChecked){
//                        Log.e(TAG,"82 swOnOff   on")
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("ProvideStandBy","1")
//                        holder.tie_StandByAmount.isEnabled = false
//                        holder.tie_Quantity.isEnabled = true
//                        holder.tie_StandByProduct.isEnabled = true
//                        holder.tie_StandByQuantity.isEnabled = true
////                        holder.tie_StandByAmount.setText("0.00")
//                        clickListener!!.onClick(
//                            position,
//                            "changeAmount"
//                        )
//                    }else{
//                        Log.e(TAG,"82 swOnOff   off")
//                        val jsonObject1 = jsonArray.getJSONObject(position)
//                        jsonObject1.put("ProvideStandBy","0")
//                        holder.tie_StandByAmount.isEnabled = false
////                        holder.tie_Quantity.isEnabled = false
//                        holder.tie_StandByProduct.isEnabled = false
//                        holder.tie_StandByQuantity.isEnabled = false
//                        holder.tie_StandByAmount.setText("")
//                        holder.tie_StandByProduct.setText("")
//                        holder.tie_StandByQuantity.setText("")
//                        clickListener!!.onClick(
//                            position,
//                            "changeAmount"
//                        )
//
//                    }
//                })

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
        internal var tv_ProductName_Delivery : TextView
        internal var tvv_productnameLabel : TextView
//        internal var tv_emi_ProductName      : TextView
//        internal var tie_emi_Quantity        : TextView
//        internal var tie_emi_Remarks         : TextView
        internal var tie_Quantity_delivery   : TextInputEditText
        internal var tie_Complaint_Type      : TextInputEditText
        internal var tie_Priority            : TextInputEditText
        internal var tie_ComplaintQty        : TextInputEditText
        internal var tie_Quantity            : TextInputEditText
        internal var tie_Description         : TextInputEditText
        internal lateinit var til_Quantity   : TextInputLayout
        internal lateinit var til_StandByProduct    : TextInputLayout
        internal lateinit var til_StandByQuantity   : TextInputLayout
        internal lateinit var til_StandByAmount     : TextInputLayout
        internal var tie_StandByQuantity     : TextInputEditText
        internal var tie_StandByProduct      : TextInputEditText
        internal var tie_StandByAmount       : TextInputEditText
        internal var tie_Remarks             : TextInputEditText
        internal var swOnOff                 : SwitchCompat
        internal var checkbox                : CheckBox
        internal var check_Complaintbox      : CheckBox
//        internal var checkbox_emi            : CheckBox
        internal var ll_complaintbox         : LinearLayout
        internal var provideStandBy          : LinearLayout
        internal var ll_Pickup               : LinearLayout
        internal var ll_delivery             : LinearLayout
        internal var ll_Productnamepickup    : LinearLayout
//        internal var ll_emi_Pickup           : LinearLayout
//        internal var txtGridNotification  : TextView
//        internal var image                : ImageView
//        internal var ll_homeGrid          : LinearLayout

        init {
            tv_ProductName         = v.findViewById<View>(R.id.tv_ProductName)         as TextView
            tv_ProductName_Delivery= v.findViewById<View>(R.id.tv_ProductName_Delivery)as TextView
            tvv_productnameLabel= v.findViewById<View>(R.id.tvv_productnameLabel)as TextView
//            tv_emi_ProductName     = v.findViewById<View>(R.id.tv_emi_ProductName)     as TextView
//            tie_emi_Quantity       = v.findViewById<View>(R.id.tie_emi_Quantity)       as TextView
//            tie_emi_Remarks        = v.findViewById<View>(R.id.tie_emi_Remarks)        as TextView
            tie_Quantity_delivery  = v.findViewById<View>(R.id.tie_Quantity_delivery)  as TextInputEditText
            tie_Priority           = v.findViewById<View>(R.id.tie_Priority)           as TextInputEditText
            tie_ComplaintQty       = v.findViewById<View>(R.id.tie_ComplaintQty)       as TextInputEditText
            tie_Complaint_Type     = v.findViewById<View>(R.id.tie_Complaint_Type)     as TextInputEditText
            tie_Description        = v.findViewById<View>(R.id.tie_Description)     as TextInputEditText
            tie_Quantity           = v.findViewById<View>(R.id.tie_Quantity)        as TextInputEditText
            tie_StandByQuantity    = v.findViewById<View>(R.id.tie_StandByQuantity) as TextInputEditText
            tie_StandByProduct     = v.findViewById<View>(R.id.tie_StandByProduct)  as TextInputEditText
            tie_StandByAmount      = v.findViewById<View>(R.id.tie_StandByAmount)   as TextInputEditText
            til_StandByProduct     = v.findViewById<View>(R.id.til_StandByProduct)   as TextInputLayout
            til_StandByQuantity    = v.findViewById<View>(R.id.til_StandByQuantity)  as TextInputLayout
            til_StandByAmount      = v.findViewById<View>(R.id.til_StandByAmount)   as TextInputLayout
            til_StandByAmount      = v.findViewById<View>(R.id.til_StandByAmount)   as TextInputLayout
            tie_Remarks            = v.findViewById<View>(R.id.tie_Remarks)         as TextInputEditText
            til_Quantity            = v.findViewById<View>(R.id.til_Quantity)       as TextInputLayout
            swOnOff                = v.findViewById<View>(R.id.swOnOff)             as SwitchCompat
            checkbox               = v.findViewById<View>(R.id.checkbox)            as CheckBox
            check_Complaintbox     = v.findViewById<View>(R.id.check_Complaintbox)  as CheckBox
//            checkbox_emi           = v.findViewById<View>(R.id.checkbox_emi)        as CheckBox
            ll_complaintbox        = v.findViewById<View>(R.id.ll_complaintbox)     as LinearLayout
            provideStandBy         = v.findViewById<View>(R.id.provideStandBy)      as LinearLayout
            ll_Pickup              = v.findViewById<View>(R.id.ll_Pickup)           as LinearLayout
            ll_delivery            = v.findViewById<View>(R.id.ll_delivery)         as LinearLayout
            ll_Productnamepickup            = v.findViewById<View>(R.id.ll_Productnamepickup)         as LinearLayout
//            ll_emi_Pickup          = v.findViewById<View>(R.id.ll_emi_Pickup)       as LinearLayout

//            txtGridNotification   = v.findViewById<View>(R.id.txtGridNotification) as TextView
//            image                 = v.findViewById<View>(R.id.image) as ImageView
//            ll_homeGrid           = v.findViewById<View>(R.id.ll_homeGrid) as LinearLayout

        }
    }


    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}