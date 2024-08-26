package com.miftah.comvis.ui.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.miftah.comvis.R
import com.miftah.comvis.ui.theme.ComvisTheme
import com.miftah.comvis.ui.theme.Green
import com.miftah.comvis.ui.theme.Red
import com.miftah.comvis.ui.theme.White60

@Composable
fun CardHome(modifier: Modifier = Modifier, cardHomeData: CardHomeData) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (cardHomeData.isAddingMoney) {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_up),
                    contentDescription = null
                )
            } else {
                Image(
                    modifier = Modifier,
                    painter = painterResource(id = R.drawable.ic_down),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = cardHomeData.description,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = cardHomeData.date,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = White60,
                        fontSize = 11.sp
                    )
                )
            }
        }
        if (cardHomeData.isAddingMoney) {
            Text(
                text = "+Rp" + cardHomeData.money,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Green,
                    fontSize = 16.sp
                )
            )
        } else {
            Text(
                text = "-Rp" + cardHomeData.money,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = Red,
                    fontSize = 16.sp
                )
            )
        }

    }
}

data class CardHomeData(
    val description: String,
    val date: String,
    val money: String,
    val isAddingMoney: Boolean,
)

@Preview
@Composable
private fun CardHomePreview() {
    ComvisTheme {
        CardHome(
            cardHomeData = CardHomeData(
                date = "07/08/2024",
                description = "Uang dari ayah",
                money = "10.000",
                isAddingMoney = false
            )
        )
    }
}