package ru.alfastrah.account.sber.service;

import com.vaadin.spring.annotation.VaadinSessionScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfastrah.account.sber.backend.model.DescriptionData;
import ru.alfastrah.account.sber.backend.service.InsuredService;
import ru.alfastrah.account.sber.model.UIText;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@VaadinSessionScope
public class UIServiceImpl implements UIService {
    private InsuredService insuredService;

    @Autowired
    public UIServiceImpl(InsuredService insuredService) {
        this.insuredService = insuredService;
    }

    @Override
    public Map<Integer, UIText> getTextMap(String login) {
        List<DescriptionData> list = insuredService.getDescriptionList(login);
        return list.stream().collect(Collectors
                .toMap(DescriptionData::getId, item -> new UIText(item.getTitle(), item.getMessage(), item.getFormId())));
    }
}
