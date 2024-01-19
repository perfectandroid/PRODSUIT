package com.perfect.prodsuit.fire

import org.json.JSONArray

data class FcmMessageNew1(val to: String, // Device token or topic
                          val data: Map<String, String>)
