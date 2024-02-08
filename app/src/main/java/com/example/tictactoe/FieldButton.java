package com.example.tictactoe;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

public class FieldButton extends AppCompatButton {

    private final int row;
    private final int column;

    public FieldButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.FieldButton,
                0, 0);
        try {
            row = a.getInt(R.styleable.FieldButton_row, 0);
            column = a.getInt(R.styleable.FieldButton_column, 0);
        } finally {
            a.recycle();
        }
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
