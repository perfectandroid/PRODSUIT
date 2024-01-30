package com.perfect.prodsuit.View.Activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.Viewmodel.DashboardCommonViewModel
import org.json.JSONArray
import org.json.JSONObject

class DashboradCommonActivity : AppCompatActivity() , View.OnClickListener {

    var TAG = "DashboradCommonActivity"
    lateinit var context: Context
    private var progressDialog: ProgressDialog? = null

    var dashboardCount: Int = 0
    lateinit var dashboardCommonViewModel: DashboardCommonViewModel
    lateinit var dashboardArrayList: JSONArray

    private var ll_main: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_dashborad_common)
        context = this@DashboradCommonActivity

        dashboardCommonViewModel = ViewModelProvider(this).get(DashboardCommonViewModel::class.java)

        setRegViews()

        getDataList()
    }



    private fun setRegViews() {
        val imback = findViewById<ImageView>(R.id.imback)
        ll_main = findViewById(R.id.ll_main)

        imback!!.setOnClickListener(this)

    }

    private fun getDataList() {
        when (Config.ConnectivityUtils.isConnected(this)) {
            true -> {
                progressDialog = ProgressDialog(context, R.style.Progress)
                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
                progressDialog!!.setCancelable(false)
                progressDialog!!.setIndeterminate(true)
                progressDialog!!.setIndeterminateDrawable(context.resources.getDrawable(R.drawable.progress))
                progressDialog!!.show()
                dashboardCommonViewModel.getDashboardCommon(this,"")!!.observe(
                    this,
                    Observer { serviceSetterGetter ->
                        val msg = serviceSetterGetter.message
                        if (msg!!.length > 0) {
                            if (dashboardCount == 0){
                                dashboardCount++
                                Log.e(TAG,"msg   2280   "+msg)
                                val jObject = JSONObject(msg)
                                if (jObject.getString("StatusCode").equals("0")) {
                                    val jobjt = jObject.getJSONObject("CorrectionDetails")
                                    dashboardArrayList = jobjt.getJSONArray("CorrectionDetailList")

                                    val customFont: Typeface = context.resources.getFont(R.font.myfont)

                                    if (dashboardArrayList.length()> 0){
                                        for (i in 0 until dashboardArrayList.length()) {
                                            var jsonObject = dashboardArrayList.getJSONObject(i)

                                            val cardView = CardView(this)
                                            val layoutParams = LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT)
                                            layoutParams.setMargins(16, 5, 16, 5) // Adjust margins as needed
                                            cardView.layoutParams = layoutParams
                                            cardView.radius = 10f // Card corner radius in pixels
                                            cardView.cardElevation = 8f // Card elevation in dp
                                            cardView.setContentPadding(0, 0, 0, 0) // Card content padding in dp
                                            cardView.background = context.resources.getDrawable(R.drawable.shape_shadownew)
                                            val drawablePadding = context.resources.getDimensionPixelSize(R.dimen.drawable_padding)


                                            val dynamicLinearLayoutmain = LinearLayout(context)
                                            dynamicLinearLayoutmain.layoutParams = LinearLayout.LayoutParams(
                                                LinearLayout.LayoutParams.MATCH_PARENT,
                                                LinearLayout.LayoutParams.WRAP_CONTENT)
                                            dynamicLinearLayoutmain.orientation = LinearLayout.VERTICAL
                                            dynamicLinearLayoutmain.setBackgroundColor(context.resources.getColor(R.color.white));

                                            val keys = jsonObject!!.keys()
                                            while (keys.hasNext()) {
                                                Log.e(TAG,"keys   11555    "+keys)
                                                val key = keys.next()

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
                                                textView2!!.setPadding(15, -0, 0, 0)


                                                val layoutParamsSub = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                layoutParamsSub.setMargins(15, -2, 15, -2)

                                                dynamicLinearLayout.addView(textView1);
                                                dynamicLinearLayout.addView(textView0);
                                                dynamicLinearLayout.addView(textView2);
                                                dynamicLinearLayout.layoutParams = layoutParamsSub

                                                val layoutParamMain = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                                                layoutParamMain.setMargins(5, 5, 5, 0)
//                                                dynamicLinearLayoutmain.setBackgroundResource(R.drawable.shape_shadownew)
                                                dynamicLinearLayoutmain.layoutParams = layoutParamMain

                                                dynamicLinearLayoutmain.addView(dynamicLinearLayout);

                                            }

                                            cardView.addView(dynamicLinearLayoutmain)
                                            cardView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))

                                            // Add the CardView to the parent layout
                                            ll_main!!.addView(cardView)


                                        }
                                    }

                                }else{
                                    val builder = AlertDialog.Builder(
                                        this@DashboradCommonActivity,
                                        R.style.MyDialogTheme
                                    )
                                    builder.setMessage(jObject.getString("EXMessage"))
                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
                                    }
                                    val alertDialog: AlertDialog = builder.create()
                                    alertDialog.setCancelable(false)
                                    alertDialog.show()
                                }
                            }
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

    override fun onClick(v: View) {
        when(v.id){
            R.id.imback->{
                finish()
            }
        }
    }

}