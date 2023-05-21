package com.master.minieshop.repository;

import com.master.minieshop.entity.PromotionDetail;
import com.master.minieshop.key.PromotionDetailKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface PromotionDetailRepository extends JpaRepository<PromotionDetail, PromotionDetailKey> {
}
