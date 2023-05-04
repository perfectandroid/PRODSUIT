package com.perfect.prodsuit.View.Adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.Model.ModelReplacedProduct
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat

class ReplacedProductAdapter(internal var context: Context, internal var modelReplacedProduct: List<ModelReplacedProduct>, internal var jsonArrayChangeMode: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ReplacedProductAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListenerValue? = null
    private var dialogCountry: Dialog? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_replaced_product, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)  {
        try {
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position
                val df = DecimalFormat("#.##")
                val ItemsModel = modelReplacedProduct[position]

                holder.tv_component.text = ItemsModel.OLD_Product
                if (ItemsModel.SPDOldQuantity.equals("")){
                    holder.edt_qty!!.setText("0")
                    ItemsModel.SPDOldQuantity = "0"
                }else{
                    holder.edt_qty!!.setText(ItemsModel.SPDOldQuantity)
                }

                if (ItemsModel.ID_Product.equals("")){
                    ItemsModel.ID_Product = "0"
                    holder.edtproduct!!.setText("")
                }else{
                    holder.edtproduct!!.setText(ItemsModel.Product)
                }

                if (ItemsModel.Amount.equals("")){
                    holder.edtBuyBackAmount!!.setText("0")
                    ItemsModel.Amount = "0"
                }else{
                    holder.edtBuyBackAmount!!.setText(ItemsModel.Amount)
                }

                if (ItemsModel.Replaced_Qty.equals("")){
                    holder.edtReplaceQuantity!!.setText("0")
                    ItemsModel.Replaced_Qty = "0"
                }else{
                    holder.edtReplaceQuantity!!.setText(ItemsModel.Replaced_Qty)
                }

                if (ItemsModel.ReplaceAmount.equals("")){
                    holder.edtreplaceamount!!.setText("0")
                    ItemsModel.ReplaceAmount = "0"
                }else{
                    holder.edtreplaceamount!!.setText(ItemsModel.ReplaceAmount)
                }

                holder.edtRemarks.setText(ItemsModel.Remarks)

                var searchType = Array<String>(jsonArrayChangeMode.length()) { "" }
                for (i in 0 until jsonArrayChangeMode.length()) {
                    val objects: JSONObject = jsonArrayChangeMode.getJSONObject(i)
                    searchType[i] = objects.getString("ModeName")
                }

                if (ItemsModel.ID_Mode.equals("0")){
                    jsonObject = jsonArrayChangeMode.getJSONObject(0)
                    holder.edtChangeMode.setText(jsonObject!!.getString("ModeName"))
                    ItemsModel.ID_Mode = jsonObject!!.getString("ID_Mode")
                    ItemsModel.ModeName = jsonObject!!.getString("ModeName")
                    if (jsonObject!!.getString("ID_Mode").equals("2")){
                        holder.edtBuyBackAmount.isEnabled = true
                        //  holder.edtServiceCost.setText("0")
                    }else{
                        holder.edtBuyBackAmount.isEnabled = false
                        //  holder.edtServiceCost.setText("0")
                    }
                }
                else{
                    holder.edtChangeMode.setText(ItemsModel.ModeName)
                    if (ItemsModel.ID_Mode.equals("2")){
                        holder.edtBuyBackAmount.isEnabled = true
                        //  holder.edtServiceCost.setText("0")
                    }else{
                        holder.edtBuyBackAmount.isEnabled = false
                        // holder.edtServiceCost.setText("0")
                    }
                }

                holder.edtChangeMode.setTag(position)
                holder.edtChangeMode.setOnClickListener {
                    Log.e(TAG,"searchType   651   "+searchType)
                    val adapter = ArrayAdapter(context, R.layout.simple_spinner_dropdown_item, searchType)
                    holder.edtChangeMode!!.setAdapter(adapter)
                    holder.edtChangeMode!!.showDropDown()
                }

                holder.edtChangeMode!!.setOnItemClickListener { parent, view, pos, id ->
                    Log.e(TAG,"searchType   651   "+searchType)
                    jsonObject = jsonArrayChangeMode.getJSONObject(pos)
                    ItemsModel.ID_Mode = jsonObject!!.getString("ID_Mode")
                    ItemsModel.ModeName = jsonObject!!.getString("ModeName")

                    Log.e(TAG,"ID_Mode  224   "+jsonObject!!.getString("ID_Mode"))

                    if (jsonObject!!.getString("ID_Mode").equals("2")){
                        Log.e(TAG,"ID_Mode  2241   "+jsonObject!!.getString("ID_Mode"))
                        holder.edtBuyBackAmount.isEnabled = true
                        holder.edtBuyBackAmount.setText("0")
                    }else{
                        Log.e(TAG,"ID_Mode  2242   "+jsonObject!!.getString("ID_Mode"))
                        holder.edtBuyBackAmount.isEnabled = false
                        holder.edtBuyBackAmount.setText("0")
                    }


                }

                holder.edt_qty.addTextChangedListener(object : TextWatcher {

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
                        var str_qty = holder.edt_qty.text
                        if (str_qty!!.isEmpty()) {
                            ItemsModel.SPDOldQuantity = "0"
                        } else {
                            ItemsModel.SPDOldQuantity = str_qty.toString()
                        }
                    }
                })

                holder.edtBuyBackAmount.addTextChangedListener(object : TextWatcher {

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
                        var str_buyback = holder.edtBuyBackAmount.text
                        if (str_buyback!!.isEmpty()) {
                            ItemsModel.Amount = "0"
                        } else {
                            ItemsModel.Amount = str_buyback.toString()
                        }
                    }
                })

                holder.edtReplaceQuantity.addTextChangedListener(object : TextWatcher {

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
                        var str_Rep_qty = holder.edtReplaceQuantity.text
                        if (str_Rep_qty!!.isEmpty()) {
                            ItemsModel.Replaced_Qty = "0"
                        } else {
                            ItemsModel.Replaced_Qty = str_Rep_qty.toString()
                        }
                    }
                })

                holder.edtreplaceamount.addTextChangedListener(object : TextWatcher {

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
                        var str_Rep_amnt = holder.edtreplaceamount.text
                        if (str_Rep_amnt!!.isEmpty()) {
                            ItemsModel.ReplaceAmount = "0"
                        } else {
                            ItemsModel.ReplaceAmount = str_Rep_amnt.toString()
                        }
                    }
                })

                holder.edtproduct.setOnClickListener {
                    Log.e(TAG,"243")
                   // clickListener!!.onClick(position, "ReplaceProdClick","")
                  //  popUpProducts(context,position,modelReplacedProduct)
                    clickListener!!.onClick(position, "popUpProducts","1")
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }




    override fun getItemCount(): Int {
        return modelReplacedProduct.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tv_component: TextView
        var edt_qty: TextInputEditText
        var edtChangeMode: AutoCompleteTextView
        var edtBuyBackAmount: TextInputEditText
        var edtproduct: TextInputEditText
        var edtRemarks: TextInputEditText
        var edtreplaceamount: EditText
        var checkbox: CheckBox
        var edtReplaceQuantity: EditText

        init {
            tv_component = v.findViewById(R.id.tv_component) as TextView
            edt_qty = v.findViewById(R.id.edt_qty) as TextInputEditText
            edtChangeMode = v.findViewById(R.id.edtChangeMode) as AutoCompleteTextView
            edtBuyBackAmount = v.findViewById(R.id.edtBuyBackAmount) as TextInputEditText
            edtproduct = v.findViewById(R.id.edtproduct) as TextInputEditText
            edtRemarks = v.findViewById(R.id.edtRemarks) as TextInputEditText
            edtreplaceamount = v.findViewById(R.id.edtreplaceamount) as EditText
            edtReplaceQuantity = v.findViewById(R.id.edtReplaceQuantity) as EditText
            checkbox = v.findViewById(R.id.checkbox) as CheckBox
        }
    }


