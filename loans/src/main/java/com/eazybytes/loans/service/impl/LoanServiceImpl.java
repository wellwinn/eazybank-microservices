package com.eazybytes.loans.service.impl;

import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.entity.Loans;
import com.eazybytes.loans.exceptions.LoanAlreadyExistException;
import com.eazybytes.loans.exceptions.ResourceNotFoundException;
import com.eazybytes.loans.mapper.LoansMapper;
import com.eazybytes.loans.repository.LoansRepository;
import com.eazybytes.loans.service.ILoansService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanServiceImpl implements ILoansService {

    private final LoansRepository loansRepository;

    /**
     * @param loansDto - LoansDto Object
     */
    @Override
    public void createLoan(LoansDto loansDto) {
        Optional<Loans> optionalLoans = loansRepository.findByMobileNumber(loansDto.getMobileNumber());
        if(optionalLoans.isPresent()){
            throw new LoanAlreadyExistException(String.format("Individual with mobile number %s, already has loan", loansDto.getLoanNumber()));
        }

        Loans loans = LoansMapper.mapToLoans(loansDto, new Loans());
        loansRepository.save(loans);
    }

    /**
     * @param mobileNumber - Customer mobile number used to apply for the loan
     * @return Loan Details based on the given mobileNumber
     */
    @Override
    public LoansDto fetchLoan(String mobileNumber) {
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException(String.format("Customer with mobile number %s, do not have loan", mobileNumber)));
        return LoansMapper.mapToLoansDto(loans, new LoansDto());
    }

    /**
     * @param loansDto - LoansDto representing modified data
     * @return boolean, true for success, false for error
     */
    @Override
    public boolean updateLoans(LoansDto loansDto) {
        boolean isUpdated = false;
        Loans loans = loansRepository.findByMobileNumber(loansDto.getMobileNumber()).orElseThrow(() -> new ResourceNotFoundException(String.format("Loan not found with mobile number, %s", loansDto.getMobileNumber())));

        LoansMapper.mapToLoans(loansDto, loans);
        loansRepository.save(loans);
        isUpdated = true;

        return isUpdated;
    }

    /**
     * @param mobileNumber - Customer mobile number to delete loan by
     * @return boolean, true for success, false for error
     */
    @Override
    public boolean deleteLoans(String mobileNumber) {
        boolean isDeleted = false;
        Loans loans = loansRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException(String.format("Customer with mobile number %s, do not have loan", mobileNumber)));

        loansRepository.delete(loans);
        isDeleted = true;

        return isDeleted;
    }
}
