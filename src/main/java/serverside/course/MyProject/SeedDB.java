package serverside.course.MyProject;//package webapi.restapplication;

import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import serverside.course.MyProject.Actors.Actor;
import serverside.course.MyProject.Actors.ActorRepo;
import serverside.course.MyProject.Directors.Director;
import serverside.course.MyProject.Directors.DirectorRepo;
import serverside.course.MyProject.Movies.Movie;
import serverside.course.MyProject.Movies.MovieController;
import serverside.course.MyProject.Movies.MovieRepo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;



@Configuration
public class SeedDB {
    private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);
    // class declares one or more @Bean method
    // Spring IoC generates bean definitions and handles request beans (at runtime)
    @Bean
    // CommandLineRunner beans are created once the application context is loaded
    CommandLineRunner initDatabase(MovieRepo movieRepo, DirectorRepo directorRepo, ActorRepo actorRepo){
        return args -> {

            Movie m1=new Movie(1L, "titanic", "drama",7.3, LocalDate.of(1990,1,1));
            Movie m2=new Movie(2L,"Inception", "action",8.2, LocalDate.of(2013,1,1));
            Actor a1=new Actor(1L,"Leunardo De-Capriu", "US");
            Actor a2= new Actor(2L,"Tom Cruse", "US");
            m1.addActor(a1);
            m2.addActor(a2);

            Director d1=new Director(1L,"ezra", "IS");
            Director d2=new Director(2L,"dan", "IS");


            logger.info("Saving director..." + directorRepo.save(d1));
            logger.info("Saving director..." + directorRepo.save(d2));
            logger.info("Saving movie..." + movieRepo.save(m1));
            logger.info("Saving movie..." + movieRepo.save(m2));
            logger.info("Saving actor..." + actorRepo.save(a1));
            logger.info("Saving actor..." + actorRepo.save(a2));

            List<Movie> movieList= new ImdbService(new RestTemplateBuilder()).getImdb100TopMovies();

            for (Movie m: movieList) {
                m.setId(movieRepo.count()+1);
                logger.info("Saving movie..." + movieRepo.save(m));
            }


        };
    }
}


