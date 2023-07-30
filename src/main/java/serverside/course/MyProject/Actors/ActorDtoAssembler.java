package serverside.course.MyProject.Actors;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import serverside.course.MyProject.Directors.DirectorDTO;
import serverside.course.MyProject.Movies.MovieController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ActorDtoAssembler implements SimpleRepresentationModelAssembler<ActorDTO> {

    @Override
    public void addLinks(EntityModel<ActorDTO> resource) {
        resource.add(linkTo(methodOn(ActorController.class)
                .singleActorInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(ActorController.class)
                .allActorsInfo())
                .withRel("actor information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<ActorDTO>> resources) {
        resources.add(linkTo(methodOn(ActorController.class).allActorsInfo()).withSelfRel());
    }
}
