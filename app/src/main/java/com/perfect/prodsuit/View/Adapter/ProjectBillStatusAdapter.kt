package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject
import java.text.DecimalFormat


class ProjectBillStatusAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    internal var remark: String



    ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ProjectTileStatusAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var tvv_countValue=0.00

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_project_billstatus, parent, false
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


                tvv_countValue=jsonObject!!.getString("Value").toDouble()
                //  tvv_countValue=1649785634.18888
                val forM= String.format("%2f",tvv_countValue)
                val deciForm= DecimalFormat("##0.00")
                val forMatNo=deciForm.format(tvv_countValue)

                Log.e(TAG, "353353 forMatNo==   "+forMatNo)



                //   tvv_count!!.text=   Config.changeTwoDecimel(jobjt.getString("StockValue"))
            //    tvv_count!!.text=   forMatNo.toString()

                holder.txtv_label.text        = jsonObject!!.getString("Label")
          //     holder.tv_newCount.text        = jsonObject!!.getString("Value").toDouble().toInt().toString()
                holder.tv_newCount.text        = forMatNo.toString()
             //   var mode = jsonObject!!.getString("ModuleMode")

                if (jsonObject!!.getString("Label").equals("Bill Amount")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.green))
                }
                if (jsonObject!!.getString("Label").equals("Advance Amount")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.ongoing))
                }
                if (jsonObject!!.getString("Label").equals("Balance Amount")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.converted))
                }
                if (jsonObject!!.getString("Label").equals("Total Due Amount")){
                    holder.txtv_label.setTextColor(context.getColor(R.color.bluecolr))
                }


                holder.ll_new!!.setTag(position)
//                holder.ll_new!!.setOnClickListener(View.OnClickListener {
//
//                    Toast.makeText(context, remark, Toast.LENGTH_SHORT).show()
//
//
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtv_label          : TextView
        internal var tv_newCount          : TextView
        internal var ll_new          : LinearLayout


        init {
           txtv_label        = v.findViewById<View>(R.id.txtv_label) as TextView
           tv_newCount        = v.findViewById<View>(R.id.tv_newCount) as TextView
           ll_new        = v.findViewById<View>(R.id.ll_new) as LinearLayout


        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}