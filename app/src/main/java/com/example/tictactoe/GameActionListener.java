package com.example.tictactoe;

public interface GameActionListener {

    void onPlayerWin(FieldState player);

    /**
     * Vibrate the device
     * @param duration in milliseconds
     * @param strength -1-255
     */
    void vibrate(int duration, int strength);

}
