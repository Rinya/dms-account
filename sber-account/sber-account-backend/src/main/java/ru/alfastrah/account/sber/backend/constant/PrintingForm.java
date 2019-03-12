package ru.alfastrah.account.sber.backend.constant;

import java.math.BigDecimal;

public enum PrintingForm {
    POLICY(BigDecimal.valueOf(7849)),
    BOOKLET(BigDecimal.valueOf(7836)),
    CHANGE_INSURED_PROGRAMM(BigDecimal.valueOf(7899)),
    CHANGE_FAMILY_PROGRAMM(BigDecimal.valueOf(7836))
    ;

    private BigDecimal formId;

    PrintingForm(BigDecimal formId) {
        this.formId = formId;
    }

    public BigDecimal getFormId() {
        return formId;
    }
}
