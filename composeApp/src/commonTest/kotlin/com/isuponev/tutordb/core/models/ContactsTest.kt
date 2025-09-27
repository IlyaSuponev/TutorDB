package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.interfaces.Model
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.models.values.PhoneNumber
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ContactsTest {

    @Test
    fun `should create contacts with phone mapping`() {
        val id = Uuid.random()
        val phones = mapOf(
            Name.of("Mobile") to PhoneNumber.of("+79161234567"),
            Name.of("Home") to PhoneNumber.of("+12125551234")
        )
        val contacts = Contacts(id, phones)
        assertEquals(id, contacts.id)
        assertEquals(2, contacts.phones.size)
        for (name in contacts.phones.keys) {
            assertEquals(phones[name], contacts.phones[name])
        }
    }

    @Test
    fun `should create contacts with empty phone map`() {
        val id = Uuid.random()
        val emptyPhones = emptyMap<Name, PhoneNumber>()

        val contacts = Contacts(id, emptyPhones)
        assertTrue(contacts.phones.isEmpty())
    }

    @Test
    fun `contacts with same properties should be equal`() {
        val id = Uuid.random()
        val phones = mapOf(Name.of("Work") to PhoneNumber.of("+79161234567"))
        val contacts1 = Contacts(id, phones)
        val contacts2 = Contacts(id, phones)

        assertEquals(contacts1, contacts2)
        assertEquals(contacts1.hashCode(), contacts2.hashCode())
    }

    @Test
    fun `contacts with different phone maps should not be equal`() {
        val id = Uuid.random()
        val phones1 = mapOf(Name.of("Mobile") to PhoneNumber.of("+79161234567"))
        val phones2 = mapOf(Name.of("Mobile") to PhoneNumber.of("+12125551234"))

        val contacts1 = Contacts(id, phones1)
        val contacts2 = Contacts(id, phones2)

        assertNotEquals(contacts1, contacts2)
    }

    @Test
    fun `should support destructuring declaration`() {
        val contacts = createTestContacts()
        val (id, phones) = contacts

        assertEquals(contacts.id, id)
        assertEquals(contacts.phones, phones)
    }

    @Test
    fun `copy method should create modified instance`() {
        val original = createTestContacts()
        val newPhones = mapOf(Name.of("New") to PhoneNumber.of("+12125551234"))

        val copied = original.copy(phones = newPhones)
        assertEquals(original.id, copied.id)
        assertEquals(newPhones, copied.phones)
        assertNotEquals(original, copied)
    }

    private fun createTestContacts(): Contacts {
        return Contacts(
            id = Uuid.random(),
            phones = mapOf(
                Name.of("Mobile") to PhoneNumber.of("+12125551234"),
                Name.of("Home") to PhoneNumber.of("+79161234567")
            )
        )
    }
}