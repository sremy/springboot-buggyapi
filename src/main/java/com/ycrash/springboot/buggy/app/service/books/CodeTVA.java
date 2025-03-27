package com.ycrash.springboot.buggy.app.service.books;

import java.math.BigDecimal;

public enum CodeTVA {
    Normal(BigDecimal.valueOf(20)),
    Intermediary(BigDecimal.valueOf(10)),
    Reduced(BigDecimal.valueOf(5.5)),
    Special(BigDecimal.valueOf(2.1));

    BigDecimal rate;

    CodeTVA(BigDecimal rate) {
        this.rate = rate;
    }
}
