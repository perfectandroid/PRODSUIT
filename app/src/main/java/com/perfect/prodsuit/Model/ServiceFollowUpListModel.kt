package com.perfect.prodsuit.Model

class ServiceFollowUpListModel{
    var ticketNumber:String = ""
    var ticketDate:String = ""
    var customer:String = ""
    var mobile:String = ""
    var priority:String = ""
    var status:String = ""
    var assignedDate:String = ""
    var longitude:String = ""
    var latitude:String = ""
    var dueDay:String = ""
    var product:String = ""
    var runningStatus:String = ""

    constructor(
        ticketNumber: String,
        ticketDate: String,
        customer: String,
        mobile: String,
        priority: String,
        status: String,
        assignedDate: String,
        longitude: String,
        latitude: String,
        dueDay: String,
        product: String,
        runningStatus: String
    ) {
        this.ticketNumber = ticketNumber
        this.ticketDate = ticketDate
        this.customer = customer
        this.mobile = mobile
        this.priority = priority
        this.status = status
        this.assignedDate = assignedDate
        this.longitude = longitude
        this.latitude = latitude
        this.dueDay = dueDay
        this.product = product
        this.runningStatus = runningStatus
    }

}