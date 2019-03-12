package ru.alfastrah.account.sber.backend.model.user;

import java.time.LocalDate;

public class ProfileData {
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
    private LocalDate birthDate;
    /***
     * Табельный номер
     */
    private String tableNumber;
    /***
     * Телефон
     */
    private String phone;
    /***
     * Email
     */
    private String email;
    /***
     * Показывать галочку «Принятие условий франшизы»
     */
    private boolean showAcceptFranch;
    /***
     * Показывать список родственников
     */
    private boolean showRelative;
    /***
     * Показывать вкладку Счета
     */
    private boolean showInvoice;
    /***
     * Показывать сумму депозита на вкладке Счета
     */
    private boolean showDeposit;
    /***
     * Показывать смену программы для страхователя
     */
    private boolean needProgramm;
    /***
     * Показывать вкладку стоматология
     */
    private boolean showDental;
    /***
     * Показывать вкладку с вопросами
     */
    private boolean showFaq;
    /***
     * Показывать вкладку обратной связи
     */
    private boolean showChat;
    /***
     * Показывать вкладку прикрепления родственников
     */
    private boolean showDistantRelative;
    /***
     * Показывать вкладку ВЗР
     */
    private boolean showVZR;
    /***
     * Может менять пароль или нет
     */
    private boolean canChangePassword;
    /***
     * Не найдена программа, на которую пользователь может перейти
     */
    private boolean notFountProgramm;
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
     * Email на которое будет отправлено письмо для прикрепления дальних родственников
     */
    private String emailReciver;
    /***
     * Email на которое будет отправлено письмо для оформления ВЗР
     */
    private String emailVzrReciver;
    /***
     * Выделенная линия Сбера, телефон для SD
     */
    private String serviceDeskPhone;

    /***
     * Договор заключенный с застрахованным
     */
    private Long contractId;

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

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
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

    public boolean isShowAcceptFranch() {
        return showAcceptFranch;
    }

    public void setShowAcceptFranch(boolean showAcceptFranch) {
        this.showAcceptFranch = showAcceptFranch;
    }

    public void setViewAcceptFranch(Integer viewAcceptFranch) {
        this.showAcceptFranch = viewAcceptFranch == 1;
    }

    public boolean isShowRelative() {
        return showRelative;
    }

    public void setShowRelative(boolean showRelative) {
        this.showRelative = showRelative;
    }

    public void setViewRelative(Integer viewRelative) {
        this.showRelative = viewRelative == 1;
    }

    public boolean isShowInvoice() {
        return showInvoice;
    }

    public void setShowInvoice(boolean showInvoice) {
        this.showInvoice = showInvoice;
    }

    public void setViewInvoice(Integer viewInvoice) {
        this.showInvoice = viewInvoice == 1;
    }

    public boolean isShowDeposit() {
        return showDeposit;
    }

    public void setShowDeposit(boolean showDeposit) {
        this.showDeposit = showDeposit;
    }

    public void setViewDeposit(Integer viewDeposit) {
        this.showDeposit = viewDeposit == 1;
    }

    public boolean isNeedProgramm() {
        return needProgramm;
    }

    public void setNeedProgramm(Integer needProgramm) {
        this.needProgramm = needProgramm == 1;
    }

    public boolean isShowDental() {
        return showDental;
    }

    public void setShowDental(Integer showDental) {
        this.showDental = showDental == 1;
    }

    public boolean isShowFaq() {
        return showFaq;
    }

    public void setShowFaq(Integer showFaq) {
        this.showFaq = showFaq == 1;
    }

    public boolean isShowChat() {
        return showChat;
    }

    public void setShowChat(Integer showChat) {
        this.showChat = showChat == 1;
    }

    public boolean isShowDistantRelative() {
        return showDistantRelative;
    }

    public void setShowDistantRelative(Integer showDistantRelative) {
        this.showDistantRelative = showDistantRelative == 1;
    }

    public boolean isShowVZR() {
        return showVZR;
    }

    public void setShowVZR(Integer showVZR) {
        this.showVZR = showVZR == 1;
    }

    public boolean isCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(Integer canChangePassword) {
        this.canChangePassword = canChangePassword == 1;
    }

    public boolean isNotFountProgramm() {
        return notFountProgramm;
    }

    public void setNotFountProgramm(boolean notFountProgramm) {
        this.notFountProgramm = notFountProgramm;
    }

    public void setNotFountProgramm(Integer notFountProgramm) {
        this.notFountProgramm = notFountProgramm == 1;
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

    public String getEmailReciver() {
        return emailReciver;
    }

    public void setEmailReciver(String emailReciver) {
        this.emailReciver = emailReciver;
    }

    public String getEmailVzrReciver() {
        return emailVzrReciver;
    }

    public void setEmailVzrReciver(String emailVzrReciver) {
        this.emailVzrReciver = emailVzrReciver;
    }

    public String getServiceDeskPhone() {
        return serviceDeskPhone;
    }

    public void setServiceDeskPhone(String serviceDeskPhone) {
        this.serviceDeskPhone = serviceDeskPhone;
    }

    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    @Override
    public String toString() {
        return "ProfileData{" +
                "policyNumber='" + policyNumber + '\'' +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthDate=" + birthDate +
                ", tableNumber='" + tableNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", showAcceptFranch=" + showAcceptFranch +
                ", showRelative=" + showRelative +
                ", showInvoice=" + showInvoice +
                ", showDeposit=" + showDeposit +
                ", needProgramm=" + needProgramm +
                ", showDental=" + showDental +
                ", showFaq=" + showFaq +
                ", showChat=" + showChat +
                ", showDistantRelative=" + showDistantRelative +
                ", showVZR=" + showVZR +
                ", canChangePassword=" + canChangePassword +
                ", notFountProgramm=" + notFountProgramm +
                ", userId=" + userId +
                ", policyId=" + policyId +
                ", historyId=" + historyId +
                ", programmId=" + programmId +
                ", emailReciver='" + emailReciver + '\'' +
                ", emailVzrReciver='" + emailVzrReciver + '\'' +
                ", serviceDeskPhone='" + serviceDeskPhone + '\'' +
                ", contractId=" + contractId +
                '}';
    }
}
