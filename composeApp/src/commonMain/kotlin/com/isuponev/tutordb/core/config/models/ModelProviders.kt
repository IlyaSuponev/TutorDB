package com.isuponev.tutordb.core.config.models

import com.isuponev.tutordb.core.interfaces.ModelProvider
import com.isuponev.tutordb.core.models.Student

expect fun studentsProvider(): ModelProvider<Student>
