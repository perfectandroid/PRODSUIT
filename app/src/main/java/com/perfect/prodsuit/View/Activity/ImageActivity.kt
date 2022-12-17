package com.perfect.prodsuit.View.Activity


import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Fragment.Landmarkone
import com.perfect.prodsuit.View.Fragment.Landmarktwo
import com.perfect.prodsuit.Viewmodel.ImageViewModel
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class ImageActivity : AppCompatActivity(),View.OnClickListener{
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var todolistViewModel: TodoListViewModel
    private var rv_todolist: RecyclerView?=null
    lateinit var todoArrayList : JSONArray
    private var Id_leadgenrteprod: String? = null
    private var tabLayout: TabLayout? = null
    lateinit var imageViewModel: ImageViewModel
    private var viewPager: ViewPager? = null
    var landmark: String?=""
    var landmark2: String?=""
    var jobjt:JSONObject?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        Id_leadgenrteprod = intent.getStringExtra("prodid")
        setRegViews()

    }
    companion object {
        var strid= ""
    }
//    private fun getLocationDetails() {
//        strid = Id_leadgenrteprod!!
//        Log.i("Id", strid)
//        context = this@ImageActivity
//        imageViewModel = ViewModelProvider(this).get(ImageViewModel::class.java)
//        when (Config.ConnectivityUtils.isConnected(this)) {
//            true -> {
//                progressDialog = ProgressDialog(this, R.style.Progress)
//                progressDialog!!.setProgressStyle(android.R.style.Widget_ProgressBar)
//                progressDialog!!.setCancelable(false)
//                progressDialog!!.setIndeterminate(true)
//                progressDialog!!.setIndeterminateDrawable(this.resources.getDrawable(R.drawable.progress))
//                progressDialog!!.show()
//                imageViewModel.getImage(this,strid)!!.observe(this,
//                        { ImageSetterGetter ->
//                            val msg = ImageSetterGetter.message
//                            if (msg!!.length > 0) {
//                                val jObject = JSONObject(msg)
//                                if (jObject.getString("StatusCode") == "0") {
//                                    jobjt = jObject.getJSONObject("LeadImageDetails")
//                                    landmark = jobjt!!.getString("LocationLandMark1")
//                                    landmark2 = jobjt!!.getString("LocationLandMark2")
//
//
//                                    if (!landmark!!.isEmpty()&&!landmark2!!.isEmpty()) {
//                                        try {
//
//
//
//                                            viewPager = findViewById(R.id.viewpager)
//                                            setupViewPager(viewPager!!)
//
//                                            tabLayout = findViewById(R.id.tabs)
//                                            tabLayout!!.setupWithViewPager(viewPager)
//                                            /* val decodedString = Base64.decode(landmark, Base64.DEFAULT)
//                                            ByteArrayToBitmap(decodedString)
//                                            val decodedByte = BitmapFactory.decodeByteArray(decodedString,0, decodedString.size)
//                                            val stream = ByteArrayOutputStream()
//                                            decodedByte.compress(Bitmap.CompressFormat.PNG,100, stream)
//                                            Glide.with(this@ImageActivity) .load(stream.toByteArray()).into(imLandmark)*/
//                                        } catch (e: Exception) {
//                                            e.printStackTrace()
//                                        }
//                                    }
//                                   else  if (!landmark!!.isEmpty()) {
//                                        try {
//
//                                            viewPager = findViewById(R.id.viewpager)
//                                            setupViewPager(viewPager!!)
//
//                                            tabLayout = findViewById(R.id.tabs)
//                                            tabLayout!!.setupWithViewPager(viewPager)
//                                            /* val decodedString = Base64.decode(landmark, Base64.DEFAULT)
//                                            ByteArrayToBitmap(decodedString)
//                                            val decodedByte = BitmapFactory.decodeByteArray(decodedString,0, decodedString.size)
//                                            val stream = ByteArrayOutputStream()
//                                            decodedByte.compress(Bitmap.CompressFormat.PNG,100, stream)
//                                            Glide.with(this@ImageActivity) .load(stream.toByteArray()).into(imLandmark)*/
//                                        } catch (e: Exception) {
//                                            e.printStackTrace()
//                                        }
//                                    }
//                                    else if (!landmark2!!.isEmpty()) {
//                                        try {
//
//                                            viewPager = findViewById(R.id.viewpager)
//                                            setupViewPager(viewPager!!)
//
//                                            tabLayout = findViewById(R.id.tabs)
//                                            tabLayout!!.setupWithViewPager(viewPager)
//                                            /* val decodedString = Base64.decode(landmark, Base64.DEFAULT)
//                                            ByteArrayToBitmap(decodedString)
//                                            val decodedByte = BitmapFactory.decodeByteArray(decodedString,0, decodedString.size)
//                                            val stream = ByteArrayOutputStream()
//                                            decodedByte.compress(Bitmap.CompressFormat.PNG,100, stream)
//                                            Glide.with(this@ImageActivity) .load(stream.toByteArray()).into(imLandmark)*/
//                                        } catch (e: Exception) {
//                                            e.printStackTrace()
//                                        }
//                                    }
//                                    progressDialog!!.dismiss()
//                                }
//
//                                else {
//                                    val builder = AlertDialog.Builder(
//                                            this@ImageActivity,
//                                            R.style.MyDialogTheme
//                                    )
//                                    builder.setMessage(jObject.getString("EXMessage"))
//                                    builder.setPositiveButton("Ok") { dialogInterface, which ->
//                                    }
//                                    val alertDialog: AlertDialog = builder.create()
//                                    alertDialog.setCancelable(false)
//                                    alertDialog.show()
//
//                                }
//
//                            } else {
//                                Toast.makeText(
//                                        applicationContext,
//                                        "Some Technical Issues.",
//                                        Toast.LENGTH_LONG
//                                ).show()
//                            }
//                        })
//                progressDialog!!.dismiss()
//            }
//            false -> {
//                Toast.makeText(applicationContext, "No Internet Connection.", Toast.LENGTH_LONG)
//                        .show()
//            }
//        }
//    }


    private fun setRegViews() {

         val imback = findViewById<ImageView>(R.id.imback)

         imback!!.setOnClickListener(this)
     //    getLocationDetails()

      }
    private fun setupViewPager(viewPager: ViewPager) {

        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Landmarkone(), "Landmarkone",jobjt.toString())
        adapter.addFragment(Landmarktwo(), "Landmarktwo", jobjt.toString())
        viewPager.adapter = adapter



    }
    internal inner class ViewPagerAdapter(manager: FragmentManager) :
            FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String, jobj: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)


            val bundle = Bundle()
            bundle.putString("landmark1", jobj)
            bundle.putString("landmark2", jobj)
            fragment.arguments =bundle
        }

        override fun getPageTitle(position: Int): CharSequence? {

            return mFragmentTitleList[position]
        }
    }




    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }


}
