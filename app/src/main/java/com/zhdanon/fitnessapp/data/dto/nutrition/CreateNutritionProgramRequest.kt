import com.zhdanon.fitnessapp.domain.models.nutrition.NutritionCategory
import kotlinx.serialization.Serializable

@Serializable
data class CreateNutritionProgramRequest(
    val category: NutritionCategory,
    val text: String
)