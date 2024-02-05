package com.example.marveluniverse.data.data_source.remote.model

data class EventsModel(
    var available: String? = null,
    var returned: String? = null,
    var collectionURI: String? = null,
    var items: ArrayList<SummaryModel> = arrayListOf(),
)
