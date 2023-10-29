package com.example.desafiostone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    private UUID id;
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("card_holder_name")
    private String cardHolderName;
    @JsonProperty("value")
    private Integer value;
    private Integer cvv;

    @JsonProperty("exp_date")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd")
    private String expDate;
}
