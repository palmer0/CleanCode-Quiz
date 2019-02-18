package es.ulpgc.eite.cleancode.quiz;

import org.junit.Test;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.quiz.app.AppRepository;
import es.ulpgc.eite.cleancode.quiz.app.QuestionToCheatState;
import es.ulpgc.eite.cleancode.quiz.app.QuizRepository;
import es.ulpgc.eite.cleancode.quiz.question.QuestionContract;
import es.ulpgc.eite.cleancode.quiz.question.QuestionModel;
import es.ulpgc.eite.cleancode.quiz.question.QuestionPresenter;
import es.ulpgc.eite.cleancode.quiz.question.QuestionState;
import es.ulpgc.eite.cleancode.quiz.question.mocks.MockQuestionActivity;
import es.ulpgc.eite.cleancode.quiz.question.mocks.MockQuestionRouter;

import static org.junit.Assert.assertEquals;

public class QuestionPresenterTests {

  @Test
  public void testNoClickAnyButton() {

    // Given
    QuestionState state = new QuestionState();
    AppRepository repository = new QuizRepository();
    QuestionContract.Presenter presenter = new QuestionPresenter(state);
    MockQuestionActivity activity = new MockQuestionActivity();
    QuestionContract.View view = activity;
    QuestionContract.Model model = new QuestionModel(repository);
    QuestionContract.Router router = new MockQuestionRouter();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    presenter.injectRouter(router);
    activity.injectPresenter(presenter);

    // When
    presenter.fetchQuestionData();
    String activityQuestion = activity.getQuestion();
    String modelQuestion = model.getCurrentQuestion();
    String activityResult = activity.getResult();

    // Then
    assertEquals(
        "Get current question failed",
        activityQuestion, modelQuestion
    );
    assertEquals(
        "Get current result failed",
        activityResult, ""
    );
  }

  @Test
  public void testFirstClickTrueButton() {

    // Given
    QuestionState state = new QuestionState();
    AppRepository repository = new QuizRepository();
    QuestionContract.Presenter presenter = new QuestionPresenter(state);
    MockQuestionActivity activity = new MockQuestionActivity();
    QuestionContract.View view = activity;
    QuestionContract.Model model = new QuestionModel(repository);
    QuestionContract.Router router = new MockQuestionRouter();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    presenter.injectRouter(router);
    activity.injectPresenter(presenter);

    // When
    presenter.fetchQuestionData();
    presenter.trueButtonClicked();
    String activityQuestion = activity.getQuestion();
    String modelQuestion = model.getCurrentQuestion();
    String activityResult = activity.getResult();

    boolean modelAnswer = model.getCurrentAnswer();
    String modelResult = view.getIncorrectLabel();
    if(modelAnswer) {
      modelResult = view.getCorrectLabel();
    }

    // Then
    assertEquals(
        "Get current question failed",
        activityQuestion, modelQuestion
    );
    assertEquals(
        "Get current result failed",
        activityResult, modelResult
    );
  }


  @Test
  public void testFirstClickFalseButton() {

    // Given
    QuestionState state = new QuestionState();
    AppRepository repository = new QuizRepository();
    QuestionContract.Presenter presenter = new QuestionPresenter(state);
    MockQuestionActivity activity = new MockQuestionActivity();
    QuestionContract.View view = activity;
    QuestionContract.Model model = new QuestionModel(repository);
    QuestionContract.Router router = new MockQuestionRouter();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    presenter.injectRouter(router);
    activity.injectPresenter(presenter);

    // When
    presenter.fetchQuestionData();
    presenter.falseButtonClicked();
    String activityQuestion = activity.getQuestion();
    String modelQuestion = model.getCurrentQuestion();
    String activityResult = activity.getResult();

    boolean modelAnswer = model.getCurrentAnswer();
    String modelResult = view.getIncorrectLabel();
    if(!modelAnswer) {
      modelResult = view.getCorrectLabel();
    }

    // Then
    assertEquals(
        "Get current question failed",
        activityQuestion, modelQuestion
    );
    assertEquals(
        "Get current result failed",
        activityResult, modelResult
    );
  }

  @Test
  public void testFirstClickNextButton() {

    // Given
    QuestionState state = new QuestionState();
    AppRepository repository = new QuizRepository();
    QuestionContract.Presenter presenter = new QuestionPresenter(state);
    MockQuestionActivity activity = new MockQuestionActivity();
    QuestionContract.View view = activity;
    QuestionContract.Model model = new QuestionModel(repository);
    QuestionContract.Router router = new MockQuestionRouter();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    presenter.injectRouter(router);
    activity.injectPresenter(presenter);

    // When
    presenter.fetchQuestionData();
    presenter.nextButtonClicked();
    String activityQuestion = activity.getQuestion();
    String modelQuestion = model.getCurrentQuestion();
    String activityResult = activity.getResult();

    // Then
    assertEquals(
        "Get current question failed",
        activityQuestion, modelQuestion
    );
    assertEquals(
        "Get current result failed",
        activityResult, ""
    );
  }

  @Test
  public void testFirstClickCheatButton() {

    // Given
    QuestionState state = new QuestionState();
    AppRepository repository = new QuizRepository();
    QuestionContract.Presenter presenter = new QuestionPresenter(state);
    MockQuestionActivity activity = new MockQuestionActivity();
    QuestionContract.View view = activity;
    QuestionContract.Model model = new QuestionModel(repository);
    MockQuestionRouter router = new MockQuestionRouter();
    presenter.injectView(new WeakReference<>(view));
    presenter.injectModel(model);
    presenter.injectRouter(router);
    activity.injectPresenter(presenter);

    // When
    presenter.fetchQuestionData();
    presenter.cheatButtonClicked();
    String activityQuestion = activity.getQuestion();
    String modelQuestion = model.getCurrentQuestion();
    String activityResult = activity.getResult();
    QuestionToCheatState presenterState =
        new QuestionToCheatState(model.getCurrentAnswer());
    QuestionToCheatState routerState = router.getState();

    // Then
    assertEquals(
        "Get current question failed",
        activityQuestion, modelQuestion
    );
    assertEquals(
        "Get current result failed",
        activityResult, ""
    );
    assertEquals(
        "Get current state failed",
        presenterState.answer, routerState.answer
    );
  }
}
