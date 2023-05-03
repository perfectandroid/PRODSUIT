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
import com.perfect.prodsuit.Model.ModelMoreServices
import com.perfect.prodsuit.Model.ModelMoreServicesTemp
import com.perfect.prodsuit.Model.ModelServiceAttended
import com.perfect.prodsuit.Model.ModelServiceAttendedTemp
import com.perfect.prodsuit.R
import org.json.JSONArray
import org.json.JSONObject

class MoreServiceAttendedAdapter  (internal var context: Context,internal var mList: List<ModelMoreServices>,internal var mListTemp: List<ModelMoreServicesTemp>,internal var mAttend: List<ModelServiceAttendedTemp>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "ServiceAttendedAdapter"
    internal var jsonObject: JSONObject? = null
    internal var jsonObject1: JSONObject? = null
     private var clickListener: ItemClickListenerValue? = null
    val data = ArrayList<ModelMoreServices>()
    val mAttend1 = ArrayList<ModelServiceAttendedTemp>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_more_service_attended, parent, false
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

                holder.tv_service_cost.text = ItemsModel.Service
                holder.chkbox_more_service.setTag(position)
                if ((ItemsModel.isChecked).equals("1")){
                    holder.chkbox_more_service.isChecked = true
                }else{
                    holder.chkbox_more_service.isChecked = false
                }

                holder.chkbox_more_service.setTag(position)
                holder.chkbox_more_service.setOnClickListener {
                    if (holder.chkbox_more_service.isChecked){
                        ItemsModelTemp.isChecked = "1"

                        clickListener!!.onClick(position, "MoreServiceClick","1")

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
                        clickListener!!.onClick(position, "MoreServiceClick","0")
                      //  data[position].copy(ItemsModel.Service,ItemsModel.ID_Services,"0")
//                        for (i in 0 until mAttend.size) {
//                            val ItemsAttend = mAttend[i]
//
//                            Log.e(TAG,"6454  "+ItemsAttend.ID_Services+"  :  "+ItemsModel.ID_Services)
//                            if (ItemsAttend.ID_Services.equals(ItemsModel.ID_Services)){
//                                Log.e(TAG,"6455  "+ItemsAttend.ID_Services+"  :  "+ItemsModel.ID_Services)
//                            }else{
//                                Log.e(TAG,"6456  "+ItemsAttend.ID_Services+"  :  "+ItemsModel.ID_Services)
//                            }
//                        }
                    }

                    Log.e(TAG,"6222  "+data)
                }

//                holder.chkbox_more_service.setTag(position)
//                holder.chkbox_more_service.setOnClickListener {
//                    if (holder.chkbox_more_service.isChecked){
//                        Toast.makeText(context, "isChecked - " +  holder.chkbox_more_service.isChecked()+"  :  "+position, Toast.LENGTH_SHORT).show()
//                      //  clickListener!!.onClick(position, "MoreServiceAttended","1")
//                        val jObject = JSONObject()
//
//                        jObject.put("ID_Services", jsonObject1!!.getString("ID_Services"))
//                        jObject.put("Service", jsonObject1!!.getString("Service"))
//                        jObject.put("isChecked","1")
//                        jObject.put("isCheckedTemp","0")
//                        selectJsonArray.put(position,jsonObject1)
//                    }else{
//                        Toast.makeText(context, "isChecked - " +  holder.chkbox_more_service.isChecked()+"  :  "+position, Toast.LENGTH_SHORT).show()
//                     //   clickListener!!.onClick(position, "MoreServiceAttended","0")
//                        val jObject = JSONObject()
//
//                        jObject.put("ID_Services", jsonObject1!!.getString("ID_Services"))
//                        jObject.put("Service", jsonObject1!!.getString("Service"))
//                        jObject.put("isChecked","0")
//                        jObject.put("isCheckedTemp","0")
//                        selectJsonArray.put(position,jsonObject1)
//                    }
//
//                    Log.e(TAG,"555   selectJsonArray   "+selectJsonArray)
//                }
//
//                holder.tv_service_cost.text = jsonObject!!.getString("Service")
//                if (jsonObject!!.getString("isChecked").equals("1")){
//                    holder.chkbox_more_service.isChecked = true
//                }else{
//                    holder.chkbox_more_service.isChecked = false
//                }

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
        var tv_service_cost: TextView
        var chkbox_more_service: CheckBox


        init {
            tv_service_cost = v.findViewById(R.id.tv_service_cost) as TextView
            chkbox_more_service = v.findViewById(R.id.chkbox_more_service) as CheckBox
        }
    }

    fun setClickListener(itemClickListener: ItemClickListenerValue) {
        clickListener = itemClickListener
    }


}