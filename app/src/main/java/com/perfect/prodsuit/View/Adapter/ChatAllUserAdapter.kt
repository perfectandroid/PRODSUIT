package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Model.ChatMessageList
import com.perfect.prodsuit.R
import java.util.*

class ChatAllUserAdapter (internal var context: Context, internal var mList: List<ChatMessageList>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "UserListAdapter"
    //    val data = ArrayList<ModelStockTransferDetails>()
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_chat_alluser, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            if (holder is MainViewHolder) {
                val empModel = mList[position]
                Log.e(TAG,"onBindViewHolder   1051   "+  empModel.mobile)

                val r = Random()
                val red: Int = r.nextInt(255 - 0 + 1) + 0
                val green: Int = r.nextInt(255 - 0 + 1) + 0
                val blue: Int = r.nextInt(255 - 0 + 1) + 0

                val draw = GradientDrawable()
                draw.cornerRadius = 50F
                draw.setShape(GradientDrawable.RECTANGLE)
                draw.setColor(Color.rgb(red, green, blue))
                holder.txt_letter.setBackground(draw)

                holder.txt_letter.text = empModel.name!!.substring(0,1)
                holder.txt_name.text = empModel.name
                holder.txt_mobile.text = empModel.mobile
                holder.txt_unread.text = empModel.unseenMessage.toString()

//                if (empModel.unseenMessage!! > 0){
//                    holder.txt_unread.visibility = View.VISIBLE
//                }else{
//                    holder.txt_unread.visibility = View.GONE
//                }

                holder.ll_userlist!!.setTag(position)
                holder.ll_userlist!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "userAllClicks"
                    )

                })



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
        var txt_mobile: TextView
        var txt_name: TextView
        var txt_unread: TextView
        var txt_letter: TextView

        var ll_userlist: LinearLayout



        init {
            txt_mobile = v.findViewById(R.id.txt_mobile) as TextView
            txt_name = v.findViewById(R.id.txt_name) as TextView
            txt_unread = v.findViewById(R.id.txt_unread) as TextView
            txt_letter = v.findViewById(R.id.txt_letter) as TextView

            ll_userlist = v.findViewById(R.id.ll_userlist) as LinearLayout

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}