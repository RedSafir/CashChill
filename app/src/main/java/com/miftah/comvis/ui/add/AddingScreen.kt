package com.miftah.comvis.ui.add

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.comvis.ui.theme.Black90
import com.miftah.comvis.ui.theme.ComvisTheme
import com.miftah.comvis.ui.theme.Gray60
import com.miftah.comvis.ui.theme.Gray70
import com.miftah.comvis.ui.theme.Gray80
import com.miftah.comvis.ui.theme.White
import com.miftah.comvis.ui.theme.White60

@Composable
fun AddingScreen(
    modifier: Modifier = Modifier,
    state: AddingState,
    event: (AddingEvent) -> Unit,
    navigate: () -> Unit
) {
    val context = LocalContext.current

    val bitmap by remember {
        derivedStateOf {
            if (state.uri != null) {
                if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, state.uri)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, state.uri)
                    ImageDecoder.decodeBitmap(source)
                }
            } else {
                null
            }
        }
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        containerColor = Black90,
        bottomBar = {
            Surface(
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White60)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "DEPOSIT",
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
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    modifier = Modifier,
                    onClick = {
                        event(AddingEvent.SaveToDB)
                        navigate()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = White
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "DEPOSIT MONEY",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White60
                    )
                )
            }
            Box(
                modifier = Modifier
            ) {
                bitmap?.let {
                    Image(
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth(),
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Money Value",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White60
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.height(IntrinsicSize.Max)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(White60)
                                .padding(16.dp)
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "Rp",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Black90
                                )
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.money,
                            onValueChange = {
                                event(AddingEvent.TextFieldMoney(it))
                            },
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                color = White60
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Count",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White60
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.count,
                        onValueChange = {
                            event(AddingEvent.TextFieldCount(it))
                        },
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            color = White60
                        )
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                /*Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Description",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = White60
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.description,
                        onValueChange = {
                            event(AddingEvent.TextFieldDescription(it))
                        },
                        textStyle = MaterialTheme.typography.titleMedium.copy(
                            color = White60
                        )
                    )
                }*/
            }
        }
    }
}

@Preview
@Composable
private fun AddingScreenPreview() {
    ComvisTheme {
        AddingScreen(
            state = AddingState(),
            event = {}
        ){

        }
    }
}