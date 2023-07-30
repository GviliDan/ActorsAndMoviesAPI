package serverside.course.MyProject.Actors;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import serverside.course.MyProject.Movies.Movie;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Actor {

    @Id
    @GeneratedValue
    @Column(name = "actor_id")
    private Long id;

    private String name;

    private String nation;

    private LocalDateTime birthDay;

    private Boolean isAlive;

    @JsonIgnore
    @ManyToMany (mappedBy = "cast")
    private List<Movie> movieList;


    public Actor(Long id, String name, String nation) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.movieList=new ArrayList<Movie>();
    }

    public void addMoviePlyed(Movie movie){
        this.movieList.add(movie);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nation='" + nation + '\'' +
                ", birthDay=" + birthDay +
                ", isAlive=" + isAlive +
                '}';
    }

}