//    private fun popUpProducts(context: Context, position: Int,modelReplacedProduct: List<ModelReplacedProduct>) {
//
//        try {
//
//            dialogCountry = Dialog(context)
//            dialogCountry!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//            dialogCountry!!.setContentView(R.layout.country_list_popup)
//            dialogCountry!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;
//            val recycCountry = dialogCountry!!.findViewById(R.id.recycCountry) as RecyclerView
//            val etsearch = dialogCountry!!.findViewById(R.id.etsearch) as EditText
//
////            countrySort = JSONArray()
////            for (k in 0 until modelReplacedProduct.length()) {
////                val jsonObject = countryArrayList.getJSONObject(k)
////                // reportNamesort.put(k,jsonObject)
////                countrySort.put(jsonObject)
////            }
//
//            val lLayout = GridLayoutManager(context, 1)
//            recycCountry!!.layoutManager = lLayout as RecyclerView.LayoutManager?
////            recyCustomer!!.setHasFixedSize(true)
////            val adapter = CountryDetailAdapter(this@LeadGenerationActivity, countryArrayList)
//            val adapter = ReplacedProductSubAdapter(context, modelReplacedProduct)
//            recycCountry!!.adapter = adapter
//         //   adapter.setClickListener(this@ReplacedProductAdapter)
//
//
//
//
//            dialogCountry!!.show()
//            dialogCountry!!.getWindow()!!.setLayout(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT
//            );
//            dialogCountry!!.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }


    fun setClickListener(itemClickListener: ItemClickListenerValue) {
        clickListener = itemClickListener
    }


}