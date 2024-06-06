package com.eazybytes.cards.repository;

import com.eazybytes.cards.entity.Card;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    Optional<Card> findByMobileNumber(String mobileNumber);

    @Transactional @Modifying
    void deleteByMobileNumber(String mobileNumber);
}
