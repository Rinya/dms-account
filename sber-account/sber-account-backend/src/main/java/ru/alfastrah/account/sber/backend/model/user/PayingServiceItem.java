package ru.alfastrah.account.sber.backend.model.user;

import ru.alfastrah.account.sber.backend.HasLogger;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayingServiceItem implements HasLogger {
    private Long subjectId;
    private Long policyId;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthDate;
    private BigDecimal premium;
    private Long programmId;
    /***
     * Признак родственника: Y – полис родственника, N – полис сотрудника
     */
    private char relativeIndicator;
    private String changeProgramm;
    private boolean canChangeProgramm;
    /***
     * ИД печатной формы
     */
    private BigDecimal templateId;

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public BigDecimal getPremium() {
        return premium;
    }

    public void setPremium(BigDecimal premium) {
        this.premium = premium;
    }

    public Long getProgrammId() {
        return programmId;
    }

    public void setProgrammId(Long programmId) {
        this.programmId = programmId;
    }

    public boolean isCanChangeProgramm() {
        return canChangeProgramm;
    }

    public char getRelativeIndicator() {
        return relativeIndicator;
    }

    public void setRelativeIndicator(char relativeIndicator) {
        this.relativeIndicator = relativeIndicator;
    }

    public void setCanChangeProgramm(String canChangeProgramm) {
        this.canChangeProgramm = "Y".equals(canChangeProgramm);
    }

    public String getChangeProgramm() {
        return changeProgramm;
    }

    public void setChangeProgramm(String changeProgramm) {
        this.changeProgramm = changeProgramm;
    }

    public BigDecimal getTemplateId() {
        return templateId;
    }

    public void setTemplateId(BigDecimal templateId) {
        this.templateId = templateId;
    }

    @Override
    public String toString() {
        return "PayingServiceItem{" +
                "subjectId=" + subjectId +
                ", policyId=" + policyId +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", premium=" + premium +
                ", programmId=" + programmId +
                ", relativeIndicator=" + relativeIndicator +
                ", changeProgramm='" + changeProgramm + '\'' +
                ", canChangeProgramm=" + canChangeProgramm +
                ", templateId=" + templateId +
                '}';
    }
}
