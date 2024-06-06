package com.eazybytes.cards.service.impl;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.entity.Card;
import com.eazybytes.cards.exceptions.LoanAlreadyExistException;
import com.eazybytes.cards.exceptions.ResourceNotFoundException;
import com.eazybytes.cards.mapper.CardsMapper;
import com.eazybytes.cards.repository.CardRepository;
import com.eazybytes.cards.service.ICardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {

    private final CardRepository cardRepository;

    /**
     * @param mobileNumber - CardDto Object
     */
    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> byMobileNumber = cardRepository.findByMobileNumber(mobileNumber);
        if(byMobileNumber.isPresent()){
            throw new LoanAlreadyExistException("Loan", "mobileNumber",mobileNumber);
        }

        cardRepository.save(createNewCard(mobileNumber));

    }

    /**
     * @param mobileNumber - Mobile Number of the Customer
     * @return the new card details
     */
    @Override
    public Card createNewCard(String mobileNumber) {
        Card newCard = new Card();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomCardNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }


    /**
     * @param mobileNumber - Input mobileNumber
     * @return Card Details based on a given mobileNumber
     */
    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardsMapper.mapToCardDto(card, new CardDto());
    }

    /**
     * @param cardDto - CardDto Object
     * @return boolean indicating if the update of Card details is successful or not
     */
    @Override
    public boolean updateCard(CardDto cardDto) {
        boolean isUpdated = false;
        Card card = cardRepository.findByMobileNumber(cardDto.getMobileNumber()).orElseThrow(()-> new ResourceNotFoundException("Card", "mobileNumber", cardDto.getMobileNumber()));

        Card mapToCard = CardsMapper.mapToCard(cardDto, card);
        cardRepository.save(mapToCard);
        isUpdated = true;

        return isUpdated;
    }

    /**
     * @param mobileNumber - Input mobileNumber
     * @return boolean indicating if the delete of Card details is successful or not
     */
    @Override
    public boolean deleteCard(String mobileNumber) {
        boolean isDeleted = false;
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));

        cardRepository.delete(card);
        isDeleted = true;

        return isDeleted;
    }
}
