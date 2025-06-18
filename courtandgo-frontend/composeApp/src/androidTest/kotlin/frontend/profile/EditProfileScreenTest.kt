package frontend.profile

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextReplacement
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import pt.isel.courtandgo.frontend.domain.User
import pt.isel.courtandgo.frontend.profile.ProfileUiState
import pt.isel.courtandgo.frontend.profile.ProfileViewModel
import pt.isel.courtandgo.frontend.profile.editProfile.EditProfileScreen
import pt.isel.courtandgo.frontend.repository.AuthRepository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import org.junit.Assert.assertEquals



class EditProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val fakeUser = User(
        id = 1,
        name = "João Silva",
        email = "joao@example.com",
        countryCode = "+351",
        phone = "912345678",
        location = "Lisboa",
        birthDate = "1990-01-01",
        gender = "Masculino",
        weight = 75.0,
        height = 180.0
    )

    private val fakeService = object : AuthRepository {
        override suspend fun loginWithEmail(email: String, password: String): User = fakeUser
        override suspend fun authenticateGoogle(
            tokenId: String,
            name: String,
            email: String
        ): User = fakeUser
        override suspend fun registerWithEmail(
            email: String,
            name: String,
            countryCode: String,
            phone: String,
            password: String
        ): User = fakeUser
        override suspend fun updateUser(user: User) : User = fakeUser
        override fun setToken(token: String) = Unit
        override fun getToken(): String = fakeUser.name
        override fun logout() = Unit
    }

    private fun fakeViewModel(): ProfileViewModel {
        return ProfileViewModel(
            authRepository = fakeService
        ).apply {
            _uiState.value = ProfileUiState.Loading
        }
    }

    @Test
    fun testEditProfileScreen_loadsUserAndShowsFields() {
        val viewModel = fakeViewModel()
        viewModel.loadUser(fakeUser)

        composeTestRule.setContent {
            EditProfileScreen(
                viewModel = viewModel,
                currentUser = fakeUser,
                onBack = {},
                onSaved = {}
            )
        }

        // Check if the name field contains initial user name
        composeTestRule.onNodeWithText("João Silva").assertExists()

        // Check if the email field contains initial user email
        composeTestRule.onNodeWithText("joao@example.com").assertExists()

        // Check if the location dropdown shows "Lisboa"
        composeTestRule.onNodeWithText("Lisboa").assertExists()

        // Check if gender dropdown shows "Masculino"
        composeTestRule.onNodeWithText("Masculino").assertExists()
    }


    @Test
    fun testEditNameField_updatesUserInViewModel() = runBlocking {
        val viewModel = fakeViewModel()
        viewModel.loadUser(fakeUser)

        composeTestRule.setContent {
            EditProfileScreen(
                viewModel = viewModel,
                currentUser = fakeUser,
                onBack = {},
                onSaved = {}
            )
        }

        val newName = "Novo Nome"
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("Nome e apelido")
            .performTextReplacement(newName)

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Guardar").performClick()


        repeat(3) {
            if (viewModel.uiState.value == ProfileUiState.Success) return@runBlocking
            delay(200)
        }

        val updatedUser = viewModel.user.value
        println("Nome atualizado: ${updatedUser?.name}")
        assertEquals(newName, updatedUser?.name)
    }

    @Test
    fun testBackButtonCallsOnBack() {
        var backCalled = false
        val viewModel = fakeViewModel()
        viewModel.loadUser(fakeUser)

        composeTestRule.setContent {
            EditProfileScreen(
                viewModel = viewModel,
                currentUser = fakeUser,
                onBack = { backCalled = true },
                onSaved = {}
            )
        }

        composeTestRule.onNodeWithContentDescription("Voltar").performClick()

        assert(backCalled)
    }

    @Test
    fun testUiStateSuccessShowsSnackbarAndCallsOnSaved() {
        val viewModel = fakeViewModel()
        viewModel.loadUser(fakeUser)

        var savedCalled = false

        composeTestRule.setContent {
            EditProfileScreen(
                viewModel = viewModel,
                currentUser = fakeUser,
                onBack = {},
                onSaved = { savedCalled = true }
            )
        }

        // Simulate UI state change to Success
        viewModel._uiState.value = ProfileUiState.Success

        // Wait for snackbar to show and onSaved to be called
        composeTestRule.waitForIdle()

        assert(savedCalled)
    }
}
