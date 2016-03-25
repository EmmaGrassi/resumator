package io.sytac.resumator.security;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class Nonce {
    
    private String userName;
    
    private String timeStamp;
    
    private String salt;
    

    @JsonCreator
    public Nonce(@JsonProperty("userName") final String userName,
                 @JsonProperty("timeStamp") final String timeStamp,
                 @JsonProperty("salt") final String salt)
    {
      this.userName=userName;
      this.timeStamp=timeStamp;
      this.salt=salt;
    }


}
