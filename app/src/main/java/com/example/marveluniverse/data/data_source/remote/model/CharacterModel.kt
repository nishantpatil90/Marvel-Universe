package com.example.marveluniverse.data.data_source.remote.model

data class CharacterModel(
  var id: String? = null,
  var name: String? = null,
  var description: String? = null,
  var thumbnail: ImageModel? = ImageModel(),
  var comics: ComicsModel? = ComicsModel(),
  var stories: StoriesModel? = StoriesModel(),
  var events: EventsModel? = EventsModel(),
  var series: Series? = Series(),
)
