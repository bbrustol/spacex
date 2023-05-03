
package com.bbrustol.features.home.compose

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.bbrustol.uikit.R as UIKIT_R
import com.bbrustol.features.home.compose.utils.LaunchPreviewParamProvider
import com.bbrustol.features.home.model.LaunchesModel

@Composable
fun LaunchesDialog(openDialogCustom: MutableState<Boolean>, launchesModel: LaunchesModel) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        LaunchesDialogUI(openDialogCustom = openDialogCustom, launchesModel = launchesModel)
    }
}

//Layout
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaunchesDialogUI(
    openDialogCustom: MutableState<Boolean>,
    launchesModel: LaunchesModel
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 20.dp
                )
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            CompositionLocalProvider(
                LocalMinimumTouchTargetEnforcement provides false,
            ) {
                IconButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = { openDialogCustom.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(UIKIT_R.string.speech_close_dialog),
                    )
                }

                Text(
                    text = "Choose",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 4.dp)
                        .align(
                            Alignment.CenterHorizontally
                        ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            if (!launchesModel.wikipediaUrl.isNullOrBlank()) {
                ButtonDialog(openDialogCustom, launchesModel.wikipediaUrl, "Wikipedia")
            }
            if (!launchesModel.articleUrl.isNullOrBlank()) {
                ButtonDialog(openDialogCustom, launchesModel.articleUrl, "Article")
            }
            if (!launchesModel.videoUrl.isNullOrBlank()) {
                ButtonDialog(openDialogCustom, launchesModel.videoUrl, "Video")
            }
        }
    }
}

@Composable
private fun ButtonDialog(
    openDialogCustom: MutableState<Boolean>,
    url: String,
    title: String
) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(url)) }

    Button(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp, 8.dp, 8.dp, 0.dp),
        onClick = {
            context.startActivity(intent)
            openDialogCustom.value = false }
    ) {
        Text(
            title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
        )
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun LaunchesDialogUIPreview(
    @PreviewParameter(LaunchPreviewParamProvider::class) launchesModel: LaunchesModel
) {
    LaunchesDialogUI(openDialogCustom = mutableStateOf(false), launchesModel = launchesModel)
}