package com.example.androidprotogenerator

import android.content.Context
import com.example.androidprotogenerator.data.ModeOption
import java.io.File
import java.io.FileInputStream
import java.lang.Exception

object ProtoFile {

    private val fileName = "myfile.txt"

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

}