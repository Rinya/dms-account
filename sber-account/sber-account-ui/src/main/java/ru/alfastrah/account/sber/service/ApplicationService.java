package ru.alfastrah.account.sber.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.model.InsuredProfile;

import java.io.Serializable;
import java.util.List;

public interface ApplicationService extends Serializable, HasLogger {
    List<FaqData> getFaqList(InsuredProfile profile);
}
