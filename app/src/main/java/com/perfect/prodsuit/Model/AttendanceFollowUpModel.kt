package com.perfect.prodsuit.Model

class AttendanceFollowUpModel{
    var name:String = ""
    var department:String = ""
    var role:String = ""
    var isChecked:String = ""

    constructor(
        name: String,
        department: String,
        role: String,
        isChecked: String
    ) {
        this.name = name
        this.department = department
        this.role = role
        this.isChecked = isChecked
    }
}