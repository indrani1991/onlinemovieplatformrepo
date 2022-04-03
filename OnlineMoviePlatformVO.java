package com.microservice.onlinemovieticketingplatform.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineMoviePlatformVO {
    private String showTime;
    private String theaterName;
    private String movieName;
    private String newShowTime;

}
