package com.lyae.housebook.common.domain

import java.time.LocalDateTime
import javax.persistence.Embeddable

@Embeddable
class Period(
    private val regDate : LocalDateTime = LocalDateTime.now(),
    private val updDate : LocalDateTime = LocalDateTime.now()
) {

}