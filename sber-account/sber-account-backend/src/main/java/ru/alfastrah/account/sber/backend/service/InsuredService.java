package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.exception.InsuredException;
import ru.alfastrah.account.sber.backend.model.DescriptionData;
import ru.alfastrah.account.sber.backend.model.user.EvailablePagesAndLogo;
import ru.alfastrah.account.sber.backend.model.user.ProfileData;

import java.io.Serializable;
import java.util.List;

public interface InsuredService extends Serializable, HasLogger {
    ProfileData getUserInfo(String login) throws InsuredException;

    List<DescriptionData> getDescriptionList(String login);

    EvailablePagesAndLogo getEvailablePages(Long contractId);
}
