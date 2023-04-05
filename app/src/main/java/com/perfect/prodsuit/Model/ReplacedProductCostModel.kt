package com.perfect.prodsuit.Model

class ReplacedProductCostModel{
    var ID_OLD_Product:String = ""
    var OLD_Product:String = ""
    var SPDOldQuantity:String = ""
    var Amount:String = ""
    var ReplaceAmount:String = ""
    var Remarks:String = ""
    var ID_Product:String = ""
    var Product:String = ""
    var isChecked:String = ""

    constructor(
        ID_OLD_Product: String,
        OLD_Product: String,
        SPDOldQuantity: String,
        Amount: String,
        ReplaceAmount: String,
        Remarks: String,
        ID_Product: String,
        Product: String,
        isChecked: String
    ) {
        this.ID_OLD_Product = ID_OLD_Product
        this.OLD_Product = OLD_Product
        this.SPDOldQuantity = SPDOldQuantity
        this.Amount = Amount
        this.ReplaceAmount = ReplaceAmount
        this.Remarks = Remarks
        this.ID_Product = ID_Product
        this.Product = Product
        this.isChecked = isChecked
    }
}