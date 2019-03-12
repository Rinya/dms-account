package ru.alfastrah.account.sber.model;

import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.model.user.EvailablePagesAndLogo;
import ru.alfastrah.account.sber.constants.BoardMenu;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringComponent
@VaadinSessionScope
public class InsuredProfile implements Serializable {
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
    private boolean notAvailableProgramm;
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
     * Показывать или нет вкладку стоматология
     */
    private boolean showDental;
    /***
     * Может менять свой пароль
     */
    private boolean canChangePassword;

    private Map<BoardMenu, Boolean> evailablePageMap;

    private StreamResource companyLogo;

    public InsuredProfile() {
        evailablePageMap = new EnumMap<>(BoardMenu.class);

        evailablePageMap.put(BoardMenu.PROFILE, true);
        evailablePageMap.put(BoardMenu.EXIT, true);
    }

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

    public boolean isShowRelative() {
        return showRelative;
    }

    public void setShowRelative(boolean showRelative) {
        this.showRelative = showRelative;
    }

    public boolean isShowInvoice() {
        return showInvoice;
    }

    public void setShowInvoice(boolean showInvoice) {
        this.showInvoice = showInvoice;
    }

    public boolean isShowDeposit() {
        return showDeposit;
    }

    public void setShowDeposit(boolean showDeposit) {
        this.showDeposit = showDeposit;
    }

    public boolean isNeedProgramm() {
        return needProgramm;
    }

    public void setNeedProgramm(boolean needProgramm) {
        this.needProgramm = needProgramm;
    }

    public void setNotAvailableProgramm(boolean notAvailableProgramm) {
        this.notAvailableProgramm = notAvailableProgramm;
    }

    public boolean isNotAvailableProgramm() {
        return notAvailableProgramm;
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

    public void setShowDental(boolean showDental) {
        this.showDental = showDental;
    }

    public boolean showDental() {
        return showDental;
    }

    public boolean isCanChangePassword() {
        return canChangePassword;
    }

    public void setCanChangePassword(boolean canChangePassword) {
        this.canChangePassword = canChangePassword;
    }

    public String getFullName() {
        return Stream.of(surname, name, patronymic)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.joining(StringUtils.SPACE));
    }

    public void setEvailablePage(BoardMenu menu, boolean isEvailable) {
        evailablePageMap.put(menu, isEvailable);
    }

    public void setEvailablePageMapAndCompanyLogo(EvailablePagesAndLogo data) {
        companyLogo = data.getCompanyLogo();
    }

    public boolean isEvailableView(BoardMenu menu) {
        return evailablePageMap.get(menu) != null ? evailablePageMap.get(menu) : false;
    }

    public StreamResource getCompanyLogo() {
        return companyLogo;
    }

    @Override
    public String toString() {
        return "InsuredProfile{" +
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
                ", userId=" + userId +
                ", policyId=" + policyId +
                ", historyId=" + historyId +
                ", programmId=" + programmId +
                ", notAvailableProgramm=" + notAvailableProgramm +
                ", emailReciver='" + emailReciver + '\'' +
                ", emailVzrReciver='" + emailVzrReciver + '\'' +
                ", serviceDeskPhone='" + serviceDeskPhone + '\'' +
                ", showDental=" + showDental +
                ", canChangePassword=" + canChangePassword +
                ", evailablePageMap=" + evailablePageMap +
                ", companyLogo=" + companyLogo +
                '}';
    }
}
