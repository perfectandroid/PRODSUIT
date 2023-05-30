package com.perfect.prodsuit.Helper

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import java.text.DecimalFormat

class CommaSeparatorTextWatcher(private val editText: EditText, val context :  Context) : TextWatcher {

    var TAG = "CommaSeparatorTextWatcher"

    private val decimalFormat = DecimalFormat("#,##,##,##,###")

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Not needed for this example, but required to implement
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Not needed for this example, but required to implement
    }

    override fun afterTextChanged(s: Editable?) {
        editText.removeTextChangedListener(this)

//        val amount = s.toString()
//        var amt : String = ""
//        try {
//            if (amount.contains(".")){
//
//                Log.e(DecimalToWordsConverter.TAG,"21510   "+amount.substringBefore("."))
//                amt  = decimalFormat.format(amount.substringBefore(".").toInt())+"."+amount.substringAfter(".")
//            }else{
//                Log.e(DecimalToWordsConverter.TAG,"21511   "+amount)
//                amt  = decimalFormat.format(amount.toInt())
//            }
//            Log.e(TAG,"19588    Amt    "+amt)
//
//        }catch (e : Exception){
//            Log.e(TAG,"19588    Exception    "+e.toString()+"   :   ")
//        }
//

//        // Remove existing comma separators
//        val strippedText = originalText.replace(",", "")

        // Format the stripped text with comma separators
//        val formattedText = decimalFormat.format(strippedText.toLong())
//
//        editText.setText(formattedText)
//        editText.setSelection(formattedText.length)
//
//        editText.addTextChangedListener(this)
    }
}