package com.isuponev.tutordb.core.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.assertThrows

class BooleanTest {
    @Test
    fun testAllTrue() {
        val result = all({ true }, { true }, { true })
        assertTrue(result)
    }

    @Test
    fun testOneFalse() {
        val result = all({ true }, { false }, { true })
        assertFalse(result)
    }

    @Test
    fun testEmptyConditions() {
        val result = all()
        assertTrue(result)
    }

    @Test
    fun testLazyOneFalse() {
        var evaluationCount = 0
        val result = all(
            { evaluationCount++ > -1 }, // Always true, count = 1
            { evaluationCount++ > 3 },  // False, count = 2, should short-circuit
            { evaluationCount++ > 2 }   // Should not execute
        )

        assertFalse(result)
        assertEquals(2, evaluationCount)
    }

    @Test
    fun testLazyMoreThanOneFalse() {
        var evaluationCount = 0
        val result = all(
            { evaluationCount++ > -1 }, // Always true, count = 1
            { evaluationCount++ > 3 },  // False, count = 2, should short-circuit
            { evaluationCount++ > 4 },  // False, Should not execute
            { evaluationCount++ > 2 }   // Should not execute
        )

        assertFalse(result)
        assertEquals(2, evaluationCount)
    }

    @Test
    fun testAllLazyWithExpensiveOperations() {
        var expensiveCallCount = 0
        var mediumCallCount = 0

        val result = all(
            { true },
            {
                mediumCallCount++
                false // This will cause short-circuit
            },
            {
                expensiveCallCount++ // Should not execute
                Thread.sleep(100) // Simulate expensive operation
                true
            }
        )

        assertFalse(result)
        assertTrue(mediumCallCount == 1)
        assertTrue(expensiveCallCount == 0) // Never executed due to short-circuit
    }

    @Test
    fun testSingleCondition() {
        assertTrue(all({ true }))
        assertFalse(all({ false }))
    }

    @Test()
    fun testExceptionThrows() {
        assertThrows<RuntimeException> {
            all(
                { true },
                { throw RuntimeException("Condition failed") },
                { true }
            )
        }
    }
}
