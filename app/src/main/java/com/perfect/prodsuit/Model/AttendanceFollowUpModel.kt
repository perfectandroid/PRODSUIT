package com.perfect.prodsuit.Model

class AttendanceFollowUpModel{
    var ID_Employee:String = ""
    var EmployeeName:String = ""
    var ID_CSAEmployeeType:String = ""
    var Attend:String = ""
    var DepartmentID:String = ""
    var Department:String = ""
    var Role:String = ""
    var Designation:String = ""
    var isChecked:String = ""

    constructor(
        ID_Employee: String,
        EmployeeName: String,
        ID_CSAEmployeeType: String,
        Attend: String,
        DepartmentID: String,
        Department: String,
        Role: String,
        Designation: String,
        isChecked: String
    ) {
        this.ID_Employee = ID_Employee
        this.EmployeeName = EmployeeName
        this.ID_CSAEmployeeType = ID_CSAEmployeeType
        this.Attend = Attend
        this.DepartmentID = DepartmentID
        this.Department = Department
        this.Role = Role
        this.Designation = Designation
        this.isChecked = isChecked
    }
}