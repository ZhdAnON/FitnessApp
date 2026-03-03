import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zhdanon.fitnessapp.domain.models.workouts.Muscle
import com.zhdanon.fitnessapp.presentation.admin.addexercise.MuscleSelector

@Composable
fun ExerciseEditorScreen(
    isEditMode: Boolean,
    onSaved: () -> Unit,
    name: String,
    onNameChange: (String) -> Unit,
    selectedMuscles: List<Muscle>,
    onMusclesChange: (List<Muscle>) -> Unit,
    technique: String,
    onTechniqueChange: (String) -> Unit,
    videoUrl: String,
    onVideoUrlChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    error: String?
) {
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Название") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        MuscleSelector(
            selected = selectedMuscles,
            onSelectedChange = onMusclesChange
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = technique,
            onValueChange = onTechniqueChange,
            label = { Text("Техника выполнения") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = videoUrl,
            onValueChange = onVideoUrlChange,
            label = { Text("Видео (необязательно)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isEditMode) "Сохранить изменения" else "Создать упражнение")
        }

        error?.let {
            Spacer(Modifier.height(12.dp))
            Text("Ошибка: $it", color = Color.Red)
        }
    }
}