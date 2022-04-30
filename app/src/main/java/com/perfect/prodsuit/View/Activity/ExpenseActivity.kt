package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.Helper.ItemClickListener
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Adapter.TodoListAdapter
import com.perfect.prodsuit.Viewmodel.AddNoteViewModel
import com.perfect.prodsuit.Viewmodel.ExpenseViewModel
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception
import java.util.*

class ExpenseActivity : AppCompatActivity(), View.OnClickListener{


    private var progressDialog: ProgressDialog? = null
    lateinit var espenseViewModel: ExpenseViewModel
    lateinit var context: Context
    private var rv_expenselist: RecyclerView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expense)
        context = this@ExpenseActivity
        setRegViews()
        getExpenses()

    }

    private fun setRegViews() {

        val imback = findViewById<ImageView>(R.id.imback)
        imback!!.setOnClickListener(this)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab!!.setOnClickListener(this)
        rv_expenselist = findViewById(R.id.rv_expenselist)
    }



    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
            R.id.fab -> {
                val i = Intent(this@ExpenseActivity, ExpenseAddActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun getExpenses() {

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
                         //   val jobjt = jObject.getJSONObject("AddAgentCustomerRemarks")
                          /*  if (jObject.getString("StatusCode") == "0") {

                             val jobjt = jObject.getJSONObject("LeadManagementDetailsList")
                                    todoArrayList = jobjt.getJSONArray("LeadManagementDetails")
                                    val lLayout = GridLayoutManager(this@TodoListActivity, 1)
                                    rv_todolist!!.layoutManager =
                                            lLayout as RecyclerView.LayoutManager?
                                    rv_todolist!!.setHasFixedSize(true)
                                    val adapter = ExpenseAdapter(applicationContext, todoArrayList)
                                    rv_todolist!!.adapter = adapter
                                    adapter.setClickListener(this@TodoListActivity)
                            }*/
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

}
