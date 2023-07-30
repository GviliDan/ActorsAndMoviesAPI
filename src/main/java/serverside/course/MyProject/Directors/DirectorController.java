package serverside.course.MyProject.Directors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serverside.course.MyProject.Actors.ActorDTO;
import serverside.course.MyProject.Movies.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class DirectorController {

    private final DirectorRepo directorRepo;

    private final DirectorEntityAssembler directorEntityAssembler;

    private final DirectorDtoAssembler directorDtoAssembler;
    private final MovieEntityAssembler movieEntityAssembler;

    private final MovieRepo movieRepo;




    public DirectorController(DirectorRepo directorRepo, DirectorEntityAssembler directorEntityAssembler, DirectorDtoAssembler directorDtoAssembler, MovieEntityAssembler movieEntityAssembler, MovieRepo movieRepo) {
        this.directorRepo = directorRepo;
        this.directorEntityAssembler = directorEntityAssembler;
        this.directorDtoAssembler = directorDtoAssembler;
        this.movieEntityAssembler = movieEntityAssembler;
        this.movieRepo = movieRepo;
    }

    @GetMapping("/simpledirectors")
    public List<Director> allDirectorsSimple() {
        return (List<Director>) directorRepo.findAll();
    }


    @GetMapping("/director/info")
    public ResponseEntity<CollectionModel<EntityModel<DirectorDTO>>> allDirectorInfo(){
        return ResponseEntity.ok(directorDtoAssembler.toCollectionModel(
                StreamSupport.stream(directorRepo.findAll().spliterator(),false)
                        .map(DirectorDTO::new).collect(Collectors.toList())));
    }

    @GetMapping("/director/info/by")
    public ResponseEntity<CollectionModel<EntityModel<DirectorDTO>>> allDirectorBy(@RequestParam(value = "name", required = false) String name) {

        if (name!=null){
            return ResponseEntity.ok(directorDtoAssembler.toCollectionModel(
                    StreamSupport.stream(directorRepo.findByName(name).spliterator(),false)
                            .map(DirectorDTO::new).collect(Collectors.toList())));
        }
        return allDirectorInfo();
    }

    @GetMapping("/director/{id}/info")
    public ResponseEntity<EntityModel<DirectorDTO>> singleDirectorInfo(@PathVariable long id){
        return directorRepo.findById(id)
                .map(DirectorDTO::new)
                .map(directorDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{directorId}/directed/{movieId}")
    public ResponseEntity<EntityModel<Movie>> updateActorMovies(@PathVariable long directorId, @PathVariable long movieId) {
        Optional<Director> actorOptional = directorRepo.findById(directorId);
        Optional<Movie> movieOptional = movieRepo.findById(movieId);

        if (actorOptional.isPresent() && movieOptional.isPresent()) {
            Director director = actorOptional.get();
            Movie movie = movieOptional.get();

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


        @PostMapping("/director/add")
        public ResponseEntity<EntityModel<Director>> newDirector (@RequestBody Director newDirector){
            Director director = new Director(directorRepo.count() + 1, newDirector.getName(), newDirector.getNation());
            Director createdDirector = directorRepo.save(director);
            EntityModel<Director> entityModel = directorEntityAssembler.toModel(createdDirector);
            return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
        }

    @DeleteMapping("/director/{id}/delete")
    public ResponseEntity<?> deleteDirector(@PathVariable Long id) {
        directorRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}




