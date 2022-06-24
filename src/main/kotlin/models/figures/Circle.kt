package models.figures

import models.abstraction.SymShape

data class Circle (
    override var x: Double,
    override var y: Double,
    var side: Double
): SymShape(x, y, side)
