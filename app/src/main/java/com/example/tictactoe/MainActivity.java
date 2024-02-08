package com.example.tictactoe;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tictactoe.databinding.ScreenBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameActionListener {

    private TicTacToe ticTacToe;
    private TextView turnText;
    private ScreenBinding binding;
    private FieldButton[][] fieldButtons;

    //Ungefähr equivalent zu main() in normalen Java-Programmen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Die von Android erstellte Klasse von dem layout "screen.xml" wird verwendet um die Views zu erstellen (abhängig natürlich von dem Gerät, auf dem die App läuft)
        binding = ScreenBinding.inflate(getLayoutInflater());

        //Die erstellten Views werden als Content der Activity gesetzt
        setContentView(binding.getRoot());

        turnText = binding.turnTV;
        fieldButtons = new FieldButton[][] {{binding.a1, binding.a2, binding.a3}, {binding.b1, binding.b2, binding.b3}, {binding.c1, binding.c2, binding.c3}};
        binding.resetButton.setOnClickListener(v -> {
            ticTacToe.reset();
            resetFields();
        });

        //Hier wird ein neues TicTacToe-Objekt erstellt
        ticTacToe = new TicTacToe(this);
        updateTurnText();
    }

    public void fieldClicked(View view) {
        if(!(view instanceof FieldButton)) return;
        FieldButton fieldButton = (FieldButton) view;
        int row = fieldButton.getRow();
        int column = fieldButton.getColumn();
        if(!ticTacToe.onFieldClicked(row, column)) {
            FieldState fieldState = ticTacToe.getFieldState(row, column);
            fieldButton.setText(fieldState.name());
            updateTurnText();
        }
    }

    private void updateTurnText() {
        turnText.setText(getString(R.string.turn, ticTacToe.getCurrentPlayer().name()));
    }

    private void resetFields() {
        for (FieldButton[] fieldButton : fieldButtons) {
            for (FieldButton button : fieldButton) {
                button.setText("");
            }
        }
    }

    @Override
    public void onPlayerWin(FieldState player) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.player_won_title)
                .setMessage(getString(R.string.player_won, player.name()))
                .setPositiveButton(R.string.ok, (dialog, which) -> {})
                .show();
        resetFields();
        updateTurnText();
    }

    @Override
    public void vibrate(int duration, int strength) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(duration, strength));
        } else {
            v.vibrate(duration);
        }
    }
}