package ru.alfastrah.account.sber.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.mapper.ApplicationMapper;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;

import java.util.List;

@Service
public class BackendApplicationServiceImpl implements BackendApplicationService {
    private ApplicationMapper applicationMapper;

    @Autowired
    public BackendApplicationServiceImpl(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    @Override
    public List<FaqData> getFaqList(PolicyInfoRequest request) {
        return applicationMapper.getFaqList(request);
    }
}
