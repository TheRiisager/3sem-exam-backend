package entities;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static javax.persistence.TemporalType.TIMESTAMP;

@Entity
@Table(name = "searches")
public class Search {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String timeOfSearch;

    @ManyToOne
    @JoinColumn(name = "BREED_ID")
    private Breed breed;
}
