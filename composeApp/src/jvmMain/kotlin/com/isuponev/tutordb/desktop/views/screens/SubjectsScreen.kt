//package com.isuponev.tutordb.desktop.views.screens
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.navigation.NavHostController
//import com.isuponev.tutordb.core.config.AppConfig
//import com.isuponev.tutordb.core.config.general.AppLocale
//import com.isuponev.tutordb.core.logging.appLogger
//import com.isuponev.tutordb.core.models.Subject
//import com.isuponev.tutordb.core.models.values.Name
//import com.isuponev.tutordb.core.resources.SharedResources
//import com.isuponev.tutordb.core.views.AppDefaults
//import com.isuponev.tutordb.core.views.screens.Screen
//import com.isuponev.tutordb.core.views.widgets.CardWidget
//import kotlin.random.Random
//import kotlin.uuid.ExperimentalUuidApi
//import kotlin.uuid.Uuid
//
///**
// * Screen object representing the Subjects management interface in the TutorDB desktop application.
// *
// * This screen provides access to view and manage educational subjects within the application.
// * It implements the [Screen] interface with proper localization support and integrates
// * with the application's navigation and configuration systems.
// *
// * Route: Localized string from [SharedResources.strings.routeOfSubjectsScreen]
// * Default: English localization used as fallback during initialization
// *
// * @see Screen
// * @see AppLocale
// * @see SharedResources
// */
//object SubjectsScreen : Screen(
//    AppLocale.ENGLISH.localize(SharedResources.strings.routeOfSubjectsScreen),
//) {
//    @OptIn(ExperimentalUuidApi::class)
//    @Composable
//    override fun view(
//        navHostController: NavHostController,
//        modifier: Modifier
//    ) = Column(
//        modifier = modifier
//            .padding(AppDefaults.Paddings.BIG)
//            .fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
//    ) {
//        appLogger.i(tag = SubjectsScreen::class.java.simpleName) { "Load subjects screen" }
//        Header(Modifier.fillMaxWidth())
//        Row(
//            modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxWidth(),
//            horizontalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
//        ) {
//            var currentSubject by remember { mutableStateOf<Subject?>(null) }
//            SubjectsList(
//                Modifier.weight(AppDefaults.Weights.ONE).fillMaxHeight()
//            )
//            Button(
//                onClick = {
////                    val n = AppConfig.Database.subjects.getAll().size
////                    val s = Subject(
////                        id = Uuid.random(),
////                        Name.of("Subject $n"),
////                        (0..n).map {
////                            Char('a'.code + Random.nextInt(0, 26 + 1))
////                        }.joinToString(""),
////                    )
////                    AppConfig.Database.subjects.insertModel(s)
//                },
//                modifier = Modifier.weight(AppDefaults.Weights.ONE).fillMaxHeight()
//            ) {
//                Text("New Subject")
//            }
//        }
//    }
//
//    @Composable
//    fun SubjectsList(
//        modifier: Modifier = Modifier,
//        onSubjectClick: ((Subject) -> Unit)? = null
//    ) {
//        LazyColumn(
//            modifier,
//            verticalArrangement = Arrangement.spacedBy(AppDefaults.Arrangements.BIG),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ) {
////            items(AppConfig.Database.subjects.getAll()) { subject ->
////                CardWidget(
////                    onClick = { onSubjectClick?.invoke(subject) },
////                    modifier = Modifier.fillMaxWidth()
////                ) {
////                    Text(
////                        subject.name.value,
////                        style = MaterialTheme.typography.displayMedium,
////                        color = MaterialTheme.colorScheme.onPrimaryContainer,
////                        modifier = Modifier.fillMaxSize(),
////                        textAlign = TextAlign.Center
////                    )
////                }
////            }
//        }
//    }
//
//    @Composable
//    private fun Header(
//        modifier: Modifier = Modifier
//    ) = ScreenHeader(
//        titleResource = SharedResources.strings.screenSubjects,
//        modifier = modifier
//    )
//}
