package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class DocumentDetailAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    internal val TAG : String = "DocumentDetailAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_document_detail, parent, false
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
                holder.txtDate.text           = jsonObject!!.getString("DocumentDate")
                holder.txtSubject.text        = jsonObject!!.getString("DocumentSubject")
                holder.txtDescription.text    = jsonObject!!.getString("DocumentDescription")

                holder.imgDownload!!.setTag(position)
                holder.imgDownload!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "Documents"
                    )
                })

                holder.llViewDesc!!.setTag(position)
                holder.llViewDesc!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "ViewDescription"
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
        return position
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {

        internal var txtDate             : TextView
        internal var txtSubject          : TextView
        internal var txtDescription      : TextView
        internal var imgDownload         : ImageView
        internal var llDocumentDetails   : LinearLayout
        internal var llViewDesc          : LinearLayout
        init {
            txtDate               = v.findViewById<View>(R.id.txtDate) as TextView
            txtSubject            = v.findViewById<View>(R.id.txtSubject) as TextView
            txtDescription        = v.findViewById<View>(R.id.txtDescription) as TextView
            imgDownload           = v.findViewById<View>(R.id.imgDownload) as ImageView
            llDocumentDetails     = v.findViewById<View>(R.id.llDocumentDetails) as LinearLayout
            llViewDesc            = v.findViewById<View>(R.id.llViewDesc) as LinearLayout
        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }

}