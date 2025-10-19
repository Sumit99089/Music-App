package com.example.music.presentation.permissions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import android.provider.Settings

@Composable
fun PermissionHandler(
    content: @Composable () -> Unit
){

    val context = LocalContext.current

    // Define the permissions needed based on the Android version
    val permissionsToRequest = remember {
        //Tiramisu is Android 13.
        // For Android 13 we need to request the POST_NOTIFICATION permission for playing media.
        // Older versions don't need notification Permissions
        // Also for accessing Media we need the READ_EXTERNAL_STORAGE permission for older version,
        // But for newer Versions we need the READ_MEDIA_AUDIO permission.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.READ_MEDIA_AUDIO
            )
        }
        else {
            listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            )
        }
    }

    //True if all Permission Granted else False
    var permissionState by remember {
        mutableStateOf(
            permissionsToRequest.all {
                context.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionMap ->
        permissionState = permissionMap.values.all { it }
    }
    // permissionMap = [{POST_NOTIFICATIONS, true}, {READ_EXTERNAL_STORAGE, true}]
    // permissionMap.values = [true, true]
    // permissionMap.values.all = true { as both values are true }

    LaunchedEffect(Unit) {
        if(!permissionState){
            launcher.launch(permissionsToRequest.toTypedArray())
        }
    }

    if(permissionState){
        content()
    }
    else{
        PermissionRationaleScreen(
            onGrantClicked = {
                launcher.launch(permissionsToRequest.toTypedArray())
            },
            onGoToSettingsClicked = {
                context.openAppSettings()
            }
        )
    }

}

private fun Context.openAppSettings() {
    val intent = Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    )
    startActivity(intent)
}