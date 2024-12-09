package com.dbng.domain.usecase

import com.dbng.core.domain.Resource
import com.dbng.domain.model.MenuItem
import com.dbng.domain.repository.MenuRepository
import javax.inject.Inject

class  MenuItemMoreInfoUseCase @Inject constructor(
    private val repository : MenuRepository
) {
    suspend operator fun invoke(itemID:Int): Resource<MenuItem> {
        return repository.fetchMenuItemsMoreInfo(itemID)
    }
}