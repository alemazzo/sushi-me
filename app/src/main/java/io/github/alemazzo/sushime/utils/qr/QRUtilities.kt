package io.github.alemazzo.sushime.utils.qr

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

fun isSushimeQRCode(content: String): Boolean =
    content.contains("://") && content.split("://")[0] == "sushime"

fun getSushimeQRCodeContent(content: String): String? =
    if (isSushimeQRCode(content)) content.split("://")[1] else null

fun isRestaurantQrCode(content: String): Boolean =
    content.contains("/") && content.split("/")[0] == "restaurant"

fun isTableQrCode(content: String): Boolean =
    content.contains("/") && content.split("/")[0] == "table"

fun getRestaurantIdFromQrCodeContent(content: String): Int? =
    if (isRestaurantQrCode(content)) content.split("/")[1].toInt() else null

fun getTableIdFromQrCode(content: String): String? =
    if (isTableQrCode(content)) content.split("/")[1] else null

fun isValidQRCode(content: String): Boolean = isRestaurantQrCode(content) || isTableQrCode(content)

fun getQrCodeBitmap(content: String): Bitmap {
    val size = 512 //pixels
    val hints = hashMapOf<EncodeHintType, Int>().also {
        it[EncodeHintType.MARGIN] = 1
    } // Make the QR code buffer border narrower
    val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
    return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
        for (x in 0 until size) {
            for (y in 0 until size) {
                it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
            }
        }
    }
}
