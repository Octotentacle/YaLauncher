package octotentacle.yalauncher

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff.Mode
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet


class RoundedImageView(ctx: Context, attrs: AttributeSet) : AppCompatImageView(ctx, attrs) {

    override fun onDraw(canvas: Canvas) {

        val drawable = drawable ?: return

        if (width == 0 || height == 0) {
            return
        }
        val b = (drawable as BitmapDrawable).bitmap
        val bitmap = b.copy(Config.ARGB_8888, true)

        val w = width
        val h = height

        val roundBitmap = getRoundedCroppedBitmap(bitmap, minOf(w, h))
        canvas.drawBitmap(roundBitmap, 0f, 0f, null)

    }

    companion object {

        fun getRoundedCroppedBitmap(bitmap: Bitmap, radius: Int): Bitmap {
            val finalBitmap: Bitmap
             if(bitmap.width < bitmap.height) {
                val offset = (bitmap.height - bitmap.width) / 2
                finalBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(bitmap, 0, offset, bitmap.width, bitmap.width), radius, radius, false)
            } else if(bitmap.width > bitmap.height){
                val offset = (bitmap.width - bitmap.height) / 2
                finalBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(bitmap, offset, 0, bitmap.height, bitmap.height), radius, radius, false)
            } else finalBitmap = bitmap

            val output = Bitmap.createBitmap(
                finalBitmap.width,
                finalBitmap.height, Config.ARGB_8888
            )
            val canvas = Canvas(output)

            val paint = Paint()
            val rect = Rect(
                0, 0, finalBitmap.width,
                finalBitmap.height
            )

            paint.isAntiAlias = true
            paint.isFilterBitmap = true
            paint.isDither = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = Color.parseColor("#BAB399")
            canvas.drawCircle(
                finalBitmap.width / 2 + 0.7f,
                finalBitmap.height / 2 + 0.7f,
                finalBitmap.width / 2 + 0.1f, paint
            )
            paint.xfermode = PorterDuffXfermode(Mode.SRC_IN)
            canvas.drawBitmap(finalBitmap, rect, rect, paint)

            return output
        }
    }

}