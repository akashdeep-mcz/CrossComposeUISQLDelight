import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun App(action: ((String) -> Unit?)? =null) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val text = remember { mutableStateOf(TextFieldValue("")) }
            BasicTF(text)
            AppButton {
                val value = text.value.text.trim()
                if (value.isNotBlank())
                    action?.invoke(value)

            }
        }

    }
}


@Composable
fun AppButton(onButtonClick: () -> Unit) {
    Button(
        onClick = {
            onButtonClick()
        }, elevation = ButtonDefaults.elevation(
            defaultElevation = 10.dp,
            pressedElevation = 15.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(text = "Save")
    }
}


@Composable
fun BasicTF(text: MutableState<TextFieldValue>) {
    TextField(value = text.value,
        onValueChange = { newText ->
            text.value = newText
        }
    )
}
