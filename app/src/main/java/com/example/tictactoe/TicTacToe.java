package com.example.tictactoe;

public class TicTacToe {

    private static final String STARTING_PLAYER = "X";
    private final String[][] board;
    private String currentPlayer = STARTING_PLAYER;
    private final GameActionListener action;

    public TicTacToe(GameActionListener action) {
        this.currentPlayer = STARTING_PLAYER;
        this.board = new String[3][3];
        this.action = action;
    }

    public boolean onFieldClicked(int row, int column) {
        if(checkIfValid(row, column, currentPlayer)) {
            board[row - 1][column - 1] = currentPlayer;
            if(checkIfWon(currentPlayer)) {
                playerWon(currentPlayer);
                return true;
            } else {
                switchPlayer();
            }
        }
        return false;
    }

    //.................

    public void playerWon(String player) {
        action.showDialog("Gewonnen", "Spieler " + player + " hat gewonnen!");
        reset();
    }

    public boolean checkIfWon(String player) {
        //Schauen ob der Spieler gewonnen hat
        return false;
    }

    public boolean checkIfValid(int row, int column, String player) {
        //Schauen ob der Zug gültig ist
        return true;
    }

    //.................

    public String getFieldState(int row, int column) {
        return board[row - 1][column - 1];
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer.equals("X")) ? "O" : "X";
    }

    public void reset() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = null;
            }
        }
        currentPlayer = STARTING_PLAYER;
        action.resetFields();
    }

}
