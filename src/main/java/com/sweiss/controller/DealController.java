package com.sweiss.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sweiss.services.DealService;
import com.sweiss.model.Deal;
import com.sweiss.exception.*;
@RestController
@RequestMapping("/api/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    @PostMapping
    public ResponseEntity<Deal> importDeal(@RequestBody Deal deal) {
        dealService.validateDeal(deal);
        Deal savedDeal = dealService.saveDeal(deal);
        return ResponseEntity.ok(savedDeal);
    }

    @ExceptionHandler({InvalidDealException.class, DuplicateDealException.class})
    public ResponseEntity<String> handleException(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
