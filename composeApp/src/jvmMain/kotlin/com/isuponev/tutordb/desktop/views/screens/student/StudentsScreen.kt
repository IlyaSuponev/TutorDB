package com.isuponev.tutordb.desktop.views.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import com.isuponev.tutordb.core.config.AppConfig
import com.isuponev.tutordb.core.config.general.AppLocale
import com.isuponev.tutordb.core.models.Student
import com.isuponev.tutordb.core.resources.SharedResourcesjvmMain
import com.isuponev.tutordb.core.views.AppDefaults
import com.isuponev.tutordb.desktop.viewmodels.Tool
import com.isuponev.tutordb.desktop.viewmodels.screens.student.StudentsViewModel
import com.isuponev.tutordb.desktop.viewmodels.screens.subject.SubjectsViewModel
import com.isuponev.tutordb.desktop.views.Header
import com.isuponev.tutordb.desktop.views.widgets.Detail
import com.isuponev.tutordb.desktop.views.widgets.EntityCard
import java.text.DecimalFormat
import javax.money.MonetaryAmount
import javax.money.format.AmountFormatQuery
import javax.money.format.MonetaryFormats
import org.javamoney.moneta.format.CurrencyStyle
import org.javamoney.moneta.format.MonetaryAmountDecimalFormatBuilder

/**
 * A composable UI component for the "Students" screen in the application.
 *
 * This is currently a placeholder/stub implementation that displays a localized screen title.
 * The actual implementation would eventually handle student-related data operations,
 * UI state management, and user interactions through the [StudentsViewModel].
 *
 * @param viewModel The [StudentsViewModel] instance managing the screen's state and logic.
 *                  Currently serves as a base for future implementation.
 * @param modifier Optional [Modifier] to customize the layout behavior of the screen container.
 */
@Composable
fun StudentsScreenView(
    viewModel: StudentsViewModel,
    modifier: Modifier
) = Column(
    modifier = modifier
        .padding(AppDefaults.Paddings.BIG)
        .fillMaxSize(),
    verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
) {
    viewModel.i("Load students screen")
    Header(
        SharedResourcesjvmMain.strings.screenStudentsName,
        tools = tools(viewModel),
        modifier = Modifier.fillMaxWidth()
    )
    val students by viewModel.students.collectAsState()
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(StudentsViewModel.COLUMN_COUNT),
        modifier = Modifier.weight(AppDefaults.Weights.ONE),
        verticalItemSpacing = AppDefaults.Arrangements.BIG,
        horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG)
    ) {
        items(students) { student ->
            StudentCard(
                student,
                {},
                {}
            )
        }
    }
}

private fun tools(viewModel: StudentsViewModel) = listOf(
    Tool(
        "Add student",
        Icons.Default.Add,
        viewModel::onClickAddStudent
    )
)

@Composable
private fun StudentCard(
    student: Student,
    onEditClick: (Student) -> Unit,
    onRemoveClick: (Student) -> Unit,
) {
    val locale by AppConfig.General.locale.collectAsState()
    EntityCard(
        student,
        student.name.value,
        listOf(
            Detail.string(
                SharedResourcesjvmMain.strings.lbl_monetary_amount,
                minSize = DpSize(Dp.Unspecified, StudentsViewModel.DESCRIPTION_MIN_HEIGHT),
                value = student.hourCost.toMonetaryString(locale),
            ),
            Detail.iterable(
                SharedResourcesjvmMain.strings.lbl_student_subjects,
                asColumn = true,
                student.subjects.map { subject -> subject.name.value }
            )
        ),
        onEditClick,
        onRemoveClick
    )
}

private fun MonetaryAmount.toMonetaryString(locale: AppLocale): String {
    if (!MonetaryFormats.isAvailable(locale.type)) return toString()
    val numberFormatter = DecimalFormat.getInstance(locale.type)
    return locale.localize(
        SharedResourcesjvmMain.strings.flbl_hour_cost_info
    ).format("${numberFormatter.format(number)} ${currency.currencyCode}")
}