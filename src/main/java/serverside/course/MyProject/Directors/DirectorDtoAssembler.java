package serverside.course.MyProject.Directors;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;
import serverside.course.MyProject.Movies.MovieController;
import serverside.course.MyProject.Movies.MovieDTO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DirectorDtoAssembler implements SimpleRepresentationModelAssembler<DirectorDTO> {

    @Override
    public void addLinks(EntityModel<DirectorDTO> resource) {
        resource.add(linkTo(methodOn(DirectorController.class)
                .singleDirectorInfo(resource.getContent().getId())).withSelfRel());
        resource.add(linkTo(methodOn(DirectorController.class)
                .allDirectorInfo())
                .withRel("director information"));
    }

    @Override
    public void addLinks(CollectionModel<EntityModel<DirectorDTO>> resources) {
        resources.add(linkTo(methodOn(DirectorController.class).allDirectorInfo()).withSelfRel());
    }
}
