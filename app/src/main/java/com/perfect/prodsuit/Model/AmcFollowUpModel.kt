package com.perfect.prodsuit.Model

class AmcFollowUpModel{
    var type:String = ""
    var dueDate:String = ""
    var renewDate:String = ""
    var docType:String = ""
    var url:String = ""

    constructor(
        type: String,
        dueDate: String,
        renewDate: String,
        docType: String,
        url: String
    ) {
        this.type = type
        this.dueDate = dueDate
        this.renewDate = renewDate
        this.docType = docType
        this.url = url
    }

}