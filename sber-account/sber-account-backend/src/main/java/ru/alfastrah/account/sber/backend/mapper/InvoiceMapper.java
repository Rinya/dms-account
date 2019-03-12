package ru.alfastrah.account.sber.backend.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceDetail;
import ru.alfastrah.account.sber.backend.model.invoice.InvoiceShortData;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface InvoiceMapper extends Serializable {
    @Results(value = {
            @Result(column = "invoice_franch_id", property = "invoiceId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "invoice_franch_number", property = "invoiceNumber", jdbcType = JdbcType.VARCHAR),
            @Result(column = "issue_date", property = "issueDate", jdbcType = JdbcType.DATE),
            @Result(column = "subj_id", property = "subjectId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "name", property = "insured", jdbcType = JdbcType.VARCHAR),
            @Result(column = "rs", property = "account", jdbcType = JdbcType.VARCHAR),
            @Result(column = "sum_rur", property = "premium", jdbcType = JdbcType.NUMERIC),
            @Result(column = "sum_rur_diff", property = "deposit", jdbcType = JdbcType.NUMERIC),
            @Result(column = "sum_rur_to_pay", property = "payment", jdbcType = JdbcType.NUMERIC),
            @Result(column = "invoice_franch_state", property = "invoiceStateId", jdbcType = JdbcType.NUMERIC),
            @Result(column = "invoice_franch_state_name", property = "invoiceState", jdbcType = JdbcType.VARCHAR)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_showInvoiceFranch ( #{login} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<InvoiceShortData> getInvoiceList(@Param("login") String login);

    @Results(value = {
            @Result(column = "rownum", property = "id", jdbcType = JdbcType.NUMERIC),
            @Result(column = "corp_full_name", property = "hospital", jdbcType = JdbcType.VARCHAR),
            @Result(column = "mt_date", property = "requestDate", jdbcType = JdbcType.DATE),
            @Result(column = "service_name", property = "medicalService", jdbcType = JdbcType.VARCHAR),
            @Result(column = "qnty", property = "serviceCount", jdbcType = JdbcType.NUMERIC),
            @Result(column = "sum_franch", property = "premium", jdbcType = JdbcType.NUMERIC)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_showInvoiceFranchDetail ( #{invoiceId} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<InvoiceDetail> getInvoiceDetailList(@Param("invoiceId") Long invoiceId);
}
