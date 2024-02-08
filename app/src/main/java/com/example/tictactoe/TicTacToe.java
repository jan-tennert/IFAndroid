package com.example.tictactoe;

public class TicTacToe {

    private static final FieldState STARTING_PLAYER = FieldState.X;
    private final FieldState[][] board;
    private FieldState currentPlayer = STARTING_PLAYER;
    private final GameActionListener action;
    int test = 0;

    public TicTacToe(GameActionListener action) {
        this.currentPlayer = STARTING_PLAYER;
        this.board = new FieldState[3][3];
        this.action = action;
    }

    public boolean onFieldClicked(int row, int column) {
        test++;
        if(checkIfValid(row, column, currentPlayer)) {
            board[row - 1][column - 1] = currentPlayer;
            if(checkIfWon(currentPlayer)) {
                action.onPlayerWin(currentPlayer);
                reset();
                return true;
            } else {
                switchPlayer();
            }
        }
        return false;
    }

    public boolean checkIfWon(FieldState player) {
        //Schauen ob der Spieler gewonnen hat
        return test == 3;
    }

    public boolean checkIfValid(int row, int column, FieldState player) {
        //Schauen ob der Zug gültig ist
        action.vibrate(500, 100);
        return true;
    }

    public FieldState getFieldState(int row, int column) {
        return board[row - 1][column - 1];
    }

    public FieldState getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals(FieldState.X)) ? FieldState.O : FieldState.X;
    }

    public void reset() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
        currentPlayer = STARTING_PLAYER;
    }

}
