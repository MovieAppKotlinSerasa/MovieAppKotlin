package com.example.movieapp.repository

import com.google.android.gms.tasks.Task
import com.google.common.truth.Truth.assertThat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class AuthenticationRepositoryTest {

    @Mock
    private lateinit var task: Task<AuthResult>

    @Mock
    private lateinit var result: AuthResult

    @Mock
    private lateinit var firebaseUser: FirebaseUser

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var authenticationRepository: AuthenticationRepository


    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        authenticationRepository = AuthenticationRepository(firebaseAuth)
    }

    @Test
    fun `Sign in user should return sucess`() = runBlocking {
        val email = "testando@gmail.com"
        val password = "123456"

        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password)).thenReturn(task)

        Mockito.`when`(task.result).thenReturn(result)
        Mockito.`when`(result.user).thenReturn(firebaseUser)

        val callback: (FirebaseUser?, String?) -> Unit = { firebaseUser, error ->
            assertThat(task.result!!.user).isEqualTo(firebaseUser)
        }

        authenticationRepository.signInWithEmailPassword(email,password, callback)

    }

    @Test
    fun `Sign in user should return failure`() = runBlocking {
        val email = "testando"
        val password = "123456"

        Mockito.`when`(firebaseAuth.signInWithEmailAndPassword(email, password))
            .thenReturn(task)
//        Mockito.`when`(task.addOnSuccessListener {  }(email, password))
            .thenReturn(task)

        Mockito.`when`(task.result).thenReturn(result)
        Mockito.`when`(result.user).thenReturn(null)


        assertThat(task.result?.user).isEqualTo(null)

    }

}