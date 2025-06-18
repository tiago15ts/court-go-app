package pt.isel.courtandgo.frontend.service.mock.repo

import pt.isel.courtandgo.frontend.domain.User

class UserRepoMock {


    private val users =
        mutableListOf<User>(
            User(1, "Joao", "joao@example.com", "+351", "912345678", null, "17/06/1999",70.5,1.63,"Lisboa"),
            User(2, "Alice", "alice@example.com", "+351", "912345679", null, null,null,null,null),
            User(3, "John", "john@example.com", "+351", "912345680", null, null,null,1.82,"Porto"),
        )

    private val passwords = mutableMapOf(
        1 to "A1234abc",
        2 to "1234VDdc",
        3 to "1234SADfs",
    )
    private var currentId = 4


    fun createUser(
        email: String,
        name: String,
        countryCode: String,
        phone: String,
        password: String
    ): User {
        if (users.any { it.email == email }) {
            throw IllegalArgumentException("Email já utilizado.")
        }
        val user = User(currentId++, name, email, countryCode, phone, null, null, null, null, null)
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

    fun findUserByEmail(email: String): User? {
        return users.find { it.email == email }
    }

    //todo Mock logout logic

    fun updateUser(user: User): User {
        val index = users.indexOfFirst { it.id == user.id }
        if (index != -1) {
            users[index] = user.copy()
            return user
        } else {
            throw IllegalArgumentException("User not found")
        }
    }


}