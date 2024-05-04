# RiskControllerApi

All URIs are relative to *http://localhost:8080/api.tuvarna.geo.com/v1*

 Method                                                  | HTTP request                | Description
---------------------------------------------------------|-----------------------------|---------------------
 [**getEarthquake**](RiskControllerApi.md#getEarthquake) | **POST** /danger/earthquake | Retrieve earthquake
 [**getSoil**](RiskControllerApi.md#getSoil)             | **POST** /risk/soil         | Retrieve soil type

<a id="getEarthquake"></a>

# **getEarthquake**

> RestApiResponseEarthquake getEarthquake(dangerDTO)

Retrieve earthquake

### Example

```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = RiskControllerApi()
val dangerDTO: RiskDTO =  // RiskDTO |
    try {
        val result: RestApiResponseEarthquake = apiInstance.getEarthquake(dangerDTO)
        println(result)
    } catch (e: ClientException) {
        println("4xx response calling RiskControllerApi#getEarthquake")
        e.printStackTrace()
    } catch (e: ServerException) {
        println("5xx response calling RiskControllerApi#getEarthquake")
        e.printStackTrace()
    }
```

### Parameters

 Name          | Type                      | Description | Notes
---------------|---------------------------|-------------|-------
 **dangerDTO** | [**RiskDTO**](RiskDTO.md) |             |

### Return type

[**RestApiResponseEarthquake**](RestApiResponseEarthquake.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

<a id="getSoil"></a>

# **getSoil**

> RestApiResponseSoil getSoil(dangerDTO)

Retrieve soil type

### Example

```kotlin
// Import classes:
//import com.tuvarna.geo.rest_api.infrastructure.*
//import com.tuvarna.geo.rest_api.models.*

val apiInstance = RiskControllerApi()
val dangerDTO: RiskDTO =  // RiskDTO |
    try {
        val result: RestApiResponseSoil = apiInstance.getSoil(dangerDTO)
        println(result)
    } catch (e: ClientException) {
        println("4xx response calling RiskControllerApi#getSoil")
        e.printStackTrace()
    } catch (e: ServerException) {
        println("5xx response calling RiskControllerApi#getSoil")
        e.printStackTrace()
    }
```

### Parameters

 Name          | Type                      | Description | Notes
---------------|---------------------------|-------------|-------
 **dangerDTO** | [**RiskDTO**](RiskDTO.md) |             |

### Return type

[**RestApiResponseSoil**](RestApiResponseSoil.md)

### Authorization

No authorization required

### HTTP request headers

- **Content-Type**: application/json
- **Accept**: Not defined

