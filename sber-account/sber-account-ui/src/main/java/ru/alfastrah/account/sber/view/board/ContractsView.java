package ru.alfastrah.account.sber.view.board;

import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.backend.exception.PolicyException;
import ru.alfastrah.account.sber.exception.PaymentException;
import ru.alfastrah.account.sber.helper.NotificationHelper;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.Insured;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.model.PayingItem;
import ru.alfastrah.account.sber.service.PolicyService;
import ru.alfastrah.account.sber.service.PrintService;
import ru.alfastrah.account.sber.service.ProfileService;
import ru.alfastrah.account.sber.styles.CommonStyles;
import ru.alfastrah.account.sber.view.component.PolicyInfoLayout;
import ru.alfastrah.account.sber.view.component.paying.NoPaying;
import ru.alfastrah.account.sber.view.component.paying.PayingForFamily;
import ru.alfastrah.account.sber.view.component.paying.PayingGrid;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

import static ru.alfastrah.account.sber.constants.BoardMenu.CONTRACT;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showSuccessNotification;

@SpringView(name = ContractsView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class ContractsView extends MainView {
    public static final String VIEW_NAME = "contracts";
    public static final String INSURED_SUCCESS_PAY_TEMPLATE = "Для %s успешно подключено улучшенное ЛПУ";

    private PolicyService policyService;
    private InsuredProfile insuredProfile;
    private PrintService printService;
    private ProfileService profileService;
    private UITexts uiTexts;

    @Autowired
    public ContractsView(BoardMenuView menu, PolicyService policyService,
                         InsuredProfile insuredProfile, PrintService printService,
                         ProfileService profileService, UITexts uiTexts) {
        super(menu);

        this.policyService = policyService;
        this.insuredProfile = insuredProfile;
        this.printService = printService;
        this.profileService = profileService;
        this.uiTexts = uiTexts;
    }

    @Override
    protected String getViewDescription() {
        return CONTRACT.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return CONTRACT.getIcon();
    }

    @PostConstruct
    private void contractInit() {
        getLogger().trace("ContractsView PostConstruct");

        fillLayout();
    }

    private void fillLayout() {
        createPoliciesLayout();
        createChangeProgrammLayout();
    }

    private void createPoliciesLayout() {
        try {
            List<Insured> insuredList = policyService.policyList(insuredProfile.getPhone());
            insuredList.forEach(insured -> {
                PolicyInfoLayout policyInfoLayout = new PolicyInfoLayout(insured, printService, uiTexts);
                policyInfoLayout.addStyleName(CommonStyles.MAX_WIDTH_1024);
                getContent().addComponent(policyInfoLayout);
            });
        } catch (Exception e) {
            getLogger().error("policyService.policyList exception {}", e);
            NotificationHelper.showErrorNotification("Не удалось получить список договоров", e);
        }
    }

    private void createChangeProgrammLayout() {
        getLogger().trace("createChangeProgrammLayout insuredProfile {}", insuredProfile);

        if (insuredProfile.isNotAvailableProgramm()) {
            NoPaying noProgramm = new NoPaying(uiTexts);
            noProgramm.addStyleName(CommonStyles.MAX_WIDTH_1024);
            getContent().addComponent(noProgramm);
        } else if (insuredProfile.isShowRelative()) {
            PayingForFamily payingForFamily = new PayingForFamily(printService, uiTexts,
                    policyService.familyList(insuredProfile));
            payingForFamily.addStyleName(CommonStyles.MAX_WIDTH_1024);
            getContent().addComponent(payingForFamily);

            payingForFamily.setListener((PayingGrid.PayingListener) (list, totalPremium) ->
                    payingFamily(payingForFamily, list, totalPremium));
        }
    }

    private void payingFamily(PayingForFamily payingForFamily, List<PayingItem> list, BigDecimal totalPremium) {
        boolean onlyUser = payingForFamily.userSelected() && CollectionUtils.isEmpty(list);
        String redirectUrl = null;
        String message;
        try {
            policyService.changeInsuredProgramm(insuredProfile);
            message = String.format(INSURED_SUCCESS_PAY_TEMPLATE, insuredProfile.getFullName());
            if (!onlyUser) {
                redirectUrl = policyService.paymentFamilyPolicies(insuredProfile, list, totalPremium);
                message = "Подключение родственников успешно выполнено";
            }

            profileService.loadProfileData(insuredProfile.getPhone());
            getContent().removeComponent(payingForFamily);
            uiTexts.refresh();
            createChangeProgrammLayout();

            if (StringUtils.isNotBlank(redirectUrl)) {
                getUI().getPage().open(redirectUrl, "_target");
            }
            showSuccessNotification(message);
        } catch (PaymentException|PolicyException e) {
            getLogger().error("policyService.paymentFamilyPolicies: exception {}", e);
            showErrorNotification("Не удалось подключить родственников по программе с франшизой ", e);
        }
    }
}
