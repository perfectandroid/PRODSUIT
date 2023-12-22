package com.perfect.prodsuit.Model



data class ServiceDetailsFullListModel(val isMaster: String, val FK_Category: String, val MasterProduct: String, val FK_Product: String, val Product: String, val SLNo: String, val BindProduct: String,
                                       val ComplaintProduct: String, val Warranty: String, val ServiceWarrantyExpireDate: String, val ReplacementWarrantyExpireDate: String,
                                       val ID_CustomerWiseProductDetails: String, val ServiceWarrantyExpired: String, val ReplacementWarrantyExpired: String,
                                       var ID_ComplaintList: String, var ComplaintName: String, var Description: String, var isChekecd: Boolean,var SearchSerialNo: String,
                                       var ID_Category : String,var CategoryName : String,var ID_SubCategory : String,var SubCategory : String,var ID_Brand : String,
                                       var Brand : String,var ID_Complaint : String,var Complaint : String)


