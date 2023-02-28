package com.perfect.prodsuit.Model

class AmcFollowUpListModel{
    var invoiceNo:String = ""
    var date:String = ""
    var dealer:String = ""

    constructor(
        invoiceNo: String,
        date: String,
        dealer: String
    ) {
        this.invoiceNo = invoiceNo
        this.date = date
        this.dealer = dealer
    }

}