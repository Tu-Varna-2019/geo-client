# RegisterControllerApi

All URIs are relative to *http://localhost:8080*

Method | HTTP request | Description
------------- | ------------- | -------------
[**create**](RegisterControllerApi.md#create) | **POST** /register | Register a new user


<a id="create"></a>
# **create**
> kotlin.String create(userDTO)

Register a new user

### Example
```kotlin
// Import classes:
//import com.tuvarna.geo.infrastructure.*
//import com.tuvarna.geo.models.*

val apiInstance = RegisterControllerApi()
val userDTO : UserDTO =  // UserDTO | 
try {
    val result : kotlin.String = apiInstance.create(userDTO)
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
 **userDTO** | [**UserDTO**](UserDTO.md)|  |

### Return type

**kotlin.String**

### Authorization

No authorization required

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined

