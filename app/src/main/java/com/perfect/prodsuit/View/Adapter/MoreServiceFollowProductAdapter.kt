package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.Model.*
import com.perfect.prodsuit.R
import org.json.JSONObject

class MoreServiceFollowProductAdapter (internal var context: Context, internal var mList: List<ModelServiceFollowProductList>, internal var mListTemp: List<ModelServiceFollowProductListTemp>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "MoreServiceFollowProductAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListenerValue? = null
    val data = ArrayList<ModelServiceFollowProductList>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_more_service_followup_product, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
//            jsonObject = jsonArray.getJSONObject(position)
//            jsonObject1 = selectJsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   1051   ")

                val ItemsModel = mList[position]
                val ItemsModelTemp = mListTemp[position]

                holder.tv_service_follow_ProductName.text = ItemsModel.ProductName
                holder.chkbox_service_follow.setTag(position)
                if ((ItemsModel.isChecked).equals("1")){
                    holder.chkbox_service_follow.isChecked = true
                }else{
                    holder.chkbox_service_follow.isChecked = false
                }

                holder.chkbox_service_follow.setTag(position)
                holder.chkbox_service_follow.setOnClickListener {
                    if (holder.chkbox_service_follow.isChecked){
                        ItemsModelTemp.isChecked = "1"
                        ItemsModelTemp.isAdded = "1"

                        clickListener!!.onClick(position, "MoreServiceFollowProductClick","1")

//                        for (i in 0 until mAttend.size) {
//                            val ItemsAttend = mAttend[i]
//
//                            Log.e(TAG,"6451  "+ItemsAttend.ID_Services+"  :  "+ItemsModel.ID_Services)
//                            if (ItemsAttend.ID_Services.equals(ItemsModel.ID_Services)){
//                                Log.e(TAG,"6452  "+ItemsAttend.ID_Services+"  :  "+ItemsModel.ID_Services)
//                            }else{
//                                Log.e(TAG,"6453  "+ItemsAttend.ID_Services+"  :  "+ItemsModel.ID_Services)
//                                val ItemsAttend2 = ModelServiceAttendedTemp("",""
//                                    ,"",ItemsModel.ID_Services,ItemsModel.Service,"", "","","",ItemsModel.isChecked,"1")
//                                mAttend1!!.add(ItemsAttend2)
//                            }
//                        }
                    }else{
                        ItemsModelTemp.isChecked = "0"
                        ItemsModelTemp.isAdded = "0"
                        clickListener!!.onClick(position, "MoreServiceFollowProductClick","0")
                    }

                    Log.e(TAG,"6222  "+data)
                }


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
        var tv_service_follow_ProductName: TextView
        var chkbox_service_follow: CheckBox


        init {
            tv_service_follow_ProductName = v.findViewById(R.id.tv_service_follow_ProductName) as TextView
            chkbox_service_follow = v.findViewById(R.id.chkbox_service_follow) as CheckBox
        }
    }

    fun setClickListener(itemClickListener: ItemClickListenerValue) {
        clickListener = itemClickListener
    }


}