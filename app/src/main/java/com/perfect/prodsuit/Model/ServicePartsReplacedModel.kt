package com.perfect.prodsuit.Model

data class ServicePartsReplacedModel(val ID_MasterProduct: String, val MainProduct: String, val ID_Product: String, val Componant: String, var Quantity: String,
                                     var WarrantyMode: String,
                                     var WarrantyName: String, val ProductAmount: String, var ReplceMode: String, var ReplceName: String, val FK_Stock: String, var isChecked: String)
