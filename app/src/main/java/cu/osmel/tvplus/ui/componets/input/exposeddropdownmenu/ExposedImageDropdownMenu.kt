package cu.osmel.tvplus.ui.componets.input.exposeddropdownmenu

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material.icons.rounded.InsertPhoto
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cu.osmel.tvplus.ui.componets.input.InputWrapper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/6/22
 */
@ExperimentalMaterialApi
@Composable
fun ExposedImageDropdownMenu(
    modifier: Modifier? = Modifier,
    inputWrapper: InputWrapper,
    @StringRes labelResId: Int,
    @StringRes placeholderResId: Int,
    options: List<ExposedDropdownItem>,
    selected: ExposedDropdownItem? = null,
    onChange: (ExposedDropdownItem) -> Unit
) {
    var expanded: Boolean by remember { mutableStateOf(false) }
    val fieldValue = remember {
        mutableStateOf(TextFieldValue(inputWrapper.value, TextRange(inputWrapper.value.length)))
    }

    var selectedOption by remember {
        mutableStateOf(selected)
    }

    Column {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = modifier!!,
                readOnly = true,
                value = fieldValue.value.copy(inputWrapper.value),
                onValueChange = {},
                label = { Text(stringResource(labelResId)) },
                placeholder = { Text(stringResource(placeholderResId) + "...") },
                isError = inputWrapper.errorId != null,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                leadingIcon = {
                    if (selectedOption !== null && selectedOption!!.image !== null) {
                        Image(
                            bitmap = selectedOption!!.image!!.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(32.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                modifier = Modifier.size(22.dp),
                                imageVector = Icons.Rounded.InsertPhoto,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                options.forEachIndexed { _, option ->
                    val scope = rememberCoroutineScope()
                    DropdownMenuItem(
                        onClick = {
                            onChange(option)
                            selectedOption = option
                            fieldValue.value.copy(option.text!!)
                            scope.launch {
                                delay(150)
                                expanded = false
                            }
                        }
                    ) {
                        if (option.image !== null) {
                            Image(
                                bitmap = option.image.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(32.dp)),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = MaterialTheme.colors.primary,
                                        shape = CircleShape
                                    )
                            ) {
                                Icon(
                                    modifier = Modifier.size(22.dp),
                                    imageVector = Icons.Rounded.InsertPhoto,
                                    contentDescription = null,
                                    tint = Color.White
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = option.text!!)
                    }
                }
            }
        }
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