package com.example.marveluniverse.data.data_source.remote.model

data class ComicsModel(
  var available: String? = null,
  var returned: String? = null,
  var collectionURI: String? = null,
  var items: ArrayList<SummaryModel> = arrayListOf(),
)
