package engine.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @NotBlank
    private String title;

    @NonNull
    @NotBlank
    private String text;

    @NonNull
    @ElementCollection
    @NotNull
    @Size(min = 2)
    private List<String> options;

    @NonNull
    @ElementCollection
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY, value = "answer")
    private List<Integer> answers;

    @JsonIgnore
    private String email;

    public boolean checkAnswer(List<Integer> usersAns) {
        return new HashSet<>(usersAns).equals(new HashSet<>(answers));
    }

}