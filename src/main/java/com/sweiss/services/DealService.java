package com.sweiss.services;

import com.sweiss.exception.DuplicateDealException;
import com.sweiss.exception.InvalidDealException;
import com.sweiss.model.Deal;
import com.sweiss.repository.DealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DealService {

    @Autowired
    private DealRepository dealRepository;

    public void validateDeal(Deal deal) {
        if (deal.getDealUniqueId() == null || deal.getFromCurrencyIsoCode() == null ||
                deal.getToCurrencyIsoCode() == null || deal.getDealTimestamp() == null ||
                deal.getDealAmount() == null) {
            throw new InvalidDealException("Missing fields in deal");
        }
        // Add additional validation logic here
    }

    public Deal saveDeal(Deal deal) {
        if (dealRepository.findByDealUniqueId(deal.getDealUniqueId()).isPresent()) {
            throw new DuplicateDealException("Deal with unique ID " + deal.getDealUniqueId() + " already exists.");
        }
        return dealRepository.save(deal);
    }
}
