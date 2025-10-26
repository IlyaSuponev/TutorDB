package com.isuponev.tutordb.core.models

import com.isuponev.tutordb.core.interfaces.Model
import com.isuponev.tutordb.core.models.values.Name
import java.util.UUID
import javax.money.MonetaryAmount

/**
 * Represents a student entity in the tutoring system.
 *
 * Students are individuals who receive tutoring services. Each student has basic information,
 * hourly cost rate, optional contact information, and a set of subjects they are studying.
 *
 * @property id The unique identifier for the student
 * @property name The student's full name wrapped in a [Name] value object
 * @property hourCost The hourly rate for the student's tutoring sessions as a [MonetaryAmount]
 * @property contacts Optional contact information for the student
 * @property subjects Set of subjects the student is currently studying, empty by default
 *
 * @see Model
 * @see Name
 * @see MonetaryAmount
 * @see Contacts
 * @see Subject
 */
data class Student(
    override val id: Long,
    val name: Name,
    val hourCost: MonetaryAmount,
    val contacts: Contacts? = null,
    val subjects: Set<Subject> = emptySet(),
) : Model
