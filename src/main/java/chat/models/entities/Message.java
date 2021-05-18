package chat.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message", schema = "my_schema")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;

    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id",
            nullable = false
    )
    @JsonBackReference
    @ManyToOne(fetch = LAZY)
    private UserEntity owner;
}
