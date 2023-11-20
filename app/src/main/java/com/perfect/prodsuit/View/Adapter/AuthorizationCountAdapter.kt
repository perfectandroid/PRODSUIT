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

class AuthorizationCountAdapter(internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    internal val TAG : String = "AccountDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    var row_index: Int = 0
    var pos = -1
//    var detailArray = ""
    var detailArray = JSONArray()

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
            var jsonObject = jsonArray!!.getJSONObject(position)
//            var jsonArray = jsonOject!!.getJSONObject()
            Log.e(TAG,"checckkkk  "+jsonObject)

                if (holder is MainViewHolder) {

                    if (jsonArray!!.getJSONObject(position).getJSONArray("Details").length() == 0){
                        holder.txt_labelName.visibility = View.GONE
                    }else{
                        holder.txt_labelName.visibility = View.VISIBLE
                        holder.txt_labelName.text = jsonObject.getString("Module")
                    }



                    if(position == pos){
                        holder.rcyexpand.visibility = View.VISIBLE
                    }else{
                        holder.rcyexpand.visibility = View.GONE
                    }

                    holder.txt_labelName!!.setTag(position)
                    holder.txt_labelName!!.setOnClickListener(View.OnClickListener {
                        Log.e(TAG,"77777ssaa2   :  "+jsonObject.getString("Details"))
                        Log.e(TAG,"77777ssaa2   :  "+position)

                        detailArray = (jsonArray!!.getJSONObject(position).getJSONArray("Details"))
                        Log.e(TAG,"ssaadd   :  "+detailArray)


                            val lLayout = GridLayoutManager(context, 1)
                            holder.rcyexpand!!.layoutManager = lLayout as RecyclerView.LayoutManager?
                            val adapter = AuthorizationExpandAdapter(context, detailArray)
                            holder.rcyexpand!!.adapter = adapter

                            pos = position
                            notifyDataSetChanged()

                    })


//                    holder.txt_labelName.text        = jsonArray.getJSONObject(position).toString()
////                    Log.e(TAG,"clickk 47471   :  "+jsonArray.getJSONObject(position).toString())
////                    val keys = jsonObject!!.keys()
////                    Log.e(TAG,"ffffffffffff 1 = "+keys.next())
////                    holder.txt_labelName.text = keys.next()
////                    while (keys.hasNext()) {
////                        val key = keys.next()
////                        Log.e(TAG, "JSON_KEY  1141   :  " + key)
////                        holder.txt_labelName.text = key
////
////
////                        Log.e(TAG,"ffffffffffff 1 = "+holder.txt_labelName.text.toString())
////                    }
//                    val keys = jsonObject!!.keys()
//                    Log.e(TAG,"sdfsdfsdfdsf  :  "+position+" "+pos )
////                    Log.e(TAG,"JSON_KEY  1149   :  "+ keys.hasNext() )
////                    Log.e(TAG,"JSON_KEY  1147   :  "+ keys.next() )
//
//
//                    holder.rcyexpand.visibility = View.VISIBLE
//                    Log.e(TAG,"clickk 47471   :  "+position+" "+pos )
//                    if(position == pos){
//                        holder.rcyexpand.visibility = View.VISIBLE
//                    }else{
//                        holder.rcyexpand.visibility = View.GONE
//                    }
////
//                    while (keys.hasNext()) {
//                        val key = keys.next()
//                        Log.e(TAG, "JSON_KEY  1141   :  " + key)
//                        holder.txt_labelName.text = key
//                    }
//
//                        holder.txt_labelName!!.setTag(position)
//                        holder.txt_labelName!!.setOnClickListener(View.OnClickListener {
////                        clickListener!!.onClick(position, "LabelClick")
//                            Log.e(TAG,"77777ddss   :  "+position)
//                            Log.e(TAG,"77777ssaa1   :  "+holder.txt_labelName.text.toString())
//                            Log.e(TAG,"77777ssaa2   :  "+jsonArray!!.getJSONObject(position).getJSONArray(holder.txt_labelName.text.toString()))
//
//                            detailArray = (jsonArray!!.getJSONObject(position).getJSONArray(holder.txt_labelName.text.toString()))
//                            Log.e(TAG,"ssaadd   :  "+detailArray)
//
//
//                            if (detailArray.length() == 0){
//
//                                Toast.makeText(context," NO DATA FOUND...",Toast.LENGTH_LONG).show()
//                            }else{
//
//                                val lLayout = GridLayoutManager(context, 1)
//                                holder.rcyexpand!!.layoutManager = lLayout as RecyclerView.LayoutManager?
//                                val adapter = AuthorizationExpandAdapter(context, detailArray)
//                                holder.rcyexpand!!.adapter = adapter
//
//                                pos = position
//                                notifyDataSetChanged()
//                            }
//
//
//
//                        })



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