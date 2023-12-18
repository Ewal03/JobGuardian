package com.example.jobguardian.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.jobguardian.data.pref.UserPreference
import com.example.jobguardian.data.response.DataItem
import com.example.jobguardian.data.retrofit.ApiServices
import kotlinx.coroutines.flow.first

class DataPagingSource (
    private val apiService: ApiServices,
) : PagingSource<Int, DataItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val page = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getcompanyData(page, params.loadSize)

            LoadResult.Page(
                data = responseData.data,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (responseData.data.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}