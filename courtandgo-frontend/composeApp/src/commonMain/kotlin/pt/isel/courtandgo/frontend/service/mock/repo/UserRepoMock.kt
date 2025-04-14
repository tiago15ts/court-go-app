package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.User

class UserRepoMock {

    companion object {
        val users =
            mutableListOf<User>(
                User(1, "Joao", "joao@example.com","+351912345678"),
                User(2, "Alice", "alice@example.com", "+351912345679"),
                User(3, "John", "john@example.com", "+351912345680"),
            )

        val passwords = mutableMapOf(
            1 to "A1234abc",
            2 to "1234VDdc",
            3 to "1234SADfs",
        )
        private var currentId = 4
    }

    fun createUser(email: String, name: String, contact: String, password: String): User {
        if (users.any { it.email == email }) {
            throw IllegalArgumentException("Email já utilizado.")
        }
        val user = User(currentId++, name, email, contact)
        users.add(user)
        passwords[user.id] = password
        return user
    }

    fun login(email: String, password: String): User? {
        val user = users.find { it.email == email }
        return if (user != null && passwords[user.id] == password) user else null
    }

    fun findUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    fun findUserByPassword(id: Int, password: String): User? {
        return if (passwords[id] == password) users.find { it.id == id } else null
    }

    fun findUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    //todo Mock logout logic
    //todo fun update user with optional params

}