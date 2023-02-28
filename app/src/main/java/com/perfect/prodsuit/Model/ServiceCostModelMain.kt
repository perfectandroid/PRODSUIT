package com.perfect.prodsuit.Model

class ServiceCostModelMain{
    var Components:String = ""
    var ServiceName:String = ""
    var serviceCost:String = ""
    var remark:String = ""
    var serviceType:String = ""
    var isChecked:String = ""

    constructor(
        Components: String,
        ServiceName: String,
        serviceCost: String,
        serviceType: String,
        remark: String,
        isChecked: String
    ) {
        this.Components = Components
        this.ServiceName = ServiceName
        this.serviceCost = serviceCost
        this.serviceType = serviceType
        this.remark = remark
        this.isChecked = isChecked
    }

}