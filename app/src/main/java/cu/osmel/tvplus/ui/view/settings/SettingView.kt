package cu.osmel.tvplus.ui.view.settings

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import cu.osmel.tvplus.data.database.TvManageDatabase
import cu.osmel.tvplus.ui.navegation.AppView
import cu.osmel.tvplus.ui.viewmodel.common.MainViewModel
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


/**
 * Created by Osmel Pérez Alzola(osmelpa86@gmail.com) on 29/5/22
 */
@ExperimentalComposeUiApi
@Composable
fun SettingView(
    darkMode: MutableState<Boolean>,
    viewModel: MainViewModel,
) {
    viewModel.setCurrentScreen(AppView.ChannelView)
    viewModel.setCurrentAppBarActions(emptyList())

//    val context = LocalContext.current
//    val launcher = rememberLauncherForActivityResult(
//        contract =
//        ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        channelViewModel.imageUri = uri
//    }

    SettingViewContent()
}

@ExperimentalComposeUiApi
@Composable
fun SettingViewContent(
) {
    val context = LocalContext.current
    var resultExportLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data !== null) {
                    var uriExport: Uri? = result.data!!.data
                    try {
                        TvManageDatabase.getDatabase(context).close()
                        val inFileName: String =
                            context!!.getDatabasePath("tv_manage").absolutePath
                        val fileDescriptor: ParcelFileDescriptor =
                            context.getContentResolver()
                                .openFileDescriptor(uriExport!!, "w")!!
                        val fileOutputStream =
                            FileOutputStream(fileDescriptor.fileDescriptor)
                        fileOutputStream.write(
                            FileUtils.readFileToByteArray(
                                File(inFileName)
                            )
                        )
                        fileOutputStream.close()
                        fileDescriptor.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    val resultImportLauncher =    rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data !== null) {
                var uriImport: Uri? = result.data!!.data
                try {
                    TvManageDatabase.getDatabase(context).close()
                    val inFileName: String =
                        context.getDatabasePath("tv_manage")
                            .toString()
                    val inputStream: InputStream? =
                        context.getContentResolver()
                            .openInputStream(uriImport!!)
                    FileUtils.copyInputStreamToFile(
                        inputStream,
                        File(inFileName)
                    )
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }

    Column {
        Button(onClick = {
            val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            intent.putExtra(
                Intent.EXTRA_TITLE,
                "tv_manage"
            )
            resultExportLauncher.launch(intent)
        }) {
            Text(text = "Exportar")
        }

        Button(onClick = {
            val intent =
                Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            resultImportLauncher.launch(intent)
        }) {
            Text(text = "Importar")
        }
    }
}