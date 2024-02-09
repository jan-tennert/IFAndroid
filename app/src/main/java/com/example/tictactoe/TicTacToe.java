package com.example.tictactoe;

public class TicTacToe {

    private static final String STARTING_PLAYER = "X";
    private final String[][] board;
    private String currentPlayer = STARTING_PLAYER;
    private final GameActionListener action;
    private BotState botState;

    public TicTacToe(GameActionListener action) {
        this.currentPlayer = STARTING_PLAYER;
        this.board = new String[3][3];
        this.action = action;
        this.botState = BotState.DEACTIVATED;
    }

    public boolean onFieldClicked(int row, int column, boolean bot) {
        if(checkIfValid(row, column, currentPlayer)) {
            board[row][column] = currentPlayer;
            action.setField(row, column, currentPlayer);
            if(checkIfWon(currentPlayer)) {
                playerWon(currentPlayer);
                return true;
            } else {
                switchPlayer();
            }
        }
        if(!bot && !botState.equals(BotState.DEACTIVATED)) {
            onBotTurn(currentPlayer);
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
        return board[row][column] == null;
    }

    //Zusatz (der Bot wird mit dem angegebenen Symbol platzieren)
    public void onBotTurn(String symbol) {
        //Mit irgendeinem intelligenten Algorithmus
        // Beispiel für das eigentliche setzen dann:
        // onFieldClicked(0, 2, true);
        // 0 ist die Reihe und 2 die Zeile
    }

    //.................

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public BotState getBotState() {
        return botState;
    }

    public void setBotState(BotState botState) {
        this.botState = botState;
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

    public void onBotGameStart() {
        if(botState.equals(BotState.AS_X) && currentPlayer.equals("X")) {
            onBotTurn("X");
        } else if(botState.equals(BotState.AS_O) && currentPlayer.equals("O")) {
            onBotTurn("O");
        }
    }

}
