package com.techquantum.template.localdb.base

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.techquantum.template.common.dispatcher.DispatcherProvider
import com.techquantum.template.common.ext.safeRun
import com.techquantum.template.common.result.AppResult
import com.techquantum.template.localdb.error.DbErrorMapper

interface LocalDataSource<T> {
    suspend fun insert(item: T): AppResult<Long>
    suspend fun insertAll(items: List<T>): AppResult<List<Long>>
    suspend fun update(item: T): AppResult<Int>
    suspend fun delete(item: T): AppResult<Int>
}

internal class LocalDataSourceImpl<T : Any>(
    private val dao: BaseDao<T>,
    private val database: RoomDatabase,
    private val dispatcherProvider: DispatcherProvider,
) : LocalDataSource<T> {

    override suspend fun insert(item: T): AppResult<Long> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = DbErrorMapper::map,
    ) {
        dao.insert(item)
    }

    override suspend fun insertAll(items: List<T>): AppResult<List<Long>> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = DbErrorMapper::map,
    ) {
        database.withTransaction {
            dao.insertAll(items)
        }
    }

    override suspend fun update(item: T): AppResult<Int> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = DbErrorMapper::map,
    ) {
        dao.update(item)
    }

    override suspend fun delete(item: T): AppResult<Int> = safeRun(
        dispatcher = dispatcherProvider.io,
        errorMapper = DbErrorMapper::map,
    ) {
        dao.delete(item)
    }
}
