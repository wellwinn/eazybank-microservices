package com.eazybytes.loans.service;


import com.eazybytes.loans.dto.LoansDto;

public interface ILoansService {

    /**
     * @param loansDto - LoansDto Object
     * */
    void createLoan(LoansDto loansDto);

    /**
     * @param mobileNumber - Customer mobile number used to apply for the loan
     * @return Loan Details based on the given mobileNumber
     * */
    LoansDto fetchLoan(String mobileNumber);

    /**
     * @param loansDto - LoansDto representing modified data
     * @return boolean, true for success, false for error
     * */
    boolean updateLoans(LoansDto loansDto);

    /**
     * @param mobileNumber - Customer mobile number to delete loan by
     * @return boolean, true for success, false for error
     * */
    boolean deleteLoans(String mobileNumber);
}
