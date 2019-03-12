package ru.alfastrah.account.sber.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;
import ru.alfastrah.account.sber.backend.model.auth.AuthResult;
import ru.alfastrah.account.sber.backend.model.auth.RegistrationDataWithCheckResult;

@Mapper
public interface AuthenticationMapper {

    @Select(value = "{ call dbo.web_lk_sb_insured_AuthIns ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "#{encodedPassword, jdbcType=VARCHAR, mode=IN}, #{success, jdbcType=CHAR, mode=OUT}, " +
            "#{faultMessage, jdbcType=VARCHAR, mode=OUT} )}")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void login(AuthResult data);

    @Select(value = "{ call dbo.web_lk_sb_insured_CheckIns ( #{policyNumber, jdbcType=VARCHAR, mode=IN}, " +
            "#{insuredSurName, jdbcType=VARCHAR, mode=IN}, #{insuredFirstName, jdbcType=VARCHAR, mode=IN}, " +
            "#{insuredPatronymic, jdbcType=VARCHAR, mode=IN}, #{insuredBirthDate, jdbcType=DATE, mode=IN}, " +
            "#{phoneNumber, jdbcType=VARCHAR, mode=IN}, #{success, jdbcType=CHAR, mode=OUT}, " +
            "#{faultMessage, jdbcType=VARCHAR, mode=OUT}, #{userId, jdbcType=NUMERIC, mode=OUT}, " +
            "#{franchise, jdbcType=INTEGER, mode=OUT} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void checkUser(RegistrationDataWithCheckResult user);

    @Select(value = "{ call dbo.web_lk_sb_insured_SaveIns ( #{userId, jdbcType=NUMERIC, mode=IN}, " +
            "#{login, jdbcType=VARCHAR, mode=IN}, #{encodedPassword, jdbcType=VARCHAR, mode=IN}, " +
            "#{email, jdbcType=VARCHAR, mode=IN}, #{success, jdbcType=CHAR, mode=OUT}, " +
            "#{faultMessage, jdbcType=VARCHAR, mode=OUT} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void registerUser(AuthResult data);

    @Select(value = "{ call dbo.web_lk_sb_insured_ChangePassword ( #{login, jdbcType=VARCHAR, mode=IN}, " +
            "           #{encodedPassword, jdbcType=VARCHAR, mode=IN}, #{success, jdbcType=CHAR, mode=OUT}, " +
            "           #{faultMessage, jdbcType=VARCHAR, mode=OUT} ) }")
    @Options(statementType = StatementType.CALLABLE, useCache = false)
    void changePassword(AuthResult data);
}
