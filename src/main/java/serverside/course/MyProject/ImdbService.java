package serverside.course.MyProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import serverside.course.MyProject.Movies.Movie;
import serverside.course.MyProject.Movies.MovieRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ImdbService {

    private RestTemplate restTemplate;

    private static final Logger serviceLogger = LoggerFactory.getLogger(ImdbService.class);


    public ImdbService(RestTemplateBuilder templateBuilder) {
        this.restTemplate = templateBuilder.interceptors(Collections.singleton((ClientHttpRequestInterceptor) (request, body, execution) -> {
            request.getHeaders().add("X-RapidAPI-Key", "bbcec0c238mshbd9a20a3799bf8dp1f7f9cjsn4470321f4ce2");
            request.getHeaders().add("X-RapidAPI-Host", "imdb-top-100-movies.p.rapidapi.com");
            return execution.execute(request, body);
        })).build();
    }

    @Async
    public CompletableFuture<ImdbMovie> getMovieDetails(String pos) {
        String template = String.format("https://imdb-top-100-movies.p.rapidapi.com/top%s", pos);
        ImdbMovie imdbMovie = this.restTemplate.getForObject(template, ImdbMovie.class);
        serviceLogger.info("Running in thread = " + Thread.currentThread().getName());
        return CompletableFuture.completedFuture(imdbMovie);
    }

    @Async
    public  List<Movie> getImdb100TopMovies() {

        List<Movie> movieList = new ArrayList<>();

        RestTemplateBuilder builder = new RestTemplateBuilder();
        ImdbService imdbService = new ImdbService(builder);

        for (Integer i = 1; i < 5; i++) {
            CompletableFuture<ImdbMovie> future = imdbService.getMovieDetails(i.toString());
            ImdbMovie movie = null;
            try {
                movie = future.get();
//                movieRepo.save(new Movie(movie));
                movieList.add(new Movie(movie));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }

        return movieList;
    }
}




