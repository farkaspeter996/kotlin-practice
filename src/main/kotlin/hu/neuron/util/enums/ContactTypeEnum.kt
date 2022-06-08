enum class ContactTypeEnum(val contactValue: String) {
    CONTACT("Kapcsolattartó"),
    OWNER("Tulajdonos"),
    EXECUTIVE("Ügyvezető");

    companion object {
        fun from(enumValue: String): String {
            return values().find { it.contactValue == enumValue }.toString()
        }
    }
}