package com.smd.flexfuel.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smd.flexfuel.model.Post

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.ui.res.stringResource
import com.smd.flexfuel.R

@Composable
fun CardPostComponent(
    post: Post,
    onMapClick: () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 0.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable{ onClick() }
            .padding(16.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = post.name, style = MaterialTheme.typography.titleMedium)
                Text(text = (stringResource(R.string.alcohol))+ ": R$ ${post.alcoholValue}")
                Text(text = (stringResource(R.string.gasoline))+ ": R$ ${post.gasolineValue}")
            }

            if (post.location != null) {
                IconButton(onClick = onMapClick) {
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Ver no Mapa",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }

}