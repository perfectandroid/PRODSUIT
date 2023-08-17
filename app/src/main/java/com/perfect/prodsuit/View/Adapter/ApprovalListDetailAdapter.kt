package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ApprovalListDetailAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ApprovalListDetailAdapter"
    internal var jsonObject: JSONObject? = null
    //private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_approvl_listdetail, parent, false
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

                val length: Int = jsonObject!!.length()
                val keys = jsonObject!!.keys()
                val dynamicLinearLayoutmain = LinearLayout(context)
                dynamicLinearLayoutmain.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                dynamicLinearLayoutmain.orientation = LinearLayout.VERTICAL
                while (keys.hasNext()) {
                    val key = keys.next()
                    Log.e(TAG,"JSON_KEY  4566   :  "+ key +"  :  "+jsonObject!!.getString(key)) // Output: key1, key2, key3

                    if (!key.equals("SlNo")){

                        val dynamicLinearLayout = LinearLayout(context)
                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        dynamicLinearLayout.orientation = LinearLayout.HORIZONTAL
                        val drawablePadding = context.resources.getDimensionPixelSize(R.dimen.drawable_padding)

                        val customFont: Typeface = context.resources.getFont(R.font.myfont)

                        val textView1 = TextView(context)
                        val layoutParams1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams1.weight = 1.9f
                        textView1!!.layoutParams = layoutParams1
//                        val layoutParams1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
//                        layoutParams1.weight = 1.9f
//                        textView1!!.layoutParams = layoutParams1
                        textView1.text = key
                        textView1!!.setTextSize(15F)
                        textView1!!.setTypeface(customFont);
                        textView1!!.setTextColor(context.resources.getColor(R.color.black))

                        val textView0 = TextView(context)
                        //  textView1.text = key
                        textView0.text = " : "
                        textView0.compoundDrawablePadding = drawablePadding
                        textView0!!.setTextSize(15F)
                        textView0.setCompoundDrawablePadding(2)
                        textView0!!.setTypeface(customFont);
                        textView0!!.setTextColor(context.resources.getColor(R.color.black))
                        textView0!!.setPadding(5, -10, 5, 0)

                        val textView2 = TextView(context)
                        val layoutParams2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams2.weight = 1.0f
                        textView2!!.layoutParams = layoutParams2
                        textView2.text = jsonObject!!.getString(key)
                        textView2!!.setTextSize(14F)
                        textView2!!.setTypeface(customFont);
                        textView2!!.setTextColor(context.resources.getColor(R.color.greydark))
                        textView2!!.setPadding(0, -10, 0, 0)
                        textView2!!.marginTop.div(-10)


                        dynamicLinearLayout.addView(textView1);
                        dynamicLinearLayout.addView(textView0);
                        dynamicLinearLayout.addView(textView2);

                        dynamicLinearLayoutmain.addView(dynamicLinearLayout);
                    }


                }

                holder.ll_main!!.addView(dynamicLinearLayoutmain);

//                holder.txtSino.text        = pos.toString()
//                holder.txtArea.text        = jsonObject!!.getString("Area")
//
//                holder.llArea!!.setTag(position)
//                holder.llArea!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "areadetail")
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
//        internal var txtSino          : TextView
//        internal var txtArea          : TextView
        internal var ll_main    : LinearLayout
        init {
//            txtSino        = v.findViewById<View>(R.id.txtSino) as TextView
//            txtArea        = v.findViewById<View>(R.id.txtArea) as TextView
            ll_main  = v.findViewById<View>(R.id.ll_main) as LinearLayout
        }
    }


}