package serverside.course.MyProject.Movies;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import serverside.course.MyProject.Actors.Actor;
import serverside.course.MyProject.Directors.Director;
import serverside.course.MyProject.ImdbMovie;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    @Column(name = "movie_id")
    private Long id;

    private String title;

    private String genere;

    private Double rate ;

    private LocalDate releaseDate;

    @JsonIgnore
    @ManyToOne
    private Director director;


    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable (name = "movie_actor",
         joinColumns = @JoinColumn(name = "movie_id"),
         inverseJoinColumns = @JoinColumn (name = "actor_id")
    )
    private List<Actor> cast;


    public Movie(Long id, String title, String genre) {
        this.id=id;
        this.title = title;
        this.genere = genre;
        this.cast= new ArrayList<Actor>();
        this.director=null;
    }

    public Movie(ImdbMovie movie) {
        this.id = Long.valueOf(movie.getRank());
//        this.id = Long.valueOf(UUID.randomUUID().toString());
        this.title = movie.getTitle();
        this.genere = "IMDB TOP 100";
        this.rate = movie.getRating();
        this.releaseDate = LocalDate.of(movie.getYear(),1,1);
        this.director = null;
        this.cast = new ArrayList<>();
    }

    public Movie(Long id, String title, String genre, Double rate, LocalDate releaseDate) {
        this.id = id;
        this.title = title;
        this.genere = genre;
        this.rate = rate;
        this.releaseDate = releaseDate;
        this.director=null;
        this.cast=new ArrayList<Actor>();
    }

    public void addActor(Actor actor){
        this.cast.add(actor);
        actor.addMoviePlyed(this);
    }

    public void setDirector(Director director) {
        this.director = director;
        director.addMovieDirect(this);
    }

    public void removeActor(Actor actor){
        this.getCast().remove(actor);
        actor.getMovieList().remove(this);
    }

    public String getCastMovie(){
        StringBuilder s1 = new StringBuilder();
        if (this.cast.isEmpty())
            return "no cast details";
        else {
            for (Actor a: this.cast) {
                s1.append(a.getName()).append(",");
            }
            return  s1.toString();
        }
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", Genre='" + genere + '\'' +
                ", rate=" + rate +
                ", releaseDate=" + releaseDate +
                ", director=" + director +
                ", cast= [" + getCastMovie() +"]"+
                '}';
    }


    public void setDirector2(Director director) {
        this.director = director;
    }
}
