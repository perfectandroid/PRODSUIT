package com.perfect.prodsuit.Model

class MoreReplacedProductCostModel{
    var ID_Product:String = ""
    var Code:String = ""
    var Name:String = ""
    var MRPs:String = ""
    var MRP1R:String = ""
    var SalesPrice1R:String = ""
    var SalePrice:String = ""
    var CurrentStock1R:String = ""
    var StockId:String = ""
    var TaxAmount:String = ""
    var StandbyStock:String = ""
    var TotalCount:String = ""
    var isChecked:String = ""

    constructor(
        ID_Product: String,
        Code: String,
        Name: String,
        MRPs: String,
        MRP1R: String,
        SalesPrice1R: String,
        SalePrice: String,
        CurrentStock1R: String,
        StockId: String,
        TaxAmount: String,
        StandbyStock: String,
        TotalCount: String,
        isChecked: String
    ) {
        this.ID_Product = ID_Product
        this.Code = Code
        this.Name = Name
        this.MRPs = MRPs
        this.MRP1R = MRP1R
        this.SalesPrice1R = SalesPrice1R
        this.SalePrice = SalePrice
        this.CurrentStock1R = CurrentStock1R
        this.StockId = StockId
        this.TaxAmount = TaxAmount
        this.StandbyStock = StandbyStock
        this.TotalCount = TotalCount
        this.isChecked = isChecked
    }
}