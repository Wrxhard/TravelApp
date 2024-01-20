package com.wrxhard.ftravel.util

sealed class Event<out T> {
    class Success<T>(val result: T): Event<T>()
    class Failure(val error: String): Event<Nothing>()
    object Loading : Event<Nothing>()
    object Empty : Event<Nothing>()
}