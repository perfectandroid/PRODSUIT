package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelStockTransferDetails
import com.perfect.prodsuit.Model.ServiceDetailsFullListModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceDetailsAdapter(
    internal var context: Context,
    internal var mList: List<ServiceDetailsFullListModel>
):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceDetailsAdapter"
//    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_servicefollowup_productdeatail, parent, false
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

                Log.e(TAG,"533..... "+ empModel)
                if (empModel.isMaster.equals("0")){
                    holder.ll_subproduct.visibility = View.VISIBLE
                    holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimary))
                    if(empModel.SearchSerialNo.equals("1"))
                    {
                        holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimarylite1))
                    }

                }else{
                    holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimarylite1))
                }

                holder.tv_followupticket.text           = empModel.Product
//                holder.til_item_name.text               = empModel.Product
                holder.til_warranty.text                = empModel.Warranty
                holder.til_warranty_date.text           = empModel.ServiceWarrantyExpireDate
                holder.til_replacement_date.text        = empModel.ReplacementWarrantyExpireDate


                holder.til_productwise_cmplt.setText(empModel.ComplaintName)
                holder.til_servicedescrption.setText(empModel.Description)

                if (empModel.isChekecd == true){
                    holder.chk_box.isChecked = true
                    holder.til_servicedescrption.isEnabled = true
                    holder.til_productwise_cmplt.isEnabled = true
                }else{
                    holder.chk_box.isChecked = false
                    holder.til_servicedescrption.isEnabled = false
                    holder.til_productwise_cmplt.isEnabled = false
                }


                Log.e(TAG,"onBindViewHolder   105100   "+empModel.ComplaintName)

                holder.til_servicedescrption.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        mList[position].Description = holder.til_servicedescrption.text.toString()
                    }
                })

                holder.ll_serviceDetails!!.setTag(position)
                holder.ll_serviceDetails!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "areadetail")
                })

                holder.til_productwise_cmplt!!.setTag(position)
                holder.til_productwise_cmplt!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "productwise_cmplt")
                })

                holder.chk_box!!.setTag(position)
                holder.chk_box!!.setOnClickListener(View.OnClickListener {
                    if (holder.chk_box!!.isChecked){
                        holder.til_servicedescrption.isEnabled = true
                        holder.til_productwise_cmplt.isEnabled = true
                        empModel.isChekecd = true
                        holder.til_servicedescrption.setText(  mList[position].Description)
                        holder.til_productwise_cmplt.setText(  mList[position].ComplaintName)
                        clickListener!!.onClick(position, "chkproductwise_cmplt")
                    }else{
                        holder.til_servicedescrption.isEnabled = false
                        holder.til_productwise_cmplt.isEnabled = false
                        empModel.isChekecd = false
                        holder.til_servicedescrption.setText("")
                        mList[position].Description = ""
                        holder.til_productwise_cmplt.setText("")
                        mList[position].ComplaintName = ""
                        mList[position].ID_ComplaintList = "0"
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
        internal var til_item_name                 : TextView
        internal var til_warranty                  : TextView
        internal var til_warranty_date             : TextView
        internal var til_replacement_date          : TextView
        internal var tv_followupticket             : TextView
        internal var til_productwise_cmplt         : TextView
        internal var til_servicedescrption         : EditText
        internal var ll_serviceDetails             : LinearLayout
        internal var ll_subproduct                 : LinearLayout
        internal var ll_tab                        : LinearLayout
        internal var chk_box                       : CheckBox

        init {
            til_item_name              = v.findViewById<View>(R.id.til_item_name)          as TextView
            til_warranty               = v.findViewById<View>(R.id.til_warranty)           as TextView
            til_warranty_date          = v.findViewById<View>(R.id.til_warranty_date)      as TextView
            til_replacement_date       = v.findViewById<View>(R.id.til_replacement_date)   as TextView
            tv_followupticket          = v.findViewById<View>(R.id.tv_followupticket)      as TextView
            til_productwise_cmplt      = v.findViewById<View>(R.id.til_productwise_cmplt)  as TextView
            til_servicedescrption      = v.findViewById<View>(R.id.til_servicedescrption)  as EditText
            ll_serviceDetails          = v.findViewById<View>(R.id.ll_serviceDetails)      as LinearLayout
            ll_subproduct          = v.findViewById<View>(R.id.ll_subproduct)      as LinearLayout
            ll_tab          = v.findViewById<View>(R.id.ll_tab)      as LinearLayout
            chk_box          = v.findViewById<View>(R.id.chk_box)      as CheckBox

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}