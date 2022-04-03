package com.microservice.onlinemovieticketingplatform.controller;

import com.microservice.onlinemovieticketingplatform.beans.MovieDetails;
import com.microservice.onlinemovieticketingplatform.beans.ShowDetails;
import com.microservice.onlinemovieticketingplatform.beans.TheaterDetails;
import com.microservice.onlinemovieticketingplatform.service.OnlineMovieTicketService;
import com.microservice.onlinemovieticketingplatform.vo.MovieVO;
import com.microservice.onlinemovieticketingplatform.vo.OnlineMoviePlatformVO;
import com.microservice.onlinemovieticketingplatform.vo.TheaterVO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/online-movie")
public class OnlineMovieTicketController {

    @Autowired
   private OnlineMovieTicketService movieTicketService;

    @PostMapping("/save/movie")
    private String addMovie(@Valid @RequestBody List<MovieVO> movieDetails)
    {
        List<MovieDetails> dedupMovies=movieTicketService.saveMovie(movieDetails);
        if(dedupMovies.size()>0)
        {
            List<String> dedupMovieName=dedupMovies.stream().map(MovieDetails::getMovieName).collect(Collectors.toList());
            String mo = String.join(", ", dedupMovieName);
            return mo+" movies already exists";
        }
        return "Movies added successfully";
    }

    @PostMapping("/save/theater")
    private String addTheater(@RequestBody List<TheaterVO> theaterDetails)
    {
        List<TheaterDetails> dedupTheaters=movieTicketService.saveTheater(theaterDetails);
        if(dedupTheaters.size()>0)
        {
            List<String> dedupTheaterName=dedupTheaters.stream().map(TheaterDetails::getTheaterName).collect(Collectors.toList());
            String mo = String.join(", ", dedupTheaterName);
            return mo+" theaters already exists";
        }
        return "Theaters added successfully";
    }

    @PostMapping("/save/shows")
    private String addShows(@RequestBody List<OnlineMoviePlatformVO> showDetails)
    {
        movieTicketService.saveShowDetails(showDetails);
        return "Added successfully";
    }

    @PostMapping("/update/shows")
    private String updateShows(@RequestBody List<OnlineMoviePlatformVO> showDetails)
    {
        movieTicketService.updateShowDetails(showDetails);
        return "Updated successfully";
    }

    @PostMapping("/delete/shows")
    private String deleteShows(@RequestBody List<OnlineMoviePlatformVO> showDetails)
    {
        movieTicketService.deleteShowDetails(showDetails);
        return "Deleted successfully";
    }

    @GetMapping("/get/shows/{movieName}")
    public List<ShowDetails> retrieveShowsByMovieName(@PathVariable String movieName){
        MovieDetails movie=movieTicketService.fetchMovie(movieName,"ByName");
        List<ShowDetails> showDetails= movieTicketService.fetchShowDetails(movie);
        return showDetails;

    }

    @GetMapping("/get/shows/{movieName}/{movieDate}")
    public List<OnlineMoviePlatformVO> retrieveShowsByMovieNameAndDate(@PathVariable String movieName,@PathVariable String movieDate){
        MovieDetails movie=movieTicketService.fetchMovie(movieName,"ByName");
        List<ShowDetails> showDetails= movieTicketService.fetchShowDetailsByDate(movie,movieDate);
        List<OnlineMoviePlatformVO> detailsVO=new ArrayList<>();
        for(ShowDetails detail:showDetails)
        {
            OnlineMoviePlatformVO onlineMoviePlatformVO= OnlineMoviePlatformVO.builder().build();

            DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String newDate=format.format(Date.from(detail.getShowTime().toInstant()));

            onlineMoviePlatformVO.setShowTime(newDate);
            onlineMoviePlatformVO.setTheaterName(detail.getTheaterDetails().getTheaterName());
            onlineMoviePlatformVO.setMovieName(movieName);
            detailsVO.add(onlineMoviePlatformVO);
        }
        return detailsVO;

    }
}
