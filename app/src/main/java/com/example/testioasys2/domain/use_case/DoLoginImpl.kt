package com.example.testioasys2.domain.use_case

import com.example.testioasys2.domain.exception.InvalidLoginException
import com.example.testioasys2.domain.exception.InvalidPasswordException
import com.example.testioasys2.domain.model.EmailStatus
import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.repository.login.LoginRepository
import com.example.testioasys2.domain.result.Result

class DoLoginImpl(
    private val validateUserEmail: ValidateUserEmail,
    private val validateUserPassword: ValidateUserPassword,
    private val loginRepository: LoginRepository
) : DoLogin {
    override suspend fun call(user: User): Result<UserSession> {
        val isValidUserPassword = validateUserPassword.call(user.password)
        return when {
            !isValidUserPassword -> Result.Error(InvalidPasswordException)
            validateUserEmail.call(user.email) == EmailStatus.VALID &&
                    isValidUserPassword -> {
                loginRepository.doLogin(user)
            }
            else -> {
                Result.Error(InvalidLoginException)
            }
        }
    }
}