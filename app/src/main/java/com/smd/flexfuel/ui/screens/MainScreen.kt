package com.smd.flexfuel.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.smd.flexfuel.R
import com.smd.flexfuel.ui.components.CardPostComponent
import com.smd.flexfuel.viewmodel.MainViewModel

import android.content.Intent
import android.net.Uri
import com.smd.flexfuel.model.PostLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel()
) {
    val context = LocalContext.current
    viewModel.initSharedPrefsManager(context = context)
    val postos by viewModel.listOfPost.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("FlexFuel") },
                actions = {
                    // TODO("add toggle language") @Nicole
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createdatascreen")
                },
            ) {
                Icon(Icons.Filled.Add, "Floating action button.")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            item {
                Text(
                    modifier = Modifier.padding(16.dp, 5.dp),
                    text = stringResource(R.string.saved_post),
                    color = MaterialTheme.colorScheme.primary)
            }
            items(
                items = postos,
                key = { posto -> posto.id },
            ) { posto ->
                CardPostComponent(
                    post = posto,
                    onClick = { navController.navigate("editdatascreen/${posto.id}") },
                    onMapClick = {
                        val location = posto.location

                        if (location != null) {
                            val gmmIntentUri = Uri.parse("geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}(${Uri.encode(posto.name)})")

                            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                            mapIntent.setPackage("com.google.android.apps.maps")

                            try {
                                context.startActivity(mapIntent)
                            } catch (e: Exception) {
                                val webIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                                try {
                                    context.startActivity(webIntent)
                                } catch (e2: Exception) {
                                    e2.printStackTrace()
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}