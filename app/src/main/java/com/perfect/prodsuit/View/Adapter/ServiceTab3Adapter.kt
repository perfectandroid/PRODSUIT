package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
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
import com.perfect.prodsuit.Model.ServiceTab3MainModel
import com.perfect.prodsuit.R

class ServiceTab3Adapter (internal var context: Context, internal var mList: List<ServiceTab3MainModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceTab3Adapter"
    //    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_service_tab3, parent, false
        )
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   105100   ")
                val pos = position+1

//                holder.txtSino.text        = pos.toString()
                val empModel = mList[position]

                DecimelFormatters.setDecimelPlace(holder.edt_serviceCost)
                Log.e(TAG,"533..... "+ empModel.main+" "+position)
                if (empModel.main.equals("1") && empModel.sub.equals("1")){
                    holder.ll_tab.visibility = View.GONE
                    holder.ll_serviceDetails.visibility = View.VISIBLE
//                    holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimary))
                }else{
                    holder.ll_tab.visibility = View.VISIBLE
                    holder.ll_serviceDetails.visibility = View.GONE

//                    holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimarylite1))
                }

                if (empModel.ID_ServiceType.equals("1")){
                    holder.edt_serviceCost.isEnabled = true
                }else{
                    holder.edt_serviceCost.isEnabled = false
                }

                if (empModel.isChecked){
                    holder.chk_box.isChecked = true
                }else{
                    holder.chk_box.isChecked = false
                }

                holder.tv_product.text           = empModel.Product
                holder.tv_service.text           = empModel.Service
                holder.tv_serviceType.text           = empModel.ServiceType
                holder.tv_taxAmount.text           = empModel.TaxAmount
                holder.tv_netAmount.text           = empModel.NetAmount

                holder.edt_serviceCost.setText(empModel.ServiceCost)
                holder.edt_remark.setText(empModel.Remarks)

                holder.img_addService!!.setTag(position)
                holder.img_addService!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "addService3Click")
                })




                val answerWatcher = object : TextWatcher {
                    override fun beforeTextChanged(value: CharSequence, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(value: CharSequence, start: Int, before: Int, count: Int) {
                        var TaxAmount   =  0.0F
                        var NetAmount   =  0.0F
                        ///   TaxableAmount  == serviceCost
                        var TaxableAmount =  holder.edt_serviceCost.text.toString()
                        var FK_TaxGroup =  empModel.FK_TaxGroup
                        var IncludeTax  =  empModel.ServiceChargeIncludeTax
                        var TaxPercentage   =  empModel.TaxPercentage

                        if (TaxableAmount.equals("") || TaxableAmount.equals(".")){
                            TaxableAmount = "0.00"
                        }



                        if (empModel.ServiceChargeIncludeTax == true){
                            // [((Service.TaxPercentage) * TaxableAmount)/((Service.TaxPercentage)+100)]
                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/(TaxPercentage.toFloat()+100)
                            NetAmount = TaxableAmount.toFloat()

                            empModel.TaxAmount = Config.changeTwoDecimel(TaxAmount.toString())
                            empModel.NetAmount = Config.changeTwoDecimel(NetAmount.toString())
                          //  notifyItemChanged(position)

                            holder.tv_taxAmount.text           = empModel.TaxAmount
                            holder.tv_netAmount.text           = empModel.NetAmount

                        }else{
                            //(Service.TaxPercentage*TaxableAmount)/100
                            // TaxableAmount+TaxAmount
                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/100
                            NetAmount = TaxableAmount.toFloat()+TaxAmount

                            empModel.TaxAmount = Config.changeTwoDecimel(TaxAmount.toString())
                            empModel.NetAmount = Config.changeTwoDecimel(NetAmount.toString())
                          //  notifyItemChanged(position)

                            holder.tv_taxAmount.text           = empModel.TaxAmount
                            holder.tv_netAmount.text           = empModel.NetAmount
                        }
                    }

                    override fun afterTextChanged(s: Editable) {

                    }
                }
//                holder.edt_serviceCost.removeTextChangedListener(answerWatcher);
                holder.edt_serviceCost.addTextChangedListener(answerWatcher);




//                holder.edt_serviceCost.addTextChangedListener(object : TextWatcher {
//                    override fun afterTextChanged(s: Editable?) {
//
//                        var TaxAmount   =  0.0F
//                        var NetAmount   =  0.0F
//                        ///   TaxableAmount  == serviceCost
//                        var TaxableAmount =  holder.edt_serviceCost.text.toString()
//                        var FK_TaxGroup =  empModel.FK_TaxGroup
//                        var IncludeTax  =  empModel.ServiceChargeIncludeTax
//                        var TaxPercentage   =  empModel.TaxPercentage
//
//                        if (TaxableAmount.equals("") || TaxableAmount.equals(".")){
//                            TaxableAmount = "0.00"
//                        }
//
//
//
//                        if (empModel.ServiceChargeIncludeTax == true){
//                            // [((Service.TaxPercentage) * TaxableAmount)/((Service.TaxPercentage)+100)]
//                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/(TaxPercentage.toFloat()+100)
//                            NetAmount = TaxableAmount.toFloat()
//
//                            empModel.TaxAmount = TaxAmount.toString()
//                            empModel.NetAmount = NetAmount.toString()
//                            notifyItemChanged(position)
//                        }else{
//                            //(Service.TaxPercentage*TaxableAmount)/100
//                            // TaxableAmount+TaxAmount
//                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/100
//                            NetAmount = TaxableAmount.toFloat()+TaxAmount
//
//                            empModel.TaxAmount = TaxAmount.toString()
//                            empModel.NetAmount = NetAmount.toString()
//                            notifyItemChanged(position)
//                        }
//                    }
//
//                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//                        var TaxAmount   =  0.0F
//                        var NetAmount   =  0.0F
//                        ///   TaxableAmount  == serviceCost
//                        var TaxableAmount =  holder.edt_serviceCost.text.toString()
//                        var FK_TaxGroup =  empModel.FK_TaxGroup
//                        var IncludeTax  =  empModel.ServiceChargeIncludeTax
//                        var TaxPercentage   =  empModel.TaxPercentage
//
//                        if (TaxableAmount.equals("") || TaxableAmount.equals(".")){
//                            TaxableAmount = "0.00"
//                        }
//
//
//
//                        if (empModel.ServiceChargeIncludeTax == true){
//                            // [((Service.TaxPercentage) * TaxableAmount)/((Service.TaxPercentage)+100)]
//                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/(TaxPercentage.toFloat()+100)
//                            NetAmount = TaxableAmount.toFloat()
//
//                            empModel.TaxAmount = TaxAmount.toString()
//                            empModel.NetAmount = NetAmount.toString()
//                            notifyItemChanged(position)
//                        }else{
//                            //(Service.TaxPercentage*TaxableAmount)/100
//                            // TaxableAmount+TaxAmount
//                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/100
//                            NetAmount = TaxableAmount.toFloat()+TaxAmount
//
//                            empModel.TaxAmount = TaxAmount.toString()
//                            empModel.NetAmount = NetAmount.toString()
//                            notifyItemChanged(position)
//                        }
//                    }
//
//                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
////
////                        var TaxAmount   =  0.0F
////                        var NetAmount   =  0.0F
////                      ///   TaxableAmount  == serviceCost
////                        var TaxableAmount =  holder.edt_serviceCost.text.toString()
////                        var FK_TaxGroup =  empModel.FK_TaxGroup
////                        var IncludeTax  =  empModel.ServiceChargeIncludeTax
////                        var TaxPercentage   =  empModel.TaxPercentage
////
////                        if (TaxableAmount.equals("") || TaxableAmount.equals(".")){
////                            TaxableAmount = "0.00"
////                        }
////
////
////
////                        if (empModel.ServiceChargeIncludeTax == true){
////                            // [((Service.TaxPercentage) * TaxableAmount)/((Service.TaxPercentage)+100)]
////                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/(TaxPercentage.toFloat()+100)
////                            NetAmount = TaxableAmount.toFloat()
////
////                            empModel.TaxAmount = TaxAmount.toString()
////                            empModel.NetAmount = NetAmount.toString()
////                            notifyItemChanged(position)
////                        }else{
////                            //(Service.TaxPercentage*TaxableAmount)/100
////                           // TaxableAmount+TaxAmount
////                            TaxAmount = (TaxPercentage.toFloat()*TaxableAmount.toFloat())/100
////                            NetAmount = TaxableAmount.toFloat()+TaxAmount
////
////                            empModel.TaxAmount = TaxAmount.toString()
////                            empModel.NetAmount = NetAmount.toString()
////                            notifyItemChanged(position)
////                        }
//                    }
//                })

                holder.tv_serviceType!!.setTag(position)
                holder.tv_serviceType!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "addServiceType3Click")
                })

                holder.chk_box!!.setTag(position)
                holder.chk_box!!.setOnClickListener(View.OnClickListener {
                   if (holder.chk_box.isChecked){
                       mList[position].isChecked = true
                   }else{
                       mList[position].isChecked = false
                   }
                })


            }


        }
        catch (e: Exception) {
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

        internal var tv_product             : TextView
        internal var img_addService         : ImageView

        internal var chk_box                : CheckBox
        internal var tv_service             : TextView
        internal var tv_serviceType         : TextView
        internal var tv_taxAmount           : TextView
        internal var tv_netAmount           : TextView

        internal var edt_serviceCost        : EditText
        internal var edt_remark             : EditText

        internal var ll_tab                 : LinearLayout
        internal var ll_serviceDetails      : LinearLayout


        init {

            tv_product              = v.findViewById<View>(R.id.tv_product)      as TextView
            img_addService          = v.findViewById<View>(R.id.img_addService)      as ImageView

            chk_box                 = v.findViewById<View>(R.id.chk_box)      as CheckBox
            tv_service              = v.findViewById<View>(R.id.tv_service)          as TextView
            tv_serviceType          = v.findViewById<View>(R.id.tv_serviceType)          as TextView
            tv_taxAmount            = v.findViewById<View>(R.id.tv_taxAmount)          as TextView
            tv_netAmount            = v.findViewById<View>(R.id.tv_netAmount)          as TextView

            edt_serviceCost         = v.findViewById<View>(R.id.edt_serviceCost)          as EditText
            edt_remark              = v.findViewById<View>(R.id.edt_remark)          as EditText

            ll_serviceDetails       = v.findViewById<View>(R.id.ll_serviceDetails)      as LinearLayout
            ll_tab                  = v.findViewById<View>(R.id.ll_tab)      as LinearLayout


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}