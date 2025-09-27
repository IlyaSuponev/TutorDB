package com.isuponev.tutordb.core.models.values

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import com.isuponev.tutordb.core.HASH_CODE_NUMBER_GENERATOR
import java.io.Serializable

/**
 * Represents a validated international phone number value object.
 *
 * This class encapsulates a phone number with guaranteed validity through parsing
 * and validation using Google's libphonenumber library. All instances are immutable
 * and thread-safe.
 *
 * The phone number is stored in its parsed format and provides access to regional
 * information and country codes.
 *
 * @see Phonenumber.PhoneNumber
 * @see PhoneNumberUtil
 * @see Serializable
 */
class PhoneNumber private constructor(private val phone: Phonenumber.PhoneNumber) : Serializable {

    /**
     * Returns the region code (country code) for this phone number.
     *
     * The region code is determined based on the phone number's country calling code
     * and national number. Returns "ZZ" for international numbers or if the region
     * cannot be determined.
     *
     * @return two-letter ISO region code (e.g., "US", "RU", "GB") or "ZZ" if unknown
     */
    fun regionCode(): String = UTILS.getRegionCodeForNumber(phone)

    /**
     * Returns the country calling code for the region of this phone number.
     *
     * The country code is determined based on the region code obtained from the phone number.
     * For example, returns 1 for US numbers, 7 for Russian numbers, etc.
     *
     * @return the country calling code as integer
     */
    fun countryCode(): Int = UTILS.getCountryCodeForRegion(regionCode())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneNumber
        return phone == other.phone
    }

    override fun hashCode(): Int = HASH_CODE_NUMBER_GENERATOR * javaClass.name.hashCode() + phone.hashCode()

    /**
     * Returns a string representation of the phone number in the specified format.
     *
     * @param format the desired phone number formatting style
     * @return the formatted phone number string
     *
     * @see PhoneNumberUtil.PhoneNumberFormat
     */
    fun toString(format: PhoneNumberUtil.PhoneNumberFormat): String = UTILS.format(phone, format)

    /**
     * Returns a string representation of the phone number in E.164 format.
     *
     * Default format is E164 (e.g., "+16502530000" for US numbers).
     *
     * @return the phone number formatted in E.164 style
     */
    override fun toString() = toString(PhoneNumberUtil.PhoneNumberFormat.E164)

    /**
     * Companion object containing factory methods for creating validated PhoneNumber instances.
     */
    companion object Builder {
        private const val serialVersionUID = 8652615001379987L
        private val UTILS = PhoneNumberUtil.getInstance()

        /**
         * Creates a PhoneNumber instance from a string representation.
         *
         * Parses the phone number string and validates it according to international standards.
         * The phone number should be in international format (with + prefix) or national format
         * with explicit region context.
         *
         * @param phone the string representation of the phone number to parse
         * @return a validated PhoneNumber instance
         * @throws IllegalArgumentException if the string cannot be parsed as a phone number
         *         or if the parsed number is not valid according to libphonenumber validation
         */
        @Throws(IllegalArgumentException::class)
        fun of(phone: String): PhoneNumber {
            val phone = try {
                UTILS.parse(phone, null)
            } catch (_: NumberParseException) {
                null
            }
            requireNotNull(phone) { "It is not phone number" }
            require(UTILS.isValidNumber(phone)) { "It is not valid number" }
            return PhoneNumber(phone)
        }
    }
}
