@file:OptIn(ExperimentalLayoutApi::class)

package com.bbrustol.uikit.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment

@SuppressLint("MutableCollectionMutableState")
@Composable
fun FilterChipLayout(
    chipList: Set<String>,
    chipChecked: Set<String>,
    onSelected: (selected: Set<String>) -> Unit
) {
    val originalChips by remember { mutableStateOf(chipList) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        FilterChipEachRow(chipList = originalChips, chipChecked = chipChecked) { selected ->
            onSelected(selected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChipEachRow(
    chipList: Set<String>,
    chipChecked: Set<String>,
    onClipClicked: (selected: Set<String>) -> Unit
) {
    var selected by rememberSaveable { mutableStateOf(chipChecked) }

    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        chipList.forEach { value ->
            val contains = selected.contains(value)
            Spacer(modifier = Modifier.padding(4.dp, 4.dp, 4.dp, 0.dp))
            FilterChip(
                selected = contains,
                onClick = {
                    selected = if (contains) selected.minus(value) else selected.plus(value)
                    onClipClicked(selected)
                },
                label = { Text(text = value) },
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = if (contains) Color.Green else Color.Red,
                    borderWidth = if (contains) 2.dp else 2.dp
                ),
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        (if (contains) Icons.Default.Check else Icons.Default.AddCircle),
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
fun ChipListPreview() {
    FilterChipLayout(
        setOf("2012", "2014", "2015", "2016", "2017", "2012"), setOf("2012", "2014")
    ) {
        it.forEach() { item ->
            println("selected - $item")
        }
        println("--------")
    }
}