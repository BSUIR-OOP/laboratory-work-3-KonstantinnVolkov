package utils.serializer

interface Serializer<T> {
    fun serialize(objects: Collection<T>): String
    fun deserialize(input: String): Collection<T>
}