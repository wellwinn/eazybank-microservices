package com.eazybytes.loans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "Loans",
        description = "Schema to hold Loan information"
)
public class LoansDto {
    @NotEmpty(message = "mobileNumber cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobileNumber;

    @Pattern(regexp = "(^$|[0-9])")
    @NotEmpty(message = "loanNumber cannot be empty or null")
    private String loanNumber;

    @NotEmpty(message = "loanType cannot be empty or null")
    @Schema(description = "Loan type", example = "Personal")
    private String loanType;

    @NotNull(message = "loanNumber cannot be empty or null")
    @Schema(description = "total loan amount")
    private int totalLoan;

    @NotNull(message = "amountPaid cannot be empty or null")
    @Schema(description = "Amount paid out by the customer")
    private int amountPaid;

    @NotNull(message = "outstandingAmount cannot be empty or null")
    @Schema(description = "Amount of loan left to pay")
    private int outstandingAmount;
}
