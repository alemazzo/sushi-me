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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.alemazzo.sushime.R

data class RestaurantInfo(val name: String, val description: String, val image: Int)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantInfoCard(restaurantInfo: RestaurantInfo) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .clickable { },
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            RestaurantInfoCardImageSection(restaurantInfo)
            RestaurantInfoCardNameAndLocationSection(restaurantInfo)
            RestaurantInfoCardStarsSection()
        }
    }
}

@Composable
fun RestaurantInfoCardImageSection(restaurantInfo: RestaurantInfo) {
    RestaurantInfoCardImage(painter = painterResource(id = restaurantInfo.image))
}

@Composable
fun RestaurantInfoCardNameAndLocationSection(restaurantInfo: RestaurantInfo) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(4.dp)
    ) {
        RestaurantInfoCardName(name = restaurantInfo.name)
        Spacer(modifier = Modifier.height(8.dp))
        RestaurantInfoCardDescription(description = restaurantInfo.description)
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
fun RestaurantInfoCardName(name: String) {
    Text(text = name, style = MaterialTheme.typography.titleSmall)
}

@Composable
fun RestaurantInfoCardDescription(description: String) {
    Text(text = description, style = MaterialTheme.typography.bodySmall)
}

@Composable
fun RestaurantInfoCardImage(painter: Painter) {
    Image(
        painter = painter,
        contentDescription = "restaurant image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(64.dp)
            .padding(4.dp)
            .clip(CircleShape)
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
    RestaurantInfoCard(restaurantInfo)
}
