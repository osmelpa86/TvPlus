package cu.osmel.tvplus.ui.componets.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import kotlin.math.absoluteValue

/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 7/6/22
 */
//fun timeFilter(text: AnnotatedString, mask: String): TransformedText {
//    // change the length
//    val trimmed =
//        if (text.text.length >= 4) text.text.substring(0..3) else text.text
//
//    val annotatedString = AnnotatedString.Builder().run {
//        for (i in trimmed.indices) {
//            append(trimmed[i])
//            if (i == 1) {
//                append(":")
//            }
//        }
//        append(mask.takeLast(mask.length - length))
//        toAnnotatedString()
//    }
//
//    val timeOffsetTranslator = object : OffsetMapping {
//        override fun originalToTransformed(offset: Int): Int {
//            if (offset <= 1) return offset
//            if (offset <= 2) return offset + 1
//            if (offset <= 3) return offset + 1
//            return 5
//        }
//
//        override fun transformedToOriginal(offset: Int): Int {
//            if (offset <= 1) return offset
//            if (offset <= 2) return offset - 1
//            if (offset <= 3) return offset - 1
//            return 4
//        }
//    }
//
//    return TransformedText(annotatedString, timeOffsetTranslator)
//}

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}