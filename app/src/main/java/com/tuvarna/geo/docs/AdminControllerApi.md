# AdminControllerApi

All URIs are relative to *http://localhost:8080/api.tuvarna.geo.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**blockUser**](AdminControllerApi.md#blockUser) | **PUT** /admin/users/{email}/block/{blocked} | Block user
[**getLogs**](AdminControllerApi.md#getLogs) | **GET** /admin/fetch/logs/{userType} | Retrieve user logs
[**getUsers**](AdminControllerApi.md#getUsers) | **GET** /admin/fetch/users/{userType} | Get all users
[**promoteUser**](AdminControllerApi.md#promoteUser) | **PUT** /admin/users/{email}/promote/{userType} | Promote or denote user to admin
[**saveLog**](AdminControllerApi.md#saveLog) | **POST** /admin/save/log/{userType} | Save log


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
> RestApiResponseListLoggerDTO getLogs(userType)

Retrieve user logs

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
val userType : kotlin.String = userType_example // kotlin.String | 
try {
    val result : RestApiResponseListLoggerDTO = apiInstance.getLogs(userType)
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

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userType** | **kotlin.String**|  |

### Return type

[**RestApiResponseListLoggerDTO**](RestApiResponseListLoggerDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="getUsers"></a>
# **getUsers**
> RestApiResponseListUserInfoDTO getUsers(userType)

Get all users

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
val userType : kotlin.String = userType_example // kotlin.String | 
try {
    val result : RestApiResponseListUserInfoDTO = apiInstance.getUsers(userType)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminControllerApi#getUsers")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminControllerApi#getUsers")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **userType** | **kotlin.String**|  |

### Return type

[**RestApiResponseListUserInfoDTO**](RestApiResponseListUserInfoDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="promoteUser"></a>
# **promoteUser**
> RestApiResponseVoid promoteUser(email, userType)

Promote or denote user to admin

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
val email : kotlin.String = email_example // kotlin.String | 
val userType : kotlin.String = userType_example // kotlin.String | 
try {
    val result : RestApiResponseVoid = apiInstance.promoteUser(email, userType)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AdminControllerApi#promoteUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AdminControllerApi#promoteUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **email** | **kotlin.String**|  |
 **userType** | **kotlin.String**|  |

### Return type

[**RestApiResponseVoid**](RestApiResponseVoid.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined

<a id="saveLog"></a>
# **saveLog**
> RestApiResponseVoid saveLog(userType, loggerDTO)

Save log

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AdminControllerApi()
val userType : kotlin.String = userType_example // kotlin.String | 
val loggerDTO : LoggerDTO =  // LoggerDTO | 
try {
    val result : RestApiResponseVoid = apiInstance.saveLog(userType, loggerDTO)
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
 **userType** | **kotlin.String**|  |
 **loggerDTO** | [**LoggerDTO**](LoggerDTO.md)|  |

### Return type

[**RestApiResponseVoid**](RestApiResponseVoid.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

