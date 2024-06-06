package com.eazybytes.cards.service;

import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Card;

public interface ICardService {
    /**
     * @param mobileNumber - Mobile Number of the Customer
     * */
    void createCard(String mobileNumber);

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     * */
    Card createNewCard(String mobileNumber);

    /**
     * @param mobileNumber - Input mobileNumber
     * @return Card Details based on a given mobileNumber
     * */
    CardDto fetchCard(String mobileNumber);

    /**
     * @param cardDto - CardDto Object
     * @return boolean indicating if the update of Card details is successful or not
     * */
    boolean updateCard(CardDto cardDto);

    /**
     * @param mobileNumber - Input mobileNumber
     * @return boolean indicating if the delete of Card details is successful or not
     * */
    boolean deleteCard(String mobileNumber);
}
