package com.example.tubes_ppb.ui.skins.allskins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tubes_ppb.data.SkinsRepository
import com.example.tubes_ppb.data.local.entity.SkinsEntity
import kotlinx.coroutines.launch

class SkinsViewModel(private val skinsRepository: SkinsRepository) : ViewModel() {
    fun getHeadlineSkins() = skinsRepository.getHeadlineSkins()

    fun getBookmarkedSkins() = skinsRepository.getBookmarkedSkins()

    fun saveSkins(skins: SkinsEntity) {
        viewModelScope.launch {
            skinsRepository.setBookmarkedSkins(skins, true)
        }
    }

    fun deleteSkins(skins: SkinsEntity) {
        viewModelScope.launch {
            skinsRepository.setBookmarkedSkins(skins, false)
        }
    }
}