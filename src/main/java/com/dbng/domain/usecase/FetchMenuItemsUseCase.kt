package com.dbng.domain.usecase

import com.dbng.core.domain.Resource
import com.dbng.core.domain.utils.ResponseError
import com.dbng.domain.model.MenuItem
import com.dbng.domain.repository.MenuRepository
import javax.inject.Inject

class  FetchMenuItemsUseCase @Inject constructor(
    private val repository : MenuRepository
) {
    var totalItemsCount = 0
    suspend operator fun invoke(from: Int, size: Int): Resource<List<MenuItem>> {
        if (totalItemsCount > 0 && from >= totalItemsCount) {
            return Resource.Error(null,responseError = ResponseError.NoDataFoundError)
        }

        val result = repository.fetchMenuItems(from, size)
        if (result is Resource.Success) {
            totalItemsCount = repository.getTotalItemCount()
        }
        return result
    }
}