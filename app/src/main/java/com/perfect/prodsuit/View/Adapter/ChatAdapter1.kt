package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Model.ChatModel
import com.perfect.prodsuit.R

class ChatAdapter1(internal var context: Context, internal var userMobile: String) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    internal val TAG : String = "ChatAdapter1"
    var mData =  ArrayList<ChatModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_chat1, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.e(TAG,"onBindViewHolder   1244444256   ")
        try {
            if (holder is MainViewHolder) {
                val empModel = mData!![position]
                Log.e(TAG,"onBindViewHolder   124444425   "+  empModel.message)

                holder.txt_opo_msg.text = empModel.message
                holder.txt_my_msg.text = empModel.message
                holder.txt_my_date.text = empModel.date+"  "+empModel.time
                holder.txt_opo_date.text = empModel.date+"  "+empModel.time

                if (empModel.mobile.equals(userMobile)){
                    holder.ll_my_msg.visibility = View.VISIBLE
                    holder.ll_opo_msg.visibility = View.GONE
                }else{
                    holder.ll_my_msg.visibility = View.GONE
                    holder.ll_opo_msg.visibility = View.VISIBLE
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }


    override fun getItemCount(): Int {
        return mData!!.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun addChat(mobile: String, msg: String, date: String, time: String) {
        Log.e(TAG,"6999991  "+mobile+" : "+msg+" :  "+mData.size)
        mData.add(ChatModel(mobile,msg,date,time))
//        notifyItemChanged(mData.size)
        Log.e(TAG,"6999991  "+mData[mData.size-1].message)
        notifyItemInserted(mData.size-1)
        Log.e(TAG,"6999992  "+mobile+" : "+msg+" :  "+mData.size)
    }


    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var txt_opo_msg: TextView
        var txt_my_msg: TextView
        var txt_my_date: TextView
        var txt_opo_date: TextView

        var ll_opo_msg: LinearLayout
        var ll_my_msg: LinearLayout

        init {
            txt_opo_msg = v.findViewById(R.id.txt_opo_msg) as TextView
            txt_my_msg = v.findViewById(R.id.txt_my_msg) as TextView
            txt_my_date = v.findViewById(R.id.txt_my_date) as TextView
            txt_opo_date = v.findViewById(R.id.txt_opo_date) as TextView

            ll_opo_msg = v.findViewById(R.id.ll_opo_msg) as LinearLayout
            ll_my_msg = v.findViewById(R.id.ll_my_msg) as LinearLayout

        }
    }
}