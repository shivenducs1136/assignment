package com.example.assignment.ui.common

import android.text.format.DateUtils
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.assignment.data.Article
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.TimeZone

@Composable
fun ListContent(article: Article) {

    Card(
        Modifier
            .fillMaxWidth()
            .padding(15.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 7.dp,
            pressedElevation = 6.dp,
            hoveredElevation = 7.dp
        ),

        )
    {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(Modifier.wrapContentSize()) {
                Column(Modifier.weight(2f)) {
                    Row() {
                        simpleTime(article.publishedAt)?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Light,
                                fontSize = 8.sp,
                                color = Color.Black
                            )
                        }
                        Spacer(modifier = Modifier.width(5.dp))
                        article.author?.let {
                            Text(
                                text = it,
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp,
                                color = Color.Black,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,

                                )
                        }
                    }
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = article.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,


                        )
                    Spacer(modifier = Modifier.height(9.dp))
                    article.content?.let {
                        Text(
                            text = it,
                            fontSize = 10.sp,
                            color = Color.Black,
                            modifier = Modifier
                                .fillMaxWidth(),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,


                            )
                    }
                }
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(article.urlToImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp))
                )

            }
        }

    }
}

fun simpleTime(string: String): String? {
    val simpleTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    simpleTime.timeZone = TimeZone.getTimeZone("GMT")
    var agoString: String? = null
    try {
        val time = simpleTime.parse(string).time
        val now = System.currentTimeMillis()
        agoString =
            DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS) as String?
    } catch (e: ParseException) {
        System.out.println(e.message)
    }
    return agoString
}
