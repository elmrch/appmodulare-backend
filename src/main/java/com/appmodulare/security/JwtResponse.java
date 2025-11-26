package com.appmodulare.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.antlr.v4.runtime.Token;

public record JwtResponse(@JsonProperty(value = "access_token") String accesstoken){
}
