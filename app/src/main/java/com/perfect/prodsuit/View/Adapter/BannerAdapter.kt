package com.perfect.prodsuit.View.Adapter

import android.content.Context
import android.util.Log
import androidx.viewpager.widget.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.view.ViewGroup
import android.widget.ImageView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R
import java.util.ArrayList

class BannerAdapter(private val context: Context, mResources: List<String>) : PagerAdapter() {

    var layoutInflater: LayoutInflater
    var mResources: List<String> = ArrayList()

    override fun getCount(): Int {
        return mResources.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.bannerslides, container, false)
        val imageView = itemView.findViewById<View>(R.id.image) as ImageView
        val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
        var IMAGE_URL   = IMAGE_URLSP.getString("IMAGE_URL", null)
        Log.e("TAG","310  "+mResources.get(position))
        PicassoTrustAll.getInstance(context)!!.load(IMAGE_URL+""+mResources.get(position)).into(imageView)
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    init {
        this.mResources = mResources
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

}