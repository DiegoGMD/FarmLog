package com.gmdproject.farmlog_project.HomePage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.gmdproject.farmlog_project.DBClasses.getAgriculturalHolding
import com.gmdproject.farmlog_project.FrontPage.wallpaper
import com.gmdproject.farmlog_project.GlobalVariables
import com.gmdproject.farmlog_project.ui.theme.ForestGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 4 })
    val scope = rememberCoroutineScope()
    val selectedTabIndex = pagerState.currentPage
    val tabs = listOf("Info Huerto", "Parcela", "Riego", "Fitosanitarios")

    var context = LocalContext.current
    var userId = GlobalVariables.userId
    var agriculturalHoldingId = GlobalVariables.agriculturalHoldingId
    var agriculturalHolding = getAgriculturalHolding(context, userId, agriculturalHoldingId)

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = {
                    if (agriculturalHolding != null) {
                        Text(
                            text = agriculturalHolding.name, color = Color.White
                        )
                    } else{
                        Text(
                            text = "No definido", color = Color.Gray
                        )
                    }
                },
//                    navigationIcon = {
//                        IconButton(onClick = { /* do something */ }) {
//                            Icon(
//                                imageVector = Icons.Filled.Menu,
//                                contentDescription = null,
//                                tint = Color.White
//                            )
//                        }
//                    },
//                    actions = {
//                        IconButton(onClick = { /* algo */ }) {
//                            Icon(
//                                Icons.Default.MoreVert,
//                                contentDescription = "Compartir",
//                                tint = Color.White
//                            )
//                        }
//                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )

                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = ForestGreen
                        )
                    }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        wallpaper()
        Column(modifier = Modifier.padding(innerPadding)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
            ) { page ->
                when (page) {
                    0 -> agriculturalHolding?.let { agriculturalHoldingCardHomePage(it) }
                    1 -> plotCardHomePage()
                    2 -> irrigationCardHomePage()
                    3 -> phytosanitaryCardHomePage()
                }
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label:", modifier = Modifier.width(120.dp), fontWeight = FontWeight.Medium
        )
        Text(
            text = value.ifEmpty { "No proporcionado" },
            color = if (value.isEmpty()) Color.Gray else Color.Black
        )
    }
}