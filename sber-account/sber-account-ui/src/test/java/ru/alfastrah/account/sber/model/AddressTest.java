package ru.alfastrah.account.sber.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddressTest {
    private Address address;

    @Before
    public void setUp() {
        address = new Address();
    }

    @After
    public void tearDown() {
        address = null;
    }

    @Test
    public void getAddress() {
        address.setArea("Московская");
        address.setCity("Подольск");
        address.setStreet("Железнодорожная");
        address.setHouse("25");

        assertEquals("Неправильно сформировался адрес", "Московская, Подольск, Железнодорожная, 25", address.getAddress());
    }

    @Test
    public void getMoscowAddress() {
        address.setCity("Москва");
        address.setStreet("Южнобутовская");
        address.setHouse("15");
        address.setAppartment("10");

        assertEquals("Неправильно сформировался адрес", "Москва, Южнобутовская, 15, 10", address.getAddress());
    }
}