package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.models.values.PhoneNumber
import org.javamoney.moneta.Money
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class StudentTest {

    @Test
    fun `should create student with required parameters`() {
        val id = Uuid.random()
        val name = Name.of("John Doe")
        val hourCost = Money.of(50, "USD")
        
        val student = Student(id, name, hourCost)
        assertEquals(id, student.id)
        assertEquals(name, student.name)
        assertEquals(hourCost, student.hourCost)
        assertNull(student.contacts)
        assertTrue(student.subjects.isEmpty())
    }

    @Test
    fun `should create student with all optional parameters`() {
        val student = createTestStudent()

        assertEquals("Alice Smith", student.name.value)
        assertEquals(Money.of(60, "USD"), student.hourCost)
        assertTrue(student.contacts != null)
        assertEquals(2, student.subjects.size)
    }

    @Test
    fun `students with same properties should be equal`() {
        val id = Uuid.random()
        val student1 = Student(id, Name.of("Bob"), Money.of(40, "USD"))
        val student2 = Student(id, Name.of("Bob"), Money.of(40, "USD"))
        
        assertEquals(student1, student2)
        assertEquals(student1.hashCode(), student2.hashCode())
    }

    @Test
    fun `students with different hour cost should not be equal`() {
        val id = Uuid.random()
        val student1 = Student(id, Name.of("Bob"), Money.of(40, "USD"))
        val student2 = Student(id, Name.of("Bob"), Money.of(50, "USD"))

        assertNotEquals(student1, student2)
    }

    @Test
    fun `copy method should modify specified properties`() {
        val original = createTestStudent()
        val newHourCost = Money.of(70, "USD")

        val modified = original.copy(hourCost = newHourCost)
        assertEquals(original.id, modified.id)
        assertEquals(original.name, modified.name)
        assertEquals(newHourCost, modified.hourCost)
        assertEquals(original.contacts, modified.contacts)
        assertEquals(original.subjects, modified.subjects)
    }

    @Test
    fun `should handle null contacts correctly`() {
        val student = Student(
            id = Uuid.random(),
            name = Name.of("Student without contacts"),
            hourCost = Money.of(30, "USD"),
            contacts = null
        )

        assertNull(student.contacts)
    }

    @Test
    fun `should handle empty subjects set`() {
        val student = Student(
            id = Uuid.random(),
            name = Name.of("New student"),
            hourCost = Money.of(25, "USD"),
            subjects = emptySet()
        )

        assertTrue(student.subjects.isEmpty())
    }

    @Test
    fun `should support destructuring`() {
        val student = createTestStudent()

        val (id, name, hourCost, contacts, subjects) = student
        assertEquals(student.id, id)
        assertEquals(student.name, name)
        assertEquals(student.hourCost, hourCost)
        assertEquals(student.contacts, contacts)
        assertEquals(student.subjects, subjects)
    }

    private fun createTestStudent(): Student {
        return Student(
            id = Uuid.random(),
            name = Name.of("Alice Smith"),
            hourCost = Money.of(60, "USD"),
            contacts = Contacts(
                id = Uuid.random(),
                phones = mapOf(Name.of("Mobile") to PhoneNumber.of("+12125551234"))
            ),
            subjects = setOf(
                Subject(Uuid.random(), Name.of("Math"), "Mathematics"),
                Subject(Uuid.random(), Name.of("Physics"), "Physics")
            )
        )
    }
}