package engine.services;

import engine.dao.CompletedRepository;
import engine.dao.QuizRepository;
import engine.entities.Completed;
import engine.entities.Quiz;
import engine.entities.UsersAnswer;
import engine.exceptions.QuizForbiddenException;
import engine.exceptions.QuizNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuizService {

    private final QuizRepository quizRepo;
    private final CompletedRepository completedRepo;

    @Autowired
    public QuizService(QuizRepository quizRepo, CompletedRepository completedRepo) {
        this.quizRepo = quizRepo;
        this.completedRepo = completedRepo;
    }

    public Quiz saveQuiz(Quiz quiz, String loggedInUser) {
        quiz.setEmail(loggedInUser);
        quizRepo.save(quiz);
        return quiz;
    }

    public Quiz getQuiz(long id) {
        Optional<Quiz> quizToGet = quizRepo.findById(id);
        if (quizToGet.isEmpty()) throw new QuizNotFoundException();
        return quizToGet.get();
    }

    public void deleteQuiz(long id, String loggedInUser) {
        if (getQuiz(id).getEmail().equals(loggedInUser)) quizRepo.deleteById(id);
        else throw new QuizForbiddenException();
    }

    public Page<Quiz> getAllQuizzes(Integer page) {
        return quizRepo.findAll(PageRequest.of(page, 10));
    }

    public Page<Completed> getCompletedQuizzes(String email, int page) {
        return completedRepo.findAllByUserOrderByCompletedAtDesc(email, PageRequest.of(page, 10));
    }

    public String checkQuizSubmission(Long quizId, UsersAnswer usersAnswer, String loggedInUser) {
        if (getQuiz(quizId).checkAnswer(usersAnswer.getAnswer())) {
            completedRepo.save(new Completed(quizId, loggedInUser));
            return UsersAnswer.correctAnsJson;
        } else
            return UsersAnswer.wrongAnsJson;
    }

}