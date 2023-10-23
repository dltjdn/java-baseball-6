package baseball.controller;

import baseball.model.FinishInput;
import baseball.model.GameInput;
import baseball.model.GameResultCalculator;
import baseball.model.Number;
import baseball.model.RandomNumberGenerator;
import baseball.view.GameView;
import java.util.List;

public class GameController {
    private final RandomNumberGenerator randomNumberGenerator;
    private final GameView view;
    private final GameInput input;
    private final GameResultCalculator calculator;
    private Number randomNumber;

    public GameController() {
        randomNumberGenerator = new RandomNumberGenerator();
        view = new GameView();
        input = new GameInput();
        calculator = new GameResultCalculator();
    }

    public void start() {
        view.printStartMessage();
        do {
            startGame();
            playGame();
        } while (isEndGame());
    }


    public void startGame() {
        List<Integer> random = randomNumberGenerator.generateRandomNumber();
        this.randomNumber = new Number(random);
    }

    public void playGame() {
        Number inputNumber;
        do {
            view.printAskInputMessage();
            inputNumber = input.readNumberInput();

            int ballCount = calculator.countBall(randomNumber, inputNumber);
            int strikeCount = calculator.countStrike(randomNumber, inputNumber);

            printGameResult(ballCount, strikeCount);

        } while (!calculator.isThreeStrike(randomNumber, inputNumber));

    }

    private void printGameResult(int ballCount, int strikeCount) {
        if (strikeCount == 0 && ballCount == 0) {
            view.printResultNothing();
        }

        if (ballCount != 0 && strikeCount == 0) {
            view.printResultBall(ballCount);
        }

        if (ballCount == 0 && strikeCount != 0) {
            view.printResultStrike(strikeCount);
        }

        if (ballCount != 0 && strikeCount != 0) {
            view.printResultBallAndStrike(ballCount, strikeCount);
        }
    }

    public boolean isEndGame() {
        view.printSuccessAndEndMessage();
        view.printRestartOrEndMessage();
        FinishInput finishInput = input.readFinishInput();

        return finishInput == FinishInput.RESTART_GAME;
    }

}
