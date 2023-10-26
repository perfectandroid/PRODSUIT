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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelMoreServices
import com.perfect.prodsuit.Model.ServicePartsReplacedModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ServiceParts_replacedAdapter (internal var context: Context, internal var mList: List<ServicePartsReplacedModel>, internal var ID_mastr: String):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "ServiceParts_replacedAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var row_index: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_parts_replaced, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            val ItemsModel = mList[position]
            Log.e(TAG,"jsonArray111  "+ItemsModel)

            if (holder is MainViewHolder) {
                val pos = position+1

//                if (!ItemsModel.ID_MasterProduct.equals(ID_mastr)){
//                    holder.llProduct.visibility = View.GONE
//                }else{
//                    holder.llProduct.visibility = View.VISIBLE
//                }

                if (ItemsModel.is_Master.equals("1")){
                    holder.ll_main_product.visibility = View.VISIBLE
                    holder.ll_serviceDetails.visibility = View.GONE
                }else{
                    holder.ll_main_product.visibility = View.GONE
                    holder.ll_serviceDetails.visibility = View.VISIBLE
                }

                Log.e(TAG,"  1111   "+ItemsModel.isChecked)
                if (ItemsModel.isChecked.equals("1")){
                    holder.check_partsreplaced.isChecked = true
                }else{
                    holder.check_partsreplaced.isChecked = false
                }

                holder.tv_mainProdname.text        = ItemsModel.MainProduct
                holder.tvv_mainProductName.text        = ItemsModel.MainProduct
                holder.tvv_component.text              = ItemsModel.Componant
                holder.tvv_warrantyMode.text           = ItemsModel.WarrantyName
                holder.tvv_replacedMode.text           = ItemsModel.ReplceName
                holder.edt_quantity.setText(ItemsModel.Quantity)

//                holder.llProduct_Main!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "MainProductList")
//                })

                holder.tvv_warrantyMode!!.setTag(position)
                holder.tvv_warrantyMode!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "WarrantyModeList")
                })

                holder.tvv_replacedMode!!.setTag(position)
                holder.tvv_replacedMode!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "ReplacedModeList")
                })


                holder.imv_addProduct!!.setTag(position)
                holder.imv_addProduct!!.setOnClickListener(View.OnClickListener {
                    try {
                        clickListener!!.onClick(position, "partAddService2Click")
                    }catch (e:Exception){
                        Log.e(TAG,"93333   "+e)
                    }

                })

                holder.check_partsreplaced!!.setTag(position)
                holder.check_partsreplaced!!.setOnClickListener(View.OnClickListener {
                    if (holder.check_partsreplaced.isChecked){
                        ItemsModel.isChecked = "1"
                    }else{
                        ItemsModel.isChecked = "0"
                    }
                    notifyItemChanged(position)
                })



                holder.edt_quantity.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                        ItemsModel.Quantity =  holder.edt_quantity.text.toString()

                    }
                })

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

        internal var tvv_mainProductName    : TextView
        internal var tvv_component          : TextView
        internal var tvv_warrantyMode       : TextView
        internal var edt_quantity           : EditText
        internal var tvv_replacedMode       : TextView
        internal var tv_mainProdname       : TextView
        internal var check_partsreplaced    : CheckBox
        internal var llProduct         : LinearLayout
        internal var ll_main_product         : LinearLayout
        internal var ll_serviceDetails         : LinearLayout
        internal var imv_addProduct         : ImageView

        init {

            tvv_mainProductName           = v.findViewById<View>(R.id.tvv_mainProductName)   as TextView
            tvv_component                 = v.findViewById<View>(R.id.tvv_component)         as TextView
            tvv_warrantyMode              = v.findViewById<View>(R.id.tvv_warrantyMode)      as TextView
            edt_quantity                  = v.findViewById<View>(R.id.edt_quantity)          as EditText
            tvv_replacedMode              = v.findViewById<View>(R.id.tvv_replacedMode)      as TextView
            tv_mainProdname               = v.findViewById<View>(R.id.tv_mainProdname)      as TextView
//            llProduct_Main              = v.findViewById<View>(R.id.llProduct_Main)        as LinearLayout
            llProduct                     = v.findViewById<View>(R.id.llProduct)        as LinearLayout
            ll_main_product                     = v.findViewById<View>(R.id.ll_main_product)        as LinearLayout
            ll_serviceDetails                     = v.findViewById<View>(R.id.ll_serviceDetails)        as LinearLayout
            check_partsreplaced           = v.findViewById<View>(R.id.check_partsreplaced)   as CheckBox
            imv_addProduct           = v.findViewById<View>(R.id.imv_addProduct)   as ImageView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }



}