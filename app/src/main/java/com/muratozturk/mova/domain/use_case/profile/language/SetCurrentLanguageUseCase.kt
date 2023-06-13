package com.muratozturk.mova.domain.use_case.profile.language

import com.muratozturk.mova.domain.repository.MovaRepository
import javax.inject.Inject

class SetCurrentLanguageUseCase @Inject constructor(private val movaRepository: MovaRepository) {
    operator fun invoke(language: String) = movaRepository.setCurrentLanguage(language)
}