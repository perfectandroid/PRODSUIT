package com.perfect.prodsuit.fire

data class FcmMessage(val to: String, // Device token or topic
                      val data: Map<String, String>,
                      val notification: NotificationPayload)
