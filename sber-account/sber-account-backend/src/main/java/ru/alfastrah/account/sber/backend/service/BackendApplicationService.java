package ru.alfastrah.account.sber.backend.service;

import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;

import java.io.Serializable;
import java.util.List;

public interface BackendApplicationService extends Serializable, HasLogger {
    List<FaqData> getFaqList(PolicyInfoRequest request);
}
