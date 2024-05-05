# AdminControllerApi

All URIs are relative to *http://localhost:8080/api.tuvarna.geo.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**blockUser**](AdminControllerApi.md#blockUser) | **PUT** /admin/users/{email}/block/{blocked} | Block user
[**getLogs**](AdminControllerApi.md#getLogs) | **GET** /admin/fetch/logs | Retrieve user logs
[**saveLog**](AdminControllerApi.md#saveLog) | **POST** /admin/save/log | Save log


<a id="blockUser"></a>
# **blockUser**
> RestApiResponseVoid blockUser(email, blocked)

Block user

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
val email : kotlin.String = email_example // kotlin.String | 
val blocked : kotlin.Boolean = true // kotlin.Boolean | 
try {
    val result : RestApiResponseVoid = apiInstance.blockUser(email, blocked)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminControllerApi#blockUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminControllerApi#blockUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **kotlin.String**|  |
 **blocked** | **kotlin.Boolean**|  |

### Return type

[**RestApiResponseVoid**](RestApiResponseVoid.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="getLogs"></a>
# **getLogs**
> RestApiResponseListLoggerDTO getLogs()

Retrieve user logs

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
try {
    val result : RestApiResponseListLoggerDTO = apiInstance.getLogs()
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminControllerApi#getLogs")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminControllerApi#getLogs")
    e.printStackTrace()
}
```

### Parameters
This endpoint does not need any parameter.

### Return type

[**RestApiResponseListLoggerDTO**](RestApiResponseListLoggerDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="saveLog"></a>
# **saveLog**
> RestApiResponseVoid saveLog(loggerDTO)

Save log

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
val loggerDTO : LoggerDTO =  // LoggerDTO | 
try {
    val result : RestApiResponseVoid = apiInstance.saveLog(loggerDTO)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminControllerApi#saveLog")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminControllerApi#saveLog")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **loggerDTO** | [**LoggerDTO**](LoggerDTO.md)|  |

### Return type

[**RestApiResponseVoid**](RestApiResponseVoid.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

