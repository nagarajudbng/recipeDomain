package com.dbng.domain.usecase

import com.dbng.core.domain.Resource
import com.dbng.core.domain.utils.ResponseError
import com.dbng.domain.model.MenuItem
import com.dbng.domain.repository.MenuRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

// Created by Nagaraju on 13/11/24.

class FetchMenuItemsUseCaseTest {
    private lateinit var menuRepository: MenuRepository
    private lateinit var fetchMenuItemsUseCase: FetchMenuItemsUseCase
    private val testDispatcher = TestCoroutineDispatcher()
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        menuRepository = Mockito.mock(MenuRepository::class.java)
        fetchMenuItemsUseCase = FetchMenuItemsUseCase(menuRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `invoke returns success with data when repository fetch is successful`() = runTest(testDispatcher){
        val from = 0
        val size = 20
        val mockMenuItems = listOf(
            com.dbng.domain.model.MenuItem(
                id = 1,
                name = "Pizza",
                imageURL = "url",
                description = "test",
                price = 20,
                quantity = 1,
                menuType = "a",
                category = "b",
                subCategory = "c",
                itemType = "d",
                ingredients = "e"
            ),
            com.dbng.domain.model.MenuItem(
                id = 1,
                name = "Burger",
                imageURL = "url",
                description = "test",
                price = 20,
                quantity = 1,
                menuType = "a",
                category = "b",
                subCategory = "c",
                itemType = "d",
                ingredients = "e"
            ),
        )
        whenever(menuRepository.fetchMenuItems(from, size)).thenReturn(com.dbng.core.domain.Resource.Success(mockMenuItems))
        val useCaseResult = fetchMenuItemsUseCase(from, size)
        assert(useCaseResult is com.dbng.core.domain.Resource.Success)
        Assert.assertEquals(2, (useCaseResult as com.dbng.core.domain.Resource.Success).data?.size)
    }

    @Test
    fun `invoke returns error when repository fetch fails`() = runTest(testDispatcher){
        val from = 0
        val size = 20
        whenever(menuRepository.fetchMenuItems(from, size)).thenReturn(
            com.dbng.core.domain.Resource.Error(data = null, responseError= com.dbng.core.domain.utils.ResponseError.UnknownError
            ))
        val response = menuRepository.fetchMenuItems(from, size)
        assert(response is com.dbng.core.domain.Resource.Error)
        Assert.assertEquals(com.dbng.core.domain.utils.ResponseError.UnknownError, (response as com.dbng.core.domain.Resource.Error).responseError)
    }

    @Test
    fun `invoke returns Exception when repository IOException`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        whenever(menuRepository.fetchMenuItems(from, size)).thenReturn (
            com.dbng.core.domain.Resource.Error(data = null, responseError= com.dbng.core.domain.utils.ResponseError.NetworkError)
        )
        val useCaseResult = fetchMenuItemsUseCase(from, size)
        assert(useCaseResult is com.dbng.core.domain.Resource.Error)
        Assert.assertEquals(
            com.dbng.core.domain.utils.ResponseError.NetworkError,
            (useCaseResult as com.dbng.core.domain.Resource.Error).responseError
        )
    }
    @Test
    fun `invoke returns Exception when repository HTTPException`() = runTest(testDispatcher) {
        val from = 0
        val size = 20
        whenever(menuRepository.fetchMenuItems(from, size)).thenReturn (
            com.dbng.core.domain.Resource.Error(data = null, responseError= com.dbng.core.domain.utils.ResponseError.ServerError)
        )
        val useCaseResult = fetchMenuItemsUseCase(from, size)
        assert(useCaseResult is com.dbng.core.domain.Resource.Error)
        Assert.assertEquals(
            com.dbng.core.domain.utils.ResponseError.ServerError,
            (useCaseResult as com.dbng.core.domain.Resource.Error).responseError
        )
    }

    @Test
    fun `invoke NoDataFoundError when greater totalItemsCount`() = runTest (testDispatcher){
        fetchMenuItemsUseCase.totalItemsCount = 10

        val result = fetchMenuItemsUseCase(11, 5)

        assertEquals(com.dbng.core.domain.utils.ResponseError.NoDataFoundError, (result as com.dbng.core.domain.Resource.Error).responseError)
    }
}