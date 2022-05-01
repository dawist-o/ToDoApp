package com.todoapp.notification.repo

import com.todoapp.notification.model.ConfirmationToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

interface TokenRepository: JpaRepository<ConfirmationToken, Long>{
    fun existsByToken(token:String): Boolean
    fun findByToken(token:String): ConfirmationToken

    @Transactional
    @Modifying
    @Query(
        "UPDATE ConfirmationToken c " +
                "SET c.confirmed = TRUE " +
                "WHERE c.token = ?1"
    )
    fun updateConfirmed(
        token: String,
    )
}