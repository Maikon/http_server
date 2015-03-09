package http.responders.ttt;

import ttt.Board;
import ttt.Display;
import ttt.PlayerFactory;

public class WebDisplay implements Display {
    public final String HUMAN_VS_HUMAN = "1";
    public final int THREE_BY_THREE = 3;
    public final int INVALID_BOARD_CHOICE = -1;
    public final String INVALID_GAME_CHOICE = "invalid";
    private int EMPTY_MOVE = -1;
    private int nextMove = EMPTY_MOVE;
    private String gameChoice = HUMAN_VS_HUMAN;
    private int boardChoice = THREE_BY_THREE;

    @Override
    public int getMove() {
        int move = nextMove;
        nextMove = EMPTY_MOVE;
        return move;
    }

    @Override
    public boolean hasMove() {
        return getMove() != -1;
    }

    public void setMove(int move) {
        this.nextMove = move;
    }

    @Override
    public String getGameChoice() {
        boolean validChoice = PlayerFactory.allGameChoices().containsKey(gameChoice);
        if (validChoice) {
            return gameChoice;
        }
        return INVALID_GAME_CHOICE;
    }

    public void setGameChoice(String choice) {
        gameChoice = choice;
    }

    @Override
    public int getBoardChoice() {
        boolean validChoice = Board.getDimensions().containsValue(boardChoice);
        if (validChoice) {
            return boardChoice;
        }
        return INVALID_BOARD_CHOICE;
    }

    public void setBoardChoice(int choice) {
        boardChoice = choice;
    }

    @Override
    public void showInvalidMoveMessage() {
    }

    @Override
    public void showResults(Board board) {
    }

    @Override
    public void show(Board board) {
    }
}
