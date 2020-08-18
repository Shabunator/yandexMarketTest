package ru.yandexMarket;

import org.junit.Test;

public class Tests extends Steps{

    @Test
    public void testYandexPage() {
        openYandexMarketPage();
        selectComputer();
        selectTablet();
        setManufacturers();
        priceFilter();
        selectFiveElements();
        assertFirstElement();
    }
}
