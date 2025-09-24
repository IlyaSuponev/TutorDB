package com.isuponev.tutordb.core.models.values

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class NameTest {
    @ParameterizedTest
    @MethodSource("validInitProvider")
    fun `test valid init`(value: String) {
        val name = assertDoesNotThrow { Name.Builder.of(value) }
        Assertions.assertEquals(name.value, value.trim())
    }

    @ParameterizedTest
    @MethodSource("invalidInitProvider")
    fun `test invalid init`(value: String) {
        assertThrows<IllegalArgumentException> { Name.Builder.of(value) }
    }

    @ParameterizedTest
    @MethodSource("validInitProvider")
    fun `test to string`(value: String) {
        val name = Name.Builder.of(value)
        Assertions.assertEquals(name.value, value.trim())
    }

    @ParameterizedTest
    @MethodSource("equalsAndHashCodeProvider")
    fun `test equals and hash code`(name: Name, obj: Any?, isEqualsExpected: Boolean) {
        val isEquals = name == obj
        assertEquals(isEqualsExpected, isEquals)
        if (isEqualsExpected) {
            assertEquals(name.hashCode(), obj.hashCode())
        }
    }

    companion object {
        @JvmStatic
        fun validInitProvider(): List<Arguments> = listOf(
            Arguments.of("John"),
            Arguments.of("John Doe"),
            Arguments.of("Name with a lot of words"),
            Arguments.of("     Name with a lot of words and trailing spaces      "),
            Arguments.of(
                "     Name with a\tlot of\nwords and trailing spaces\nand different spaces characters      "
            ),
        )

        @JvmStatic
        fun invalidInitProvider(): List<Arguments> = listOf(
            Arguments.of(""),
            Arguments.of("name with small letter"),
            Arguments.of("Name!With@Special1"),
            Arguments.of("Name@With#Special2"),
            Arguments.of($$"Name#With$Special3"),
            Arguments.of($$"Name$With%Special3"),
            Arguments.of("Name%With-Special4"),
            Arguments.of("Name-With_Special4"),
            Arguments.of("Name_With+Special4"),
            Arguments.of("Name?With=Special4"),
            Arguments.of("Name with a lot    of spaces between     words"),
        )

        @JvmStatic
        fun equalsAndHashCodeProvider(): List<Arguments> = listOf(
            Arguments.of(
                Name.of("John Doe"),
                Name.of("John Doe"),
                true
            ),
            Name.of("John Doe").let { name ->
                Arguments.of(
                    name,
                    name,
                    true
                )
            },
            Arguments.of(
                Name.of("John Doe"),
                null,
                false
            ),
            Arguments.of(
                Name.of("John Doe"),
                "John Doe",
                false
            ),
            Arguments.of(
                Name.of("John Doe"),
                Name.of("Other John Doe"),
                false
            ),
        )
    }
}