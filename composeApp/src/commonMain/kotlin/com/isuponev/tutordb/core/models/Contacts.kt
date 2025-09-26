package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.models.interfaces.Model
import com.isuponev.tutordb.core.models.values.Name
import com.isuponev.tutordb.core.models.values.PhoneNumber
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

/**
 * Represents contact information for a person or organization.
 *
 * Stores multiple phone numbers associated with descriptive names (e.g., "Home", "Mobile", "Work").
 * Uses a map structure to allow flexible contact management with labeled phone numbers.
 *
 * @property id The unique identifier for the contacts collection
 * @property phones Mapping of contact labels to phone numbers using [Name] and [PhoneNumber] value objects
 *
 * @see Model
 * @see Name
 * @see PhoneNumber
 */
@OptIn(ExperimentalUuidApi::class)
data class Contacts(override val id: Uuid, val phones: Map<Name, PhoneNumber>) : Model
