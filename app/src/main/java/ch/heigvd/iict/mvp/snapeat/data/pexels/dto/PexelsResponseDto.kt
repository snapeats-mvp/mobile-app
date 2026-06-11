package ch.heigvd.iict.mvp.snapeat.data.pexels.dto

data class PexelsResponseDto(
    val photos: List<PexelsPhotoDto>
)

data class PexelsPhotoDto(
    val src: PexelsSrcDto
)

data class PexelsSrcDto(
    val medium: String
)
