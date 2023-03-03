package com.perfect.prodsuit.Helper

import org.json.JSONObject

public interface ItemClickListenerData {
    fun onClick(position: Int, data: String, jsonObject: JSONObject)
}