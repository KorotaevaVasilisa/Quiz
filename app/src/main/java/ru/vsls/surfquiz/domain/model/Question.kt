package ru.vsls.surfquiz.domain.model

data class Question(
    val category: String,
    val difficulty: String,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
)
