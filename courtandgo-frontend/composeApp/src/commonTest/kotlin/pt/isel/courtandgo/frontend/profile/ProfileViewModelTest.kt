package pt.isel.courtandgo.frontend.profile

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.repository.AuthRepositoryImpl
import pt.isel.courtandgo.frontend.service.mock.CourtAndGoServiceMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


class ProfileViewModelTest {

    private lateinit var viewModel: ProfileViewModel

    @BeforeTest
    fun setup() {
        viewModel = ProfileViewModel(
            authRepository = AuthRepositoryImpl(CourtAndGoServiceMock())
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load user test`() = runTest {
        viewModel.loadUser(User(1, "Joao", "joao@email.com",  "123456789", "123456789"))

        advanceUntilIdle()

        assertEquals("Joao", viewModel.user.value?.name)
        assertEquals("joao@email.com", viewModel.user.value?.email)
    }

}