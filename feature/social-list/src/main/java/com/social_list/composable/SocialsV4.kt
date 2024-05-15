package com.social_list.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.core.R
import com.core.ui.composable.social_card.SocialCardV4
import com.core.ui.model.SocialV4
import com.core.ui.theme.MagicDownloaderTheme

@Composable
internal fun SocialsV4(
    modifier: Modifier = Modifier,
) {
    Content(
        modifier = modifier,
    )
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .clip(shape = RoundedCornerShape(size = 24.dp))
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .padding(vertical = 24.dp)
    ) {
        FirstLine()

        Spacer(modifier = Modifier.height(height = 32.dp))

        SecondLine()
    }
}

@Composable
private fun FirstLine() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
    ) {
        SocialCardV4(item = SocialV4.Facebook()) {}

        SocialCardV4(item = SocialV4.Instagram()) {}

        SocialV4.Websites(
            link = "https://vimeo.com/watch",
            icon = R.drawable.ic_vimeo,
            title = R.string.vimeo,
            backgroundColor = Color(0xFF32B8E8),
        ).let {
            SocialCardV4(item = it) {
            }
        }

        SocialCardV4(item = SocialV4.Twitter()) {}
    }
}

@Composable
private fun SecondLine() {
    val tictok = false

    Row(
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
    ) {
        if (!tictok) Spacer(modifier = Modifier.weight(weight = 0.5f))

        SocialV4.Websites(
            link = "https://www.dailymotion.com/",
            icon = R.drawable.ic_dailymotion,
            title = R.string.dailymotion,
            backgroundColor = Color(0xFF00BEF9),
        ).let {
            SocialCardV4(item = it) {
            }
        }

        if (tictok) SocialV4.Websites(
            link = "https://www.tiktok.com/",
            icon = R.drawable.ic_tiktok,
            title = R.string.tik_tok,
            backgroundColor = Color(0xFF000000),
        ).let {
            SocialCardV4(item = it) {
            }
        }

        SocialV4.Websites(
            link = "https://www.google.com/",
            icon = R.drawable.ic_google,
            title = R.string.google,
            backgroundColor = Color(0xFFF2F2F2),
        ).let {
            SocialCardV4(item = it) {
            }
        }

        SocialV4.Websites(
            link = "https://m.imdb.com/",
            icon = R.drawable.ic_imdb,
            title = R.string.imdb,
            backgroundColor = Color(0xFFF5C518),
        ).let {
            SocialCardV4(item = it) {
            }
        }

        if (!tictok) Spacer(modifier = Modifier.weight(weight = 0.5f))
    }
}

@Preview
@Composable
private fun Preview() {
    MagicDownloaderTheme {
        Content()
    }
}