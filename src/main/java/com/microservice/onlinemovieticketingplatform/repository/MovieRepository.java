package com.microservice.onlinemovieticketingplatform.repository;

import com.microservice.onlinemovieticketingplatform.beans.MovieDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<MovieDetails, Long> {
    MovieDetails findByMovieName(String movieName);
}
