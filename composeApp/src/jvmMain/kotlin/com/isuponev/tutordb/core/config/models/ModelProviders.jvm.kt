package com.isuponev.tutordb.core.config.models

import com.isuponev.tutordb.core.interfaces.ModelProvider
import com.isuponev.tutordb.core.logging.appLogger
import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.models.values.Name
import org.javamoney.moneta.Money
import javax.money.Monetary
import javax.money.MonetaryAmount
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
actual fun studentsProvider(): ModelProvider<Student> = object : ModelProvider<Student> {
    private val data = mutableMapOf<Uuid, Student>()

    init {
        repeat(100) { index ->
            val uuid = Uuid.random()
            data[uuid] = Student(
                id = uuid,
                Name.of("Stduent $index"),
                Money.of(10, "USD"),
            )
        }
    }

    override fun getModel(id: Uuid): Student? = data[id]

    override fun setModel(id: Uuid, model: Student) {
        data[id] = model
    }

    override fun getAll(): List<Student> = data.values.toList()

    override fun insertModel(model: Student) {
        data[model.id] = model
    }

    override fun removeModel(id: Uuid): Student? {
        return data.remove(id)
    }
}