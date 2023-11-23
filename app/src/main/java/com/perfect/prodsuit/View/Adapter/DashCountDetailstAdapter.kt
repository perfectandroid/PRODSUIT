package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ModuleWiseExpandModel
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class DashCountDetailstAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "DashCountDetailstAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var row_index: Int = 0
    var pos = -1
//    var detailArray = ""
    var detailArray = JSONArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_homecount_details, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            var jsonObject = jsonArray!!.getJSONObject(position)
//            var jsonArray = jsonOject!!.getJSONObject()
            Log.e(TAG,"checckkkk  "+jsonObject)


                if (holder is MainViewHolder) {

                    if (jsonArray!!.getJSONObject(position).getJSONArray("ListData").length() == 0){
                        holder.txt_labelName.visibility = View.GONE
                    }else{
                        holder.txt_labelName.visibility = View.VISIBLE
                        holder.txt_labelName.text = jsonObject.getString("ModuleName")

                        Log.e(TAG,"ModuleName33333  "+jsonObject.getString("ModuleName"))
                    }



                    if(position == pos){
                        holder.rcyexpand.visibility = View.VISIBLE
                    }else{
                        holder.rcyexpand.visibility = View.GONE
                    }

                    holder.txt_labelName!!.setTag(position)
                    holder.txt_labelName!!.setOnClickListener(View.OnClickListener {
                        Log.e(TAG,"77777ssaa2   :  "+jsonObject.getString("ListData"))
                        Log.e(TAG,"77777ssaa2   :  "+position)

                        detailArray = (jsonArray!!.getJSONObject(position).getJSONArray("ListData"))
                        Log.e(TAG,"ssaadd   :  "+detailArray)


                            val lLayout = GridLayoutManager(context, 1)
                            holder.rcyexpand!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                            val adapter = HomeCountDetailExpandAdapter(context, detailArray)
                            holder.rcyexpand!!.adapter = adapter

                            pos = position
                            notifyDataSetChanged()

                    })


//
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        Log.e(TAG,"qwqwq  "+jsonArray.length())
        return jsonArray.length()
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        internal var txt_labelName        : TextView
        internal var rcyexpand            : RecyclerView
        init {
              txt_labelName               = v.findViewById<View>(R.id.txt_labelName) as TextView
              rcyexpand                   = v.findViewById<View>(R.id.rcyexpand)     as RecyclerView
        }
    }


//    fun setClickListener(itemClickListener: Context) {
//        context = itemClickListener
//    }

}