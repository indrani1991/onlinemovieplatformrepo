package com.microservice.onlinemovieticketingplatform.repository;


import com.microservice.onlinemovieticketingplatform.beans.MovieDetails;
import com.microservice.onlinemovieticketingplatform.beans.TheaterDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<TheaterDetails, Long> {
    TheaterDetails findByTheaterName(String theaterName);
}
