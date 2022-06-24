package utils

enum class Action(val raw: String) {
    Add("1"),
    Edit("2"),
    Delete("3"),
    View("4"),
    Encode("5"),
    Decode("6")
}