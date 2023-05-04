package com.perfect.prodsuit.Model

data class ModelServiceAttended(val ID_ProductWiseServiceDetails: String, val SubProduct: String, var ID_Product: String, val ID_Services: String,
                                var Service: String, var ServiceCost: String, var ServiceTaxAmount: String, var ServiceNetAmount: String,
                                var Remarks: String, var isChecked: String, var isDelete: String, var isCheckedAdd: String,
                                var ServiceTypeId: String, var ServiceTypeName: String)

