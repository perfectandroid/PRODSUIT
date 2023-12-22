package com.perfect.prodsuit.Model

data class ServiceTab3MainModel(var FK_Product:String, var Product:String, var main:String, var sub:String,
                                var ID_Service:String,var Service:String,var ID_ServiceType:String,var ServiceType:String,
                                var ServiceCost:String,var TaxAmount:String,var NetAmount:String,var Remarks:String,var isChecked:Boolean,
                                var FK_TaxGroup:String, var TaxPercentage:String, var ServiceChargeIncludeTax:Boolean,
                                var ID_Category:String,var ID_SubCategory:String,var ID_Brand:String,var ID_Complaint:String)

//var ID_Category : String,var CategoryName : String,var ID_SubCategory : String,var SubCategory : String,var ID_Brand : String,
//var Brand : String,var ID_Complaint : String,var Complaint : String
