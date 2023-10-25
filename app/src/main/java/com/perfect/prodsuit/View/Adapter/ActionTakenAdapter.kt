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
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ActionTakenMainModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ActionTakenAdapter(
    internal var context: Context,
    internal var mList: List<ActionTakenMainModel>,
    private val textChangedListener: TextChangedListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG: String = "ServiceDetailsAdapter"

    //    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_action_taken, parent, false
        )
        vh = MainViewHolder(v)
        return vh

    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {

            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   105100   ")
                val empModel = mList[position]
                holder.ll_checkbox.visibility = View.GONE
                holder.ll_security_amount.visibility = View.GONE
                holder.ll_leadAction.visibility = View.GONE
                holder.ll_buy_back_amount.visibility = View.GONE
                holder.tv_product.text = empModel.Product
                holder.tv_action_action_taken.text = empModel.actionName
                holder.tv_lead_action.text = empModel.leadAction
                holder.edt_customer_note.setText(empModel.Customer_note)
                Log.v("sdfsdfdsfds","check value2 "+empModel.ProvideStandBy)
                Log.v("sdfsdfdsfds","check action "+empModel.actionStatus)

                holder.tv_action_action_taken!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "action_taken_action")
                })
                holder.tv_lead_action!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "lead_action")
                })
                if (empModel.actionStatus.equals("5")) {
                    holder.ll_checkbox.visibility = View.VISIBLE
                } else if (empModel.actionStatus.equals("9")) {
                    holder.ll_leadAction.visibility = View.VISIBLE
                    holder.ll_buy_back_amount.visibility = View.VISIBLE
                } else if (empModel.actionStatus.equals("6")) {
                    holder.ll_security_amount.visibility = View.VISIBLE
                } else if (empModel.actionStatus.equals("10")) {
                    holder.ll_buy_back_amount.visibility = View.VISIBLE
                }




                holder.edt_customer_note.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        holder.edt_customer_note.setSelection(holder.edt_customer_note.text!!.length)
                        textChangedListener.onTextChanged(position, "customer_note", s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                holder.edt_employee_note.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        holder.edt_employee_note.setSelection(holder.edt_employee_note.text!!.length)
                        textChangedListener.onTextChanged(position, "employee_note", s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                holder.edit_security_amount.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        holder.edit_security_amount.setSelection(holder.edit_security_amount.text!!.length)
                        textChangedListener.onTextChanged(position, "security_amount", s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })

                holder.edit_buy_back_amount.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        holder.edit_buy_back_amount.setSelection(holder.edit_buy_back_amount.text!!.length)
                        textChangedListener.onTextChanged(position, "buy_back", s.toString())
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })


//holder.til_productwise_cmplt!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "productwise_cmplt")
//                })
//
//                if (empModel.isMaster.equals("0")){
//                    holder.ll_subproduct.visibility = View.VISIBLE
//                    holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimary))
//                }else{
//                    holder.ll_tab.setBackgroundColor(context.getColor(R.color.colorPrimarylite1))
//                }
//
//                holder.tv_followupticket.text           = empModel.Product
////                holder.til_item_name.text               = empModel.Product
//                holder.til_warranty.text                = empModel.Warranty
//                holder.til_warranty_date.text           = empModel.ServiceWarrantyExpireDate
//                holder.til_replacement_date.text        = empModel.ReplacementWarrantyExpireDate
//
//
//                holder.til_productwise_cmplt.setText(empModel.ComplaintName)
//                holder.til_servicedescrption.setText(empModel.Description)
//
//                if (empModel.isChekecd == true){
//                    holder.chk_box.isChecked = true
//                    holder.til_servicedescrption.isEnabled = true
//                    holder.til_productwise_cmplt.isEnabled = true
//                }else{
//                    holder.chk_box.isChecked = false
//                    holder.til_servicedescrption.isEnabled = false
//                    holder.til_productwise_cmplt.isEnabled = false
//                }
//
//
//                Log.e(TAG,"onBindViewHolder   105100   "+empModel.ComplaintName)
//
//                holder.til_servicedescrption.addTextChangedListener(object : TextWatcher {
//                    override fun afterTextChanged(s: Editable?) {
//                    }
//
//                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                    }
//
//                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                        mList[position].Description = holder.til_servicedescrption.text.toString()
//                    }
//                })
//
//                holder.ll_serviceDetails!!.setTag(position)
//                holder.ll_serviceDetails!!.setOnClickListener(View.OnClickListener {
////                    clickListener!!.onClick(position, "areadetail")
//                })
//
//                holder.til_productwise_cmplt!!.setTag(position)
//                holder.til_productwise_cmplt!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "productwise_cmplt")
//                })
//
//                holder.chk_box!!.setTag(position)



                holder.check!!.setOnClickListener(View.OnClickListener {
                    if(empModel.ProvideStandBy.equals("false"))
                    {
                        holder.check.isChecked=true
                        holder.ll_security_amount.visibility = View.VISIBLE
                        holder.edit_security_amount.setText("")
                    }
                    else
                    {
                        holder.check.isChecked=false
                        holder.ll_security_amount.visibility = View.GONE
                        holder.edit_security_amount.setText("")
                    }
                   clickListener!!.onClick(position, "check_click")
                })
            }


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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

    interface TextChangedListener {
        fun onTextChanged(position: Int, field: String, newText: String)
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var tv_product: TextView
        internal var tv_action_action_taken: TextView
        internal var tv_lead_action: TextView
        internal var ll_checkbox: LinearLayout
        internal var ll_security_amount: LinearLayout
        internal var ll_leadAction: LinearLayout
        internal var ll_buy_back_amount: LinearLayout
        internal var edit_security_amount: TextInputEditText
        internal var check: CheckBox
        internal var edt_customer_note: TextInputEditText
        internal var edt_employee_note: TextInputEditText
        internal var edit_buy_back_amount: TextInputEditText


        init {
            tv_product = v.findViewById<View>(R.id.tv_product) as TextView
            tv_action_action_taken = v.findViewById<View>(R.id.tv_action_action_taken) as TextView
            tv_lead_action = v.findViewById<View>(R.id.tv_lead_action) as TextView
            ll_checkbox = v.findViewById<View>(R.id.ll_checkbox) as LinearLayout
            ll_security_amount = v.findViewById<View>(R.id.ll_security_amount) as LinearLayout
            ll_leadAction = v.findViewById<View>(R.id.ll_leadAction) as LinearLayout
            ll_buy_back_amount = v.findViewById<View>(R.id.ll_buy_back_amount) as LinearLayout
            check = v.findViewById<View>(R.id.check) as CheckBox
            edit_security_amount =
                v.findViewById<View>(R.id.edit_security_amount) as TextInputEditText
            edt_customer_note =
                v.findViewById<View>(R.id.edt_customer_note) as TextInputEditText
            edt_employee_note =
                v.findViewById<View>(R.id.edt_employee_note) as TextInputEditText
            edit_buy_back_amount =
                v.findViewById<View>(R.id.edit_buy_back_amount) as TextInputEditText

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}