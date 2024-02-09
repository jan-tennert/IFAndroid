package com.example.tictactoe;

public interface GameActionListener {

    void resetFields();

    void setField(int row, int column, String symbol);

    void showDialog(String title, String message);

    /**
     * Vibrate the device
     * @param duration in milliseconds
     * @param strength -1-255
     */
    void vibrate(int duration, int strength);

}
