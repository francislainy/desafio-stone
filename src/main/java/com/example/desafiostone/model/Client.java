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
public class Client {

    @JsonProperty("client_id")
    UUID id;

    @JsonProperty("client_name")
    String name;
}
