package com.isuponev.tutordb.database.columns

import com.isuponev.tutordb.core.models.values.PhoneNumber
import com.isuponev.tutordb.desktop.database.columns.PhoneNumberColumnType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class PhoneNumberColumnTypeTest {
    @Test
    fun `PhoneNumberColumnType should handle string type correctly`() {
        val phoneNumberColumnType = PhoneNumberColumnType()
        val value = "+79161234567"
        val result = phoneNumberColumnType.valueFromDB(value)
        assertEquals(PhoneNumber.of(value), result)
    }

    @Test
    fun `PhoneNumberColumnType should handle Name type correctly`() {
        val phoneNumberColumnType = PhoneNumberColumnType()
        val value = PhoneNumber.of("+79161234567")
        val result = phoneNumberColumnType.valueFromDB(value)
        assertEquals(value, result)
    }

    @Test
    fun `PhoneNumberColumnType should handle invalid type correctly`() {
        val phoneNumberColumnType = PhoneNumberColumnType()
        val value = 19L
        assertThrows<IllegalArgumentException> { phoneNumberColumnType.valueFromDB(value) }
    }
}
