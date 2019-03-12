package ru.alfastrah.account.sber.view.component;

import com.vaadin.data.Binder;
import com.vaadin.data.BindingValidationStatus;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.apache.commons.lang3.StringUtils;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.Address;

import java.util.stream.Collectors;

import static ru.alfastrah.account.sber.util.FieldUtils.createTextField;

public class AddressField extends AbstractCollapsedField<Address> {
    private static final String FILL_ADDRESS_MESSAGE = "Заполните адрес";
    private Address address;
    private Binder<Address> binder;
    private VerticalLayout addressLayout;
    private Button expandButton;

    AddressField() {
        address = new Address();
        binder = new Binder<>();
        binder.setBean(address);
    }

    @Override
    protected Component initContent() {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setId("address-component-layout");
        mainLayout.setMargin(false);
        mainLayout.setSpacing(false);

        addressLayout = createAddressLayout();
        expandButton = getCollapsedButton();

        mainLayout.addComponent(expandButton);
        mainLayout.addComponent(addressLayout);

        return mainLayout;
    }

    @Override
    protected void doSetValue(Address address) {
        this.address = address;
        binder.setBean(address);
        changeButtonCaption(address);
    }

    @Override
    public Address getValue() {
        return address;
    }

    @Override
    protected String getCollapseCaption() {
        return FILL_ADDRESS_MESSAGE;
    }

    @Override
    protected Button.ClickListener addClickListener() {
        return clickEvent -> collapseField(!addressLayout.isVisible());
    }

    void validate() throws ValidateException {
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

    void collapse() {
        collapseField(false);
    }

    private void collapseField(boolean visible) {
        addressLayout.setVisible(visible);
        changeButtonCaption(address);
    }

    private void changeButtonCaption(Address address) {
        if (expandButton != null) {
            expandButton.setCaption(address != null && StringUtils.isNotBlank(address.getAddress()) ?
                    address.getAddress() : FILL_ADDRESS_MESSAGE);
        }
    }

    private VerticalLayout createAddressLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setId("address-layout");
        layout.setMargin(false);
        layout.setResponsive(true);
        layout.setVisible(false);

        TextField cityField = createTextField(null, "city", "Город", true);
        binder.forField(cityField)
                .withValidator(StringUtils::isNotBlank, "Заполните поле город")
                .bind(Address::getCity, Address::setCity);

        TextField areaField = createTextField(null, "area", "Район/область");
        binder.forField(areaField)
                .bind(Address::getArea, Address::setArea);

        TextField streetField = createTextField(null, "street", "Улица", true);
        binder.forField(streetField)
                .withValidator(StringUtils::isNotBlank, "Заполните поле улица")
                .bind(Address::getStreet, Address::setStreet);

        TextField houseField = createTextField(null, "house", "Дом", true);
        binder.forField(houseField)
                .withValidator(StringUtils::isNotBlank, "Заполните поле дом")
                .bind(Address::getHouse, Address::setHouse);

        TextField buildingField = createTextField(null, "building", "Корпус");
        binder.forField(buildingField)
                .bind(Address::getBuilding, Address::setBuilding);

        TextField apartmentField = createTextField(null, "apartment", "Квартира");
        binder.forField(apartmentField)
                .bind(Address::getAppartment, Address::setAppartment);

        layout.addComponents(areaField, cityField, streetField, houseField, buildingField, apartmentField);

        return layout;
    }
}
