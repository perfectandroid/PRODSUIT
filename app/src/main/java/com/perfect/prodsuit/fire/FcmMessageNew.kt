package com.perfect.prodsuit.fire

import org.json.JSONArray

data class FcmMessageNew(val registration_ids: Array<String>, // Device token or topic
                         val data: Map<String, String>)
