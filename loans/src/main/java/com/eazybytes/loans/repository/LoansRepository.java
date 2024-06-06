package com.eazybytes.loans.repository;

import com.eazybytes.loans.entity.Loans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoansRepository extends JpaRepository<Loans, String> {

    Optional<Loans> findByMobileNumber(String mobileNumber);

}
