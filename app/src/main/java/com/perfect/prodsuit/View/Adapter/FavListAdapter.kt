package com.perfect.prodsuit.View.Adapter

import android.app.AlertDialog
import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.perfect.favourites.DataBaseHelper
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.Model.FavlistModel
import com.perfect.prodsuit.Model.InsertFavModel
import com.perfect.prodsuit.R
import org.json.JSONObject
import java.util.ArrayList

class FavListAdapter(internal var context: Context, val list: ArrayList<FavlistModel>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    internal val TAG : String = "FavListAdapter"
    internal var jsonObject: JSONObject? = null
    private var clickListener: ItemClickListenerValue? = null
    var db: DataBaseHelper? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val vh: RecyclerView.ViewHolder
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_fav, parent, false
        )
        vh = MainViewHolder(v)
        return vh
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
          //  jsonObject = jsonArray.getJSONObject(position)
            if (holder is MainViewHolder) {
                val pos = position+1
                holder.txtSino.text        = pos.toString()

                val currentItem = list[position]
                Log.e(TAG,"Currentposi"+position)
                holder.txtv_fav.text = currentItem.title
                var title=holder.txtv_fav.text.toString()

                holder.crd_view!!.setTag(position)
                holder.crd_view!!.setOnClickListener(View.OnClickListener {
                    db = DataBaseHelper(context, null)
                    if(db!!.itemExists(title)) {
                        val builder = AlertDialog.Builder(
                            context,
                            R.style.MyDialogTheme
                        )
                        builder.setMessage("Already Exists")
                        builder.setPositiveButton("Ok") { dialogInterface, which ->
                            clickListener!!.onClick(position, "favlist",title)
                        }
                        val alertDialog: AlertDialog = builder.create()
                        alertDialog.setCancelable(false)
                        alertDialog.show()
                    }
                    else
                    {
                        db!!.insert(title)
                        clickListener!!.onClick(position, "favlist",title)
                    }


                  /*  holder.txtv_fav!!.setTag(position)
                    if(holder.txtv_fav.text.equals("Dashboard"))
                    {
                        val intent = Intent(context, DashBoardActivity::class.java)
                        context.startActivity(intent)

                    }*/


                })
          /*      holder.llfav!!.setOnClickListener(View.OnClickListener {
                    Config.disableClick(it)
                    Log.e(TAG,"poss"+position)
                    if(position==0)
                    {
                        clickListener!!.onClick(
                            position,
                            "QuickLead"


                        )

                    }
                    else if(position==1)
                    {
                        clickListener!!.onClick(
                            position,
                            "Dashboard"


                        )
                    }
                    else if(position==2)
                    {
                        clickListener!!.onClick(
                            position,
                            "Report"


                        )
                    }
                    *//*  holder.txtv_fav!!.setTag(position)
                      if(holder.txtv_fav.text.equals("Dashboard"))
                      {
                          val intent = Intent(context, DashBoardActivity::class.java)
                          context.startActivity(intent)

                      }*//*


                })
*/
              /*  holder.llfav!!.setTag(position)
                holder.llfav!!.setOnClickListener(View.OnClickListener {
                    clickListener!!.onClick(
                        position,
                        "favclick"
                    )
                })*/
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG,"Exception   105   "+e.toString())
        }
    }

    override fun getItemCount(): Int {
        return list.size
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
        internal var txtSino    : TextView
        internal var crd_view    : CardView
        init {
            txtSino        = v.findViewById<View>(R.id.txtsino) as TextView
            crd_view        = v.findViewById<View>(R.id.crd_view) as CardView
            txtv_fav  = v.findViewById<View>(R.id.txtv_fav) as TextView
            llfav  = v.findViewById<View>(R.id.llfav) as LinearLayout
        }
    }


    fun setClickListener(itemClickListener: ItemClickListenerValue?) {
        clickListener = itemClickListener
    }
}

