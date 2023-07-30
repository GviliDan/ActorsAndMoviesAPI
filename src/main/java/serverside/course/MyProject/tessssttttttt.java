//package serverside.course.MyProject;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.web.client.RestTemplateBuilder;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.web.client.RestOperations;
//import org.springframework.web.client.RestTemplate;
//import serverside.course.MyProject.Actors.ActorRepo;
//import serverside.course.MyProject.Directors.DirectorRepo;
//import serverside.course.MyProject.Movies.Movie;
//import serverside.course.MyProject.Movies.MovieRepo;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//public class tessssttttttt {
//
//    public static void main(String[] args) {
//
//            RestTemplateBuilder builder = new RestTemplateBuilder();
//            ImdbService imdbService = new ImdbService(builder);
//            CompletableFuture<ImdbMovie> future = imdbService.getMovieDetails("17");
//
//            ImdbMovie movie = null;
//            try {
//                movie = future.get();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            }
//
//            System.out.println("Imdb movie: "+ movie.toString());
//
//            Movie m1= new Movie(movie);
//
//            System.out.println("Movie class: "+ m1.toString());
//
//
//
//
//        }
//
//    }
//
//
//
//
//
////        Director director=new Director(1L, "ezra", "IS", LocalDateTime.of(1992,15,11), true,new ArrayList<Movie>());
////        Movie m1=new Movie(1L, "titanic", "drama");
////        Movie m2=new Movie(2L,"Inception", "action");
////        Actor a1=new Actor(1L,"Liam Nissan", "US");
////        Actor a2= new Actor(2L,"Tom Cruse", "US");
////        m1.addActor(a1);
////        m2.addActor(a2);
////        m1.addActor(a2);
////
////        System.out.println(m1.getCast());
////        System.out.println(m2.getCast());
////        System.out.println(a2.getMovieList());
//
//
