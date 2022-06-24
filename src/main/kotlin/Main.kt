import models.abstraction.Shape
import models.factory.ShapesFactory
import utils.*
import utils.serializer.SerializerImpl

val shapes = mutableListOf<Shape>()
val serializer = SerializerImpl()
val fileService = FileService("resources/resFile.txt")

fun main() {
    while (true) {
        printMenu()
        val option = getChoseAct(readln());
        performAction(option);
    }
}

fun performAction(action: Action) {
    when (action) {
        Action.Add -> addElement()
        Action.Edit -> editElement()
        Action.Delete -> deleteElement()
        Action.View -> viewElements()
        Action.Decode -> decodeElements()
        Action.Encode -> encodeElements()
    }
}

fun addElement() {
    printShapeType()
    addShape()
}

fun editElement() {
    printAllElements(shapes)
    println("Choose an element to edit:")
    val numEl = readln()
    shapes.removeAt(numEl.toInt())
    addShape()
}

fun deleteElement() {
    printAllElements(shapes)
    println("Choose an element to delete:")
    val numEl = readln()
    shapes.removeAt(numEl.toInt())
}

fun viewElements() {
    printAllElements(shapes)
}

fun decodeElements() {
    val sonString = fileService.loadFromFile()
    shapes.addAll(serializer.deserialize(sonString))
}

fun encodeElements() {
    fileService.saveToFile(serializer.serialize(shapes))
}

fun addShape() {
    println("Choose shape type: ")
    val shapeType = readln()
    val properties = getProperties(shapeType)
    val args = mutableListOf<Double>()
    try {
        properties.forEach { property ->
            println("Enter $property: ")
            val arg = readln()
            args.add(arg.toDouble())
        }
    } catch (_: Exception) {
        println("Error occurred! Try again")
        return
    }
    val shape = ShapesFactory.getInstance().getShape(shapeType, args)
    shapes.add(shape)
    println("Shape has been successfully added")
}

fun getChoseAct(input: String): Action {
    return Action.values().firstOrNull { act -> act.raw == input }!!
}
