package com.perfect.prodsuit.Model

class ReplacedProductCostModel{
    var components:String = ""
    var amount:String = ""
    var quantity:String = ""
    var changeMode:String = ""
    var buyBackAmount:String = ""
    var remark:String = ""
    var product:String = ""
    var isChecked:String = ""

    constructor(
        components: String,
        amount: String,
        quantity: String,
        changeMode: String,
        buyBackAmount: String,
        product: String,
        remark: String,
        isChecked: String
    ) {
        this.components = components
        this.amount = amount
        this.quantity = quantity
        this.changeMode = changeMode
        this.buyBackAmount = buyBackAmount
        this.product = product
        this.remark = remark
        this.isChecked = isChecked
    }
}