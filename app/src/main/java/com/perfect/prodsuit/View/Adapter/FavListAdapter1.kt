package com.perfect.prodsuit.View.Adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.favourites.DataBaseHelper
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.Model.InsertFavModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Activity.DashBoardActivity
import com.perfect.prodsuit.View.Activity.FavActivity
import com.perfect.prodsuit.View.Activity.LeadActivity
import com.perfect.prodsuit.View.Activity.LeadGenerationQuickActivity
import com.perfect.prodsuit.View.Activity.ProjectActivity
import com.perfect.prodsuit.View.Activity.ReportMainActivity
import com.perfect.prodsuit.View.Activity.ServiceActivity
import org.json.JSONObject


class FavListAdapter1(
    internal var context: Context,
    var list: List<InsertFavModel>?,

    ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var db: DataBaseHelper? = null
    internal val TAG : String = "FavListAdapter1"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListenerValue? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.fav_adap, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
          //  jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                holder.crdv_1.visibility=View.VISIBLE
                val currentItem = list!![position]
                Log.e(TAG,"Currenaddedtitem"+currentItem.title)
                holder.txtv_fav.text = currentItem.title
                var title=holder.txtv_fav.text.toString()
                if(title.equals("QuickLead"))
                {
                    holder.imgv_fav!!.setBackgroundResource(R.drawable.svg_lead_gen_sub)
                }
                else if(title.equals("Dashboard"))
                {
                    holder.imgv_fav!!.setBackgroundResource(R.drawable.dashboard_home)
                }
                else if(title.equals("Lead"))
                {
                    holder.imgv_fav!!.setBackgroundResource(R.drawable.lead_home)
                }
                else if(title.equals("Service"))
                {
                    holder.imgv_fav!!.setBackgroundResource(R.drawable.svg_ser_cs)
                }
                else if(title.equals("Project"))
                {
                    holder.imgv_fav!!.setBackgroundResource(R.drawable.dash_project)
                }
                else if(title.equals("Report"))
                {
                    holder.imgv_fav!!.setBackgroundResource(R.drawable.dash_collection)
                }
                holder.imgv_del!!.setTag(position)
                holder.imgv_del!!.setOnClickListener(View.OnClickListener {
                  //  clickListener!!.onClick(position, "delete",title)


                    db = DataBaseHelper(context, null)

                    // on below line we are calling a method to delete our
                    // course and we are comparing it with our course name.

                    // on below line we are calling a method to delete our
                    // course and we are comparing it with our course name.
                    db!!.deleteitem(title)
                    //   rclrvw_favlist!!.removeViewAt(position);

                    Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();
                    val myIntent = Intent(context, FavActivity::class.java)
                    context.startActivity(myIntent)
                    (context as Activity).finish()
                   // Log.e(TAG,"deleted   ")
                })


                holder.crdv_1!!.setTag(position)
                holder.crdv_1!!.setOnClickListener(View.OnClickListener {
                    if(title.equals("QuickLead"))
                    {

                        val intent = Intent(context, LeadGenerationQuickActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                    else if(title.equals("Dashboard"))
                    {

                        val intent = Intent(context, DashBoardActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                    else if(title.equals("Lead"))
                    {

                        val intent = Intent(context, LeadActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                    else if(title.equals("Service"))
                    {

                        val intent = Intent(context, ServiceActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                    else if(title.equals("Project"))
                    {

                        val intent = Intent(context, ProjectActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                    else if(title.equals("Report"))
                    {

                        val intent = Intent(context, ReportMainActivity::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()

                    }
                })


            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    private inner class MainViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        //        internal var txtSino          : TextView
        internal var llfav          : LinearLayout
        internal var txtv_fav    : TextView
        internal var crdv_1    : CardView
        internal var imgv_del    : ImageView
        internal var imgv_fav    : ImageView

        init {
//            txtSino        = v.findViewById<View>(R.id.txtSino) as TextView
//            txtArea        = v.findViewById<View>(R.id.txtArea) as TextView
            txtv_fav  = v.findViewById<View>(R.id.txtv_fav) as TextView
            llfav  = v.findViewById<View>(R.id.llfav) as LinearLayout
            crdv_1= v.findViewById<CardView>(R.id.crdv_1)
            imgv_del= v.findViewById<ImageView>(R.id.imgv_del)
            imgv_fav= v.findViewById<ImageView>(R.id.imgv_fav)
        }
    }


    fun setClickListener(itemClickListener: ItemClickListenerValue?) {
        clickListener = itemClickListener
    }
}

