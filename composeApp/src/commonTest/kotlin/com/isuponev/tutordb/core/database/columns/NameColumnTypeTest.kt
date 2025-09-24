package com.isuponev.tutordb.core.database.columns

import com.isuponev.tutordb.core.models.values.Name
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class NameColumnTypeTest {
    @Test
    fun `NameColumn should handle string type correctly`() {
        val nameColumnType = NameColumnType()
        val value = "Test Name"
        val result = nameColumnType.valueFromDB(value)
        assertEquals(Name.of(value), result)
    }

    @Test
    fun `NameColumn should handle Name type correctly`() {
        val nameColumnType = NameColumnType()
        val value = Name.of("Test Name")
        val result = nameColumnType.valueFromDB(value)
        assertEquals(value, result)
    }

    @Test
    fun `NameColumn should handle invalid type correctly`() {
        val nameColumnType = NameColumnType()
        val value = 19L
        assertThrows<IllegalArgumentException> { nameColumnType.valueFromDB(value) }
    }
}
