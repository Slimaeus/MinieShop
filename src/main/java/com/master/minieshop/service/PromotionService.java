package com.master.minieshop.service;

import com.master.minieshop.common.BaseEntityService;
import com.master.minieshop.entity.Promotion;
import com.master.minieshop.entity.PromotionDetail;
import com.master.minieshop.repository.PromotionDetailRepository;
import com.master.minieshop.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService extends BaseEntityService<Promotion, Long, PromotionRepository> {
    @Autowired
    private PromotionDetailRepository promotionDetailRepository;
    public PromotionService(PromotionRepository repository) {
        super(repository);
    }

    public List<PromotionDetail> savePromotionDetails(List<PromotionDetail> promotionDetails) {
        promotionDetailRepository.saveAll(promotionDetails);
        return promotionDetails;
    }
}
