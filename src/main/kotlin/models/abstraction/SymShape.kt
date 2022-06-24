package models.abstraction

abstract class SymShape (
    override var x: Double,
    override var y: Double,
    side: Double
): Shape(x, y, side, side)