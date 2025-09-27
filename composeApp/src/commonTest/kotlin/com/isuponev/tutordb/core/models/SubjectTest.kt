package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.values.Name
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class SubjectTest {

    @Test
    fun `should create subject with valid parameters`() {
        val id = Uuid.random()
        val name = Name.of("Mathematics")
        val description = "Advanced calculus and algebra"
        
        val subject = Subject(id, name, description)
        assertEquals(id, subject.id)
        assertEquals(name, subject.name)
        assertEquals(description, subject.description)
    }

    @Test
    fun `should have correct string representation`() {
        val subject = createTestSubject()

        val stringRepr = subject.toString()
        assertTrue(stringRepr.contains("Mathematics"))
        assertTrue(stringRepr.contains(subject.id.toString()))
    }

    @Test
    fun `subjects with same properties should be equal`() {
        val id = Uuid.random()
        val subject1 = Subject(id, Name.of("Physics"), "Physics description")
        val subject2 = Subject(id, Name.of("Physics"), "Physics description")

        assertEquals(subject1, subject2)
        assertEquals(subject1.hashCode(), subject2.hashCode())
    }

    @Test
    fun `subjects with different ids should not be equal`() {
        val subject1 = Subject(Uuid.random(), Name.of("Chemistry"), "Chem desc")
        val subject2 = Subject(Uuid.random(), Name.of("Chemistry"), "Chem desc")

        assertNotEquals(subject1, subject2)
    }

    @Test
    fun `subjects with different names should not be equal`() {
        val id = Uuid.random()
        val subject1 = Subject(id, Name.of("Biology"), "Description")
        val subject2 = Subject(id, Name.of("Geography"), "Description")

        assertNotEquals(subject1, subject2)
    }

    private fun createTestSubject(): Subject {
        return Subject(
            id = Uuid.random(),
            name = Name.of("Mathematics"),
            description = "Advanced mathematics course"
        )
    }
}