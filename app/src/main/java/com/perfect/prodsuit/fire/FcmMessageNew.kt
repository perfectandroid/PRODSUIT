package com.perfect.prodsuit.fire

data class FcmMessageNew(val to: String, // Device token or topic
                         val data: Map<String, String>)
