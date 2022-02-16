package com.metis.rickmorty.ui.episode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.metis.rickmorty.ui.RickMortyTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EpisodeListItem(episode: ViewEpisodeItem) {
  Card(
    onClick = { episode.onClick() },
    modifier = Modifier
      .padding(horizontal = 8.dp, vertical = 8.dp)
      .fillMaxWidth(),
    elevation = 2.dp,
    backgroundColor = Color.White,
    shape = RoundedCornerShape(corner = CornerSize(4.dp))
  ) {
    Row {
      Column(
        modifier = Modifier.padding(6.dp)
      ) {
        Text(text = episode.episode, style = MaterialTheme.typography.h6)
        Text(
          text = episode.name,
          style = MaterialTheme.typography.caption,
        )
      }
    }
  }
}

val episode = ViewEpisodeItem(
  name = "Pilot",
  episode = "S01E01",
  airDate = "December 2 1990",
  onClick = {}
)

@Preview
@Composable
fun PreviewDarkEpisodeItem() {
  RickMortyTheme(darkTheme = true) {
    EpisodeListItem(episode = episode)
  }
}

@Preview
@Composable
fun PreviewLightEpisodeItem() {
  RickMortyTheme(darkTheme = false) {
    EpisodeListItem(episode = episode)
  }
}
