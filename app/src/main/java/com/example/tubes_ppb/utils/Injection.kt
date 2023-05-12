package com.example.tubes_ppb.utils

import android.content.Context
import com.example.tubes_ppb.data.SkinsRepository
import com.example.tubes_ppb.data.local.room.SkinsDatabase
import com.example.tubes_ppb.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context) : SkinsRepository {
        val apiService = ApiConfig.getApiService()
        val database = SkinsDatabase.getInstance(context)
        val dao = database.skinsDao()
        val appExecutors = AppExecutors()
        return SkinsRepository.getInstance(apiService, dao, appExecutors)
    }
}