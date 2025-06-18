package frontend.profile

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.*
import org.junit.Rule
import org.junit.Test
import pt.isel.courtandgo.frontend.profile.ProfileScreen

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileScreen_displaysUserNameAndButtons() {
        val testName = "João Teste"

        composeTestRule.setContent {
            ProfileScreen(
                name = testName,
                onEditProfile = {},
                onNotifications = {},
                onLogout = {}
            )
        }

        // Verifica se o nome aparece
        composeTestRule.onNodeWithText(testName).assertIsDisplayed()

        // Verifica os botões
        composeTestRule.onNodeWithText("Editar Perfil").assertIsDisplayed()
        composeTestRule.onNodeWithText("Notificações").assertIsDisplayed()
        composeTestRule.onNodeWithText("Terminar Sessão").assertIsDisplayed()
    }

    @Test
    fun profileScreen_clickingButtons_callsCallbacks() {
        var editClicked = false
        var notificationsClicked = false
        var logoutClicked = false

        composeTestRule.setContent {
            ProfileScreen(
                name = "João Teste",
                onEditProfile = { editClicked = true },
                onNotifications = { notificationsClicked = true },
                onLogout = { logoutClicked = true }
            )
        }

        composeTestRule.onNodeWithText("Editar Perfil").performClick()
        assert(editClicked)

        composeTestRule.onNodeWithText("Notificações").performClick()
        assert(notificationsClicked)

        composeTestRule.onNodeWithText("Terminar Sessão").performClick()
        assert(logoutClicked)
    }
}
