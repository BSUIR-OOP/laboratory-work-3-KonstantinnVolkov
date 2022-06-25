package utils

import models.abstraction.Shape
import models.factory.ShapesFactory

fun printAllElements(elements: List<Shape>) {
    elements.forEachIndexed() { indx, shape ->
        print("$indx - ${shape::class.simpleName}\n")
    }
}

fun printShapeType() {
    println("""
        Choose shape type:
        ${ShapesFactory.getInstance().getNames()}
    """.trimIndent())
}

fun getProperties(shapeType: String): List<String> {
    val mock = ShapesFactory.getInstance().getShape(shapeType, listOf(0.0,0.0,0.0,0.0))
    return mock::class.java.declaredFields.map { it.name }
}

fun printMenu() {
    println("""
        Choose an option:
        1.Add Entity,
        2.Edit Entity, 
        3.Delete Entity
        4.View Entities, 
        5.Encode in File, 
        6.Decode from File
    """.trimIndent())
}