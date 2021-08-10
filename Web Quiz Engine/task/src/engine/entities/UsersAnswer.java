package engine.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class UsersAnswer {

    @Getter
    @Setter
    private List<Integer> answer;

    public static final String correctAnsJson = "{\"success\":true,\"feedback\":\"Congratulations, you're right!\"}";

    public static final String wrongAnsJson = "{\"success\":false,\"feedback\":\"Wrong answer! Please, try again.\"}";

}