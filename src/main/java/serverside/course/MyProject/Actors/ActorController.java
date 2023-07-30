package serverside.course.MyProject.Actors;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import serverside.course.MyProject.Directors.*;
import serverside.course.MyProject.Movies.Movie;
import serverside.course.MyProject.Movies.MovieRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
public class ActorController {

    private final ActorRepo actorRepo;

    private final ActorEntityAssembler actorEntityAssembler;

    private final ActorDtoAssembler actorDtoAssembler;
    private final MovieRepo movieRepo;

    public ActorController(ActorRepo actorRepo, ActorEntityAssembler actorEntityAssembler, ActorDtoAssembler actorDtoAssembler, MovieRepo movieRepo) {
        this.actorRepo = actorRepo;
        this.actorEntityAssembler = actorEntityAssembler;
        this.actorDtoAssembler = actorDtoAssembler;
        this.movieRepo = movieRepo;
    }

    @GetMapping("/simpleactors")
    public List<Actor> allDirectorsSimple() {
        return (List<Actor>) actorRepo.findAll();
    }


    @GetMapping("/actors/info")
    public ResponseEntity<CollectionModel<EntityModel<ActorDTO>>> allActorsInfo(){
        return ResponseEntity.ok(actorDtoAssembler.toCollectionModel(
                StreamSupport.stream(actorRepo.findAll().spliterator(),false)
                        .map(ActorDTO::new).collect(Collectors.toList())));
    }

    @GetMapping("/actors/info/by")
    public ResponseEntity<CollectionModel<EntityModel<ActorDTO>>> allActorBy(@RequestParam(value = "name", required = false) String name,
                                                                             @RequestParam(value = "nation", required = false) String nation) {
        if((nation!=null)&&(name!=null)){
            return ResponseEntity.ok(actorDtoAssembler.toCollectionModel(
                    StreamSupport.stream(actorRepo.findByNameAndNation(name, nation).spliterator(),false)
                            .map(ActorDTO::new).collect(Collectors.toList())));
        }
        if (nation!=null){
            return ResponseEntity.ok(actorDtoAssembler.toCollectionModel(
                    StreamSupport.stream(actorRepo.findByNation(nation).spliterator(),false)
                            .map(ActorDTO::new).collect(Collectors.toList())));
        }

        if (name!=null){
            return ResponseEntity.ok(actorDtoAssembler.toCollectionModel(
                    StreamSupport.stream(actorRepo.findByName(name).spliterator(),false)
                            .map(ActorDTO::new).collect(Collectors.toList())));
        }

        return allActorsInfo();
    }

    @GetMapping("/actors/{id}/info")
    public ResponseEntity<EntityModel<ActorDTO>> singleActorInfo(@PathVariable long id){
        return actorRepo.findById(id)
                .map(ActorDTO::new)
                .map(actorDtoAssembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{actorId}/play-in/{movieId}")
    public ResponseEntity<EntityModel<Actor>> updateActorMovies(@PathVariable long actorId, @PathVariable long movieId) {
        Optional<Actor> actorOptional = actorRepo.findById(actorId);
        Optional<Movie> movieOptional = movieRepo.findById(movieId);

        if (actorOptional.isPresent() && movieOptional.isPresent()) {
            Actor actor = actorOptional.get();
            Movie movie = movieOptional.get();

            actor.getMovieList().add(movie);
            movie.getCast().add(actor);

            actorRepo.save(actor);
            movieRepo.save(movie);

            EntityModel<Actor> entityModel = actorEntityAssembler.toModel(actor);

            return ResponseEntity.ok(entityModel);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/actors/add")
    public ResponseEntity<EntityModel<Actor>> createActor(@RequestBody Actor newActor) {
        Actor actor = new Actor(actorRepo.count()+1, newActor.getName(), newActor.getNation());
        Actor createdActor = actorRepo.save(actor);
        EntityModel<Actor> entityModel = actorEntityAssembler.toModel(createdActor);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/actors/delete/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable Long id) {
        actorRepo.deleteById(id);
        return ResponseEntity.noContent().build();
    }





}
