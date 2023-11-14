package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.firebase.messaging.Constants.MessageNotificationKeys.IMAGE_URL
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class CorrectionModuleListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "CorrectionModuleListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_correction_module_list, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                Log.i("array", jsonArray.toString()+"\n"+"Test")
                jsonObject = jsonArray.getJSONObject(position)
                Log.e(TAG,"onBindViewHolder   36   ")
                val pos = position+1
                val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
               val IMAGE_URL =  IMAGE_URLSP.getString("IMAGE_URL", null)


//                Picasso.with(context).load("https://202.164.150.65:14271/ProdsuiteAPI/Images/Module/Lead.png").into(holder.img_module)
//                Picasso.with(context).load(IMAGE_URL+jsonObject!!.getString("MobImage")).into(holder.img_module)

                PicassoTrustAll.getInstance(context)!!.load(IMAGE_URL+jsonObject!!.getString("MobImage")).error(R.drawable.svg_trans).into(holder.img_module)

//                val backgroundColor = Color.parseColor((jsonObject!!.getString("MobColor")))
//               holder.tvv_count!!.setBackgroundTintList(ContextCompat.getColorStateList(context, backgroundColor))

                holder.tvv_count.text        = jsonObject!!.getString("NoofRecords")
                holder.tvv_text.text        = jsonObject!!.getString("Module_Name")
                holder.ll_correction_module.setBackgroundColor(Color.parseColor(jsonObject!!.getString("MobColor")))

                holder.count_card.setCardBackgroundColor(Color.parseColor(jsonObject!!.getString("MobColor")))
//                holder.tvv_count.setBackgroundColor(Color.parseColor(jsonObject!!.getString("MobColor")))



//                holder.tvv_count.setBackgroundResource(R.drawable.bg_module)
//                holder.tvv_count.backgroundTintList = ContextCompat.getColorStateList(getApplicationContext(),R.color.red)

//               holder.tvv_count!!.setBackgroundTintList(ContextCompat.getColorStateList(context, backgroundColor))

//                Log.e(TAG,"backgroundColor   "+backgroundColor)

//                holder.img_module.setImageBitmap(jsonObject.getString("MobImage"))
                Log.e(TAG,"IMAGE_URL   "+IMAGE_URL)
                Log.e(TAG,"IMAGE_URL   "+jsonObject!!.getString("MobImage"))
                Log.e(TAG,"IMAGE_URL   "+IMAGE_URL+jsonObject!!.getString("MobImage"))


                holder.ll_module_list!!.setTag(position)
                holder.ll_module_list!!.setOnClickListener(View.OnClickListener {

                    clickListener!!.onClick(
                        position,
                        "moduleListClick"
                    )
//                    Log.e(TAG,"moduleListClick   "+jsonObject!!.getString("Module_Name"))
                })
            }


        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   2233   "+e.toString())
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

//        internal var txtEnquiryAbout    : TextView
//        internal var txtAction          : TextView
//        internal var txtActionType      : TextView
//        internal var txtActionDate      : TextView
//        internal var txtStatus          : TextView
        internal var tvv_text                     : TextView
        internal var tvv_count                    : TextView
        internal var img_module                   : ImageView
        internal var ll_correction_module         : LinearLayout
        internal var ll_module_list               : LinearLayout
        internal var count_card                   : CardView


        init {

//            txtEnquiryAbout      = v.findViewById<View>(R.id.txtEnquiryAbout) as TextView
//            txtAction            = v.findViewById<View>(R.id.txtAction) as TextView
//            txtActionType        = v.findViewById<View>(R.id.txtActionType) as TextView
//            txtActionDate        = v.findViewById<View>(R.id.txtActionDate) as TextView
//            txtStatus            = v.findViewById<View>(R.id.txtStatus) as TextView
            tvv_text                = v.findViewById<View>(R.id.tvv_text) as TextView
            tvv_count               = v.findViewById<View>(R.id.tvv_count) as TextView
            img_module              = v.findViewById<View>(R.id.img_module) as ImageView
            ll_correction_module    = v.findViewById<View>(R.id.ll_correction_module) as LinearLayout
            ll_module_list          = v.findViewById<View>(R.id.ll_module_list) as LinearLayout
            count_card              = v.findViewById<View>(R.id.count_card) as CardView

        }
    }

    override fun getItemCount(): Int {
        return  jsonArray.length()
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}