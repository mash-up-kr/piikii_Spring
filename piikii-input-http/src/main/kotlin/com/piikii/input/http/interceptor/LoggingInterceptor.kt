package com.piikii.input.http.interceptor

import com.fasterxml.jackson.databind.ObjectMapper
import com.piikii.common.logutil.HookLogger
import com.piikii.common.logutil.SystemLogger.logger
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper

@Component
class LoggingInterceptor(
    private val hookLogger: HookLogger,
    private val objectMapper: ObjectMapper,
) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
    ): Boolean {
        val wrappedRequest = ContentCachingRequestWrapper(request)
        request.setAttribute(WRAPPED_REQUEST_KEY, wrappedRequest)

        val wrappedResponse = ContentCachingResponseWrapper(response)
        request.setAttribute(WRAPPED_RESPONSE_KEY, wrappedResponse)

        return true
    }

    @Async("hookLoggerTaskExecutor")
    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?,
    ) {
        val wrappedResponse = response as ContentCachingResponseWrapper
        val responseBody = wrappedResponse.contentAsByteArray.toString(Charsets.UTF_8)
        val errorResponse = parseErrorResponse(responseBody)

        val message = errorResponse?.get("message")?.asText().orEmpty()
        val cause = errorResponse?.get("cause")?.asText().orEmpty()

        val logMessage = createLogMessage(request, message, cause)
        logResponse(wrappedResponse.status, logMessage)
        wrappedResponse.copyBodyToResponse()
    }

    private fun parseErrorResponse(responseBody: String) =
        runCatching {
            objectMapper.readTree(responseBody)
        }.getOrNull()

    private fun createLogMessage(
        request: HttpServletRequest,
        message: String,
        cause: String,
    ): String {
        val logNode =
            objectMapper.createObjectNode().apply {
                put("uri", request.requestURI)
                put("method", request.method)
                put("queryString", request.queryString)
                put("message", message)
                put("cause", cause)
            }
        return objectMapper.writeValueAsString(logNode)
    }

    private fun logResponse(
        status: Int,
        logMessage: String,
    ) {
        when (status) {
            in 400..499 -> logger.warn(logMessage)
            500 -> {
                logger.error(logMessage)
                hookLogger.send(logMessage)
            }
        }
    }

    companion object {
        private const val WRAPPED_REQUEST_KEY = "wrappedRequest"
        private const val WRAPPED_RESPONSE_KEY = "wrappedRequest"
    }
}
