@file:Suppress("ArrayInDataClass", "EnumEntryName", "RemoveRedundantQualifierName", "UnusedImport")

package com.tuvarna.geo.controller.apis

import com.tuvarna.geo.controller.models.RestApiResponseVoid
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class AdminControllerApiTest : ShouldSpec() {
  init {
    val apiInstance = AdminControllerApi()

    should("test blockUser") {
      val email: kotlin.String = "q"
      val blocked: kotlin.Boolean = true // kotlin.Boolean |
      val result: RestApiResponseVoid = apiInstance.blockUser(email, blocked)
      result shouldBe (RestApiResponseVoid(message = "User blocked!", status = 200))
    }
    //
    //    should("test getLogs") {
    //      val userType: kotlin.String = "customer"
    //      val result: RestApiResponseListLoggerDTO = apiInstance.getLogs(userType)
    //      result shouldBe ("TODO")
    //    }
    //
    //    should("test getUsers") {
    //      val userType: kotlin.String = "customer"
    //      val result: RestApiResponseListUserInfoDTO = apiInstance.getUsers(userType)
    //      result shouldBe ("TODO")
    //    }
    //
    //    should("test promoteUser") {
    //      val email: kotlin.String = "test@test.com"
    //      val userType: kotlin.String = "customer"
    //      val result: RestApiResponseVoid = apiInstance.promoteUser(email, userType)
    //      result shouldBe ("TODO")
    //    }
    //
    //    should("test saveLog") {
    //      val userType: kotlin.String = "customer"
    //      val loggerDTO: LoggerDTO =
    //        LoggerDTO("username", "event", "1.1.1.1", "24.24.24.24") // LoggerDTO |
    //      val result: RestApiResponseVoid = apiInstance.saveLog(userType, loggerDTO)
    //      result shouldBe ("TODO")
    //    }
  }
}
