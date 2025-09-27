package com.isuponev.tutordb.core.models.values

import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

/**
 * Unit tests for [PhoneNumber] class.
 *
 * @see PhoneNumber
 */
class PhoneNumberTest {

    @Test
    fun `test of with valid international number`() {
        val validNumber = "+79161234567"

        val phoneNumber = PhoneNumber.of(validNumber)
        assertTrue(phoneNumber.toString().isNotBlank())
    }

    @Test
    fun `test of with valid US number`() {
        val validNumber = "+14155552671"

        val phoneNumber = PhoneNumber.of(validNumber)
        assertEquals("US", phoneNumber.regionCode())
        assertEquals(1, phoneNumber.countryCode())
    }

    @Test
    fun `test of with valid UK number`() {
        val validNumber = "+442072193000"

        val phoneNumber = PhoneNumber.of(validNumber)
        assertEquals("GB", phoneNumber.regionCode())
        assertEquals(44, phoneNumber.countryCode())
    }

    @Test
    fun `test of with invalid format should throw IllegalArgumentException`() {
        val invalidNumber = "not-a-phone-number"

        val exception = assertFailsWith<IllegalArgumentException> {
            PhoneNumber.of(invalidNumber)
        }
        assertEquals(exception.message?.contains("It is not phone number"), true)
    }

    @Test
    fun `test of with invalid phone number should throw IllegalArgumentException`() {
        val invalidNumber = "+1234567" // Too short to be valid

        val exception = assertFailsWith<IllegalArgumentException> {
            PhoneNumber.of(invalidNumber)
        }
        assertEquals(exception.message?.contains("It is not valid number"), true)
    }

    @Test
    fun `test regionCode returns correct region for Russian number`() {
        val russianNumber = "+79161234567"
        val phoneNumber = PhoneNumber.of(russianNumber)

        val regionCode = phoneNumber.regionCode()
        assertEquals("RU", regionCode)
    }

    @Test
    fun `test regionCode returns correct region for US number`() {
        val usNumber = "+12125551234"

        val phoneNumber = PhoneNumber.of(usNumber)
        val regionCode = phoneNumber.regionCode()
        assertEquals("US", regionCode)
    }

    @Test
    fun `test countryCode returns correct code for Russian number`() {
        val russianNumber = "+79161234567"

        val phoneNumber = PhoneNumber.of(russianNumber)
        val countryCode = phoneNumber.countryCode()
        assertEquals(7, countryCode)
    }

    @Test
    fun `test countryCode returns correct code for US number`() {
        val usNumber = "+12125551234"

        val phoneNumber = PhoneNumber.of(usNumber)
        val countryCode = phoneNumber.countryCode()
        assertEquals(1, countryCode)
    }

    @Test
    fun `test equals returns true for same phone numbers`() {
        val number1 = PhoneNumber.of("+79161234567")
        val number2 = PhoneNumber.of("+79161234567")

        assertEquals(number1, number2)
        assertEquals(number1.hashCode(), number2.hashCode())
    }

    @Test
    fun `test equals returns false for different phone numbers`() {
        val number1 = PhoneNumber.of("+79161234567")
        val number2 = PhoneNumber.of("+79161234568")


        assertNotEquals(number1, number2)
    }

    @Test
    fun `test equals returns false for different type`() {
        val phoneNumber = PhoneNumber.of("+79161234567")
        val string = "not a phone number"

        assertFalse(phoneNumber.equals(string))
    }

    @Test
    fun `test equals returns true for same object`() {
        val phoneNumber = PhoneNumber.of("+79161234567")

        assertEquals(phoneNumber, phoneNumber)
    }

    @Test
    fun `test toString with INTERNATIONAL format`() {
        val phoneNumber = PhoneNumber.of("+79161234567")

        val result = phoneNumber.toString(PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
        assertEquals("+7 916 123-45-67", result)
    }

    @Test
    fun `test toString with NATIONAL format`() {
        val phoneNumber = PhoneNumber.of("+79161234567")

        
        val result = phoneNumber.toString(PhoneNumberUtil.PhoneNumberFormat.NATIONAL)
        assertEquals("8 (916) 123-45-67", result)
    }

    @Test
    fun `test toString with E164 format`() {
        val phoneNumber = PhoneNumber.of("+79161234567")
        
        val result = phoneNumber.toString(PhoneNumberUtil.PhoneNumberFormat.E164)
        assertEquals("+79161234567", result)
    }

    @Test
    fun `test default toString uses INTERNATIONAL format`() {
        val phoneNumber = PhoneNumber.of("+79161234567")

        val defaultString = phoneNumber.toString()
        val explicitString = phoneNumber.toString(PhoneNumberUtil.PhoneNumberFormat.E164)
        assertEquals(explicitString, defaultString)
    }

    @Test
    fun `test phone numbers from different regions are not equal`() {
        val russianNumber = PhoneNumber.of("+79161234567")
        val usNumber = PhoneNumber.of("+12125551234")

        assertNotEquals(russianNumber, usNumber)
    }
}