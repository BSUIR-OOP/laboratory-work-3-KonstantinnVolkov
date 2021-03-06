package models.figures

import models.abstraction.Shape

class Rectangle (
    override var x: Double,
    override var y: Double,
    override var width: Double,
    override var height: Double
): Shape(x, y, width, height)