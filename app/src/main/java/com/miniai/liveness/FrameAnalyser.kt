package com.miniai.liveness

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.fm.face.FaceBox
import com.fm.face.FaceSDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Processes camera frames, runs detection/liveness, and updates the overlay + UI prompts.
 */
class FrameAnalyser(
    private val context: Context,
    private val boundingBoxOverlay: BoundingBoxOverlay,
    private val viewBackgroundOfMessage: View,
    private val textViewMessage: TextView
) : ImageAnalysis.Analyzer {

    companion object {
        private val TAG = FrameAnalyser::class.simpleName
        const val LIVENESS_THRESHOLD = 0.5f
    }

    enum class PROC_MODE { VERIFY, REGISTER }

    var mode = PROC_MODE.VERIFY
    var startVerifyTime: Long = 0

    private var isRunning = false
    private var isProcessing = false
    private var isRegistering = false
    private var frameInterface: FrameInferface? = null

    fun cancelRegister() {
        mode = PROC_MODE.VERIFY
        isRegistering = false
    }

    fun setRunning(running: Boolean) {
        isRunning = running
        viewBackgroundOfMessage.alpha = 0f
        textViewMessage.alpha = 0f
        boundingBoxOverlay.clear()
    }

    fun addOnFrameListener(frameInterface: FrameInferface) {
        this.frameInterface = frameInterface
    }

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        if (!isRunning) {
            boundingBoxOverlay.clear()
            image.close()
            return
        }

        if (isProcessing) {
            image.close()
            return
        }

        isProcessing = true

        // Convert ImageProxy to Bitmap for your SDK
        val mediaImage = image.image
        if (mediaImage == null) {
            isProcessing = false
            image.close()
            return
        }

        val frameBitmap: Bitmap = BitmapUtils.imageToBitmap(mediaImage, image.imageInfo.rotationDegrees)

        // Always set/update the frame size (cheap + idempotent)
        boundingBoxOverlay.setFrameSize(frameBitmap.width, frameBitmap.height)

        var livenessScore = 0.0f
        var livenessResult = 2 // default to "multiple/other" until we know

        // Detect faces
        val faceResult: List<FaceBox>? = try {
            FaceSDK.getInstance().detectFace(frameBitmap)
        } catch (t: Throwable) {
            Log.e(TAG, "Face detection failed: ${t.message}", t)
            null
        }

        if (!faceResult.isNullOrEmpty()) {
            if (faceResult.size == 1) {
                hideMessage()
                // Liveness on single face
                livenessScore = try {
                    FaceSDK.getInstance().checkLiveness(frameBitmap, faceResult[0])
                } catch (t: Throwable) {
                    Log.e(TAG, "Liveness failed: ${t.message}", t)
                    0.0f
                }

                Log.i(TAG, "liveness score: $livenessScore")

                livenessResult = if (livenessScore > LIVENESS_THRESHOLD) {
                    1 // REAL
                } else {
                    0 // SPOOF
                }

                if (livenessResult == 0) {
                    // show/hide message as you prefer
                    hideMessage()
                }
            } else {
                // Multiple faces
                livenessResult = 2
                showMessage(context.getString(R.string.multiple_face_detected))
            }
        } else {
            // No faces
            hideMessage()
        }

        // Push results to UI on main thread
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) {
                boundingBoxOverlay.updateDetections(faceResult, livenessScore, livenessResult)
            }
        }

        isProcessing = false
        image.close()
    }

    private fun showMessage(msg: String) {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) {
                textViewMessage.text = msg
                viewBackgroundOfMessage.alpha = 1.0f
                textViewMessage.alpha = 1.0f
            }
        }
    }

    private fun hideMessage() {
        CoroutineScope(Dispatchers.Default).launch {
            withContext(Dispatchers.Main) {
                viewBackgroundOfMessage.alpha = 0.0f
                textViewMessage.alpha = 0.0f
            }
        }
    }
}
