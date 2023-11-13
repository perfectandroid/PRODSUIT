package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AgendaTypeAdapter (internal var context: Context, internal var jsonArray: JSONArray, internal var selectedPos: Int):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AgendaTypeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_agenda_type, parent, false
        )

        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   3611   "+selectedPos)


                Log.i("kkkkkkkkkk","selectedPos="+selectedPos)
                Log.i("kkkkkkkkkk","position="+position)
                Log.i("kkkkkkkkkk","jsonObject=="+jsonObject)
                val pos = position+1


//                val IMAGE_URLSP = applicationContext.getSharedPreferences(Config.SHARED_PREF29, 0)
//                val IMAGE_URLEditer = IMAGE_URLSP.edit()
//                IMAGE_URLEditer.putString("IMAGE_UR", IMAGE_URL)
//                IMAGE_URLEditer.commit()

                if (selectedPos == position){
                  //  holder.ll_type!!.setBackgroundColor(Color.parseColor("#567845"))
                    holder.ll_type!!.setBackgroundResource(R.drawable.shape_rectangle_border)
                }
//                if (position == 1){
//                    //  holder.ll_type!!.setBackgroundColor(Color.parseColor("#567845"))
//                    holder.ll_type!!.setBackgroundResource(R.drawable.shape_rectangle_border)
//                   // holder.ll_type!!.visibility = View.GONE
//                }



                val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
                Log.e(TAG,"IMAGE_URL 43   "+IMAGE_URLSP.getString("IMAGE_URL", null))
                val imgPath = IMAGE_URLSP.getString("IMAGE_URL", null)+jsonObject!!.getString("ImageCode")
                holder.tv_AgendaName.setText(jsonObject!!.getString("AgendaName"))
                holder.tv_count.setText(jsonObject!!.getString("Count"))
                PicassoTrustAll.getInstance(context)!!.load(imgPath).into(holder.img_Type)
                holder.ll_type.setTag(position)
                holder.ll_type.setOnClickListener(View.OnClickListener {
                    Log.i("kkkkkkkkkk","position_click="+position)
                    Log.i("kkkkkkkkkk","position_click_selected_pos="+selectedPos)
               //     holder.ll_type!!.setBackgroundResource(R.drawable.shape_rectangle_border)
                    clickListener!!.onClick(
                        position,
                        "agendaType"
                    )

                })



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
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var ll_type        : LinearLayout
        internal var tv_count       : TextView
        internal var tv_AgendaName  : TextView
        internal var img_Type       : ImageView

        init {

            ll_type          = v.findViewById<View>(R.id.ll_type) as LinearLayout
            tv_count         = v.findViewById<View>(R.id.tv_count) as TextView
            tv_AgendaName    = v.findViewById<View>(R.id.tv_AgendaName) as TextView
            img_Type         = v.findViewById<View>(R.id.img_Type) as ImageView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }


}