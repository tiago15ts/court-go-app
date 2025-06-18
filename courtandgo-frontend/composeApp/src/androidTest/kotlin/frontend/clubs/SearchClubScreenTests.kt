package frontend.clubs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubScreen
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubViewModel
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock

class SearchClubScreenTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun fakeSearchClubViewModel() : SearchClubViewModel {
        return SearchClubViewModel(
            clubService =  MockClubService(ClubRepoMock()),
            scheduleService = MockScheduleCourtService(ScheduleCourtRepoMock()),
            courtService = MockCourtService(CourtRepoMock())
        )
    }

    @Test
    fun testSearchClubScreen_displaysClubsAndFields() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen (
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica título
        composeTestRule.onNodeWithText("Pesquisa de campo").assertExists()

        // Verifica campos de clubes
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertExists()

    }

    @Test
    fun testToggleSportFilter_updatesSportState() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        composeTestRule.onNodeWithText("Ténis").performClick()
        assertEquals(SportType.TENNIS, viewModel.selectedSport.value)

        composeTestRule.onNodeWithText("Padel").performClick()
        assertEquals(SportType.PADEL, viewModel.selectedSport.value)
    }

    @Test
    fun testSearchClubScreen_backButtonNavigatesBack() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Voltar").assertExists()
        composeTestRule.onNodeWithContentDescription("Voltar").performClick()
    }

    @Test
    fun testSearchClubScreen_searchFunctionality() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("Beloura")
        assertEquals("Beloura", viewModel.query.value)

        // Verifica que a lista de clubes é filtrada
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertDoesNotExist()
    }

    @Test
    fun testSearchClubScreen_searchWithEmptyQuery() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("")
        assertEquals(null, viewModel.query.value)

        // Verifica que todos os clubes são exibidos
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertExists()
        composeTestRule.onNodeWithText("Estoril Country Club").assertExists()
    }

    @Test
    fun testSearchClubScreen_searchWithDistrict() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("Lisboa")

        composeTestRule.onNodeWithTag("suggestionsCard").performClick()

        composeTestRule.onNodeWithText("Lisboa").performClick()
        assertEquals("Lisboa", viewModel.selectedDistrict.value)

        // Verifica que a lista de clubes é filtrada por distrito
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertExists()
        composeTestRule.onNodeWithText("Estoril Country Club").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertDoesNotExist()
    }

    @Test
    fun testSearchClubScreen_searchWithCounty() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("Cascais")

        composeTestRule.onNodeWithTag("suggestionsCard").performClick()

        composeTestRule.onNodeWithText("Cascais").performClick()
        assertEquals("Cascais", viewModel.selectedCounty.value)

        // Verifica que a lista de clubes é filtrada por concelho
        composeTestRule.onNodeWithText("Estoril Country Club").assertExists()
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertDoesNotExist()
    }

    @Test
    fun testSearchClubScreen_searchWithPostalCode() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("2710-123")

        composeTestRule.onNodeWithTag("suggestionsCard").performClick()

        composeTestRule.onNodeWithText("2710-123").performClick()
        assertEquals("2710-123", viewModel.selectedPostalCode.value)

        // Verifica que a lista de clubes é filtrada por código postal
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertDoesNotExist()
    }

    @Test
    fun testSearchClubScreen_searchWithCountry() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("Portugal")

        composeTestRule.onNodeWithTag("suggestionsCard").performClick()

        composeTestRule.onNodeWithText("Portugal").performClick()
        assertEquals("Portugal", viewModel.selectedCountry.value)

        // Verifica que a lista de clubes é filtrada por país
        composeTestRule.onNodeWithText("Beloura Tennis Academy").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertExists()
        composeTestRule.onNodeWithText("Estoril Country Club").assertExists()
    }

    @Test
    fun testSearchClubScreen_searchWithSport() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica campo de pesquisa

        composeTestRule.onNodeWithText("Padel").performClick()
        assertEquals(SportType.PADEL, viewModel.selectedSport.value)

        composeTestRule.onNodeWithTag("searchField").performTextReplacement("Portugal")

        composeTestRule.onNodeWithTag("suggestionsCard").performClick()

        composeTestRule.onNodeWithText("Portugal").performClick()
        assertEquals("Portugal", viewModel.selectedCountry.value)

        composeTestRule.onNodeWithText("Lisboa Rackets").assertExists()
        composeTestRule.onNodeWithText("Braga Tennis Club").assertDoesNotExist()
    }

    @Test
    fun testSearchClubScreen_suggestionsCardVisibility() {
        val viewModel = fakeSearchClubViewModel()

        composeTestRule.setContent {
            SearchClubScreen(
                viewModel = viewModel,
                onBackClick = {},
                onClubClick = {}
            )
        }

        // Verifica que o cartão de sugestões está inicialmente invisível
        composeTestRule.onNodeWithTag("suggestionsCard").assertDoesNotExist()

        // Simula a digitação no campo de pesquisa
        composeTestRule.onNodeWithTag("searchField").performTextReplacement("Lisboa")

        // Verifica que o cartão de sugestões agora está visível
        composeTestRule.onNodeWithTag("suggestionsCard").assertExists()
    }
}