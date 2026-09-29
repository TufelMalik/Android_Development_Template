package com.techquantum.template.common.mapper

interface Mapper<in From, out To> {
    fun map(from: From): To
}

fun <F, T> List<F>.mapAll(mapper: Mapper<F, T>): List<T> = map { mapper.map(it) }
