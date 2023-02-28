package com.perfect.prodsuit.Model

class PaymentMethodModel{
    var id:String = ""
    var method:String = ""
    var reff:String = ""
    var amount:String = ""

    constructor(
        id: String,
        method: String,
        reff: String,
        amount: String
    ) {
        this.id = id
        this.method = method
        this.reff = reff
        this.amount = amount
    }

}