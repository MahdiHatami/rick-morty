package com.metis.rickmorty.data.model

/**
 * A sealed class is an abstract class with a restricted class hierarchy.
 * Classes that inherit from it have to be in the same file as the sealed class.
 * This provides more control over the inheritance.
 */
sealed class ApiResult<out R> {

    data class Success<R>(val data: R) : ApiResult<R>()

    sealed class Error : ApiResult<Nothing>() {

        data class ServerError(val reason: Reason) : Error() {

            enum class Reason {
                UNAUTHORIZED,
                BAD_REQUEST,
                FORBIDDEN,
                SERVICE_UNAVAILABLE,
                SERVER_UNREACHABLE,
                UNKNOWN
            }
        }

        data class UnknownError(val exception: Throwable) : Error()
    }
}
