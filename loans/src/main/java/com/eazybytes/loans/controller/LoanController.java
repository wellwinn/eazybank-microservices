package com.eazybytes.loans.controller;

import com.eazybytes.loans.constants.LoansConstants;
import com.eazybytes.loans.dto.ErrorResponseDto;
import com.eazybytes.loans.dto.LoansContactInfoDto;
import com.eazybytes.loans.dto.LoansDto;
import com.eazybytes.loans.dto.ResponseDto;
import com.eazybytes.loans.service.ILoansService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIss for Loans in EazyBank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE loans"
)
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class LoanController {

    private final ILoansService iLoansService;

    private final LoansContactInfoDto loansContactInfoDto;

    private final Environment environment;

    @Value("${build.version}")
    private String buildVersion;

    public LoanController(ILoansService iLoansService, LoansContactInfoDto loansContactInfoDto, Environment environment) {
        this.iLoansService = iLoansService;
        this.loansContactInfoDto = loansContactInfoDto;
        this.environment = environment;
    }

    @Operation(
            summary = "Create Loan REST API",
            description = "REST API handler to create loan, in the Loans Microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "HTTP Status BAD_REQUEST"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createLoan(@Valid @RequestBody LoansDto loansDto){

        iLoansService.createLoan(loansDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(
                LoansConstants.STATUS_201,
                LoansConstants.MESSAGE_201
        ));
    }

    @Operation(
            summary = "Fetch loan REST API ",
            description = "REST API to fetch loan details, it takes mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/fetch")
    public ResponseEntity<LoansDto> fetchLoan(@Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number, should be digits, length should be 10") String mobileNumber){
        LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(
                loansDto);
    }

    @Operation(
            summary = "UPDATE loan REST API ",
            description = "REST API to update/modify loan details"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "417",
                    description = "Expectation Failed"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PutMapping("/updateLoan")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody LoansDto loansDto){
        boolean updatedLoan = iLoansService.updateLoans(loansDto);
        if(updatedLoan){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE)
            );
        }

    }

    @Operation(
            summary = "DELETE loan REST API ",
            description = "REST API to delete/shutdown loan, it takes mobile number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "HTTP Status NOT_FOUND",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/deleteLoan")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number, should be digits, length should be 10") String mobileNumber){
        boolean deletedLoan =  iLoansService.deleteLoans(mobileNumber);
        if(deletedLoan){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE)
            );
        }
    }

    @Operation(
            summary = "Get build version REST API",
            description = "Get build version details of EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
    }

    @Operation(
            summary = "Get JAVA info REST API",
            description = "Get Java environment details of EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/java-version")
    public ResponseEntity<String> getJavaInfo(){
        System.out.println(environment.getProperty("JAVA_HOME"));
        return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
    }
    @Operation(
            summary = "Get contact information ",
            description = "Get contact information of developer that deployed cards microservice"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @GetMapping("/contact-info")
    public ResponseEntity<LoansContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(loansContactInfoDto);
    }



}
