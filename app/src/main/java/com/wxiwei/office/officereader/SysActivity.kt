/*
 * 文件名称:          SysActivity.java
 *  
 * 编译器:            android2.2
 * 时间:              下午4:48:16
 */
package com.wxiwei.office.officereader

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.wxiwei.office.constant.MainConstant
import com.wxiwei.office.system.FileKit
import com.wxiwei.office.system.IControl
import java.io.File
import java.io.InputStream

/**
 * Office Reader 主控activity
 *
 *
 *
 *
 * Read版本:        Read V1.0
 *
 *
 * 作者:            ljj8494
 *
 *
 * 日期:            2011-11-28
 *
 *
 * 负责人:          ljj8494
 *
 *
 * 负责小组:
 *
 *
 *
 *
 */
class SysActivity : Activity() {
    
    /**
     * 构造器
     */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        control = SysControl(this)
        sysFrame = SysFrame(this, control)
        sysFrame!!.post { init() }
        setContentView(sysFrame)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
        }
    }

    fun pickFile() {
        val permissionCheck = ContextCompat.checkSelfPermission(control!!.activity, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_CODE
            )
            return
        }
        launchPicker()
    }

    fun launchPicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        //        intent.setType("application/pdf")
        intent.setType("*/*")
        try {
            startActivityForResult(intent, REQUEST_CODE)
        } catch (e: ActivityNotFoundException) {
            //alert user that file manager not working
            Toast.makeText(this, "掉起系统面板失败", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleFile(uri: Uri) {
        val fileRet = uri2file(this, uri)
        val file = fileRet.getOrNull()
        if (file == null) {
            Toast.makeText(this, "复制文件失败：${fileRet.exceptionOrNull()}", Toast.LENGTH_SHORT).show()
            return
        }
        if (FileKit.instance().isSupport(file.getName())) {
            val intent = Intent()
            intent.setClass(this, AppActivity::class.java)
            intent.putExtra(MainConstant.INTENT_FILED_FILE_PATH, file.getAbsolutePath())
            startActivityForResult(intent, RESULT_FIRST_USER)
        }
    }

    fun uri2file(activity: Activity, uri: Uri, forcibly: Boolean = false): Result<File> {
        var fileName: String? = null
        var cursor: Cursor? = null
        try {
            cursor = activity.contentResolver.query(
                    uri,
                    arrayOf<String>(MediaStore.Images.ImageColumns.DISPLAY_NAME),
                    null,
                    null,
                    null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME)
                fileName = cursor.getString(columnIndex)
            }
        } catch (e: Exception) {
            return Result.failure(e)
        } finally {
            cursor?.close()
        }
        val tmpDir = File(activity.externalCacheDir, "tmp")
        if (!tmpDir.exists()) {
            tmpDir.mkdirs()
        }
        val tmpFile = File(tmpDir, "$fileName")
        if (tmpFile.exists() && !forcibly) {
            return Result.success(tmpFile)
        }
        var openInputStream: InputStream? = null
        try {
            openInputStream = activity.contentResolver.openInputStream(uri)
            if (openInputStream == null) {
                return Result.failure(Exception("打开文件失败"))
            }
            openInputStream.copyTo(tmpFile.outputStream())
        } catch (e: Exception) {
            return Result.failure(Exception("打开文件失败：${e.message}"))
        } finally {
            openInputStream?.close()
        }
        return Result.success(tmpFile)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            if (uri != null) {
                handleFile(uri)
            } else {
                Toast.makeText(this, "选择文件失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     *
     */
    fun init() {
        sysFrame!!.init()
    }

    /**
     *
     *
     */
    fun getSysFrame(): View? {
        return sysFrame
    }

    /**
     *
     */
    override fun onBackPressed() {
        super.onBackPressed()
        System.exit(0)
    }

    /**
     *
     *
     */
    fun dispose() {
        sysFrame!!.dispose()
        control!!.dispose()
    }

    //
    private var sysFrame: SysFrame? = null

    //
    private var control: IControl? = null

    companion object {
        private const val REQUEST_CODE = 42
        private const val PERMISSION_CODE = 42042
    }
}
