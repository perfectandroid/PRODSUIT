package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.graphics.Typeface
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class ApproveListAdapter (internal var context: Context, internal var jsonArray: JSONArray):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ApproveListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_approve_list, parent, false
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

                val customFont: Typeface = context.resources.getFont(R.font.myfont)
                val length: Int = jsonObject!!.length()
                val keys = jsonObject!!.keys()
                val dynamicLinearLayoutmain = LinearLayout(context)
                dynamicLinearLayoutmain.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                dynamicLinearLayoutmain.orientation = LinearLayout.VERTICAL
                while (keys.hasNext()) {
                    val key = keys.next()

                    if (!key.equals("SlNo") && !key.equals("ID_FIELD")  && !key.equals("drank") && !key.equals("TotalCount")){
                        Log.e(TAG,"JSON_KEY  4566   :  "+ key +"  :  "+jsonObject!!.getString(key)) // Output: key1, key2, key3

//                    val img: Drawable = context.getResources().getDrawable(R.drawable.vtr_common)

                        val drawable1 = ContextCompat.getDrawable(context, R.drawable.vtr_common)
                        val drawablePadding = context.resources.getDimensionPixelSize(R.dimen.drawable_padding)


                        val dynamicLinearLayout = LinearLayout(context)
                        dynamicLinearLayout.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT)
                        dynamicLinearLayout.orientation = LinearLayout.HORIZONTAL




                        val textView1 = TextView(context)
                        val layoutParams1 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams1.weight = 1.9f
                        textView1!!.layoutParams = layoutParams1
                        //  textView1.text = key
                        val cleanText: String = Config.addSpaceBetweenLowerAndUpper(key)
                        Log.e(TAG,"722224   "+key+"  :   cleanText   "+cleanText+"  :  "+Config.addSpaceBetweenLowerAndUpper(key))

                        textView1.text = cleanText
                        //    textView1.setCompoundDrawablesWithIntrinsicBounds(drawable1, null, null, null)
                        textView1.compoundDrawablePadding = drawablePadding
                        textView1!!.setTextSize(15F)
                        textView1.setCompoundDrawablePadding(2)
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
                        textView2.text = jsonObject!!.getString(key)
                        val layoutParams2 = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                        layoutParams2.weight = 1.0f
                        textView2!!.layoutParams = layoutParams2
                      //  textView2.text = "ghfdghjbvhjbfvghjdfmhjbfbnbjknjkkjvfbdnkngkjdfjglkdftytrytrytrytryryrbfvhfghfghfghfghfhbfhfhhfghkjghdfjhdfhh"
                        textView2!!.setTextSize(14F)
                        textView2!!.setTypeface(customFont);
                        textView2!!.setTextColor(context.resources.getColor(R.color.greydark))
                        textView2!!.setPadding(5, -10, 0, 0)

                        dynamicLinearLayout.addView(textView1);
                        dynamicLinearLayout.addView(textView0);
                        dynamicLinearLayout.addView(textView2);

                        dynamicLinearLayoutmain.addView(dynamicLinearLayout);
                    }

                }

//                val dynamicLinearLayoutButton = LinearLayout(context)
//                dynamicLinearLayoutButton.layoutParams = LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT)
//                dynamicLinearLayoutButton.orientation = LinearLayout.HORIZONTAL

//
//                val paramsExample = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f)
//                paramsExample.setMargins(20, 30, 20, 20);
//
//
//                val textView3 = TextView(context)
//                textView3.text = "Reject"
//                textView3!!.setTextSize(15F)
//                textView3.setGravity(Gravity.CENTER);
//                textView3!!.setTypeface(customFont);
//                textView3!!.setTextColor(context.resources.getColor(R.color.color_reject))
//                textView3.setPadding(10, 10, 10, 10);
//                textView3!!.layoutParams = paramsExample
//                textView3.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_reject))
//
//                val textView4 = TextView(context)
//                textView4.text =  "Approved"
//                textView4!!.setTextSize(15F)
//                textView4.setGravity(Gravity.CENTER);
//                textView4!!.setTypeface(customFont);
//                textView4!!.setTextColor(context.resources.getColor(R.color.color_approve))
//                textView4.setPadding(10, 10, 10, 10);
//                textView4!!.layoutParams = paramsExample
//                textView4.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.shape_approve))
//
//
//                dynamicLinearLayoutButton.addView(textView3);
//                dynamicLinearLayoutButton.addView(textView4);


                holder.ll_main!!.addView(dynamicLinearLayoutmain);
//                holder.ll_main!!.addView(dynamicLinearLayoutButton);
//
//                textView3!!.setTag(position)
//                textView3!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "rejectClick")
//                })
//
//                textView4!!.setTag(position)
//                textView4!!.setOnClickListener(View.OnClickListener {
//                    clickListener!!.onClick(position, "approveClick")
//                })

//                holder.txtSino.text        = pos.toString()
//                holder.txtArea.text        = jsonObject!!.getString("Area")
//
                holder.ll_main!!.setTag(position)
                holder.ll_main!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(position, "approveListClick")
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
        return position % 2
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


    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}