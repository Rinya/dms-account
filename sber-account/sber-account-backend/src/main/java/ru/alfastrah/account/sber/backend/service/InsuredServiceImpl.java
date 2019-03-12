package ru.alfastrah.account.sber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.mapper.InsuredMapper;
import ru.alfastrah.account.sber.backend.model.DescriptionData;
import ru.alfastrah.account.sber.backend.model.auth.AuthResult;
import ru.alfastrah.account.sber.backend.model.user.EvailablePagesAndLogo;
import ru.alfastrah.account.sber.backend.model.user.ProfileData;

import java.util.List;


@Service
public class InsuredServiceImpl implements InsuredService {
    private InsuredMapper insuredMapper;

    @Autowired
    public InsuredServiceImpl(InsuredMapper insuredMapper) {
        this.insuredMapper = insuredMapper;
    }

    @Override
    public ProfileData getUserInfo(String login) {
        //Вызов для получения информации о пользователе
        AuthResult result = new AuthResult();
        result.setLogin(login);
        ProfileData userInfo = insuredMapper.getUserInfo(result);
        getLogger().trace("userInfo {}", userInfo);

        return userInfo;
    }

    @Override
    public List<DescriptionData> getDescriptionList(String login) {
        return insuredMapper.getDescriptionList(login);
    }

    @Override
    public EvailablePagesAndLogo getEvailablePages(Long contractId) {
        return insuredMapper.getEvailablePages(contractId);
    }
}
