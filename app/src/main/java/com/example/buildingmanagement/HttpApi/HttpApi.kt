package com.example.buildingmanagement.HttpApi

import android.util.Log
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class HttpApi {
    val TAG = "HttpApi"
    private var onRequestListener : OnRequestListener? = null
    var client: OkHttpClient? = null
    private val baseUrl = "https://asia-east2-ductech-cms.cloudfunctions.net/"

    init {
        client = OkHttpClient()
    }
    //所有社區
    fun GetAllProjectName(listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetAllProjectName")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()
                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    if (responseStr != null) {
                        onRequestListener?.onSuccess(responseStr)
                    }
//                    val itemList = JSONObject(responseStr)
                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }

            }
        })
    }

    //用戶資訊
    fun BindUserData(projectName: String, residentSerialNumber: String, phoneID: String, listener: OnRequestListener) {
        onRequestListener = listener
        val request: Request?
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("ResidentSerialNumber", residentSerialNumber)
        builder.add("PhoneID", phoneID)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "BindUserData")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            lateinit var bindUserDat: BindUserDat
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONObject(responseStr)
                    bindUserDat = BindUserDat(
                        houseNumber = itemList.getString("houseNumber"),
                        address = itemList.getString("address"),
                        floor = itemList.getString("floor"),
                        symbol = itemList.getString("symbol"),
                        residentSerialNumber = itemList.getString("residentSerialNumber"),
                        name = itemList.getString("name"),
                        gasNumber = itemList.getString("gasNumber"),
                        points = itemList.getString("points")
                    )
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //包裹資訊
    fun GetUserMail(projectName: String, houseSerialNumber: String, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSerialNumber", houseSerialNumber)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetUserMail")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            lateinit var getUserMail: GetUserMail
            var arrayList = arrayListOf<GetUserMail>()
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
//                    Log.d(TAG, "itemList: " + itemList )
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: item " + i + "-> " + itemList[i])
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("name"))
                        getUserMail = GetUserMail(
                            settingTime = itemList.getJSONObject(i).getString("settingTime"),
                            receiveTime = itemList.getJSONObject(i).getString("receiveTime"),
                            floor = itemList.getJSONObject(i).getString("floor"),
                            receiveManger = itemList.getJSONObject(i).getString("receiveManger"),
                            name =itemList.getJSONObject(i).getString("name"),
                            status = itemList.getJSONObject(i).getString("status"),
                            placement = itemList.getJSONObject(i).getString("placement"),
                            sort = itemList.getJSONObject(i).getString("sort"),
                            symbol = itemList.getJSONObject(i).getString("symbol"),
                            type = itemList.getJSONObject(i).getString("type"),
                            projectName = itemList.getJSONObject(i).getString("projectName"),
                            returnForm = itemList.getJSONObject(i).getString("returnForm"),
                            houseName = itemList.getJSONObject(i).getString("houseName"),
                            remarks = itemList.getJSONObject(i).getString("remarks"),
                            mailNumber= itemList.getJSONObject(i).getString("mailNumber"),
                        )
                        arrayList.add(getUserMail)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //行事曆資訊
    fun GetYearCalendar(projectName: String, Year: String,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("Year", Year)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetYearCalendar")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<GetYearCalendar>()
            lateinit var getYearCalendar: GetYearCalendar
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("title"))
                        getYearCalendar = GetYearCalendar(
                            calendarDate = itemList.getJSONObject(i).getString("calendarDate"),
                            settingTime = itemList.getJSONObject(i).getString("settingTime"),
                            title = itemList.getJSONObject(i).getString("title"),
                            place = itemList.getJSONObject(i).getString("place"),
                        )
                        arrayList.add(getYearCalendar)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //公告資訊
    fun GetUserAllAnnouncement(projectName: String ,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetUserAllAnnouncement")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<GetUserAllAnnouncement>()
            lateinit var getUserAllAnnouncement: GetUserAllAnnouncement
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("postTime"))
                        getUserAllAnnouncement = GetUserAllAnnouncement(
                            postTime = itemList.getJSONObject(i).getString("postTime"),
                            type = itemList.getJSONObject(i).getString("type"),
                            title = itemList.getJSONObject(i).getString("title"),
                            announcer = itemList.getJSONObject(i).getString("announcer"),
                            isTop = itemList.getJSONObject(i).getBoolean("isTop"),
                            content = itemList.getJSONObject(i).getString("content"),
                        )
                        arrayList.add(getUserAllAnnouncement)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //公設資訊
    fun UserGetAllAmenities(projectName: String,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetAllAmenities")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<UserGetAllAmenities>()
            lateinit var userGetAllAmenities: UserGetAllAmenities
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("amenitiesName"))
                        userGetAllAmenities = UserGetAllAmenities(
                            amenitiesName = itemList.getJSONObject(i).getString("amenitiesName"),
                            point = itemList.getJSONObject(i).getString("point"),
                            costMode = itemList.getJSONObject(i).getInt("costMode"),
                            reservationMode = itemList.getJSONObject(i).getInt("reservationMode"),
                            costUnit = itemList.getJSONObject(i).getInt("costUnit"),
                            timeLimit = itemList.getJSONObject(i).getInt("timeLimit"),
                            accommodate = itemList.getJSONObject(i).getInt("accommodate"),
                            content = itemList.getJSONObject(i).getString("content"),
                            isOpen = itemList.getJSONObject(i).getBoolean("isOpen"),
                            imageUrl = itemList.getJSONObject(i).getString("imageUrl"),
                            identify = itemList.getJSONObject(i).getString("identify")
                        )
                        arrayList.add(userGetAllAmenities)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //已預約時間人數
    fun UserGetDateReservation(projectName: String,AmenititesID: String,listener: OnRequestListener) {
        onRequestListener = listener
        val request: Request?
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("AmenititesID",AmenititesID)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetDateReservation")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<UserGetDateReservation>()
            lateinit var userGetDateReservation: UserGetDateReservation
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("amenitiesName"))
                        userGetDateReservation = UserGetDateReservation(
                            date = itemList.getJSONObject(i).getString("date"),
                            time = itemList.getJSONObject(i).getString("time"),
                            people = itemList.getJSONObject(i).getInt("people"),
                        )
                        arrayList.add(userGetDateReservation)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //用戶公設預約
    fun UserAddNewReservation(projectName: String, AmenitiesName: String, AmenitiesIdentify:String,
                              ReservationDate:String, ReservationTime:String, Name:String, HouseName:String,
                              Floor:String, Symbol:String, ResidentSerialNumber:String, ReservationNumber:Int,
                              Point:Int, Identify:String, Time:String,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("AmenititesID",AmenitiesName)
        builder.add("AmenitiesIdentify",AmenitiesIdentify)
        builder.add("ReservationDate",ReservationDate)
        builder.add("ReservationTime",ReservationTime)
        builder.add("Name",Name)
        builder.add("HouseName",HouseName)
        builder.add("Floor",Floor)
        builder.add("Symbol",Symbol)
        builder.add("ResidentSerialNumber",ResidentSerialNumber)
        builder.add("ReservationNumber","")
        builder.add("Point","")
        builder.add("Identify",Identify)
        builder.add("Time",Time)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetDateReservation")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            //            lateinit var userAddNewReservation: UserAddNewReservation
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()


                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    onRequestListener?.onSuccess(Any())
//                    val itemList = JSONArray(responseStr)
//                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //公設預約記錄
    fun GetUserALLRese(projectName: String, houseSerialNumber: String, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSerialNumber", houseSerialNumber)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetUserALLRese")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<GetUserALLRese>()
            lateinit var getUserALLRese: GetUserALLRese
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("reservationDate"))
//                        Log.d(TAG, "itemList: " + itemList)
                        getUserALLRese = GetUserALLRese(
                            reservationDate = itemList.getJSONObject(i).getString("reservationDate"),
                            enterTime = itemList.getJSONObject(i).getString("enterTime"),
                            leaveTime = itemList.getJSONObject(i).getString("leaveTime"),
                            amenitiesName = itemList.getJSONObject(i).getString("amenitiesName"),
                            reservationTime = itemList.getJSONObject(i).getString("reservationTime"),
                            name =itemList.getJSONObject(i).getString("name"),
                            residentSerialNumber = itemList.getJSONObject(i).getString("residentSerialNumber"),
                            reservationNumber = itemList.getJSONObject(i).getInt("reservationNumber"),
                            point = itemList.getJSONObject(i).getInt("point"),
                            sort = itemList.getJSONObject(i).getString("sort"),
                            identify = itemList.getJSONObject(i).getString("identify"),
                            amenitiesID = itemList.getJSONObject(i).getString("amenitiesID"),
                        )
                        arrayList.add(getUserALLRese)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //用戶取消公設預約
    fun UserDeleteReservaion(projectName: String, ResSN: String, Point:Int, Time:String, Identify:String,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("ResSN",ResSN)
        builder.add("Point","")
        builder.add("Time",Time)
        builder.add("Identify",Identify)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserDeleteReservaion")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            //lateinit var userDeleteReservaion: UserDeleteReservaion
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    onRequestListener?.onSuccess(Any())
//                    val itemList = JSONObject(responseStr)
//                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //點數紀錄_ResPointLog
    fun GetUserPointLog_ResPointLog(projectName: String, houseSerialNumber: String,  listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSerialNumber",houseSerialNumber)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetUserPointLog")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<GetUserPointLog_ResPointLog>()
            lateinit var getUserPointLog_ResPointLog: GetUserPointLog_ResPointLog
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONObject(responseStr)
                    getUserPointLog_ResPointLog = GetUserPointLog_ResPointLog(
                        amenitiesName =  itemList.getString("amenitiesName"),
                        time =  itemList.getString("time"),
                        reservationTime =  itemList.getString("reservationTime"),
                        point =  itemList.getInt("point"),
                        reservationNumber =  itemList.getInt("reservationNumber"),
                        isBook =  itemList.getBoolean("isBook"),
                        sort =  itemList.getString("sort"),
                        amenitiesID = itemList.getString("amenitiesID"),
                        logID = itemList.getString("logID"),
                    )
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }
    //點數紀錄_DepositPointLog
    fun GetUserPointLog_DepositPointLog(projectName: String, houseSerialNumber: String,  listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSerialNumber",houseSerialNumber)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "GetUserPointLog")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<GetUserPointLog_DepositPointLog>()
            lateinit var getUserPointLog_DepositPointLog: GetUserPointLog_DepositPointLog
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
                        getUserPointLog_DepositPointLog = GetUserPointLog_DepositPointLog(
                            manage = itemList.getJSONObject(i).getString("manage"),
                            point = itemList.getJSONObject(i).getInt("point"),
                            sort = itemList.getJSONObject(i).getString("sort"),
                            title = itemList.getJSONObject(i).getString("title"),
                            time = itemList.getJSONObject(i).getString("time"),
                            logID = itemList.getJSONObject(i).getString("logID"),
                        )
                        arrayList.add(getUserPointLog_DepositPointLog)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //意見留言資訊
    fun UserGetMessage(projectName: String, ResidentSN: String, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("ResidentSN", ResidentSN)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetMessage")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<UserGetMessage>()
            lateinit var userGetMessage: UserGetMessage
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                    Log.d(TAG, "itemList: " + itemList )
                        userGetMessage = UserGetMessage(
                            title = itemList.getJSONObject(i).getString("title"),
                            time = itemList.getJSONObject(i).getString("time"),
                            messageID = itemList.getJSONObject(i).getString("messageID"),
                            sort = itemList.getJSONObject(i).getString("sort"),
                            isUserRead = itemList.getJSONObject(i).getBoolean("isUserRead")
                        )
                        arrayList.add(userGetMessage)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //新增留言
    fun UserCreateMess(projectName: String, Time: String, Symbol:String,
                       Name:String, HouseName:String, HouseSerialNumber:String, Title:String,
                       Floor:String, MessageID:String, Content:String, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("Time",Time)
        builder.add("Symbol",Symbol)
        builder.add("Name",Name)
        builder.add("HouseName",HouseName)
        builder.add("HouseSerialNumber",HouseSerialNumber)
        builder.add("Title",Title)
        builder.add("Floor",Floor)
        builder.add("MessageID",MessageID)
        builder.add("Content",Content)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserCreateMess")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            //lateinit var userCreateMess: UserCreateMess
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    onRequestListener?.onSuccess(Any())
//                    val itemList = JSONObject(responseStr)
//                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //留言內容
    fun UserGetMessChat(projectName: String, MessageID: String, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("MessageID", MessageID)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetMessChat")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<UserGetMessChat>()
            lateinit var userGetMessChat: UserGetMessChat
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("isManager"))
//                        Log.d(TAG, "itemList: " + itemList)
                        userGetMessChat = UserGetMessChat(
                            content = itemList.getJSONObject(i).getString("content"),
                            messageTime = itemList.getJSONObject(i).getString("messageTime"),
//                            isManager = itemList.getJSONObject(i).getBoolean("isManager")
                        )
                        arrayList.add(userGetMessChat)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //新增留言內容
    fun SendMessage(projectName: String, MessageTime: String, MessageID:String, Content:String, IsManager:Boolean, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("MessageTime",MessageTime)
        builder.add("MessageID",MessageID)
        builder.add("Content",Content)
        builder.add("IsManager","")
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "SendMessage")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            //            lateinit var sendMessage: SendMessage
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    onRequestListener?.onSuccess(Any())
//                    val itemList = JSONObject(responseStr)
                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //當期瓦斯抄錶
    fun UserGetGas(projectName: String, HouseSN : String,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSN",HouseSN)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetGas")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            lateinit var userGetGas: UserGetGas
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONObject(responseStr)
                    userGetGas = UserGetGas(
                        openTime = itemList.getString("openTime"),
                        closeTime = itemList.getString("closeTime"),
                        identity = itemList.getString("identity"),
//                        degree = itemList.getString("degree"),
                        isOpen = itemList.getBoolean("isOpen")
                    )
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //上傳瓦斯度數
    fun RegisterGas(projectName: String, HouseSN: String, BookTime:String, Booker:String, Degree:String, GasID:String,  listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSN",HouseSN)
        builder.add("BookTime",BookTime)
        builder.add("Booker",Booker)
        builder.add("Degree",Degree)
        builder.add("GasID",GasID)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "RegisterGas")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            //lateinit var registerGas: RegisterGas
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    onRequestListener?.onSuccess(Any())
//                    val itemList = JSONObject(responseStr)
                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //瓦斯抄錶紀錄
    fun UserGetGasLog(projectName: String, HouseSN: String,listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("HouseSN", HouseSN)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetGasLog")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<UserGetGasLog>()
            lateinit var userGetGasLog: UserGetGasLog
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
//                        Log.d(TAG, "onResponse: object -> " + itemList.getJSONObject(i).getString("degree"))
                        userGetGasLog = UserGetGasLog(
                            degree = itemList.getJSONObject(i).getString("degree"),
                            openTime = itemList.getJSONObject(i).getString("openTime"),
                            closeTime = itemList.getJSONObject(i).getString("closeTime")
                        )
                        arrayList.add(userGetGasLog)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //當期管理費
    fun UserGetMagFee(listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UserGetMagFee")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }
            var arrayList = arrayListOf<UserGetMagFee>()
            lateinit var userGetMagFee: UserGetMagFee
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    val itemList = JSONArray(responseStr)
                    for (i in 0 until itemList.length()) {
                        userGetMagFee = UserGetMagFee(
                            title = itemList.getJSONObject(i).getString("title"),
                            closeTime = itemList.getJSONObject(i).getString("closeTime"),
                            identify = itemList.getJSONObject(i).getString("identify"),
                            isOpen = itemList.getJSONObject(i).getBoolean("isOpen"),
                            magFee = itemList.getJSONObject(i).getString("magFee"),
                            oneBarCode = itemList.getJSONObject(i).getString("oneBarCode"),
                            twoBarCode = itemList.getJSONObject(i).getString("twoBarCode"),
                            threeBarCode = itemList.getJSONObject(i).getString("threeBarCode"),
                            isPay = itemList.getJSONObject(i).getBoolean("isPay")
                        )
                        arrayList.add(userGetMagFee)
                    }
                    // TODO("response to json object")
                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    //變更用戶名稱
    fun UpdateUserName(projectName: String, ResidentSerialNumber: String, Name:String, listener: OnRequestListener) {
        onRequestListener = listener
        var request: Request? = null
        val builder = FormBody.Builder()
        builder.add("ProjectName", projectName)
        builder.add("ResidentSerialNumber",ResidentSerialNumber)
        builder.add("Name",Name)
        val formBody = builder.build()
        request = Request.Builder()
            .method("POST",formBody)
            .url(baseUrl + "UpdateUserName")
            .build()
        client?.newCall(request)?.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.d(TAG, "onFailure: " + e)
                onRequestListener?.onError()
            }

            //lateinit var updateUserName: UpdateUserName
            override fun onResponse(call: Call, response: Response) {
                val responseStr = response.body()?.string()

                if (responseStr == "null" || responseStr == "Error"){
                    onRequestListener?.onError()
                }else{
                    onRequestListener?.onSuccess(Any())
//                    val itemList = JSONObject(responseStr)
                    // TODO("response to json object")
//                    onRequestListener?.onSuccess(itemList)
//                    Log.d(TAG, "" + itemList)
                }
            }
        })
    }

    interface OnRequestListener {
        fun onSuccess(result: Any)
        fun onError()
    }

}