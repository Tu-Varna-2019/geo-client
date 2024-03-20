# LoginControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**authenticateUser**](LoginControllerApi.md#authenticateUser) | **POST** /login | Logging on a new user


<a id="authenticateUser"></a>
# **authenticateUser**
> RestApiResponseVoid authenticateUser(loginUserDTO)

Logging on a new user

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = LoginControllerApi()
val loginUserDTO : LoginUserDTO =  // LoginUserDTO | 
try {
    val result : RestApiResponseVoid = apiInstance.authenticateUser(loginUserDTO)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling LoginControllerApi#authenticateUser")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling LoginControllerApi#authenticateUser")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **loginUserDTO** | [**LoginUserDTO**](LoginUserDTO.md)|  |

### Return type

[**RestApiResponseVoid**](RestApiResponseVoid.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

