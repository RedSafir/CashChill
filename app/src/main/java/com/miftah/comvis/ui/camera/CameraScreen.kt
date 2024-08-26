package com.miftah.comvis.ui.camera

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.miftah.comvis.R
import com.miftah.comvis.domain.FromScanned
import com.miftah.comvis.ui.theme.ComvisTheme
import com.miftah.comvis.ui.theme.Gray80
import com.miftah.comvis.ui.theme.White60
import com.miftah.comvis.utils.AppUtils.toStringSafe
import com.miftah.comvis.utils.saveBitmapToFile
import com.miftah.comvis.utils.UiState

@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    state: CameraState,
    event: (CameraEvent) -> Unit,
    navigate: (FromScanned) -> Unit
) {
    val context = LocalContext.current
    val controller = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(
                CameraController.IMAGE_CAPTURE or CameraController.VIDEO_CAPTURE
            )
        }
    }

    var uri by remember { mutableStateOf<Uri?>(null) }
    val bitmap by remember {
        derivedStateOf {
            if (uri != null) {
                if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, uri!!)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri!!)
                    ImageDecoder.decodeBitmap(source)
                }
            } else {
                null
            }
        }
    }
    val launcherGallery = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent(),
        onResult = {
            uri = it
        }
    )
    var lensFacing by remember {
        mutableIntStateOf(CameraSelector.LENS_FACING_BACK)
    }
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()
    LaunchedEffect(lensFacing) {
        controller.setCameraSelector(cameraSelector)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        if (uri == null) {
            CameraX(
                controller = controller, modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            bitmap?.let {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if (uri == null) {
                IconButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(White60),
                    onClick = {
                        launcherGallery.launch("image/*")
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gallery),
                        contentDescription = null,
                        tint = Gray80,
                        modifier = Modifier.size(24.dp)
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(32.dp)
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(White60),
                    onClick = {
                        takePhoto(controller, context) {
                            uri = saveBitmapToFile(context, it)
                        }
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_camera),
                        contentDescription = null,
                        tint = Gray80
                    )
                }
                IconButton(
                    modifier = Modifier
                        .padding(10.dp)
                        .clip(CircleShape)
                        .background(White60),
                    onClick = {
                        lensFacing =
                            if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT else CameraSelector.LENS_FACING_BACK
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_rotation),
                        contentDescription = null,
                        tint = Gray80,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(White60)
                            .padding(16.dp)
                            .clickable {
                                event(CameraEvent.ScanToCLod(uri!!, context))
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "SCAN",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Gray80
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
    Box(modifier = Modifier.fillMaxSize()) {
        state.imageState?.collectAsState(initial = null)?.value.let { data ->
            when (data) {
                is UiState.Error -> {
                    Toast.makeText(context, "ERR", Toast.LENGTH_SHORT).show()
                }

                UiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(64.dp)
                            .align(Alignment.Center),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }

                is UiState.Success -> {
                    Toast.makeText(context, "SCC", Toast.LENGTH_SHORT).show()
                    navigate(
                        FromScanned(
                            money = data.data.value.toString(),
                            uri = uri?.toStringSafe()!!
                        )
                    )
                }

                null -> {

                }
            }
        }
    }
}

private fun takePhoto(
    controller: LifecycleCameraController,
    context: Context,
    onPhotoTaken: (Bitmap) -> Unit
) {
    if (!hasPermission(context)) {
        return
    }
    controller.takePicture(
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                super.onCaptureSuccess(image)

                val matrix = Matrix().apply {
                    postRotate(image.imageInfo.rotationDegrees.toFloat())
                    postScale(1f, 1f)
                }
                val rotatedBitMap = Bitmap.createBitmap(
                    image.toBitmap(),
                    0,
                    0,
                    image.width,
                    image.height,
                    matrix,
                    true
                )
                onPhotoTaken(rotatedBitMap)
            }

            override fun onError(exception: ImageCaptureException) {
                super.onError(exception)
                Log.d("Camera ", "Could't take photo", exception)
            }
        }
    )
}

fun hasPermission(context: Context): Boolean {
    return CameraPermission.CAMERAX_PERMISSION.all {
        ContextCompat.checkSelfPermission(
            context,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

object CameraPermission {
    val CAMERAX_PERMISSION = arrayOf(Manifest.permission.CAMERA)
}

@Preview
@Composable
private fun CameraScreenPreview() {
    ComvisTheme {
//        CameraScreen()
    }
}