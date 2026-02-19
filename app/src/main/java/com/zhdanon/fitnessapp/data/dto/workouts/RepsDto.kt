import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class RepsDto {
    @Serializable @SerialName("Fixed")
    data class Fixed(val count: Int) : RepsDto()

    @Serializable @SerialName("Range")
    data class Range(val from: Int, val to: Int) : RepsDto()

    @Serializable @SerialName("TimeFixed")
    data class TimeFixed(val duration: Int) : RepsDto()

    @Serializable @SerialName("TimeRange")
    data class TimeRange(val from: Int, val to: Int) : RepsDto()

    @Serializable @SerialName("None")
    data object None : RepsDto()
}