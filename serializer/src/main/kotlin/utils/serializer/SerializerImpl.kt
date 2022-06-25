package utils.serializer

import models.abstraction.Shape
import models.factory.ShapesFactory

class SerializerImpl: Serializer<Shape> {
    private val ids = listOf("type", "fields")
    private lateinit var fields: Collection<String>

    private var currentIndex = 0
    private var jsonString = ""

    override fun serialize(objects: Collection<Shape>): String {
        var out = String()
        objects.forEach { shape ->
            out += "{type=${shape::class.simpleName},fields={${getArgs(shape)}}},\n"
        }
        return out.substring(0..out.length - 3)
    }

    override fun deserialize(input: String): Collection<Shape> {
        jsonString = input
        val result = mutableListOf<Shape>()
        val objects = input.replace(" ", "").split("\n")
        objects.forEach { string ->
            try { result.add(getObject(string)) }
            catch (ex: Exception) { ex.printStackTrace() }
        }

        return result
    }

    private fun getObject(string: String): Shape {
        currentIndex = 0
        jsonString = string
        val args = mutableListOf<Double>()
        val typeToken = readToken()
        try {
            while (true) {
                val arg = readToken().second.toDouble()
                args.add(arg)
            }
        } catch (_: Exception) {}
        return ShapesFactory.getInstance().getShape(typeToken.second, args)
    }

    private fun readToken(): Pair<String, String> {
        val currentLetter = jsonString[currentIndex]
        if (!currentLetter.isLetterOrDigit() && (currentLetter != '.')) {
            currentIndex++
            return readToken()
        }
        var id = readWord()

        if (id == "type") {
            skipSymbols()
            val value = readWord()
            initFieldConstants(value)
            return id to value
        }
        else if (id == "fields") {
            skipWord()
            id = readWord()
            skipWord()
            val value = readWord()
            return id to value
        }
        else if (fields.contains(id)) {
            skipSymbols()
            val value = readWord()
            return id to value
        }

        if (!ids.contains(id) && !fields.contains(id)) {
            throw java.lang.IllegalArgumentException("Token not found $id")
        }

        throw IllegalArgumentException("Illegal format")
    }

    private fun initFieldConstants(objectType: String) {
        val obj = ShapesFactory.getInstance().getShape(objectType, listOf(0.0, 0.0, 0.0, 0.0))
        fields = obj::class.memberProperties.map { prop -> prop.name }
    }

    private fun readWord(): String {
        var currentLetter = jsonString[currentIndex]
        var res = ""
        if (currentLetter.isLetterOrDigit() || currentLetter == '.') {
            currentIndex++
            res += currentLetter + readWord()
        }
        return res
    }

    private fun skipSymbols() {
        while (!jsonString[currentIndex].isLetterOrDigit()) {
            currentIndex++
        }
    }

    private fun skipWord() {
        while (jsonString[currentIndex].isLetterOrDigit()) {
            currentIndex++
        }
        return skipSymbols()
    }

    private fun getArgs(shape: Shape): String {
        var out = String()
        val javaClass = shape.javaClass
        javaClass.declaredFields.forEach { field ->
            field.isAccessible = true
            val fieldName = field.name
            val fieldValue = field.get(shape)
            out += "${fieldName}=${fieldValue},"
        }
        return out.substring(0..out.length - 2)
    }
}