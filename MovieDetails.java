package com.microservice.onlinemovieticketingplatform.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String movieName;
    @Version
    private long versionId;
   // @OneToMany(fetch = FetchType.LAZY,mappedBy = "movieDetails")
  //  private List<ShowDetails> showDetails;


}
