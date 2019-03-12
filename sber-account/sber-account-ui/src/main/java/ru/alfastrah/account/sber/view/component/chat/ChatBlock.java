package ru.alfastrah.account.sber.view.component.chat;

import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.data.HasValue;
import com.vaadin.ui.*;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.backend.HasLogger;
import ru.alfastrah.account.sber.backend.model.mail.ChatMailData;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.ChatQuestion;
import ru.alfastrah.account.sber.model.InsuredProfile;
import ru.alfastrah.account.sber.styles.CommonStyles;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static ru.alfastrah.account.sber.backend.constant.ChatTable.INCORRECT_PERSONAL_DATA;
import static ru.alfastrah.account.sber.backend.constant.ChatTable.INCORRECT_RELATIVE_DATA;

public class ChatBlock extends VerticalLayout implements HasLogger {
    private ComboBox<ChatMailData> title;
    private TextArea textArea;
    private Upload attachFile;
    private ChatQuestion chatQuestion;
    private Binder<ChatQuestion> binder;
    private IncorrectPersonalDataBlock personalDataBlock;
    private IncorrectRelativeDataBlock relativeDataBlock;
    private VerticalLayout container;

    public ChatBlock(List<ChatMailData> titleList, InsuredProfile profile) {
        chatQuestion = new ChatQuestion();
        binder = new Binder<>();
        binder.setBean(chatQuestion);

        personalDataBlock = new IncorrectPersonalDataBlock(profile);
        relativeDataBlock = new IncorrectRelativeDataBlock(profile);

        buildLayout();

        mountData(titleList);
    }

    public ChatQuestion getItem() {
        chatQuestion.setMailData(title.getValue());
        if (INCORRECT_PERSONAL_DATA.equals(title.getValue().getChatTable())) {
            chatQuestion.setQuestion(personalDataBlock.getValue());
        } else if (INCORRECT_RELATIVE_DATA.equals(title.getValue().getChatTable())) {
            chatQuestion.setQuestion(relativeDataBlock.getValue());
        } else {
            chatQuestion.setQuestion(StringUtils.replace(textArea.getValue(), "\n", "<br>"));
        }

        return chatQuestion;
    }

    public void validate() throws ValidateException {
        if (title.getValue() != null && INCORRECT_RELATIVE_DATA.equals(title.getValue().getChatTable())) {
            relativeDataBlock.validate();
        }

        if (!binder.validate().isOk()) {
            String errorMessage = binder
                    .validate()
                    .getFieldValidationErrors()
                    .stream()
                    .filter(BindingValidationStatus::isError)
                    .map(BindingValidationStatus::getMessage)
                    .map(item -> item.orElse(""))
                    .collect(Collectors.joining("<br>"));
            throw new ValidateException(errorMessage);
        }
    }

    private void buildLayout() {
        setId("chat-block");
        setMargin(false);
        addStyleName(CommonStyles.MAX_WIDTH_1024);

        title = createTitleComboBox();

        container = createContainer();
        textArea = createQuestionBlock();
        container.addComponent(textArea);

        attachFile = createAttachFile();

        binder
                .forField(title)
                .withValidator(Objects::nonNull, "Выберите тему вопроса")
                .bind(ChatQuestion::getMailData, ChatQuestion::setMailData);

        addBindingForTextArea();

        addComponents(title, container, attachFile);
    }

    private void addBindingForTextArea() {
        binder
                .forField(textArea)
                .withValidator(StringUtils::isNotBlank, "Заполните свой вопрос")
                .bind(ChatQuestion::getQuestion, ChatQuestion::setQuestion);
    }

    private void mountData(List<ChatMailData> titleList) {
        title.setItems(titleList);
    }

    private VerticalLayout createContainer() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("container");
        layout.setMargin(false);
        layout.setSizeFull();

        return layout;
    }

    private ComboBox<ChatMailData> createTitleComboBox() {
        ComboBox<ChatMailData> filed = new ComboBox<>();
        filed.setId("title");
        filed.setEmptySelectionAllowed(false);
        filed.setPlaceholder("Тема");
        filed.setTextInputAllowed(false);
        filed.setWidth(500, Unit.PIXELS);
        filed.setPopupWidth("800px");
        filed.setRequiredIndicatorVisible(true);
        filed.setItemCaptionGenerator((ItemCaptionGenerator<ChatMailData>) ChatMailData::getTopic);

        filed.addValueChangeListener((HasValue.ValueChangeListener<ChatMailData>) event -> {
            changeQuestationBlock(event.getValue());
        });

        return filed;
    }

    private void changeQuestationBlock(ChatMailData data) {
        container.removeAllComponents();
        binder.removeBinding(textArea);
        if (INCORRECT_PERSONAL_DATA.equals(data.getChatTable())) {
            //replaceTextArea(personalDataBlock);
            container.addComponent(personalDataBlock);
        } else if (INCORRECT_RELATIVE_DATA.equals(data.getChatTable())) {
            //replaceTextArea(relativeDataBlock);
            container.addComponent(relativeDataBlock);
        } else {
            /*if (components.contains(personalDataBlock)) {
                replaceComponent(personalDataBlock, textArea);
            } else if (!components.contains(textArea)) {
                addComponent(textArea, 1);
            }*/
            container.addComponent(textArea);
            addBindingForTextArea();
        }
    }

    /*private void replaceTextArea(FormLayout block) {
        if (components.contains(textArea)) {
            replaceComponent(textArea, block);
        } else {
            addComponent(block, 1);
        }
        binder.removeBinding(textArea);
    }*/

    private TextArea createQuestionBlock() {
        TextArea area = new TextArea();
        area.setId("question");
        area.setPlaceholder("Вопрос");
        area.setWordWrap(true);
        area.setWidth(100, Unit.PERCENTAGE);
        area.setRows(10);

        return area;
    }

    private Upload createAttachFile() {
        FileReciever reciever = new FileReciever();
        Upload upload = new Upload();
        upload.setId("attach-file");
        upload.setResponsive(true);
        upload.setImmediateMode(true);
        upload.setReceiver(reciever);
        upload.addSucceededListener(reciever);
        upload.addStyleName(CommonStyles.ALFA_UPLOAD);
        upload.setButtonCaption("Прикрепить файл");

        return upload;
    }

    class FileReciever implements Upload.Receiver, Upload.SucceededListener {
        private transient ByteArrayOutputStream outputStream;

        @Override
        public OutputStream receiveUpload(String filename, String mimeType) {
            chatQuestion.setAttachName(filename);
            outputStream = new ByteArrayOutputStream();
            return outputStream;
        }

        @Override
        public void uploadSucceeded(Upload.SucceededEvent succeededEvent) {
            try {
                File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), "tmp");
                try (FileOutputStream fileOutputStream = new FileOutputStream(tempFile)) {
                    outputStream.writeTo(fileOutputStream);
                    outputStream.flush();
                    outputStream.close();

                    chatQuestion.setAttach(tempFile);
                    attachFile.setEnabled(false);
                }
            } catch (IOException e) {
                getLogger().error("create temp file exception {}", e);
            }
        }
    }
}
