package com.microservice.onlinemovieticketingplatform.beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime showTime;
    @Version
    private long versionId;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="movie_id")
    private MovieDetails movieDetails;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="theater_id")
    private TheaterDetails theaterDetails;


}
