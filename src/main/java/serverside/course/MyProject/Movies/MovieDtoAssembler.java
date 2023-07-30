package serverside.course.MyProject.Movies;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MovieDtoAssembler implements SimpleRepresentationModelAssembler<MovieDTO> {

    @Override
    public void addLinks(EntityModel<MovieDTO> resource) {
        resource.add(linkTo(methodOn(MovieController.class)
                .singleMovieInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(MovieController.class)
                .allMovieInfo())
                .withRel("movie information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<MovieDTO>> resources) {
        resources.add(linkTo(methodOn(MovieController.class).allMovieInfo()).withSelfRel());
    }
}
