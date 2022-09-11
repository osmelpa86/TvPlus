package cu.osmel.tvplus.ui.componets.input

import android.app.TimePickerDialog
import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.util.*

@Composable
fun TimerPiackerInput(
    modifier: Modifier? = Modifier,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int,
    @StringRes placeholderResId: Int,
    onSelect: (String) -> Unit,
    context: Context,
) {
    val source = remember { MutableInteractionSource() }

    if (source.collectIsPressedAsState().value) {
        val calendar = Calendar.getInstance()
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = calendar[Calendar.MINUTE]
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hour, minute ->
                onSelect("$hour:$minute")
                with(inputWrapper) { copy(value = "$hour:$minute") }
            }, hour, minute, false
        )
        timePickerDialog.show()
    }

    Column {
        TextField(
            interactionSource = source,
            readOnly = true,
            modifier = modifier!!,
            value = inputWrapper.value,
            onValueChange = {},
            label = { Text(stringResource(labelResId)) },
            placeholder = { Text(stringResource(placeholderResId) + "...") },
            isError = inputWrapper.errorId != null,
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