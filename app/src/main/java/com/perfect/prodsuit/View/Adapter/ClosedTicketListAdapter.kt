package com.perfect.prodsuit.View.Adapter

import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
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
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject


class ClosedTicketListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceListAHistdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_closed_tkt, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1




                holder.txtv_ticketno.text        = jsonObject!!.getString("TicketNo")
                holder.txtv_customer.text        = jsonObject!!.getString("Customer")
                holder.txtv_Address.text        = jsonObject!!.getString("CusAddress")





             /*
                holder.im_edit!!.setTag(position)
                holder.im_edit!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(position, "ServiceEdit")
                })

                holder.im_tracker!!.setTag(position)
                holder.im_tracker!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    clickListener!!.onClick(position, "ServiceTracker")
                })



                holder.llServiceList!!.setTag(position)
                holder.llServiceList!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "ServiceList")
                })
*/
//                val colorAnim: ValueAnimator = ObjectAnimator.ofInt(holder.tv_TimeDue!!, "textColor", Color.RED, Color.BLUE)
//                colorAnim.setDuration(5000)
//                colorAnim.setEvaluator(ArgbEvaluator())
//                colorAnim.setRepeatCount(ValueAnimator.INFINITE)
//                colorAnim.setRepeatMode(ValueAnimator.REVERSE)
//                colorAnim.start()

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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtv_ticketno          : TextView
        internal var txtv_customer          : TextView
        internal var txtv_Address          : TextView


        init {
            txtv_ticketno        = v.findViewById<View>(R.id.txtv_ticketno) as TextView
            txtv_customer        = v.findViewById<View>(R.id.txtv_customer) as TextView
            txtv_Address        = v.findViewById<View>(R.id.txtv_Address) as TextView


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}