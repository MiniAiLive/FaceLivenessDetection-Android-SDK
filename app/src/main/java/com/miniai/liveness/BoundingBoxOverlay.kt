package com.miniai.liveness

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.fm.face.FaceBox

/**
 * A lightweight overlay View that draws face bounding boxes and labels (REAL/SPOOF).
 * Usage:
 *  - Call setFrameSize(frameW, frameH) when you know the camera frame size (can be called every frame; cheap).
 *  - Call updateDetections(faceBoxes, score, result) whenever new results arrive.
 *    result: 0 = Spoof, 1 = Real, 2 = Multiple
 *  - Optionally set mirrorFrontCamera = true for front-camera previews.
 */
class BoundingBoxOverlay @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    // Source (camera) frame size
    private var frameWidth: Int = 0
    private var frameHeight: Int = 0

    // If using front camera preview, you likely want to mirror horizontally.
    var mirrorFrontCamera: Boolean = false
        set(value) {
            field = value
            dimsInitialized = false
            invalidate()
        }

    // Latest detections and liveness info
    private var faceBoundingBoxes: List<FaceBox>? = null
    private var livenessScore: Float = 0f
    private var livenessResult: Int = 0 // 0=spoof, 1=real, 2=multiple

    // Matrix to map frame coordinates to this view
    private val output2OverlayTransform = Matrix()
    private var dimsInitialized = false

    // Paints
    private val realBoxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.parseColor("#FF00FF00") // green
    }
    private val spoofBoxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.parseColor("#FFFF0000") // red
    }
    private val multipleBoxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 5f
        color = Color.parseColor("#FFFFFF00") // yellow
    }
    private val realTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FF00FF00")
        textSize = 64f
        style = Paint.Style.FILL
    }
    private val spoofTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#FFFF0000")
        textSize = 64f
        style = Paint.Style.FILL
    }

    /** Set the source (camera) frame size used for coordinate scaling. */
    fun setFrameSize(width: Int, height: Int) {
        if (width <= 0 || height <= 0) return
        if (width != frameWidth || height != frameHeight) {
            frameWidth = width
            frameHeight = height
            dimsInitialized = false
            invalidate()
        }
    }

    /**
     * Update detections and liveness info, then request a redraw.
     * @param boxes list of FaceBox from your SDK; can be null/empty
     * @param score liveness score
     * @param result 0=spoof, 1=real, 2=multiple
     */
    fun updateDetections(
        boxes: List<FaceBox>?,
        score: Float,
        result: Int
    ) {
        faceBoundingBoxes = boxes
        livenessScore = score
        livenessResult = result
        invalidate()
    }

    /** Clear overlay. */
    fun clear() {
        faceBoundingBoxes = null
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val boxes = faceBoundingBoxes ?: return
        if (boxes.isEmpty()) return
        if (frameWidth == 0 || frameHeight == 0) return
        if (width == 0 || height == 0) return

        if (!dimsInitialized) {
            val viewW = width.toFloat()
            val viewH = height.toFloat()
            val xFactor = viewW / frameWidth.toFloat()
            val yFactor = viewH / frameHeight.toFloat()
            output2OverlayTransform.reset()
            output2OverlayTransform.preScale(xFactor, yFactor)
            if (mirrorFrontCamera) {
                output2OverlayTransform.postScale(-1f, 1f, viewW / 2f, viewH / 2f)
            }
            dimsInitialized = true
        }

        val cornerRadius = 16f
        val textOffsetY = 30f
        val formatted = "%.4f".format(livenessScore)

        for (face in boxes) {
            val r = RectF(
                face.left.toFloat(),
                face.top.toFloat(),
                face.right.toFloat(),
                face.bottom.toFloat()
            )
            output2OverlayTransform.mapRect(r)

            when (livenessResult) {
                0 -> { // spoof
                    canvas.drawText("SPOOF $formatted", r.left + 20f, r.top - textOffsetY, spoofTextPaint)
                    canvas.drawRoundRect(r, cornerRadius, cornerRadius, spoofBoxPaint)
                }
                1 -> { // real
                    canvas.drawText("REAL $formatted", r.left + 20f, r.top - textOffsetY, realTextPaint)
                    canvas.drawRoundRect(r, cornerRadius, cornerRadius, realBoxPaint)
                }
                2 -> { // multiple
                    canvas.drawRoundRect(r, cornerRadius, cornerRadius, multipleBoxPaint)
                }
                else -> {
                    // default/fallback
                    canvas.drawRoundRect(r, cornerRadius, cornerRadius, multipleBoxPaint)
                }
            }
        }
    }
}
