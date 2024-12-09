package com.dbng.domain.repository

import com.dbng.core.domain.Resource
import com.dbng.domain.model.MenuItem

interface MenuRepository {
    suspend fun fetchMenuItems(from: Int, size: Int): Resource<List<MenuItem>>
    fun getTotalItemCount(): Int
    suspend fun fetchMenuItemsMoreInfo(itemID: Int): Resource<MenuItem>
}