package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.internal.s
import com.google.gson.Gson
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat

class TodoListAdapter(internal var context: Context, internal var jsonArray: JSONArray,SubMode : String):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = ""
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    private var SubMode:String?=SubMode
    var sharedPreferences: SharedPreferences? =null
    var lstChk = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_todolist, parent, false
        )
        sharedPreferences =context.getSharedPreferences("AgendaReminder", Context.MODE_PRIVATE)
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG, "onBindViewHolder   1051   "+SubMode)
                Log.i("Todo Date", jsonObject!!.getString("LgLeadDate"))
//                var date = jsonObject!!.getString("LgLeadDate")
//                var spf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
//                val newDate = spf.parse(date)
//                spf = SimpleDateFormat("dd-MM-yyyy")
//                date = spf.format(newDate)
              //  println(date)

                Log.e("dfsdf33fff","submode "+SubMode)
                if (SubMode.equals("1")){
                    holder.ll_leadNo!!.setBackgroundColor(context.getColor(R.color.todolist_Color));
                    holder.tv_leadno!!.getBackground().setTint(context.getColor(R.color.todolist_light_Color));
                }
                if (SubMode.equals("2")){
                    holder.ll_leadNo!!.setBackgroundColor(context.getColor(R.color.overdue_Color));
                    holder.tv_leadno!!.getBackground().setTint(context.getColor(R.color.overdue_light_Color));
                }
                if (SubMode.equals("3")){
                    holder.ll_leadNo!!.setBackgroundColor(context.getColor(R.color.upcomung_Color));
                    holder.tv_leadno!!.getBackground().setTint(context.getColor(R.color.upcomung_light_Color));
                }
                if (SubMode.equals("4")){
                    holder.ll_leadNo!!.setBackgroundColor(context.getColor(R.color.mylead_Color));
                    holder.tv_leadno!!.getBackground().setTint(context.getColor(R.color.mylead_light_Color));
                    holder.immessage.visibility=View.GONE
                }

             //   internal var impreference    : ImageView

                val CompanyCategorySP = context.getSharedPreferences(Config.SHARED_PREF46, 0)
                var CompanyCategory = CompanyCategorySP.getString("CompanyCategory","").toString()

                if (CompanyCategory.equals("0")  || CompanyCategory.equals("1")){
                    holder.tv_product.text        = "Product Name : "+jsonObject!!.getString("ProdName")
                }
                else if (CompanyCategory.equals("2")){
                    holder.tv_product.text        = "Destination : "+jsonObject!!.getString("ProdName")
                }

              //  holder.txtv_date.text        = date
                holder.txtv_date.text        = jsonObject!!.getString("LgLeadDate")
                holder.tv_custmr.text        = jsonObject!!.getString("LgCusName")
                holder.tv_address.text        = jsonObject!!.getString("CusAddress")
                holder.tv_mobile.text        = jsonObject!!.getString("LgCusMobile")

                holder.txtv_collectedby.text        = "Collected By : "+jsonObject!!.getString("LgCollectedBy")
                holder.txtv_preference.text        = jsonObject!!.getString("Preference")
                holder.tv_nextaction.text        = "Next Action  : "+jsonObject!!.getString("Action")
                if(jsonObject!!.getString("Preference").equals("Hot")){
                    holder.impreference.setImageResource(R.drawable.preference2)
                    holder.impreference.setColorFilter(context.getColor(R.color.ColorHot))
                }else if (jsonObject!!.getString("Preference").equals("Warm")){
                    holder.impreference.setImageResource(R.drawable.preference3)
                    holder.impreference.setColorFilter(context.getColor(R.color.ColorWarm))

                }else if (jsonObject!!.getString("Preference").equals("Cold")){
                    holder.impreference.setImageResource(R.drawable.preference1)
                    holder.impreference.setColorFilter(context.getColor(R.color.ColorCold))

                }
                if (SubMode.equals("2")){
                    holder.tv_nextdate.text        = "Next Action Date : "+jsonObject!!.getString("NextActionDate")
                }
                else{
                    holder.tv_nextdate.text        = "Next Action Date : "+jsonObject!!.getString("NextActionDate")
                }

                holder.tv_leadno.text        = jsonObject!!.getString("LeadNo")
                holder.lToDoList!!.setTag(position)
                holder.lToDoList!!.setOnClickListener(View.OnClickListener {
                 //   if (!SubMode.equals("4")){
                        clickListener!!.onClick(position, "todolist")
                 //   }

                })

                holder.imcall!!.setTag(position)
                holder.imcall!!.setOnClickListener(View.OnClickListener {
                        clickListener!!.onClick(position, "todocall")
                })

                holder.immessage!!.setTag(position)
                holder.immessage!!.setOnClickListener(View.OnClickListener {
                    if (!SubMode.equals("4")) {
                        clickListener!!.onClick(position, "todoMessage")
                    }
                })

                holder.cb_Meeting.setTag(position)
                    holder.cb_Meeting.setOnCheckedChangeListener { buttonView, isChecked ->
                        if (isChecked) {

                            val jsonObject1 = jsonArray.getJSONObject(position)

//                        val ActionTypeName1 = jsonObject1!!.getString("ActionTypeName")
//                        val EnquiryAbout1 = jsonObject1!!.getString("EnquiryAbout")
//                        val NextActionDate = jsonObject1!!.getString("NextActionDate")
//                        val ID_LeadGenerate1 = jsonObject1!!.getString("ID_LeadGenerateProduct")

                            val Customer = jsonObject1!!.getString("LgCusName")
                            val ProdName = jsonObject1!!.getString("ProdName")
                            val NextActionDate = jsonObject1!!.getString("NextActionDate")


//                        val desc = "Action : "+ActionTypeName1+", Product : "+EnquiryAbout1+" , Status : "+Status1
                            val desc =
                                "Customer : " + Customer + ", Product : " + ProdName + " , Next Action Date : " + NextActionDate
                            lstChk.add(desc)
                            val gson = Gson()
                            val json = gson.toJson(lstChk)
                            val editor = sharedPreferences!!.edit()
                            editor.putString("Set", json);
                            editor.commit();
                            Log.e(TAG, "lstChk_size  " + lstChk.size)
                            Log.e(TAG, "desc  3413  " + desc)

                        } else {
                            val jsonObject1 = jsonArray.getJSONObject(position)
//                        val ActionTypeName1 = jsonObject1!!.getString("ActionTypeName")
//                        val EnquiryAbout1 = jsonObject1!!.getString("EnquiryAbout")
//                        val Status1 = jsonObject1!!.getString("Status")
//                        val ID_LeadGenerate1 = jsonObject1!!.getString("ID_LeadGenerateProduct")

//                        val desc = ActionTypeName1+"\n"+EnquiryAbout1+"\n"+Status1
                            val Customer = jsonObject1!!.getString("LgCusName")
                            val ProdName = jsonObject1!!.getString("ProdName")
                            val NextActionDate = jsonObject1!!.getString("NextActionDate")


//                        val desc = "Action : "+ActionTypeName1+", Product : "+EnquiryAbout1+" , Status : "+Status1
                            val desc =
                                "Customer : " + Customer + ", Product : " + ProdName + " , Next Action Date : " + NextActionDate

                            lstChk.remove(desc)
                            val gson = Gson()
                            val json = gson.toJson(lstChk)
                            val editor = sharedPreferences!!.edit()
                            editor.putString("Set", json);
                            editor.commit();
                            Log.e(TAG, "lstChk_size  " + lstChk.size)
                            Log.e(TAG, "desc  3414  " + desc)
                        }


                    }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "Exception   105   " + e.toString())
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
        internal var lToDoList   : LinearLayout
        internal var ll_leadNo   : LinearLayout
        internal var llside_bar   : LinearLayout
        internal var txtv_date   : TextView
        internal var txtv_preference       : TextView
        internal var tv_leadno    : TextView
        internal var tv_custmr    : TextView
        internal var tv_address    : TextView
        internal var tv_mobile    : TextView
        internal var tv_product    : TextView
        internal var tv_nextdate    : TextView
        internal var tv_nextaction    : TextView
        internal var txtv_collectedby    : TextView
        internal var impreference    : ImageView
        internal var imcall    : ImageView
        internal var immessage    : ImageView
        internal var cb_Meeting  : CheckBox
       init {
           lToDoList          = v.findViewById<View>(R.id.lToDoList) as LinearLayout
           ll_leadNo          = v.findViewById<View>(R.id.ll_leadNo) as LinearLayout
           llside_bar          = v.findViewById<View>(R.id.llside_bar) as LinearLayout
           txtv_date          = v.findViewById<View>(R.id.txtv_date) as TextView
           txtv_preference          = v.findViewById<View>(R.id.txtv_preference) as TextView
           tv_leadno          = v.findViewById<View>(R.id.tv_leadno) as TextView
           tv_custmr          = v.findViewById<View>(R.id.tv_custmr) as TextView
           tv_address              = v.findViewById<View>(R.id.tv_address) as TextView
           tv_mobile              = v.findViewById<View>(R.id.tv_mobile) as TextView
           tv_product           = v.findViewById<View>(R.id.tv_product) as TextView
           tv_nextdate           = v.findViewById<View>(R.id.tv_nextdate) as TextView
           tv_nextaction           = v.findViewById<View>(R.id.tv_nextaction) as TextView
           txtv_collectedby           = v.findViewById<View>(R.id.txtv_collectedby) as TextView
           imcall           = v.findViewById<View>(R.id.imcall) as ImageView
           immessage           = v.findViewById<View>(R.id.immessage) as ImageView
           impreference           = v.findViewById<View>(R.id.impreference) as ImageView
           cb_Meeting            = v.findViewById<View>(R.id.cb_Meeting) as CheckBox
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}