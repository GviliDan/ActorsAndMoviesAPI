package serverside.course.MyProject.Movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface MovieRepo  extends JpaRepository<Movie,Long> {

    List<Movie> findByGenere(String genere);

    List<Movie> findByRateGreaterThan(Long rate);
    List<Movie> findByReleaseDateAfter(LocalDate date);
    List<Movie> findByGenereAndReleaseDateAfter(String genre, LocalDate date);

    List<Movie> findByTitle(String title);

    List<Movie> findByTitleAndGenere(String title, String genere);


}
