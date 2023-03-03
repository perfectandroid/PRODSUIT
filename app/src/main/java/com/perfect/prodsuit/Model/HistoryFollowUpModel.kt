package com.perfect.prodsuit.Model

class HistoryFollowUpModel {
    var ticketNo:String = ""
    var service:String = ""
    var complaint:String = ""
    var status:String = ""
    var closedDate:String = ""
    var employeeNote:String = ""

    constructor(
        ticketNo: String,
        service: String,
        complaint: String,
        status: String,
        closedDate: String,
        employeeNote: String
    ) {
        this.ticketNo = ticketNo
        this.service = service
        this.complaint = complaint
        this.status = status
        this.closedDate = closedDate
        this.employeeNote = employeeNote
    }
}