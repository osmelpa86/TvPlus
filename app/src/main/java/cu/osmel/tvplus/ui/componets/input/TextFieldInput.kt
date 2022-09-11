package cu.osmel.tvplus.ui.componets.input

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.ui.componets.util.manageLength

@Composable
fun TextFieldInput(
    modifier: Modifier? = Modifier,
    inputWrapper: InputWrapper = InputWrapper(),
    @StringRes labelResId: Int,
    @StringRes placeholderResId: Int,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onValueChange: OnValueChange,
    visualTransformation: VisualTransformation? = VisualTransformation.None,
    onImeKeyAction: OnImeKeyAction? = null,
    regex: Regex? = null,
    totalDigits: Int? = null,
    maxLines: Int = 1,
) {
    Column(/*modifier = Modifier.height(70.dp)*/) {
        TextField(
            modifier = modifier!!,
            value = inputWrapper.value,
            onValueChange = {
                if (regex !== null && !it.matches(regex)) {
                    if (totalDigits !== null) {
                        val result =
                            manageLength(it.filter { it1 -> it1.isLetter() }, totalDigits)
                        with(inputWrapper) {
                            copy(value = result)
                        }
                        onValueChange(result)
                    } else {
                        val result = it.filter { it1 -> it1.isLetter() }
                        with(inputWrapper) {
                            copy(value = result)
                        }
                        onValueChange(result)
                    }
                } else {
                    if (totalDigits !== null) {
                        val result = manageLength(it, totalDigits)
                        with(inputWrapper) {
                            copy(value = result)
                        }
                        onValueChange(result)
                    } else {
                        with(inputWrapper) { copy(value = it) }
                        onValueChange(it)
                    }
                }
            },
            label = { Text(stringResource(labelResId)) },
            placeholder = { Text(stringResource(placeholderResId) + "...") },
            isError = inputWrapper.errorId != null,
            visualTransformation = visualTransformation!!,
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(onAny = {
                if (onImeKeyAction != null) {
                    onImeKeyAction()
                }
            }),
            maxLines = maxLines
        )
        if (inputWrapper.errorId != null) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 2.dp, top = 2.dp)
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colors.error,
                )
                Text(
                    text = stringResource(inputWrapper.errorId),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                )
            }

        }
    }
}