package com.example.movieapp.model

data class SignUpModel(
    val email: String,
    val password: String,
    val passwordConfirm: String
) {
    fun checkUser() : String? {
        if (email.isEmpty() && password.isEmpty() && passwordConfirm.isEmpty()) {
            return "Por favor preencha todos os campos"
        }
        else if (password.length < 6) {
            return "A senha deve ter 6 digitos ou mais."
        }
        if (password != passwordConfirm) {
            return  "As senhas não coincidem"
        }
        return null
    }

}
