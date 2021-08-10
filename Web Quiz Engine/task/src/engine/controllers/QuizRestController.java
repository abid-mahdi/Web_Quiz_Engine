package engine.controllers;

import engine.entities.Completed;
import engine.entities.Quiz;
import engine.entities.UsersAnswer;
import engine.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class QuizRestController {

    private final QuizService quizService;

    @Autowired
    public QuizRestController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping(path = "/quizzes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> addQuiz(@Valid @RequestBody Quiz quizToSave, @Autowired Principal principal) {
        return new ResponseEntity<>(quizService.saveQuiz(quizToSave, principal.getName()), HttpStatus.OK);
    }

    @GetMapping(path = "/quizzes/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> getQuiz(@PathVariable long id) {
        return new ResponseEntity<>(quizService.getQuiz(id), HttpStatus.OK);
    }

    @DeleteMapping("/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@PathVariable long id, @Autowired Principal principal) {
        quizService.deleteQuiz(id, principal.getName());
    }

    @GetMapping(path = "/quizzes", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Quiz>> getAllQuizzes(@RequestParam(defaultValue = "0") int page) {
        return new ResponseEntity<>(quizService.getAllQuizzes(page), HttpStatus.OK);
    }

    @PostMapping(path = "/quizzes/{id}/solve", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> checkAnswer(@PathVariable long id, @RequestBody UsersAnswer answer,
                                              @Autowired Principal principal) {
        return new ResponseEntity<>(quizService.checkQuizSubmission(id, answer, principal.getName()), HttpStatus.OK);
    }

    @GetMapping(path = "/quizzes/completed", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Completed>> getCompletedQuizzes(@RequestParam(defaultValue = "0") int page,
                                                               @Autowired Principal principal) {
        return new ResponseEntity<>(quizService.getCompletedQuizzes(principal.getName(), page), HttpStatus.OK);
    }

}