package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "horse_positions")
public class RaceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "horse_position")
    private int position;

    @ManyToOne()
    @JoinColumn(name="horse_Id", nullable=false)
    private Horse horse;
    @ManyToOne()
    @JoinColumn(name="race_id", nullable=false)
    private Race race;

    @Column(name = "is_chosen")
    private boolean isChosen;
}
