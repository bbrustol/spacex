package com.bbrustol.uikit.compose.scaffold

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DrawerHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 64.dp)
        ,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(text = "Header", fontSize = 60.sp)
    }
}

@Composable
fun DrawerBody(closeNavDrawer: () -> Unit) {
    Column {
        DrawerMenuItem(
            imageVector = Icons.Default.Home,
            text = "Item 1",
            onItemClick = {
                closeNavDrawer()
            }
        )
        DrawerMenuItem(
            imageVector = Icons.Default.Search,
            text = "Item 2",
            onItemClick = {
                closeNavDrawer()
            }
        )
    }
}

@Composable
private fun DrawerMenuItem(
    imageVector: ImageVector,
    text: String,
    onItemClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {onItemClick()}
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ){
        Icon(
            imageVector = imageVector,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, )
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun DrawerHeaderPreview(){
    DrawerHeader()
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun DrawerBodyPreview(){
    DrawerBody(closeNavDrawer = {})
}