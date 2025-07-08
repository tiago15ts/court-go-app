package pt.isel.courtandgo.frontend.clubs

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import pt.isel.courtandgo.frontend.clubs.searchClub.ClubSearchUiState
import pt.isel.courtandgo.frontend.clubs.searchClub.SearchClubViewModel
import pt.isel.courtandgo.frontend.domain.SportType
import pt.isel.courtandgo.frontend.service.mock.MockClubService
import pt.isel.courtandgo.frontend.service.mock.MockCourtService
import pt.isel.courtandgo.frontend.service.mock.MockScheduleCourtService
import pt.isel.courtandgo.frontend.service.mock.repo.ClubRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.CourtRepoMock
import pt.isel.courtandgo.frontend.service.mock.repo.ScheduleCourtRepoMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue


class SearchClubViewModelTest {

    private lateinit var viewModel: SearchClubViewModel

    @BeforeTest
    fun setup() {
        viewModel = SearchClubViewModel(
            clubService = MockClubService(ClubRepoMock()),
            courtService = MockCourtService(CourtRepoMock()),
            scheduleService = MockScheduleCourtService(ScheduleCourtRepoMock())
        )
    }

    @Test
    fun `fetchClubs should return tennis clubs in Lisboa`() = runTest {
        viewModel.updateDistrict("Lisboa")
        viewModel.updateSport(SportType.TENNIS)

        viewModel.fetchClubs()

        val state = viewModel.uiState.first { it is ClubSearchUiState.Success }
        assertTrue(state is ClubSearchUiState.Success)
        val clubs = (state as ClubSearchUiState.Success).clubs
        assertTrue(clubs.all { it.sportType == SportType.TENNIS && it.location.district.name == "Lisboa" })
    }

    @Test
    fun `fetchClubs should return padel clubs with name containing 'Arena'`() = runTest {
        viewModel.updateQuery("Arena")
        viewModel.updateSport(SportType.PADEL)

        viewModel.fetchClubs()
        val state = viewModel.uiState.first { it is ClubSearchUiState.Success }
        val clubs = (state as ClubSearchUiState.Success).clubs

        assertTrue(clubs.all {
            it.sportType == SportType.PADEL && it.name.contains("Arena", ignoreCase = true)
        })
    }

    @Test
    fun `fetchClubs should return clubs matching district county and postal code`() = runTest {
        viewModel.updateDistrict("Porto")
        viewModel.updateCounty("Matosinhos")
        viewModel.updatePostalCode("4450")

        viewModel.fetchClubs()
        val state = viewModel.uiState.first { it is ClubSearchUiState.Success }
        val clubs = (state as ClubSearchUiState.Success).clubs

        assertTrue(clubs.all {
            it.location.district.name == "Porto" &&
                    it.location.county == "Matosinhos" &&
                    it.location.postalCode.startsWith("4450")
        })
    }

    @Test
    fun `fetchClubs should return clubs from Portugal`() = runTest {
        viewModel.updateCountry("Portugal")

        viewModel.fetchClubs()
        val state = viewModel.uiState.first { it is ClubSearchUiState.Success }
        val clubs = (state as ClubSearchUiState.Success).clubs

        assertTrue(clubs.all { it.location.country.name == "Portugal" })
    }

    @Test
    fun `fetchClubs should return empty list when no clubs match`() = runTest {
        viewModel.updateQuery("NomeInexistente")

        viewModel.fetchClubs()
        val state = viewModel.uiState.first { it is ClubSearchUiState.Success }
        val clubs = (state as ClubSearchUiState.Success).clubs

        assertTrue(clubs.isEmpty())
    }

    @Test
    fun `fetchClubs should handle empty search gracefully`() = runTest {
        viewModel.updateQuery("")
        viewModel.updateDistrict("")
        viewModel.updateCounty("")
        viewModel.updatePostalCode("")
        viewModel.updateCountry("")

        viewModel.fetchClubs()
        val state = viewModel.uiState.first { it is ClubSearchUiState.Success }
        val clubs = (state as ClubSearchUiState.Success).clubs

        assertTrue(clubs.isNotEmpty()) // Should return all clubs when no filters are applied
    }

}
