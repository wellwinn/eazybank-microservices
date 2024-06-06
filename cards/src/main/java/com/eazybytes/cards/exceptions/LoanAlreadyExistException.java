package com.eazybytes.cards.exceptions;

public class LoanAlreadyExistException extends RuntimeException{
    public LoanAlreadyExistException(String resourceName, String fieldName, String fieldValue){
        super(String.format("%s not found with the given input data %s : '%s'", resourceName, fieldName, fieldValue));
    }
}
