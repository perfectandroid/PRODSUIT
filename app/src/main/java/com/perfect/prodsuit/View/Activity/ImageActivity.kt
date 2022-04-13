package com.perfect.prodsuit.View.Activity

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.perfect.prodsuit.R
import com.perfect.prodsuit.View.Fragment.Landmarkone
import com.perfect.prodsuit.View.Fragment.Landmarktwo
import com.perfect.prodsuit.Viewmodel.TodoListViewModel
import org.json.JSONArray
import java.util.*

class ImageActivity : AppCompatActivity(),View.OnClickListener{
    private var progressDialog: ProgressDialog? = null
    lateinit var context: Context
    lateinit var todolistViewModel: TodoListViewModel
    private var rv_todolist: RecyclerView?=null
    lateinit var todoArrayList : JSONArray
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)

        setRegViews()
       // getTodoList()
    }



     private fun setRegViews() {

         val imback = findViewById<ImageView>(R.id.imback)

         imback!!.setOnClickListener(this)

         viewPager = findViewById(R.id.viewpager)
         setupViewPager(viewPager!!)

         tabLayout = findViewById(R.id.tabs)
         tabLayout!!.setupWithViewPager(viewPager)
      }
    private fun setupViewPager(viewPager: ViewPager) {


        val adapter: ViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Landmarkone(), "Landmarkone")
        adapter.addFragment(Landmarktwo(), "Landmarktwo")
        viewPager.adapter = adapter



    }




    internal class ViewPagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager!!) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()
        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }

    fun selectTab(position: Int) {
        viewPager!!.currentItem = position
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.imback -> {
                finish()
            }
        }
    }
}
