package com.perfect.prodsuit.Model

data class AddServiceDetailMode(var ID_Services:String, var Service:String, var FK_TaxGroup:String, var TaxPercentage:String, var ServiceChargeIncludeTax:Boolean, var isChecked:Boolean)
