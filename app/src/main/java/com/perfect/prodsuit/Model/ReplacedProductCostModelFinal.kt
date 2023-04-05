package com.perfect.prodsuit.Model

class ReplacedProductCostModelFinal{
    var id:String = ""
    var Component:String = ""
    var Quantity:String = ""
    var changeMode:String = ""
    var buyBackAmount:String = ""
    var replacedProduct:String = ""
    var replacedQuantity:String = ""
    var replacedAmount:String = ""
    var remarks:String = ""
    var isChecked:String = ""

    constructor(
        id: String,
        Component: String,
        Quantity: String,
        changeMode: String,
        buyBackAmount: String,
        replacedProduct: String,
        replacedQuantity: String,
        replacedAmount: String,
        remarks: String,
        isChecked: String
    ) {
        this.id = id
        this.Component = Component
        this.Quantity = Quantity
        this.changeMode = changeMode
        this.buyBackAmount = buyBackAmount
        this.replacedProduct = replacedProduct
        this.replacedQuantity = replacedQuantity
        this.replacedAmount = replacedAmount
        this.remarks = remarks
        this.isChecked = isChecked
    }
}