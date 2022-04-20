package com.perfect.prodsuit.View.Fragment

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.perfect.prodsuit.R
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Landmarktwo  : Fragment(){
    private var landmark:String?=""
    var jobjt: JSONObject?=null
    private var progressDialog: ProgressDialog? = null
    internal var imLandmark: ImageView? = null
    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_landmarktwo, container, false)


        val landmark2 = arguments!!.getString("landmark2")
        Log.i("landmark2", landmark2.toString())

        imLandmark = rootView.findViewById(R.id.imLandmark2) as ImageView

        var obj = JSONObject(landmark2)
        landmark = obj!!.getString("LocationLandMark1")

        val decodedString = Base64.decode(landmark, Base64.DEFAULT)
        ByteArrayToBitmap(decodedString)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        val stream = ByteArrayOutputStream()
        decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream)
        Glide.with(this) .load(stream.toByteArray()).into(imLandmark!!)


        return rootView
    }
    fun ByteArrayToBitmap(byteArray: ByteArray): Bitmap {
        val arrayInputStream = ByteArrayInputStream(byteArray)
        return BitmapFactory.decodeStream(arrayInputStream)
    }
}