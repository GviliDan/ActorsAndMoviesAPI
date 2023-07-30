package serverside.course.MyProject.Movies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serverside.course.MyProject.Actors.Actor;
import serverside.course.MyProject.Actors.ActorEntityAssembler;
import serverside.course.MyProject.Actors.ActorRepo;
import serverside.course.MyProject.Directors.Director;
import serverside.course.MyProject.Directors.DirectorRepo;
import serverside.course.MyProject.SeedDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class MovieController {

    private static final Logger logger = LoggerFactory.getLogger(SeedDB.class);
    private final MovieRepo movieRepo;

    private final MovieEntityAssembler movieEntityAssembler;

    private final MovieDtoAssembler movieDtoAssembler;

    private final ActorRepo actorRepo;

    private final DirectorRepo directorRepo;

    public MovieController(MovieRepo movieRepo, MovieEntityAssembler movieEntityAssembler, MovieDtoAssembler movieDtoAssembler, ActorRepo actorRepo, DirectorRepo directorRepo) {
        this.movieRepo = movieRepo;
        this.movieEntityAssembler = movieEntityAssembler;
        this.movieDtoAssembler = movieDtoAssembler;
        this.actorRepo = actorRepo;
        this.directorRepo = directorRepo;
    }


    @GetMapping("/simplemovie")
    public List<Movie> allMovieSimpleRequst(@RequestParam(value = "genere", required = false) String genere,
                                            @RequestParam(value = "title", required = false) String title) {
        if ((genere!=null)&&(title!=null)){
            return movieRepo.findByTitleAndGenere(title, genere);
        }
        if (genere!=null)
            return movieRepo.findByGenere(genere);
        else
        if (title!=null)
            return movieRepo.findByTitle(title);
        else
            return movieRepo.findAll();

    }


    @GetMapping("/movie/info")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> allMovieInfo(){
        return ResponseEntity.ok(movieDtoAssembler.toCollectionModel(
                StreamSupport.stream(movieRepo.findAll().spliterator(),false)
                        .map(MovieDTO::new).collect(Collectors.toList())));
    }


    @GetMapping("/movie/info/byname")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> allMovieInfoByName(@RequestParam(value = "title", required = false) String title){
        if (title!=null){
            return ResponseEntity.ok(movieDtoAssembler.toCollectionModel(
                    StreamSupport.stream(movieRepo.findByTitle(title).spliterator(),false)
                            .map(MovieDTO::new).collect(Collectors.toList())));
        }

        return allMovieInfo();
    }



    @GetMapping("/movies/info")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> allMovieInfoRe(@RequestParam(value = "genre", required = false) String genre,
                                                                                 @RequestParam(value = "year", required = false) Integer year) {
        if((year!=null)&&(genre!=null )){
            return ResponseEntity.ok(movieDtoAssembler.toCollectionModel(
                    StreamSupport.stream(movieRepo.findByGenereAndReleaseDateAfter(genre, LocalDate.of(year-1,12,31)).spliterator(),false)
                            .map(MovieDTO::new).collect(Collectors.toList())));
        }
        else
        if( genre!= null)
        {
            return ResponseEntity.ok(movieDtoAssembler.toCollectionModel(
                    StreamSupport.stream(movieRepo.findByGenere(genre).spliterator(),false)
                            .map(MovieDTO::new).collect(Collectors.toList())));
        }
        else if (year!=null){
            return ResponseEntity.ok(movieDtoAssembler.toCollectionModel(
                    StreamSupport.stream(movieRepo.findByReleaseDateAfter(LocalDate.of(year,1,1)).spliterator(),false)
                            .map(MovieDTO::new).collect(Collectors.toList())));
        }

        return allMovieInfo();
    }



        @GetMapping("/movie/{id}/info")
    public ResponseEntity<EntityModel<MovieDTO>> singleMovieInfo(@PathVariable long id){
        return movieRepo.findById(id)
                .map(MovieDTO::new)
                .map(movieDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }




    @PostMapping("/movies/add")
    public ResponseEntity<EntityModel<Movie>> createMovie(@RequestBody Movie newMovie) {
        Movie movie= new Movie(movieRepo.count()+1, newMovie.getTitle(), newMovie.getGenere(), newMovie.getRate(), newMovie.getReleaseDate());
        Movie createdMovie = movieRepo.save(movie);
        EntityModel<Movie> entityModel = movieEntityAssembler.toModel(createdMovie);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    @PutMapping("/movie/{id}/m")
    public ResponseEntity<EntityModel<Movie>> replaceMovie(@RequestBody Movie newMovie, @PathVariable Long id) {
        Movie updatedMovie = movieRepo.findById(id).map(movie -> {
            movie.setTitle(newMovie.getTitle());
            movie.setDirector2(newMovie.getDirector());
            movie.setRate(newMovie.getRate());
            movie.setReleaseDate(newMovie.getReleaseDate());
            movie.setCast(new ArrayList<>());
            return movieRepo.save(movie);
        }).orElseGet(() -> {
            newMovie.setId(id);
            return movieRepo.save(newMovie);
        });
        EntityModel<Movie> entityModel = movieEntityAssembler.toModel(updatedMovie);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/{movieId}/cast/{actorId}")
    public ResponseEntity<EntityModel<Movie>> addToMovieCast(@PathVariable long movieId, @PathVariable long actorId) {
        // Retrieve the movie and actor from their respective repositories
        Optional<Movie> movieOptional = movieRepo.findById(movieId);
        Optional<Actor> actorOptional = actorRepo.findById(actorId);

        if (movieOptional.isPresent() && actorOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Actor actor = actorOptional.get();

            // Update the cast of the movie
            movie.getCast().add(actor);
            movieRepo.save(movie);

            // Convert the updated movie to an EntityModel
            EntityModel<Movie> entityModel = movieEntityAssembler.toModel(movie);

            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{movieId}/setdirector/{directoId}")
    public ResponseEntity<EntityModel<Movie>> updateDirector(@PathVariable long movieId, @PathVariable long directoId) {
        // Retrieve the movie and actor from their respective repositories
        Optional<Movie> movieOptional = movieRepo.findById(movieId);
        Optional<Director> directorOptional = directorRepo.findById(directoId);

        if (movieOptional.isPresent() && directorOptional.isPresent()) {
            Movie movie = movieOptional.get();
            Director director = directorOptional.get();

            // Update the cast of the movie
            movie.setDirector(director);
            movieRepo.save(movie);
            directorRepo.save(director);

            // Convert the updated movie to an EntityModel
            EntityModel<Movie> entityModel = movieEntityAssembler.toModel(movie);

            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/movie/{id}/delete")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) {
        movieRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/actor/{actorId}/movies")
    public ResponseEntity<CollectionModel<EntityModel<MovieDTO>>> getMoviesByActor(@PathVariable long actorId) {
        Optional<Actor> actorOptional = actorRepo.findById(actorId);

        if (actorOptional.isPresent()) {
            Actor actor = actorOptional.get();
            List<MovieDTO> movies = StreamSupport.stream(movieRepo.findAll().spliterator(), false)
                    .filter(movie -> movie.getCast().contains(actor))
                    .map(MovieDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(movieDtoAssembler.toCollectionModel(movies));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}


