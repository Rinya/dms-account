package ru.alfastrah.account.sber.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.model.FaqData;
import ru.alfastrah.account.sber.backend.model.policy.PolicyInfoRequest;
import ru.alfastrah.account.sber.backend.service.BackendApplicationService;
import ru.alfastrah.account.sber.model.InsuredProfile;

import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private BackendApplicationService applicationService;

    @Autowired
    public ApplicationServiceImpl(BackendApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Override
    public List<FaqData> getFaqList(InsuredProfile profile) {
        getLogger().trace("Enter to getFaqList {}", profile);
        PolicyInfoRequest request = new PolicyInfoRequest();
        request.setLogin(profile.getPhone());
        request.setSubjectId(profile.getUserId());
        request.setPolicyId(profile.getPolicyId());

        return applicationService.getFaqList(request);
    }
}
