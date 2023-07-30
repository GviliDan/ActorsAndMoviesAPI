//package serverside.course.MyProject;
//
//import jakarta.persistence.EmbeddedId;
//import jakarta.persistence.Entity;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.MapsId;
//import serverside.course.MyProject.Actors.Actor;
//import serverside.course.MyProject.Movies.Movie;
//
//@Entity
//public class MovieActor {
//
//    @EmbeddedId
//    private MovieActorId id;
//
//    @ManyToOne
//    @MapsId("movieID")
//    private Movie movie;
//
//    @ManyToOne
//    @MapsId("actorId")
//    private Actor actor;
//
//}
