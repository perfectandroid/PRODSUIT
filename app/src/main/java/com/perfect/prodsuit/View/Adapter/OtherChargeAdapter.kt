package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModelOtherCharges
import com.perfect.prodsuit.Model.ServiceDetailsFullListModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class OtherChargeAdapter(internal var context: Context, internal var mList: List<ModelOtherCharges>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "OtherChargeAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_project_othercharges, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {

            if (holder is MainViewHolder) {
                val empModel = mList[position]
                Log.e(TAG,"onBindViewHolder   1051   ")
                val pos = position+1
                holder.tv_Type.text        = empModel!!.Type_Name

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
        internal var tv_Type   : TextView
      //  internal var tv_TransType     : TextView
        internal var edt_Amount    : EditText
        internal var edt_Tax_Amount    : EditText
        init {
            tv_Type          = v.findViewById<View>(R.id.tv_Type) as TextView
         //   tv_TransType                = v.findViewById<View>(R.id.tv_TransType) as TextView
            edt_Amount           = v.findViewById<View>(R.id.edt_Amount) as EditText
            edt_Tax_Amount           = v.findViewById<View>(R.id.edt_Tax_Amount) as EditText
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}