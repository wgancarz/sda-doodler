package pl.sda.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String type;
    @Column
    private String value;
    @Column
    private LocalDateTime recordedAt;

    @ManyToMany(mappedBy = "events", cascade = CascadeType.PERSIST)
    private Set<Player> players = new HashSet<>();

    public void addPlayer(Player player) {
        this.players.add(player);
        player.getEvents().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        return new EqualsBuilder()
                .append(id, event.id)
                .append(type, event.type)
                .append(recordedAt, event.recordedAt)
                .append(value, event.value)
                .append(players, event.players)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(type)
                .append(value)
                .append(recordedAt)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("type", type)
                .append("value", value)
                .append("recordedAt", recordedAt)
                .toString();
    }
}
