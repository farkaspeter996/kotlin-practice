enum class ContactTypeEnum(val contactValue: String) {
    CONTACT("Kapcsolattartó"),
    OWNER("Tulajdonos"),
    EXECUTIVE("Ügyvezető"),
    DEFAULT("Kapcsolattartó");

    //TODO PIPA return default
    companion object {
        fun findByValue(enumValue: String?): ContactTypeEnum {
            return try {
                values().first { value -> value.contactValue == enumValue }
            } catch (exception: NoSuchElementException) {
                DEFAULT
            }
        }
    }

}