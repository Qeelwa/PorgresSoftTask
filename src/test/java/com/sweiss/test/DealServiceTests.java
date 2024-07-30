package com.sweiss.test;

import com.sweiss.exception.DuplicateDealException;
import com.sweiss.model.Deal;
import com.sweiss.repository.DealRepository;
import com.sweiss.services.DealService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = com.sweiss.services.DealService.class) // Replace with your main application class

class DealServiceTests {

    @Autowired
    private DealService dealService;

    @MockBean
    private DealRepository dealRepository;

    @Test
    void testSaveDeal() {
        Deal deal = new Deal();
        deal.setDealUniqueId("unique-id");
        deal.setFromCurrencyIsoCode("USD");
        deal.setToCurrencyIsoCode("EUR");
        deal.setDealTimestamp("2023-01-01T12:00:00Z");
        deal.setDealAmount(1000.0);

        when(dealRepository.findByDealUniqueId("unique-id")).thenReturn(Optional.empty());
        when(dealRepository.save(deal)).thenReturn(deal);

        Deal savedDeal = dealService.saveDeal(deal);
        assertNotNull(savedDeal);
    }

    @Test
    void testDuplicateDeal() {
        Deal deal = new Deal();
        deal.setDealUniqueId("unique-id");
        deal.setFromCurrencyIsoCode("USD");
        deal.setToCurrencyIsoCode("EUR");
        deal.setDealTimestamp("2023-01-01T12:00:00Z");
        deal.setDealAmount(1000.0);

        when(dealRepository.findByDealUniqueId("unique-id")).thenReturn(Optional.of(deal));

        assertThrows(DuplicateDealException.class, () -> dealService.saveDeal(deal));
    }
}
