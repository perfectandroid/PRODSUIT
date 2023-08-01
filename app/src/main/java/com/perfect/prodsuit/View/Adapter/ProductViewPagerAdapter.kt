package com.perfect.prodsuit.View.Adapter
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.impulsive.zoomimageview.ZoomImageView
import com.perfect.nbfcmscore.Helper.PicassoTrustAll
import com.perfect.prodsuit.Helper.Config
import com.perfect.prodsuit.R


class ProductViewPagerAdapter(private val context: Context, mResources: List<String>) : PagerAdapter() {

    var layoutInflater: LayoutInflater
    var mResources: List<String> = ArrayList()
    var TAG = "ProductViewPagerAdapter"
    private var scaleGestureDetector: ScaleGestureDetector? = null
    private val mScaleFactor = 1.0f

    override fun getCount(): Int {
        return mResources.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = layoutInflater.inflate(R.layout.adapter_product_view_pager, container, false)
        val imageView = itemView.findViewById<View>(R.id.image) as ImageView
        val IMAGE_URLSP = context.getSharedPreferences(Config.SHARED_PREF29, 0)
        var IMAGE_URL   = IMAGE_URLSP.getString("IMAGE_URL", null)
        Log.e(TAG,"3101  "+IMAGE_URL+mResources.get(position))
        PicassoTrustAll.getInstance(context)!!.load(IMAGE_URL+""+mResources.get(position)).into(imageView)
        imageView.setOnClickListener {
            Log.e(TAG,"41111  "+position)

            ShowEnalargeImage(IMAGE_URL+mResources.get(position))
        }
        container.addView(itemView)

        return itemView
    }

    private fun ShowEnalargeImage(imageUrl: String) {

        try {
            var  dialogDetailSheet = Dialog(context)
            dialogDetailSheet!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogDetailSheet!! .setContentView(R.layout.enlarge_product_image)
            dialogDetailSheet!!.window!!.attributes.gravity = Gravity.CENTER_VERTICAL;


            //   var img_enlarge: ImageView? = dialogDetailSheet.findViewById<ImageView>(R.id.img_enlarge)
            var img_enlarge: ZoomImageView? = dialogDetailSheet.findViewById<ZoomImageView>(R.id.img_enlarge)
            PicassoTrustAll.getInstance(context)!!.load(imageUrl).into(img_enlarge)

            val window: Window? = dialogDetailSheet!!.getWindow()
            window!!.setBackgroundDrawableResource(android.R.color.transparent);
            window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            dialogDetailSheet!!.show()
        }catch (e: Exception){

        }



    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }

    init {
        this.mResources = mResources
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }



}