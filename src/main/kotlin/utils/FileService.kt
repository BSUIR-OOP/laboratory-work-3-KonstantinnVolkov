package utils

import java.io.File
import java.nio.charset.Charset

class FileService constructor(val fileName: String) {

    private val filePath: String = "./"
    private val file: File

    init {
        if (File(fileName).exists()) {
            File(fileName).delete()
        }
        file = File(fileName)
    }

    fun saveToFile(string: String) {
        file.writeText(string, Charset.defaultCharset())
    }

    fun loadFromFile(): String {
        return file.readLines().joinToString()
    }
}