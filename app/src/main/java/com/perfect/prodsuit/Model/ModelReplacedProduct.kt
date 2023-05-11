package com.perfect.prodsuit.Model

data class ModelReplacedProduct(
    var isChecked: String, val ID_OLD_Product: String, var OLD_Product: String,
    var SPDOldQuantity: String, var Amount: String, var ID_Mode: String, var ModeName: String, var ID_Product: String,
    var Product: String, var Replaced_Qty: String, var ReplaceAmount: String, var Remarks: String, var isAdded: String,
    var boolSecAmnt : Boolean, var boolRepProd : Boolean, var boolRepQty : Boolean, var boolRepAmnt : Boolean,
    var MRPs: String, var StockId: String)
