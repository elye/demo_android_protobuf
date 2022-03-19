package com.example.androidprotogenerator

import android.content.Context
import com.example.androidprotogenerator.data.ModeOption
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

object ProtoFile {

    private val fileName = "myfile.pbd"
    private val folder = "LET"

    fun writeToFile(context: Context, mode: ModeOption.Mode) {
        val outputMode = ModeOption.newBuilder().setMode(mode).build()
        context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
            it.write(outputMode.toByteArray())
        }
    }

    fun readFromFile(context: Context): ModeOption? {
        return try {
            val file = File(context.filesDir, fileName)
            ModeOption.parseFrom(FileInputStream(file))
        } catch (e: Exception) {
            null
        }
    }

    fun writeToExternalFile(context: Context, mode: ModeOption.Mode) {
        val outputMode = ModeOption.newBuilder().setMode(mode).build()
        val path = context.getExternalFilesDir(null)
        val letDirectory = File(path, folder)
        letDirectory.mkdirs()
        val file = File(letDirectory, fileName)

        FileOutputStream(file).use {
            it.write(outputMode.toByteArray())
        }
    }

    fun readFromExternalFile(context: Context): ModeOption? {
        return try {
            val path = context.getExternalFilesDir(null)
            val letDirectory = File(path, folder)
            letDirectory.mkdirs()
            val file = File(letDirectory, fileName)
            ModeOption.parseFrom(FileInputStream(file))
        } catch (e: Exception) {
            null
        }
    }
}