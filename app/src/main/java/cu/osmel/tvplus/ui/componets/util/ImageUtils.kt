package cu.osmel.tvplus.ui.componets.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Base64
import java.io.ByteArrayOutputStream


/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 1/6/22
 */
/*Convertir un base64 a Bitmap*/
fun decodeImageBase64(base64: String): Bitmap {
    val decodedString: ByteArray = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
}

/*Convertir imagen de una uri a base64*/
fun encodeImageBase64(uri: Uri, context: Context): String {
    return Base64.encodeToString(
        context.contentResolver.openInputStream(uri)?.readBytes(),
        Base64.DEFAULT
    )
}

/*Convertir bitmap a base64*/
fun encodeBase64FromBitmap(bitmap:Bitmap) {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray: ByteArray = byteArrayOutputStream.toByteArray()
    val encoded = Base64.encodeToString(byteArray, Base64.DEFAULT)

}

/*Obtener el tamaño de una imagen*/
fun dumpImageMetaData(uri: Uri?, context: Context): String? {
    var size: String? = null
    val cursor = context.contentResolver
        .query(uri!!, null, null, null, null, null)
    try {
        if (cursor != null && cursor.moveToFirst()) {
            val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
            size = if (!cursor.isNull(sizeIndex)) {
                cursor.getString(sizeIndex)
            } else {
                "Unknown"
            }
        }
    } finally {
        cursor!!.close()
    }
    return size
}

/*Reducir tamaño de una imagen*/
fun compressImage(image: Bitmap, maxBytes: Int): Bitmap? {
    var image: Bitmap = image
    var oldSize = image.byteCount

    // attempt to resize the image as much as possible while valid
    while (image.byteCount > maxBytes) {

        // Prevent image from becoming too small
        if (image.width <= 20 || image.height <= 20)
            return null

        // scale down the image by a factor of 2
        image = Bitmap.createScaledBitmap(image, image.width / 2, image.height / 2, false)

        // the byte count did not change for some reason, can not be made any smaller
        if (image.byteCount == oldSize)
            return null

        oldSize = image.byteCount
    }

    return image
}