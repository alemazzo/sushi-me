package io.github.alemazzo.sushime.ui.screens.restaurants.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.R
import io.github.alemazzo.sushime.utils.WeightedColumnCenteredHorizontally

data class RestaurantInfo(val name: String, val description: String, val image: Int)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantInfoCard(restaurantInfo: RestaurantInfo, enabled: Boolean, onClick: () -> Unit) {

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .clickable(enabled = enabled) { onClick() },
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            WeightedColumnCenteredHorizontally(1f) {
                RestaurantInfoCardImageSection(restaurantInfo)
            }
            WeightedColumnCenteredHorizontally(2f) {
                RestaurantInfoCardNameAndLocationSection(restaurantInfo)
            }
            WeightedColumnCenteredHorizontally(1f) {
                RestaurantInfoCardStarsSection()
            }
        }
    }
}


@Composable
fun RestaurantInfoCardImageSection(restaurantInfo: RestaurantInfo) {
    CircleShapeImage(painter = painterResource(id = restaurantInfo.image))
}

@Composable
fun RestaurantInfoCardNameAndLocationSection(restaurantInfo: RestaurantInfo) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(4.dp)
    ) {
        TextTitleMedium(name = restaurantInfo.name)
        Spacer(modifier = Modifier.height(8.dp))
        TextBodySmall(description = restaurantInfo.description)
    }
}

@Composable
fun RestaurantInfoCardStarsSection() {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 4.dp)
    ) {
        Text(text = "* * * * *")
    }
}


@Composable
fun TextTitleMedium(name: String) {
    Text(text = name, style = MaterialTheme.typography.titleMedium)
}

@Composable
fun TextBodySmall(description: String) {
    Text(text = description, style = MaterialTheme.typography.bodySmall)
}

@Composable
fun CircleShapeImage(painter: Painter, size: Dp = 80.dp, onClick: () -> Unit = {}) {
    Image(
        painter = painter,
        contentDescription = "restaurant image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .padding(4.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}

@Composable
fun CircleShapeImage(bitmap: ImageBitmap, size: Dp = 80.dp, onClick: () -> Unit = {}) {
    Image(
        bitmap = bitmap,
        contentDescription = "restaurant image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size)
            .padding(4.dp)
            .clip(CircleShape)
            .clickable { onClick() }
    )
}

@Preview
@Composable
fun RestaurantInfoCardPreview() {
    val restaurantInfo = RestaurantInfo(
        "Restaurant Name",
        "Restaurant Description",
        image = R.drawable.example_restaurant_image
    )
    RestaurantInfoCard(restaurantInfo, true) {}
}
