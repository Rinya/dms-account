package ru.alfastrah.account.sber.backend.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.type.JdbcType;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;

import java.io.Serializable;
import java.util.List;

@Mapper
public interface ApplicationMapper extends Serializable {

    @Results(value = {
            @Result(column = "title", property = "title", jdbcType = JdbcType.VARCHAR),
            @Result(column = "message", property = "message", jdbcType = JdbcType.VARCHAR)
    })
    @Select(value = "{ call dbo.web_lk_sb_insured_faq ( #{login, jdbcType=VARCHAR, mode=IN}, " +
                    " #{subjectId, jdbcType=NUMERIC, mode=IN}, #{policyId, jdbcType=NUMERIC, mode=IN} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    List<FaqData> getFaqList(PolicyInfoRequest request);
}
