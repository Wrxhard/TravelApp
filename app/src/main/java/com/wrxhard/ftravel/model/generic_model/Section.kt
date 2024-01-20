package com.wrxhard.ftravel.model.generic_model

data class Section(
    val title: String,
    var items: List<Item<*>>?
)
