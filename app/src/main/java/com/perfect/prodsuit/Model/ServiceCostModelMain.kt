package com.perfect.prodsuit.Model

class ServiceCostModelMain{
    var Components:String = ""
    var ServiceName:String = ""
    var serviceCost:String = ""
    var remark:String = ""
    var serviceType:String = ""
    var taxAmount:String = ""
    var netAmount:String = ""
    var isChecked:String = ""

    constructor(
        Components: String,
        ServiceName: String,
        serviceCost: String,
        serviceType: String,
        taxAmount: String,
        netAmount: String,
        remark: String,
        isChecked: String
    ) {
        this.Components = Components
        this.ServiceName = ServiceName
        this.serviceCost = serviceCost
        this.serviceType = serviceType
        this.taxAmount = taxAmount
        this.netAmount = netAmount
        this.remark = remark
        this.isChecked = isChecked
    }

}