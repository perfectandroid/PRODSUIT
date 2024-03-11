package com.perfect.prodsuit.View.Activity

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.IntentFilter
import android.database.Cursor
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.perfect.favourites.DataBaseHelper
import com.perfect.prodsuit.Helper.ItemClickListenerValue
import com.perfect.prodsuit.Helper.NetworkChangeReceiver
import com.perfect.prodsuit.Model.FavlistModel
import com.perfect.prodsuit.Model.InsertFavModel
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.FavListAdapter
import com.perfect.prodsuit.View.Adapter.FavListAdapter1
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class FavActivity : AppCompatActivity() , View.OnClickListener, ItemClickListenerValue
     {
    var db: DataBaseHelper? = null
    private lateinit var categoriesList: ArrayList<FavlistModel>
    private lateinit var favnamesort: ArrayList<FavlistModel>
    //.............
    var ID_Master: String? = ""
    var TransMode: String? = ""
    var dialogReason: Dialog? = null
    //...............
    private lateinit var insertfavList: ArrayList<InsertFavModel>
    var TAG  ="FavActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    lateinit var serviceListViewModel: ServiceListViewModel
    lateinit var serviceListArrayList: JSONArray
    lateinit var serviceListSort: JSONArray

    private var tv_listCount: TextView? = null
    private var crdv_1: CardView? = null
    private var txtv_headlabel: TextView? = null
    private var imgv_filter: ImageView? = null
    internal var rclrv_favpop: RecyclerView? = null
    var serviceList = 0
    var label : String?= ""
    var SubMode : String?= ""
    var ID_Branch : String?= ""
    var FK_Area : String?= ""
    var ID_Employee : String?= ""
    var strFromDate : String?= ""
    var strToDate : String?= ""
    var strCustomer : String?= ""
    var strMobile : String?= ""
    var strTicketNo : String?= ""
    var strDueDays : String?= ""

    private var rclrvw_favlist: RecyclerView? = null
    private var til_Status: TextInputLayout? = null
    private var til_AttendedBy: TextInputLayout? = null

    private var tie_Status: TextInputEditText? = null
    private var tie_AttendedBy: TextInputEditText? = null

    private var tv_Ticket: TextView? = null
    private var tv_Customer: TextView? = null
    private var tv_Name: TextView? = null
    private var tv_Complaint: TextView? = null
    private var txtReset: TextView? = null
    private var txtUpdate: TextView? = null

    private var fab_fav: FloatingActionButton? = null
    private var tie_Branch: TextInputEditText? = null
    private var tie_Customer: TextInputEditText? = null
    private var tie_Mobile: TextInputEditText? = null
    private var tie_Area: TextInputEditText? = null
    private var tie_DueDays: TextInputEditText? = null

    private var txtFilterReset: TextView? = null
    private var txtFilterSearch: TextView? = null

    var filterTicketNumber: String? = ""
    var filterBranch : String? = ""
    var filterCustomer : String? = ""
    var filterMobile : String? = ""
    var filterArea   : String? = ""
    var filterDueDays : String? = ""
    var exmessage : String? = ""

    private var dialogReportName: Dialog? = null
    var statusCount = 0
    lateinit var followUpActionViewModel: FollowUpActionViewModel
    lateinit var followUpActionArrayList : JSONArray
    lateinit var followUpActionSort : JSONArray
    private var dialogFollowupAction : Dialog? = null
    var recyFollowupAction: RecyclerView? = null

    var attendCount = 0
    lateinit var employeeViewModel: EmployeeViewModel
    lateinit var employeeArrayList: JSONArray
    lateinit var employeeSort: JSONArray
    private var dialogEmployee: Dialog? = null
    var recyEmployee: RecyclerView? = null
    var labels: List<InsertFavModel>? = null

    var ReqMode: String? = ""
    var ID_Status: String? = ""
    var ID_AttendedBy: String? = ""

    var serAssignCount = 0
    lateinit var serviceAssignDetailViewModel: ServiceAssignDetailsViewModel
    var ID_CustomerServiceRegister: String? = ""
    var FK_CustomerserviceregisterProductDetails: String? = ""
    var TicketStatus: String? = ""
    var TicketDate: String? = ""

    var ID_Priority: String? = ""


    var strEditTicket : String?= ""
    var strEditProductComplaint : String?= ""
    var strEditCustomer : String?= ""
    var strEditProductname : String?= ""

    lateinit var serviceEditUpdateViewModel: ServiceEditUpdateViewModel
    var serUpdateCount = 0
    var strVisitDate : String?= ""
    var saveAttendanceMark = false
    val jsons= JSONObject()
    var recyReportName: RecyclerView? = null

    private lateinit var networkChangeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_fav_list)
        context = this@FavActivity

        setRegViews()
        labels = ArrayList<InsertFavModel>()
        db = DataBaseHelper(this, null)
        labels = db!!.getAllLabels();
        Log.e(TAG,"Labels"+labels)
   //     rclrvw_favlist!!.visibility=View.VISIBLE
        if (labels!!.size>0){
         //   labels = db!!.getAllLabels();
            rclrvw_favlist!!.visibility=View.VISIBLE
            val lLayout = GridLayoutManager(this@FavActivity, 1)
            rclrvw_favlist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FavListAdapter1(this@FavActivity,labels)
            rclrvw_favlist!!.adapter = adapter
        }
        // Stetho.initializeWithDefaults(this)

        // List categories
     //
        //   txtv_headlabel!!.setText(label)


        //  serviceList = 0


        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    private fun getFavList() {
        db = DataBaseHelper(this, null)
        var categoriesCursor: Cursor? = db!!.rawQuery("SELECT id, title FROM favourites")
        var favSize: Int = categoriesCursor!!.count
        Log.d(TAG, "favsize=" + favSize)
      //  val favlist = ArrayList<FavlistModel>()
        categoriesList = ArrayList<FavlistModel>()
        try {


            if (categoriesCursor.moveToFirst()) {
                while (!categoriesCursor.isAfterLast()) {
                    val favId = categoriesCursor.getInt(0)
                    val favName = categoriesCursor.getString(1)

                    categoriesList.add(FavlistModel(favId, favName))
                //    list.add(name);
                    categoriesCursor.moveToNext()

                }
            }


        }
        catch (e:Exception)
        {

        }
        Log.i(TAG,"CATEEEE"+categoriesList.toString())
        favpopup(categoriesList)

        rclrvw_favlist!!.visibility=View.VISIBLE
/*


*/

    }




    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        fab_fav= findViewById<FloatingActionButton>(R.id.fab_fav)
        fab_fav!!.setOnClickListener(this)
        rclrvw_favlist = findViewById<RecyclerView>(R.id.rclrvw_favlist)



        /*    imgv_filter = findViewById<ImageView>(R.id.imgv_filter)
            imgv_filter!!.setOnClickListener(this)

            fab_fav= findViewById<FloatingActionButton>(R.id.fab_fav)
            fab_fav!!.setOnClickListener(this)



            txtv_headlabel= findViewById<TextView>(R.id.txtv_headlabel)

            recyServiceList = findViewById<RecyclerView>(R.id.recyServiceList)
            recyServiceList!!.adapter = null
            tv_listCount = findViewById<TextView>(R.id.tv_listCount)*/

    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()

            }
            R.id.fab_fav->{
                rclrvw_favlist!!.visibility=View.VISIBLE
                getFavList()

            }


        }
    }








    override fun onRestart() {
        super.onRestart()

        networkChangeReceiver = NetworkChangeReceiver()
        registerReceiver(networkChangeReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        //  serviceList = 0
        //   getServiceNewList()
    }




    fun favpopup(favList: ArrayList<FavlistModel>) {
        try {

            dialogReason = Dialog(this)
            dialogReason!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogReason!! .setContentView(R.layout.fav_popup)
            dialogReason!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;

            val window: Window? = dialogReason!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
          //  window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            rclrv_favpop = dialogReason!!.findViewById(R.id.rclrv_favpop) as RecyclerView

        /*    favnamesort = favList
            for (k in 0 until favList.size) {
                val currentItem = favList[k]
               // val jsonObject = favList.getJSONObject(k)
              //  reasonSort.put(jsonObject)

                favnamesort.add(currentItem)
            }
*/



            val lLayout = GridLayoutManager(this@FavActivity, 1)
            rclrv_favpop!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FavListAdapter(this@FavActivity, favList)
            rclrv_favpop!!.adapter = adapter
            adapter.setClickListener(this@FavActivity)

            dialogReason!!.show()

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("eeee", "rreeeerree " + e)
        }
    }

    override fun onClick(position: Int, data: String, value: String) {
        if (data.equals("favlist")){
           // Toast.makeText(applicationContext,"FAV : "+value,Toast.LENGTH_LONG).show()

            labels = db!!.getAllLabels();
          //  Log.e(TAG,""+labels.toString())
            dialogReason!!.dismiss()
            rclrvw_favlist!!.visibility=View.VISIBLE
            val lLayout = GridLayoutManager(this@FavActivity, 1)
            rclrvw_favlist!!.layoutManager = lLayout as RecyclerView.LayoutManager?
            val adapter = FavListAdapter1(this@FavActivity,labels)
            rclrvw_favlist!!.adapter = adapter

        }


    }

         override fun onClick(position: Int, data: String) {
             TODO("Not yet implemented")
         }
     }