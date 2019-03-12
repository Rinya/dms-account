package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;
import ru.alfastrah.account.sber.backend.model.policy.PolicyPdfResult;
import ru.alfastrah.account.sber.backend.model.user.InsuredResult;
import ru.alfastrah.account.sber.backend.model.user.PayingServiceItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public interface BackendPolicyService extends Serializable, HasLogger {
    /***
     * Процедура получения итоговой премии, которую клиент должен заплатить для подключения родственников
     * @param familyXml Список id родственников в виде XML
     *                  Пример:
     *                  <Relatives>
     *                      <UserID>100</UserID>
     *                      <UserID>200</UserID>
     *                      <UserID>300</UserID>
     *                  </Relatives>
     * @return премия
     */
    BigDecimal totalAmountToFamily(String familyXml) throws PolicyException;

    /***
     * Процедура получения ПФ в формате base64 по полису и памятке
     * @param request На входе необходимо иметь ID полиса и ID программы
     * @return
     */
    PolicyPdfResult getPolicyAndBookletPdf(PolicyInfoRequest request);

    List<InsuredResult> getPolicyList(String login);

    List<InsuredResult> getDentalPolicyList(String login);

    List<PayingServiceItem> getFamilyList(PolicyInfoRequest request);

    List<PayingServiceItem> getDentalAttachList(PolicyInfoRequest request);

    void changeInsuredProgramm(PolicyInfoRequest request) throws PolicyException;

    List<ChatMailData> getChatMailDataList(PolicyInfoRequest request);

    List<AttachMailData> getAttachMailDataList(PolicyInfoRequest request);

    List<AttachMailData> getVzrMailDataList(PolicyInfoRequest request);
}
