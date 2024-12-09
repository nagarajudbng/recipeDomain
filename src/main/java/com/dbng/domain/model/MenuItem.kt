package com.dbng.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class MenuItem(
 val id: Int,
 val name: String,
 val imageURL: String,
 val description: String,
 val price: Int?,
 val quantity: Int,
 val menuType: String,
 val category: String,
 val subCategory: String,
 val itemType:String,
 val ingredients: String
) : Parcelable