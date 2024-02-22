package com.perfect.prodsuit.View.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.AttendanceReportListActivity
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat


class AttendanceReportAdapter(
    internal var context: Context,
    internal var jsonArray: JSONArray,
    internal var outPunchArray: JSONArray,
    internal var date: String,



    ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "AttendanceReportAdapter"
    internal var jsonObject: JSONObject? = null
    internal var jsonObject1: JSONObject? = null
    private var clickListener: ItemClickListener? = null
    internal var time1: String?=""
    internal var loc1: String?=""
    internal var punchin: String?=""
    internal var time2: String?=""
    internal var loc2: String?=""
    internal var punchout: String?=""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter__attendance_report1, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            Log.i("Crpos",position.toString())
            val pos = position
            jsonObject = jsonArray.getJSONObject(position)
            jsonObject1 = outPunchArray.getJSONObject(position)

            if (holder is MainViewHolder) {
                Log.e(TAG,"onBindViewHolder   idcustomerservice and    "+jsonArray.getJSONObject(pos).toString()+" Pos : "+position)
                holder.txtv_Designation.text        = jsonObject!!.getString("Designation")



                holder.txtDate.text        = jsonObject!!.getString("EnteredDate")


                time1 = jsonObject!!.getString("EnteredTime")
                loc1  = jsonObject!!.getString("LocationName")

                punchin =jsonObject!!.getString("PunchStatus")

                if(time1.equals(""))
                {
                    holder.time0.visibility=View.GONE
                }
                else
                {
                    holder.txtv_time1.text        = jsonObject!!.getString("EnteredTime")
                }
                if(loc1.equals(""))
                {
                    holder.txtv_loc1.visibility=View.GONE
                }
                else
                {
                    holder.txtv_loc1.text        = jsonObject!!.getString("LocationName")
                }

                if(punchin.equals(""))
                {
                    holder.txtPunchin.visibility=View.GONE
                }
                else
                {
                    holder.txtPunchin.text        = jsonObject!!.getString("PunchStatus")
                    holder.txtPunchin.setTextColor(ContextCompat.getColor(context, R.color.green1));
                }


                holder.txtv_name.text        = jsonObject!!.getString("EmployeeName")
                holder.txtv_name.setTextColor(R.color.green1)
                // holder.txtv_time1.text        = jsonObject!!.getString("EnteredTime")
                //   holder.txtv_loc1.text        = jsonObject!!.getString("LocationName")
                // holder.txtPunchin.text        = jsonObject!!.getString("PunchStatus")

                /*    if(jsonObject!!.getString("EnteredTime").equals(""))
                    {
                        holder.time0.visibility=View.GONE
                    }
                    if(jsonObject!!.getString("LocationName").equals("")),,,,,,,
                    {
                        holder.txtv_loc1.visibility=View.GONE
                    }
                    if(jsonObject1!!.getString("PunchStatus").equals(""))
                    {
                        holder.txtPunchin.visibility=View.GONE
                    }
    */


                time2 = jsonObject1!!.getString("EnteredTime")
                loc2  = jsonObject1!!.getString("LocationName")

                punchout =jsonObject1!!.getString("PunchStatus")



                if(time2.equals(""))
                {
                    holder.time.visibility=View.GONE
                }
                else
                {
                    holder.txtv_time2.text        = jsonObject1!!.getString("EnteredTime")
                }
                if(loc2.equals(""))
                {
                    holder.txtv_loc2.visibility=View.GONE
                }
                else
                {
                    holder.txtv_loc2.text        = jsonObject1!!.getString("LocationName")
                }
                if(punchout.equals(""))
                {
                    holder.txtPunchout.visibility=View.GONE
                }
                else
                {
                    holder.txtPunchout.text        = jsonObject1!!.getString("PunchStatus")
                    holder.txtPunchout.setTextColor(ContextCompat.getColor(context, R.color.red))
                }




                Log.e(TAG,"Adapterdet in"+"Time : "+jsonObject1!!.getString("EnteredTime")+"\n"+"Loc : "+jsonObject1!!.getString("LocationName")+"Punchout : "+jsonObject1!!.getString("PunchStatus"))


                Log.e(TAG,"Adapterdet"+"Time "+jsonObject1!!.getString("EnteredTime")+"\n"+"Loc "+jsonObject1!!.getString("LocationName")+"Punchout "+jsonObject1!!.getString("PunchStatus"))

                /*  if(jsonObject1!!.getString("EnteredTime").equals(""))
                  {
                      holder.time.visibility=View.GONE
                  }
                  if(jsonObject1!!.getString("LocationName").equals(""))
                  {
                      holder.txtv_loc2.visibility=View.GONE
                  }
                  if(jsonObject1!!.getString("PunchStatus").equals(""))
                  {
                      holder.txtPunchout.visibility=View.GONE
                  }*/



            //   holder.txtPunchout.setTextColor(ContextCompat.getColor(context, R.color.red));



                holder.im_detail!!.setTag(position)
                holder.im_detail!!.setOnClickListener(View.OnClickListener {
                    /*clickListener!!.onClick(
                        position,
                        "Service_timeLine"
                    )*/
                    var name =holder.txtv_name.text.toString()
                    if(name.equals(""))
                    {

                    }
                    else
                    {

                            val intent = Intent(context, AttendanceReportListActivity::class.java)
                            intent.putExtra("name", name)
                            intent.putExtra("id", jsonObject!!.getString("ID_EmployeeAttanceMarking"))


                            val inputFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
                            val outputFormat: DateFormat = SimpleDateFormat("dd-MM-yyyy")



                            var dates =  jsonObject!!.getString("EnteredDate")
                            val dateFrom = inputFormat.parse(dates)
                            // val dateFrom = inputFormat.parse("08-04-2022")
                            val strDate = outputFormat.format(dateFrom)


                            intent.putExtra("date", strDate)

                            context.startActivity(intent)


                    }


                })
                //  holder.tv_punchDate.text        = jsonObject!!.getString("EnteredTime")
                /*   if(jsonObject!!.getString("Status").equals("True"))
                   {
                       holder.txtv_punchstatus.text        = "Punch In"
                       holder.pnch_image.setBackgroundResource(R.drawable.punch_in);
                   }
                   else
                   {
                       holder.txtv_punchstatus.text        = "Punch Out"
                       holder.pnch_image.setBackgroundResource(R.drawable.punch_out);
                   }


                                   holder.im_tracker!!.setTag(position)
                                   holder.im_tracker!!.setOnClickListener(View.OnClickListener {
                                       Config.disableClick(it)
                                       clickListener!!.onClick(
                                           position,
                                           "ServiceTracker"
                                       )
                                   })



                                   holder.llServiceList!!.setTag(position)
                                   holder.llServiceList!!.setOnClickListener(View.OnClickListener {
                                       clickListener!!.onClick(
                                           position,
                                           "ServiceList"
                                       )
                                   })

                                   holder.ll_ticketNumber!!.setTag(position)
                                   holder.ll_ticketNumber!!.setOnClickListener(View.OnClickListener {
                                       clickListener!!.onClick(
                                           position,
                                           "Service_timeLine"
                                       )
                                   })
                   */

            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   att   "+e.toString())
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
        internal var txtv_name          : TextView
        internal var txtv_Designation          : TextView

        internal var txtv_time1          : TextView
        internal var txtv_loc1          : TextView
        internal var txtv_time2          : TextView
        internal var txtv_loc2          : TextView


        internal var txtPunchin          : TextView


        internal var txtPunchout          : TextView
        internal var txtDate          : TextView
        internal var time          : LinearLayout
        internal var time0          : LinearLayout



        internal var im_detail          : ImageView


        init {
            txtPunchin        = v.findViewById<View>(R.id.txtPunchin) as TextView
            txtPunchout        = v.findViewById<View>(R.id.txtPunchout) as TextView

            txtv_name        = v.findViewById<View>(R.id.txtv_name) as TextView
            txtv_time1        = v.findViewById<View>(R.id.txtPunchinTime) as TextView
            txtv_loc1        = v.findViewById<View>(R.id.txtPunchinLoc) as TextView
            txtv_time2        = v.findViewById<View>(R.id.txtPunchoutTime) as TextView
            txtv_loc2        = v.findViewById<View>(R.id.txtPunchoutLoc) as TextView
            txtv_Designation  = v.findViewById<View>(R.id.txtv_Designation) as TextView

            txtDate  = v.findViewById<View>(R.id.txtDate) as TextView
            time= v.findViewById<View>(R.id.time) as LinearLayout
            time0= v.findViewById<View>(R.id.time0) as LinearLayout



            //  llback = v.findViewById<View>(R.id.llback) as LinearLayout

            im_detail = v.findViewById<View>(R.id.im_detail) as ImageView

        }
    }

    fun setClickListener(itemClickListener: ItemClickListener?) {
        clickListener = itemClickListener
    }
}