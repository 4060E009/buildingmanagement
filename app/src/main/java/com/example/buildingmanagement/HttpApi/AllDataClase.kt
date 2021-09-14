package com.example.buildingmanagement.HttpApi

data class BindUserData(
    val houseNumber : String,
    val address : String,
    val floor : String,
    val symbol : String,
    val residentSerialNumber : String,
    val name : String,
    val gasNumber : String,
    val points : String
)

data class GetUserMail(
    val settingTime : String,
    val receiveTime : String,
    val floor : String,
    val receiveManger : String,
    val name : String,
    val status : String,
    val placement : String,
    val sort : String,
    val symbol : String,
    val type : String,
    val projectName : String,
    val returnForm : String,
    val houseName : String,
    val remarks : String,
    val mailNumber : String
)


data class GetYearCalendar(
    val calendarDate : String,
    val settingTime : String,
    val title : String,
    val place : String
)

data class GetUserAllAnnouncement(
    val postTime : String,
    val type : String,
    val title : String,
    val announcer : String,
    val isTop : Boolean,
    val content : String,
//    val fileName : [String],
//    val fileUrl : [String]
)
data class UserGetAllAmenities(
    val amenitiesName : String,
    val point : String,
//    val normalDays : [String],
//    val weekDays : [String],
//    val closeDays : [Int],
    val costMode : Int,
    val reservationMode : Int,
    val costUnit : Int,
    val timeLimit : Int,
    val accommodate : Int,
    val content : String,
    val isOpen : Boolean,
    val imageUrl : String,
    val identify : String

)

data class UserGetDateReservation(
    val date : String,
    val time : String,
    val people : Int
)

data class GetUserALLRese(
    val reservationDate : String,
    val enterTime : String,
    val leaveTime : String,
    val amenitiesName : String,
    val reservationTime : String,
    val name : String,
    val residentSerialNumber : String,
    val reservationNumber : Int,
    val point : Int,
    val sort : String,
    val identify : String,
    val amenitiesID : String
)

data class GetUserPointLog_ResPointLog(

    val amenitiesName : String,
    val time : String,
    val reservationTime : String,
    val point : Int,
    val reservationNumber : Int,
    val isBook : Boolean,
    val sort : String,
    val amenitiesID : String,
    val logID : String
)
data class GetUserPointLog_DepositPointLog(
    val manage : String,
    val point : Int,
    val sort : String,
    val title : String,
    val time : String,
    val logID : String
)

data class UserGetMessage(
    val title : String,
    val time : String,
    val messageID : String,
    val sort : String,
    val isUserRead : Boolean
)

data class UserGetMessChat(
    val content : String,
    val messageTime : String,
//    val isManager : Boolean
)

data class UserGetGas(
    val openTime : String,
    val closeTime : String,
    val identity : String,
//    val degree : String,
    val isOpen : Boolean
)

data class UserGetGasLog(
    val degree : String,
    val openTime : String,
    val closeTime : String
)

data class UserGetMagFee(
    val title : String,
    val closeTime : String,
    val identify : String,
    val isOpen : Boolean,
    val magFee : String,
    val oneBarCode : String,
    val twoBarCode : String,
    val threeBarCode : String,
    val isPay : Boolean
)