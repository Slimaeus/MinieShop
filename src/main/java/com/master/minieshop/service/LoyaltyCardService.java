package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.LoyaltyCard;
import com.master.minieshop.repository.LoyaltyCardRepository;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyCardService extends BaseEntityService<LoyaltyCard, Long, LoyaltyCardRepository> {
    public LoyaltyCardService(LoyaltyCardRepository repository) {
        super(repository);
    }
}
