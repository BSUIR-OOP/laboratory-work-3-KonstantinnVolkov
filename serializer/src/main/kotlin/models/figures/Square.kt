package models.figures

import models.abstraction.SymShape

class Square (
    override var x: Double,
    override var y: Double,
    var side: Double
): SymShape(x, y, side)