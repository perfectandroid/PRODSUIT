package com.perfect.prodsuit.View.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.*
import org.json.JSONArray

class TicketReportDetailActivity : AppCompatActivity() , View.OnClickListener{

    val TAG : String = "TicketReportDetailActivity"
    lateinit var context: Context
    private var imback: ImageView? = null

    private var ReportMode:String?=""
    private var ID_Branch:String?=""
    private var strFromdate:String?=""
    private var strTodate:String?=""
    private var ID_Product:String?=""
    private var ID_NextAction:String?=""
    private var ID_ActionType:String?=""
    private var ID_Priority:String?=""
    private var ID_Status:String?=""
    private var GroupId:String?=""

    lateinit var actionListReportViewModel: ActionListTicketReportViewModel
    lateinit var actionListReportArrayList : JSONArray
    var recyActionListReport  : RecyclerView? = null

    lateinit var followUpTicketReportViewModel: FollowUpTicketReportViewModel
    lateinit var followUpTicketReportArrayList : JSONArray
    var recyFollowUpTicketReport  : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_ticket_report_detail)
        context = this@TicketReportDetailActivity

        actionListReportViewModel = ViewModelProvider(this).get(ActionListTicketReportViewModel::class.java)
        followUpTicketReportViewModel = ViewModelProvider(this).get(FollowUpTicketReportViewModel::class.java)

        setRegViews()

        if (getIntent().hasExtra("ReportMode")) {
            ReportMode = intent.getStringExtra("ReportMode")
        }
        if (getIntent().hasExtra("ID_Branch")) {
            ID_Branch = intent.getStringExtra("ID_Branch")
        }
        if (getIntent().hasExtra("Fromdate")) {
            strFromdate = intent.getStringExtra("Fromdate")
        }
        if (getIntent().hasExtra("Todate")) {
            strTodate = intent.getStringExtra("Todate")
        }
        if (getIntent().hasExtra("ID_Product")) {
            ID_Product = intent.getStringExtra("ID_Product")
        }
        if (getIntent().hasExtra("ID_NextAction")) {
            ID_NextAction = intent.getStringExtra("ID_NextAction")
        }
        if (getIntent().hasExtra("ID_ActionType")) {
            ID_ActionType = intent.getStringExtra("ID_ActionType")
        }
        if (getIntent().hasExtra("ID_Priority")) {
            ID_Priority = intent.getStringExtra("ID_Priority")
        }
        if (getIntent().hasExtra("ID_Status")) {
            ID_Status = intent.getStringExtra("ID_Status")
        }
        if (getIntent().hasExtra("GroupId")) {
            GroupId = intent.getStringExtra("GroupId")
        }



    }

    private fun setRegViews() {
        imback = findViewById(R.id.imback)
        imback!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }
}