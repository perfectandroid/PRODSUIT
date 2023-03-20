package com.perfect.prodsuit.Model

class ServiceCostModelMain{
    var Components:String = ""
    var ID_ProductWiseServiceDetails:String = ""
    var SubProduct:String = ""
    var ID_Product:String = ""
    var ID_Services:String = ""
    var Service:String = ""
    var ServiceCost:String = ""
    var ServiceTaxAmount:String = ""
    var ServiceNetAmount:String = ""
    var Remarks:String = ""
    var isChecked:String = ""
    var serviceType:String = ""

    constructor(
        Components: String,
        ID_ProductWiseServiceDetails: String,
        SubProduct: String,
        ID_Product: String,
        ID_Services: String,
        Service: String,
        ServiceCost: String,
        ServiceTaxAmount: String,
        ServiceNetAmount: String,
        Remarks: String,
        isChecked: String,
        serviceType: String
    ) {
        this.Components = Components
        this.ID_ProductWiseServiceDetails = ID_ProductWiseServiceDetails
        this.SubProduct = SubProduct
        this.ID_Product = ID_Product
        this.ID_Services = ID_Services
        this.Service = Service
        this.ServiceCost = ServiceCost
        this.ServiceTaxAmount = ServiceTaxAmount
        this.ServiceNetAmount = ServiceNetAmount
        this.Remarks = Remarks
        this.isChecked = isChecked
        this.serviceType = serviceType
    }
//    serviceCostArrayList.add(pos,
//    ServiceCostModelMain(jsonObject!!.getString("Components"),
//    jsonObject!!.getString("ID_ProductWiseServiceDetails"),
//    jsonObject!!.getString("SubProduct"),
//    jsonObject!!.getString("ID_Product"),
//    jsonObject!!.getString("ID_Services"),
//    jsonObject!!.getString("Service"),
//    jsonObject!!.getString("ServiceCost"),
//    jsonObject!!.getString("ServiceTaxAmount"),
//    jsonObject!!.getString("ServiceNetAmount"),
//    jsonObject!!.getString("Remarks"),
//    jsonObject!!.getString("isChecked"),
//    jsonObject!!.getString("serviceType"))
//    )

}