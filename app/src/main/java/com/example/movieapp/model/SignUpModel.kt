package com.example.movieapp.model

data class SignUpModel(
    val email: String,
    val pwd: String,
    val pwdConfirm: String
) {
    fun checkUser() : String? {
        if (email.isEmpty() && pwd.isEmpty() && pwdConfirm.isEmpty()) {
            return "Por favor preencha todos os campos"
        }
        else if (pwd.length < 6) {
            return "A senha deve ter 6 digitos ou mais."
        }
        if (pwd != pwdConfirm) {
            return  "As senhas não coincidem"
        }
        return null
    }

}
