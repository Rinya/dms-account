package ru.alfastrah.account.sber.backend.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.BlobTypeHandler;
import org.apache.ibatis.type.JdbcType;
import ru.alfastrah.account.sber.backend.model.DescriptionData;
import ru.alfastrah.account.sber.backend.model.auth.AuthResult;
import ru.alfastrah.account.sber.backend.model.user.EvailablePagesAndLogo;
import ru.alfastrah.account.sber.backend.model.user.ProfileData;

import java.io.Serializable;
import java.util.List;


@Mapper
public interface InsuredMapper extends Serializable{
    @Results(value = {
            @Result(column = "PolicyNumber", property = "policyNumber", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredSurName", property = "surname", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredFirstName", property = "name", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredPatronymic", property = "patronymic", jdbcType = JdbcType.VARCHAR),
            @Result(column = "InsuredBirthDate", property = "birthDate", jdbcType = JdbcType.DATE),
            @Result(column = "TabNum", property = "tableNumber", jdbcType = JdbcType.VARCHAR),
            @Result(column = "EMailAdress", property = "email", jdbcType = JdbcType.VARCHAR),
            @Result(column = "TelephoneNumber", property = "phone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ViewAcceptFranch", property = "showAcceptFranch", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ViewRelative", property = "showRelative", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ViewInvoice", property = "showInvoice", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ViewDeposit", property = "showDeposit", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ViewChangeProgramm", property = "needProgramm", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ViewStom", property = "showDental", jdbcType = JdbcType.NUMERIC),
            @Result(column = "view_questions", property = "showFaq", jdbcType = JdbcType.NUMERIC),
            @Result(column = "view_feedback", property = "showChat", jdbcType = JdbcType.NUMERIC),
            @Result(column = "pay_vzr", property = "showVZR", jdbcType = JdbcType.NUMERIC),
            @Result(column = "distant_relative", property = "showDistantRelative", jdbcType = JdbcType.NUMERIC),
            @Result(column = "change_password", property = "canChangePassword", jdbcType = JdbcType.NUMERIC),
            @Result(column = "NoFoundProgramm", property = "notFountProgramm", jdbcType = JdbcType.NUMERIC),
            @Result(column = "UserID", property = "userId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "PolicyID", property = "policyId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "HistoryID", property = "historyId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "ProgrammID", property = "programmId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "EmailRequestAttach", property = "emailReciver", jdbcType = JdbcType.VARCHAR),
            @Result(column = "EmailVzr", property = "emailVzrReciver", jdbcType = JdbcType.VARCHAR),
            @Result(column = "lkPhone", property = "serviceDeskPhone", jdbcType = JdbcType.VARCHAR),
            @Result(column = "ag_id", property = "contractId", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_InsDataPers ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{success, jdbcType=VARCHAR, mode=OUT}, #{faultMessage, jdbcType=VARCHAR, mode=OUT} )}")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    ProfileData getUserInfo(AuthResult result);

    @Results(value = {
            @Result(column = "id_message", property = "id", jdbcType = JdbcType.NUMERIC),
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "message", property = "message", jdbcType = JdbcType.CLOB, javaType = String.class),
            @Result(column = "format_id", property = "formId", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_getMessages ( #{login} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<DescriptionData> getDescriptionList(@Param("login") String login);

    @Results(value = {
            @Result(column = "view_lk_agr", property = "showPolicies", jdbcType = JdbcType.NUMERIC),
            @Result(column = "pay_stomo", property = "showDental", jdbcType = JdbcType.NUMERIC),
            @Result(column = "view_questions", property = "showFaq", jdbcType = JdbcType.NUMERIC),
            @Result(column = "view_feedback", property = "showChat", jdbcType = JdbcType.NUMERIC),
            @Result(column = "pay_vzr", property = "showVZR", jdbcType = JdbcType.NUMERIC),
            @Result(column = "change_password", property = "canChangePassword", jdbcType = JdbcType.NUMERIC),
            @Result(column = "logo", property = "companyLogo", jdbcType = JdbcType.LONGVARBINARY, typeHandler = BlobTypeHandler.class)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_Settings ( #{ag_id} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    EvailablePagesAndLogo getEvailablePages(Long contractId);
}
