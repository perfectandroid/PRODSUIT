package com.perfect.prodsuit.Model

data class ActionTakenMainModel(var FK_Product:String, var Product:String, var actionName:String, var ID_Action:String,
                                var Customer_note:String, var Employee_note:String,var ProvideStandBy:Boolean,var securityAmount:String,
                                var leadAction:String,var buyBackAmount:String,var leadActionStatus:String,var ID_CustomerWiseProductDetails:String,var actionStatus:String,
                                var ID_leadAction:String,var ID_ActionType:String,var ActionType:String,var ActionTypeMode:String,var FollowupDate:String,
                                var ID_Emplloyee:String,var EmplloyeeName:String)

// leadActionStatus = ID_Next Action
