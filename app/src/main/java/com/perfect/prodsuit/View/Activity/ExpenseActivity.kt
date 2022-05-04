package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.ExpenseAdapter
import com.perfect.prodsuit.Viewmodel.ExpenseViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ExpenseActivity : AppCompatActivity(), View.OnClickListener,ItemClickListener{

    private var progressDialog: ProgressDialog? = null
    lateinit var espenseViewModel: ExpenseViewModel
    lateinit var context: Context
    private var rv_expenselist: RecyclerView?=null
    private var txtv_totexp: TextView?=null
    private var imexpense: ImageView?=null
    lateinit var expenseArrayList : JSONArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        context = this@ExpenseActivity
        setRegViews()

        val c = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val formattedDate = simpleDateFormat.format(c)

        getExpenses(formattedDate, formattedDate)

    }

    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        imexpense = findViewById(R.id.imexpense)
        imexpense!!.setOnClickListener(this)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener(this)
        rv_expenselist = findViewById(R.id.rv_expenselist)
        txtv_totexp = findViewById<TextView>(R.id.txtv_totexp)
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.imexpense -> {
                finish()
            }
            R.id.fab -> {
                val i = Intent(this@ExpenseActivity, ExpenseAddActivity::class.java)
                startActivity(i)
            }
        }
    }

    companion object {
        var strfdate= ""
        var strtdate= ""
    }

    private fun getExpenses(formDate: String, toDate: String) {

        strfdate = formDate
        strtdate = toDate

        espenseViewModel = ViewModelProvider(this).get(ExpenseViewModel::class.java)
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(this, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                espenseViewModel.getExpenselist(this)!!.observe(
                    this,
                    Observer { expenseSetterGetter ->
                        val msg = expenseSetterGetter.message
                        if (msg!!.length > 0) {
                            val jObject = JSONObject(msg)
                          //  val jobjt = jObject.getJSONObject("DateWiseExpenseDetails")
                            if (jObject.getString("StatusCode") == "0") {

                             val jobjt = jObject.getJSONObject("DateWiseExpenseDetails")
                                var tot = jobjt.getString("Total")
                                txtv_totexp!!.text="\u20B9 "+tot

                                expenseArrayList = jobjt.getJSONArray("DateWiseExpenseDetailsList")
                                    val lLayout = GridLayoutManager(this@ExpenseActivity, 1)
                                rv_expenselist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                rv_expenselist!!.setHasFixedSize(true)
                                    val adapter = ExpenseAdapter(applicationContext, expenseArrayList)
                                rv_expenselist!!.adapter = adapter
                                    adapter.setClickListener(this@ExpenseActivity)
                            }
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "Some Technical Issues.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
                progressDialog!!.dismiss()
            }
            false -> {
                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    override fun onClick(position: Int, data: String) {
       /* if (data.equals("todolist")){
            val jsonObject = todoArrayList.getJSONObject(position)
            val i = Intent(this@TodoListActivity, AccountDetailsActivity::class.java)
            i.putExtra("jsonObject",jsonObject.toString())
            startActivity(i)
        }*/
    }

}
