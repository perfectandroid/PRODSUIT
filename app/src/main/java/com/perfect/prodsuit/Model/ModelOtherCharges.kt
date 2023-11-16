package com.perfect.prodsuit.Model

data class ModelOtherCharges(var ID_OtherChargeType:String, var OctyName:String, var OctyTransTypeActive:String, var OctyTransType:String,
                             var FK_TaxGroup:String, var OctyAmount:String, var OctyTaxAmount:String,var OctyIncludeTaxAmount:Boolean,
                             var ID_TransType:String,var TransType_Name:String,var isCalculate:Boolean)

