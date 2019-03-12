package ru.alfastrah.account.sber.backend.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import ru.alfastrah.account.sber.backend.model.auth.AuthResult;
import ru.alfastrah.account.sber.backend.model.mail.AttachMailData;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.backend.model.policy.FamilyAddPremium;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoResult;
import ru.alfastrah.account.sber.backend.model.policy.PolicyPdfResult;
import ru.alfastrah.account.sber.backend.model.user.InsuredResult;
import ru.alfastrah.account.sber.backend.model.user.PayingServiceItem;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface PolicyMapper extends Serializable {
    @Results(value = {
            @Result(column = "PolicyNumber", property = "policyNumber", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredSurName", property = "surname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredFirstName", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredPatronymic", property = "patronymic", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredBirthDate", property = "birthDate", jdbcType = JdbcType.DATE),
            @Result(column = "PartnerName", property = "insurer", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TelephoneNumber", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "EMailAdress", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Filial", property = "filial", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TabNum", property = "tableNumber", jdbcType = JdbcType.VARCHAR),
            @Result(column = "GroupName", property = "programmGroup", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PolicyBeginDate", property = "beginDate", jdbcType = JdbcType.DATE),
            @Result(column = "PolicyEndDate", property = "endDate", jdbcType = JdbcType.DATE),
            @Result(column = "CancelDate", property = "cancelDate", jdbcType = JdbcType.DATE),
            @Result(column = "ContractNumber", property = "contractNumber", jdbcType = JdbcType.VARCHAR),
            @Result(column = "IndRelative", property = "relativeIndicator", jdbcType = JdbcType.CHAR),
            @Result(column = "Franch", property = "franchise", jdbcType = JdbcType.CHAR),
            @Result(column = "UserID", property = "userId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PolicyID", property = "policyId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "HistoryID", property = "historyId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ProgrammID", property = "programmId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "templateID", property = "bookletTemplateId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "templateIDPolicy", property = "policyTemplateId", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_InsData ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{success, jdbcType=VARCHAR, mode=OUT}, #{faultMessage, jdbcType=VARCHAR, mode=OUT}, " +
            "#{risk, jdbcType=VARCHAR, mode=IN} )}")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<InsuredResult> policyList(AuthResult result);

    @Results(value = {
            @Result(column = "SubjID", property = "subjectId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PolicyID", property = "policyId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "RelativeSurName", property = "surname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RelativeFirstName", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RelativePatronymic", property = "patronymic", jdbcType = JdbcType.VARCHAR),
            @Result(column = "RelativeBirthDate", property = "birthDate", jdbcType = JdbcType.DATE),
            @Result(column = "AvansSum", property = "premium", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ProgrammID", property = "programmId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IndRelative", property = "relativeIndicator", jdbcType = JdbcType.CHAR),
            @Result(column = "ChangeProg", property = "changeProgramm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Pay", property = "canChangeProgramm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "templateID", property = "templateId", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_Relatives ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<PayingServiceItem> getFamilyAttachmentList(PolicyInfoRequest request);

    @Results(value = {
            @Result(column = "SubjID", property = "subjectId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PolicyID", property = "policyId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "SurName", property = "surname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FirstName", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Patronymic", property = "patronymic", jdbcType = JdbcType.VARCHAR),
            @Result(column = "BirthDate", property = "birthDate", jdbcType = JdbcType.DATE),
            @Result(column = "Cost", property = "premium", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ProgrammID", property = "programmId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "IndRelative", property = "relativeIndicator", jdbcType = JdbcType.CHAR),
            /*@Result(column = "ChangeProg", property = "changeProgramm", jdbcType = JdbcType.VARCHAR),
            @Result(column = "Pay", property = "canChangeProgramm", jdbcType = JdbcType.VARCHAR),*/
            @Result(column = "templateID", property = "templateId", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_StomOffer ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<PayingServiceItem> getDentalFamilyList(PolicyInfoRequest request);

    @Results(value = {
            @Result(column = "Success", property = "success", jdbcType = JdbcType.VARCHAR),
            @Result(column = "FaultMessage", property = "faultMessage", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PremiumSum", property = "premium", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_GetAvansSum ( #{familyList, jdbcType=SQLXML, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    FamilyAddPremium getTotalAmountToFamily(String familyList);

    @Results(value = {
            @Result(column = "PDFPolicy", property = "policy", jdbcType = JdbcType.VARCHAR),
            @Result(column = "PDFProgram", property = "booklet", jdbcType = JdbcType.VARCHAR)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_GetPrintForm ( #{policyId}, #{programmId} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    PolicyPdfResult getPolicyPdf(PolicyInfoRequest request);

    @Select(value = "{ call dbo.web_lk_sb_insured_ChangeProgr ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{success, jdbcType=VARCHAR, mode=OUT}, #{faultMessage, jdbcType=VARCHAR, mode=OUT}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN}, " +
            "#{historyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void changeInsuredProgramm(PolicyInfoResult result);

    @Results(value = {
            @Result(column = "topic", property = "topic", jdbcType = JdbcType.VARCHAR),
            @Result(column = "subj", property = "mailSubject", jdbcType = JdbcType.VARCHAR),
            @Result(column = "body", property = "mailBody", jdbcType = JdbcType.VARCHAR),
            @Result(column = "recipients", property = "recipients", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ntable", property = "chatTable", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_feedback ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<ChatMailData> getChatMailDataList(PolicyInfoRequest request);

    @Results(value = {
            @Result(column = "subj", property = "subject", jdbcType = JdbcType.VARCHAR),
            @Result(column = "body", property = "text", jdbcType = JdbcType.VARCHAR),
            @Result(column = "recipients", property = "recipients", jdbcType = JdbcType.VARCHAR)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_DistRel ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<AttachMailData> getAttachMailDataList(PolicyInfoRequest request);

    @Results(value = {
            @Result(column = "SubjectLetter", property = "subject", jdbcType = JdbcType.VARCHAR),
            @Result(column = "body", property = "text", jdbcType = JdbcType.VARCHAR),
            @Result(column = "email", property = "recipients", jdbcType = JdbcType.VARCHAR)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_GetLetterVzr ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<AttachMailData> getVzrMailDataList(PolicyInfoRequest request);
}
