package ru.moscow.ucbazar.model

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Items")
data class Items(
    @Schema(description = "banner image")
    var banner_img: String,
    @Schema(description = "title")
    var title: String,
    @Schema(description = "is Active")
    var uc: List<UcModel>
)
