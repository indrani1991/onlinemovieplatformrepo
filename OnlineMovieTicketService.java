package com.microservice.onlinemovieticketingplatform.service;

import com.microservice.onlinemovieticketingplatform.beans.MovieDetails;
import com.microservice.onlinemovieticketingplatform.beans.ShowDetails;
import com.microservice.onlinemovieticketingplatform.beans.TheaterDetails;
import com.microservice.onlinemovieticketingplatform.repository.MovieRepository;
import com.microservice.onlinemovieticketingplatform.repository.ShowRepository;
import com.microservice.onlinemovieticketingplatform.repository.TheaterRepository;
import com.microservice.onlinemovieticketingplatform.vo.MovieVO;
import com.microservice.onlinemovieticketingplatform.vo.OnlineMoviePlatformVO;
import com.microservice.onlinemovieticketingplatform.vo.TheaterVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OnlineMovieTicketService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowRepository showRepository;

    public List<MovieDetails> saveMovie(List<MovieVO> movieDetails) {
        List<MovieDetails> duplicateMovies=new ArrayList<>();
        for(MovieVO movie:movieDetails)
        {
            MovieDetails mo=movieRepository.findByMovieName(movie.getMovieName());
            MovieDetails movieD=MovieDetails.builder().movieName(movie.getMovieName()).build();
            if(mo!=null)
            {
                System.out.println("movie.."+movie.getMovieName()+"..already exists");
                duplicateMovies.add(movieD);
                continue;
            }
            movieRepository.save(movieD);
        }
        return duplicateMovies;
    }

    public List<TheaterDetails> saveTheater(List<TheaterVO> theaterDetails) {
        List<TheaterDetails> duplicateTheaters=new ArrayList<>();
        for(TheaterVO theater:theaterDetails)
        {
            TheaterDetails th=theaterRepository.findByTheaterName(theater.getTheaterName());
            TheaterDetails theaterD=TheaterDetails.builder().theaterName(theater.getTheaterName()).build();

            if(th!=null)
            {
                System.out.println("theater.."+theater.getTheaterName()+"..already exists");
                duplicateTheaters.add(theaterD);
                continue;
            }
            theaterRepository.save(theaterD);
        }
        return duplicateTheaters;
    }

    public MovieDetails fetchMovie(String movieName, String byName) {
        return movieRepository.findByMovieName(movieName);
    }

    public List<ShowDetails> fetchShowDetails(MovieDetails movie) {
        List<ShowDetails> showDetails=showRepository.findByMovieDetails(movie);
        return showDetails;
    }
    public List<ShowDetails> fetchShowDetailsByDate(MovieDetails movie,String movieDate) {
        System.out.println("search date..."+movieDate);
        List<ShowDetails> showDetails=showRepository.findByMovieDetails(movie);
        List<ShowDetails> filterByDate=new ArrayList<>();
        for(ShowDetails show:showDetails)
        {
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String newDate=format.format(Date.from(show.getShowTime().toInstant()));
            System.out.println("newDate..."+newDate);
            if(newDate.compareTo(movieDate)==0){
                filterByDate.add(show);
            }
        }
        return filterByDate;
    }

    public void saveShowDetails(List<OnlineMoviePlatformVO> showDetails) {
        for(OnlineMoviePlatformVO show:showDetails)
        {
            MovieDetails movie=movieRepository.findByMovieName(show.getMovieName());
            TheaterDetails theater=theaterRepository.findByTheaterName(show.getTheaterName());
            if(movie==null)
            {
                 movie =MovieDetails.builder().movieName(show.getMovieName()).build();
            }
            if(theater==null)
            {
                theater =TheaterDetails.builder().theaterName(show.getTheaterName()).build();
            }
            ShowDetails showDetail=ShowDetails.builder().build();
            showDetail.setMovieDetails(movie);
            showDetail.setTheaterDetails(theater);

            ZonedDateTime dt = ZonedDateTime.parse(show.getShowTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()));
            System.out.println(dt);
            showDetail.setShowTime(dt);
            showRepository.save(showDetail);
        }

    }

    public void updateShowDetails(List<OnlineMoviePlatformVO> showDetails) {
        for(OnlineMoviePlatformVO show:showDetails)
        {
            MovieDetails movie=movieRepository.findByMovieName(show.getMovieName());
            TheaterDetails theater=theaterRepository.findByTheaterName(show.getTheaterName());
            List<ShowDetails> showDetailFromDB=showRepository.findByMovieDetailsAndTheaterDetails(movie,theater);
            ZonedDateTime dt = ZonedDateTime.parse(show.getShowTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()));
            ZonedDateTime finalDt = dt;
            Optional<ShowDetails> filteredShow=showDetailFromDB.stream().filter(t->t.getShowTime().compareTo(finalDt)==0).findFirst();
            ShowDetails showDetail=filteredShow.get();
            showDetail.setMovieDetails(movie);
            showDetail.setTheaterDetails(theater);

            dt = ZonedDateTime.parse(show.getNewShowTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()));
            System.out.println(dt);
            showDetail.setShowTime(dt);
            showRepository.save(showDetail);
        }
    }

    public void deleteShowDetails(List<OnlineMoviePlatformVO> showDetails) {
        for(OnlineMoviePlatformVO show:showDetails)
        {
            MovieDetails movie=movieRepository.findByMovieName(show.getMovieName());
            TheaterDetails theater=theaterRepository.findByTheaterName(show.getTheaterName());
            List<ShowDetails> showDetailFromDB=showRepository.findByMovieDetailsAndTheaterDetails(movie,theater);
            ZonedDateTime dt = ZonedDateTime.parse(show.getShowTime(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()));
            ZonedDateTime finalDt = dt;
            Optional<ShowDetails> filteredShow=showDetailFromDB.stream().filter(t->t.getShowTime().compareTo(finalDt)==0).findFirst();
            ShowDetails showDetail=filteredShow.get();

            showRepository.delete(showDetail);
        }
    }
}
