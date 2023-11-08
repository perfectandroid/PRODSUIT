package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class AuthorizationCountAdapter(internal var context: Context, internal var jsonObjct: JSONObject):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "AccountDetailAdapter"
    internal var jsonObj: JSONArray? = null
    private var clickListener: ItemClickListener? = null
    internal var rcyexpand: RecyclerView? = null
    var row_index: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_authorization_count, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
//            jsonObject = jsonArray.getJSONObject(position)
//            jsonObj = jsonObjct.getJSONArray()


            Log.e(TAG,"checckkkk  "+jsonObjct)
            if (holder is MainViewHolder) {
//                holder.txtAccount.text  = jsonObj!!.getString("name")
//                Log.e(TAG,"onBindViewHolder   1051   ")
//                val pos = position+1
//                holder.txtAccount.text        = jsonObj!!.getString("name")
//                holder.imgIcon.setImageResource(jsonObj!!.getInt("image"))
//                if (position == row_index){
//                    holder.llaccontdetail!!.setBackgroundResource(R.drawable.shape_selected)
//                }else{
//                    holder.llaccontdetail!!.setBackgroundResource(R.drawable.shape_default)
//                }
//                holder.llaccontdetail!!.setTag(position)
//                holder.llaccontdetail!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, jsonObj!!.getString("name"))
//                    row_index=position;
//                    notifyDataSetChanged()
//                })

//                val lLayout = GridLayoutManager(this@AuthorizationCountAdapter, 1)
//                rcyexpand!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                val adapter = AuthorizationExpandAdapter(this@AuthorizationCountAdapter, jobjt)
//                rcyexpand!!.adapter = adapter
//                adapter.setClickListener(this@AuthorizationCountAdapter)

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return jsonObjct!!.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txtAccount        : TextView
//        internal var rcyexpand         : RecyclerView
        init {
            txtAccount               = v.findViewById<View>(R.id.txtAccount) as TextView
//            rcyexpand                = v.findViewById<View>(R.id.rcyexpand) as RecyclerView
        }
    }


    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}