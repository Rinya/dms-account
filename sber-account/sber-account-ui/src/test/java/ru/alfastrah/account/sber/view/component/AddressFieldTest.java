package ru.alfastrah.account.sber.view.component;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.alfastrah.account.sber.exception.ValidateException;
import ru.alfastrah.account.sber.model.Address;

public class AddressFieldTest {
    private AddressField addressField;

    @Before
    public void setUp() {
        addressField = new AddressField();
        addressField.initContent();
    }

    @After
    public void tearDown() {
        addressField = null;
    }

    @Test
    public void doSetValue() throws ValidateException {
        Address address = new Address();
        address.setCity("Москва");
        address.setStreet("Южнобутовская");
        address.setHouse("15");
        address.setAppartment("10");

        addressField.setValue(address);
        addressField.validate();
    }
}