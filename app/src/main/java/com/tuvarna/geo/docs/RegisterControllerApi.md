# RegisterControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](RegisterControllerApi.md#create) | **POST** /register | Register a new user


<a id="create"></a>
# **create**
> RestApiResponseVoid create(registerUserDTO)

Register a new user

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = RegisterControllerApi()
val registerUserDTO : RegisterUserDTO =  // RegisterUserDTO | 
try {
    val result : RestApiResponseVoid = apiInstance.create(registerUserDTO)
    println(result)
} catch (e: ClientException) {
    println("4xx response calling RegisterControllerApi#create")
    e.printStackTrace()
} catch (e: ServerException) {
    println("5xx response calling RegisterControllerApi#create")
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

