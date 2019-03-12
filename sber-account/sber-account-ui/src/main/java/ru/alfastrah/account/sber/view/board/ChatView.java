package ru.alfastrah.account.sber.view.board;

import com.vaadin.event.MouseEvents;
import com.vaadin.server.Resource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;
import ru.alfastrah.account.sber.AppUI;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.helper.UITexts;
import ru.alfastrah.account.sber.model.ChatQuestion;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.service.EmailService;
import ru.alfastrah.account.sber.service.PolicyService;
import ru.alfastrah.account.sber.styles.view.ContractStyle;
import ru.alfastrah.account.sber.view.component.FeedbackBlock;
import ru.alfastrah.account.sber.view.component.chat.ChatBlock;
import ru.alfastrah.account.sber.view.menu.BoardMenuView;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

import static ru.alfastrah.account.sber.constants.BoardMenu.CHAT;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showErrorNotification;
import static ru.alfastrah.account.sber.helper.NotificationHelper.showSuccessNotification;

@SpringView(name = ChatView.VIEW_NAME, ui = AppUI.class)
@UIScope
public class ChatView extends MainView {
    public static final String VIEW_NAME = "chat";
    private static final int FEEDBACK_EMAIL_TEXT = 21;
    private static final int VIEW_TEXT = 18;
    private InsuredProfile profile;
    private PolicyService policyService;
    private EmailService emailService;
    private UITexts uiTexts;
    private VerticalLayout chatContainer;
    private transient List<ChatMailData> mailDataList;
    private FeedbackBlock feedbackBlock;

    @Autowired
    public ChatView(BoardMenuView menu, PolicyService policyService,
                    UITexts uiTexts, EmailService emailService, InsuredProfile profile) {
        super(menu);
        this.policyService = policyService;
        this.emailService = emailService;
        this.uiTexts = uiTexts;
        this.profile = profile;
    }

    @Override
    protected String getViewDescription() {
        return CHAT.getName();
    }

    @Override
    protected Resource getViewIcon() {
        return CHAT.getIcon();
    }

    @PostConstruct
    private void buildLayout() {
        mailDataList = policyService.getChatMailDataList(profile);

        Component textComponent = uiTexts.getTextComponent(VIEW_TEXT);

        Button sendButton = createSendButton();
        Button addChatBlock = createAddChatBlock();
        chatContainer = createChatContainer();
        chatContainer.addComponent(new ChatBlock(mailDataList, profile));
        feedbackBlock = new FeedbackBlock(emailService, uiTexts.getUiText(FEEDBACK_EMAIL_TEXT));

        getContent().addComponents(textComponent, chatContainer, addChatBlock, feedbackBlock, sendButton);
        getContent().setComponentAlignment(sendButton, Alignment.MIDDLE_CENTER);
    }

    private VerticalLayout createChatContainer() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("chat-container");
        layout.setMargin(false);
        return layout;
    }

    private Button createAddChatBlock() {
        Button button = new Button("Задать еще вопрос");
        button.setId("add-question");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent ->
                chatContainer.addComponent(createChatBlock()));
        return button;
    }

    private Button createSendButton() {
        Button button = new Button("ОТПРАВИТЬ");
        button.setId("send-button");
        button.addStyleName(ValoTheme.BUTTON_PRIMARY);
        button.addClickListener((Button.ClickListener) clickEvent -> {
            try {
                sendEmail();
                feedbackBlock.sendFeedback();
                clearPage();
                showSuccessNotification("Ваш вопрос(ы) успешно отправлен");
            } catch (Exception e) {
                getLogger().error("getting question data exception {}", e);
                showErrorNotification(e);
            }
        });

        return button;
    }

    private void clearPage() {
        chatContainer.removeAllComponents();
        chatContainer.addComponent(new ChatBlock(mailDataList, profile));
        feedbackBlock.clear();
    }

    private void sendEmail() throws MessagingException, ValidateException {
        List<ChatQuestion> questionList = new ArrayList<>();

        for (int i = 0; i < chatContainer.getComponentCount(); i++) {
            Component component = chatContainer.getComponent(i);
            boolean isChatBlock = component instanceof ChatBlock;

            if (isChatBlock) {
                ((ChatBlock) component).validate();
                questionList.add(((ChatBlock) component).getItem());
            } else if (component instanceof HorizontalLayout &&
                    ((HorizontalLayout) component).getComponent(1) instanceof ChatBlock) {
                ChatBlock chatBlock = (ChatBlock) ((HorizontalLayout) component).getComponent(1);
                chatBlock.validate();
                questionList.add(chatBlock.getItem());
            }
        }

        emailService.sendEmailWithAttachment(questionList);
    }

    private HorizontalLayout createChatBlock() {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setId("chat-block-layout");
        layout.setSizeFull();

        Image delete = new Image();
        delete.setId("delete-button");
        delete.addStyleName(ContractStyle.CLOSE_BUTTON);
        delete.addClickListener((MouseEvents.ClickListener) clickEvent -> chatContainer.removeComponent(layout));

        layout.addComponent(delete);
        layout.addComponentsAndExpand(new ChatBlock(mailDataList, profile));
        return layout;
    }
}
