package com.microservice.onlinemovieticketingplatform.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TheaterDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String theaterName;
    @Version
    private long versionId;

    //@OneToMany(fetch = FetchType.LAZY,mappedBy = "theaterDetails")
    //private List<ShowDetails> showDetails;


}
