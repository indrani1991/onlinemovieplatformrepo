package com.microservice.onlinemovieticketingplatform.repository;

import com.microservice.onlinemovieticketingplatform.beans.MovieDetails;
import com.microservice.onlinemovieticketingplatform.beans.ShowDetails;
import com.microservice.onlinemovieticketingplatform.beans.TheaterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<ShowDetails, Long> {
    List<ShowDetails> findByMovieDetails(MovieDetails movieDetails);
    List<ShowDetails> findByMovieDetailsAndTheaterDetails(MovieDetails movieDetails, TheaterDetails theaterDetails);
}
