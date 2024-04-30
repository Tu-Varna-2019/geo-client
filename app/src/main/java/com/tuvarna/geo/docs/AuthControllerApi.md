# AuthControllerApi

All URIs are relative to *http://localhost:8080/api.tuvarna.geo.com/v1*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](AuthControllerApi.md#create) | **POST** /auth/register | Register a new user
[**login**](AuthControllerApi.md#login) | **POST** /auth/login | Logging on a new user


<a id="create"></a>
# **create**
> RestApiResponseVoid create(registerUserDTO)

Register a new user

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AuthControllerApi()
val registerUserDTO : RegisterUserDTO =  // RegisterUserDTO | 
try {
    val result : RestApiResponseVoid = apiInstance.create(registerUserDTO)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthControllerApi#create")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthControllerApi#create")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **registerUserDTO** | [**RegisterUserDTO**](RegisterUserDTO.md)|  |

### Return type

[**RestApiResponseVoid**](RestApiResponseVoid.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

<a id="login"></a>
# **login**
> RestApiResponseLoggedInUserDTO login(loginUserDTO)

Logging on a new user

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = AuthControllerApi()
val loginUserDTO : LoginUserDTO =  // LoginUserDTO | 
try {
    val result : RestApiResponseLoggedInUserDTO = apiInstance.login(loginUserDTO)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling AuthControllerApi#login")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling AuthControllerApi#login")
    e.printStackTrace()
}
```

### Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **loginUserDTO** | [**LoginUserDTO**](LoginUserDTO.md)|  |

### Return type

[**RestApiResponseLoggedInUserDTO**](RestApiResponseLoggedInUserDTO.md)

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

