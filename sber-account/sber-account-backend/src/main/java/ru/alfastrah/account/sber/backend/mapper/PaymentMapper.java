package ru.alfastrah.account.sber.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import ru.alfastrah.account.sber.backend.model.PaymentData;
import ru.alfastrah.account.sber.backend.model.policy.InvoiceCreateResult;

import java.io.Serializable;

@Mapper
public interface PaymentMapper extends Serializable {
    @Select(value = "{ call dbo.web_lk_sb_insured_InvoiceCreate ( #{xml, jdbcType=SQLXML, mode=IN}, " +
            "#{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN}, " +
            "#{premium, jdbcType=NUMERIC, mode=IN}, #{payType, jdbcType=NUMERIC, mode=IN}, " +
            "#{success, jdbcType=CHAR, mode=OUT}, #{faultMessage, jdbcType=VARCHAR, mode=OUT}, " +
            "#{invoiceId, jdbcType=NUMERIC, mode=OUT} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void invoiceCreation(InvoiceCreateResult result);

    @Select(value = "{ call dbo.web_platron_pay1 ( #{invoiceId, jdbcType=NUMERIC, mode=IN}, " +
            "#{premium, jdbcType=NUMERIC, mode=OUT}, #{info, jdbcType=VARCHAR, mode=OUT} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void getPaymentInfo(PaymentData info);
}
