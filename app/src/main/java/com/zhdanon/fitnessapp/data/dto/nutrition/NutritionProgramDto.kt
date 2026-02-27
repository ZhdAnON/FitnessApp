import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionCategory
import kotlinx.serialization.Serializable

@Serializable
data class NutritionProgramDto(
    val id: String,
    val category: NutritionCategory,
    val text: String
)