package es.ulpgc.eite.cleancode.quiz.question;

import java.lang.ref.WeakReference;

import es.ulpgc.eite.cleancode.quiz.app.CheatToQuestionState;
import es.ulpgc.eite.cleancode.quiz.app.QuestionToCheatState;


public class QuestionPresenter implements QuestionContract.Presenter {

  public static String TAG = QuestionPresenter.class.getSimpleName();

  private WeakReference<QuestionContract.View> view;
  private QuestionState state;
  private QuestionContract.Model model;
  private QuestionContract.Router router;

  public QuestionPresenter(QuestionState state) {
    this.state = state;
  }

  @Override
  public void injectView(WeakReference<QuestionContract.View> view) {
    this.view = view;
  }

  @Override
  public void injectModel(QuestionContract.Model model) {
    this.model = model;
  }

  @Override
  public void injectRouter(QuestionContract.Router router) {
    this.router = router;
  }

  @Override
  public void onResumeCalled() {
    //Log.e(TAG, "onResumeCalled()");

    // set passed state
    CheatToQuestionState savedState = router.getDataFromCheatScreen();
    if(savedState != null) {

        if(savedState.cheated){
          nextButtonClicked();
          return;
        }
    }

    // call the model
    model.setCurrentIndex(state.quizIndex);
    state.questionText = model.getCurrentQuestion();

    view.get().displayQuestionData(state);

  }

  private void updateQuestionData(boolean userAnswer) {

    boolean currentAnswer = model.getCurrentAnswer();

    /*
    if(currentAnswer == userAnswer) {
      state.resultText = view.get().getCorrectLabel();
    } else {
      state.resultText = view.get().getIncorrectLabel();
    }
    */

    if(currentAnswer == userAnswer) {
      state.resultText = model.getCorrectLabel();
    } else {
      state.resultText = model.getIncorrectLabel();
    }

    state.falseButton = false;
    state.trueButton = false;
    state.cheatButton = false;

    if(model.isLastQuestion()) {
      state.nextButton = false;
    } else {
      state.nextButton = true;
    }

    view.get().displayQuestionData(state);
  }


  @Override
  public void trueButtonClicked() {
    updateQuestionData(true);
  }

  @Override
  public void falseButtonClicked() {
    updateQuestionData(false);
  }

  @Override
  public void cheatButtonClicked() {
    boolean answer = model.getCurrentAnswer();
    QuestionToCheatState newState = new QuestionToCheatState(answer);
    router.passDataToCheatScreen(newState);
    router.navigateToCheatScreen();
  }

  @Override
  public void nextButtonClicked() {
    //Log.e(TAG, "nextButtonClicked()");

    state.quizIndex++;
    model.incrQuizIndex();

    state.questionText = model.getCurrentQuestion();
    state.resultText = "";

    state.falseButton = true;
    state.trueButton = true;
    state.cheatButton = true;
    state.nextButton = false;

    view.get().displayQuestionData(state);
  }

}
