package com.eazybytes.cards.controller;

import com.eazybytes.cards.constants.CardsConstants;
import com.eazybytes.cards.dto.CardDto;
import com.eazybytes.cards.dto.CardsContactInfoDto;
import com.eazybytes.cards.dto.ErrorResponseDto;
import com.eazybytes.cards.dto.ResponseDto;
import com.eazybytes.cards.service.ICardService;
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
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD REST APIs for Cards in Eazybank",
        description = "CRUD REST APIs in EazyBank to CREATE, UPDATE, FETCH and DELETE Cards details"
)
@RestController
@RequestMapping(value = "api", produces = MediaType.APPLICATION_JSON_VALUE)

public class CardsController {

    private final ICardService iCardService;

    private final Environment environment;

    private final CardsContactInfoDto cardsContactInfoDto;

    @Value("${build.version}")
    private String buildVersion;

    public CardsController(ICardService iCardService, Environment environment, CardsContactInfoDto cardsContactInfoDto) {
        this.iCardService = iCardService;
        this.environment = environment;
        this.cardsContactInfoDto = cardsContactInfoDto;
    }

    @Operation(
            summary = "Create Card REST API",
            description = "REST API to create new Customer & Card inside EazyBank"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "HTTP Status CREATED"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "HTTP Status Internal Server Error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })

    @PostMapping("/createCard")
    public ResponseEntity<ResponseDto> createAccount(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
        iCardService.createCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(
                CardsConstants.STATUS_201, CardsConstants.MESSAGE_201
        ));
    }

    @Operation(
            summary = "FETCH Card Details REST API",
            description = "REST API to fetch Card details inside EazyBank"
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
    @GetMapping("/fetchCard")
    public ResponseEntity<CardDto> fetchAccountDetails(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber){
        CardDto cardDto  = iCardService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardDto);
    }

    @Operation(
            summary = "UPDATE Card REST API ",
            description = "REST API to update/modify Card details"
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
    @PutMapping("/updateCard")
    public ResponseEntity<ResponseDto> updateLoan(@Valid @RequestBody CardDto cardDto){
        boolean updatedLoan = iCardService.updateCard(cardDto);
        if(updatedLoan){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto(CardsConstants.STATUS_200, CardsConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_UPDATE)
            );
        }
    }

    @Operation(
            summary = "DELETE CARD REST API ",
            description = "REST API to delete CARD, it takes mobile number"
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
    @DeleteMapping("/deleteCard")
    public ResponseEntity<ResponseDto> deleteLoan(@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number, should be digits, length should be 10") String mobileNumber){
        boolean deletedLoan =  iCardService.deleteCard(mobileNumber);
        if(deletedLoan){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDto( CardsConstants.STATUS_200, CardsConstants.MESSAGE_200)
            );
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseDto(CardsConstants.STATUS_417, CardsConstants.MESSAGE_417_DELETE)
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
    public ResponseEntity<CardsContactInfoDto> getContactInfo(){
        return ResponseEntity.status(HttpStatus.OK).body(cardsContactInfoDto);
    }

}
