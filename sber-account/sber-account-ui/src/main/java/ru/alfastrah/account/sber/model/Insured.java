package ru.alfastrah.account.sber.model;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class Insured implements Serializable {
    /***
     * Номер полиса
     */
    private String policyNumber;
    /***
     * Фамилия
     */
    private String surname;
    /***
     * Имя
     */
    private String name;
    /***
     * Отчество
     */
    private String patronymic;
    /***
     * Дата рождения
     */
    private transient LocalDate birthDate;
    /***
     * Страхователь
     */
    private String insurer;
    /***
     * Телефон
     */
    private String phone;
    /***
     * Email
     */
    private String email;
    /***
     * Филиал
     */
    private String filial;
    /***
     * Табельный номер
     */
    private String tableNumber;
    /***
     * Группа программы
     */
    private String programmGroup;
    /***
     * Дата начала действия полиса
     */
    private transient LocalDate beginDate;
    /***
     * Дата окончания действия полиса
     */
    private transient LocalDate endDate;
    /***
     * Дата досрочного открепления
     */
    private transient LocalDate cancelDate;
    /***
     * Номер договора
     */
    private String contractNumber;
    /***
     * Признак родственника: Y – полис родственника, N – полис сотрудника
     */
    private char relativeIndicator;
    /***
     * Y – c франшизой, N – без франшизы
     */
    private Integer franchise;
    /***
     * Идентификатор застрахованного (скрыть для отображения, только для вызова следующих процедур)
     */
    private Long userId;
    /***
     * Идентификатор полиса застрахованного (скрыть для отображения, только для вызова следующих процедур)
     */
    private Long policyId;
    /***
     * Идентификатор действующей истории полиса застрахованного (скрыть для отображения, только для вызова следующих процедур)
     */
    private Long historyId;
    /***
     * Идентификатор страховой программы (скрыть для отображения, только для вызова следующих процедур)
     */
    private Long programmId;
    /***
     * ИД печатной формы памятки
     */
    private BigDecimal bookletTemplateId;

    /***
     * ИД печатной формы полиса
     */
    private BigDecimal policyTemplateId;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
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

    public String getInsurer() {
        return insurer;
    }

    public void setInsurer(String insurer) {
        this.insurer = insurer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getProgrammGroup() {
        return programmGroup;
    }

    public void setProgrammGroup(String programmGroup) {
        this.programmGroup = programmGroup;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(LocalDate cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public char getRelativeIndicator() {
        return relativeIndicator;
    }

    public void setRelativeIndicator(char relativeIndicator) {
        this.relativeIndicator = relativeIndicator;
    }

    public Integer getFranchise() {
        return franchise;
    }

    public void setFranchise(Integer franchise) {
        this.franchise = franchise;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public Long getHistoryId() {
        return historyId;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public Long getProgrammId() {
        return programmId;
    }

    public void setProgrammId(Long programmId) {
        this.programmId = programmId;
    }

    public BigDecimal getBookletTemplateId() {
        return bookletTemplateId;
    }

    public void setBookletTemplateId(BigDecimal bookletTemplateId) {
        this.bookletTemplateId = bookletTemplateId;
    }

    public BigDecimal getPolicyTemplateId() {
        return policyTemplateId;
    }

    public void setPolicyTemplateId(BigDecimal policyTemplateId) {
        this.policyTemplateId = policyTemplateId;
    }

    public String getFullName() {
        return Stream.of(surname, name, patronymic).map(value -> StringUtils.isNotBlank(value)? value : StringUtils.EMPTY)
                .collect(Collectors.joining(StringUtils.SPACE));
    }

    @Override
    public String toString() {
        return "Insured{" +
                "policyNumber='" + policyNumber + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", insurer='" + insurer + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", filial='" + filial + '\'' +
                ", tableNumber='" + tableNumber + '\'' +
                ", programmGroup='" + programmGroup + '\'' +
                ", beginDate=" + beginDate +
                ", endDate=" + endDate +
                ", cancelDate=" + cancelDate +
                ", contractNumber='" + contractNumber + '\'' +
                ", relativeIndicator=" + relativeIndicator +
                ", franchise=" + franchise +
                ", userId=" + userId +
                ", policyId=" + policyId +
                ", historyId=" + historyId +
                ", programmId=" + programmId +
                ", bookletTemplateId=" + bookletTemplateId +
                ", policyTemplateId=" + policyTemplateId +
                '}';
    }
}
