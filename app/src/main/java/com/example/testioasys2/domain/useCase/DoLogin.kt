package com.example.testioasys2.domain.useCase

import com.example.testioasys2.domain.model.User
import com.example.testioasys2.domain.model.UserSession
import com.example.testioasys2.domain.result.Result

interface DoLogin {
    suspend fun call(user: User): Result<UserSession>
}