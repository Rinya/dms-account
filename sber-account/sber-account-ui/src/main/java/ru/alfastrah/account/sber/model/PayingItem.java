package ru.alfastrah.account.sber.model;

import com.vaadin.spring.annotation.SpringComponent;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
public class PayingItem {
    private boolean selected;
    private String surname;
    private String name;
    private String patronymic;
    private LocalDate birthDate;
    private Long subjectId;
    private Long policyId;
    private BigDecimal premium;
    private Long programmId;
    private String changeProgramm;
    private boolean canChangeProgramm;
    /***
     * Это сам сотрудник или родственник
     */
    private boolean user;
    /***
     * ИД печатной формы
     */
    private BigDecimal templateId;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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

    public String getChangeProgramm() {
        return changeProgramm;
    }

    public void setChangeProgramm(String changeProgramm) {
        this.changeProgramm = changeProgramm;
    }

    public boolean isCanChangeProgramm() {
        return canChangeProgramm;
    }

    public void setCanChangeProgramm(boolean canChangeProgramm) {
        this.canChangeProgramm = canChangeProgramm;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public boolean isUser() {
        return user;
    }

    public BigDecimal getTemplateId() {
        return templateId;
    }

    public void setTemplateId(BigDecimal templateId) {
        this.templateId = templateId;
    }

    public String getFullName() {
        return Stream.of(surname, name, patronymic).map(value -> StringUtils.isNotBlank(value)? value : StringUtils.EMPTY)
                .collect(Collectors.joining(StringUtils.SPACE));
    }

    @Override
    public String toString() {
        return "FamilyItem{" +
                "selected=" + selected +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", subjectId=" + subjectId +
                ", policyId=" + policyId +
                ", premium=" + premium +
                ", programmId=" + programmId +
                ", paying='" + changeProgramm + '\'' +
                ", canChangeProgramm=" + canChangeProgramm +
                ", user=" + user +
                ", templateId=" + templateId +
                '}';
    }
}
