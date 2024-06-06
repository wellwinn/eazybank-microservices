package com.eazybytes.cards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Schema(name = "Card",description = "Schema to hold card information")
@Data
public class CardDto {
    @Schema(description = "Mobile number on the card")
    @NotEmpty
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobileNumber;

    @NotEmpty
    @Schema(description = "Card number on the card")
    private String cardNumber;

    @NotEmpty
    @Schema(description = "Type of card", example = "Visa")
    private String cardType;

    @NotNull
    @Schema(description = "Total limit on the card", example = "1000")
    private int totalLimit;

    @NotNull
    @Schema(description = "Amount spent from the card", example = "1000")
    private int amountUsed;

    @NotNull
    @Schema(description = "Amount available on the card", example = "1000")
    private int availableAmount;
}
