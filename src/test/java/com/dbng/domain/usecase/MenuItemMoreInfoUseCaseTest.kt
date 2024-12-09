package com.dbng.domain.usecase

import com.dbng.domain.repository.MenuRepository
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

// Created by Nagaraju on 13/11/24.

class MenuItemMoreInfoUseCaseTest {
    private lateinit var menuRepository: MenuRepository
    private lateinit var menuUseCase:MenuItemMoreInfoUseCase
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        menuRepository = Mockito.mock(MenuRepository::class.java)
        menuUseCase =MenuItemMoreInfoUseCase(menuRepository)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun `invoke Item More info when repository fetchItemMoreInfo succeeds`() =
        runTest(testDispatcher) {
            val itemID = 123
            val mockMenuItem = com.dbng.domain.model.MenuItem(
                id = 123,
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
            )
            whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
                com.dbng.core.domain.Resource.Success(
                    mockMenuItem
                )
            )
            val useCaseResult = menuUseCase(itemID)
            assert(useCaseResult is com.dbng.core.domain.Resource.Success)
            Assert.assertEquals(itemID, (useCaseResult as com.dbng.core.domain.Resource.Success).data?.id)
        }

    @Test
    fun `invoke returns error when repository fetch fails`() = runTest(testDispatcher) {
        val itemID = 123
        whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
            com.dbng.core.domain.Resource.Error(
                data = null, responseError = com.dbng.core.domain.utils.ResponseError.UnknownError
            )
        )
        val response = menuUseCase(itemID)
        assert(response is com.dbng.core.domain.Resource.Error)
        Assert.assertEquals(com.dbng.core.domain.utils.ResponseError.UnknownError, (response as com.dbng.core.domain.Resource.Error).responseError)
    }

    @Test
    fun `invoke returns Exception when repository IOException`() = runTest(testDispatcher) {
        val itemID = 123
        whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
            com.dbng.core.domain.Resource.Error(data = null, responseError = com.dbng.core.domain.utils.ResponseError.NetworkError)
        )
        val useCaseResult = menuUseCase(itemID)
        assert(useCaseResult is com.dbng.core.domain.Resource.Error)
        Assert.assertEquals(
            com.dbng.core.domain.utils.ResponseError.NetworkError,
            (useCaseResult as com.dbng.core.domain.Resource.Error).responseError
        )
    }

    @Test
    fun `invoke returns Exception when repository HTTPException`() = runTest(testDispatcher) {
        val itemID = 123
        whenever(menuRepository.fetchMenuItemsMoreInfo(itemID)).thenReturn(
            com.dbng.core.domain.Resource.Error(data = null, responseError = com.dbng.core.domain.utils.ResponseError.ServerError)
        )
        val useCaseResult = menuUseCase(itemID)
        assert(useCaseResult is com.dbng.core.domain.Resource.Error)
        Assert.assertEquals(
            com.dbng.core.domain.utils.ResponseError.ServerError,
            (useCaseResult as com.dbng.core.domain.Resource.Error).responseError
        )
    }
}