package com.example.todo;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.example.todo.data.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Objects;

/**
 * Fragment für den Dialog zum Erstellen einer neuen Aufgabe.
 *
 * Fragmente sind wiederverwendbare UI-Komponenten, die in Activities eingebunden werden können.
 *
 * Hier wird ein DialogFragment verwendet, das einen Dialog anzeigt, der über den gesamten Bildschirm geht.
 */
public class CreateTaskDialogFragment extends DialogFragment {

    private Toolbar toolbar;
    private TextInputEditText taskContent;
    private final CreateTaskListener listener;
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private final DateTimeFormatter dateFormatter;

    //Warum ich hier zwei Variablen für das Datum habe, weiß ich auch nicht. It works on my machine.
    private LocalDate dueDate = null;
    private Pair<Integer, Integer> dueTime = null;


    @SuppressLint("NewApi") //Sollte man nicht machen, aber ich machs trotzdem
    public CreateTaskDialogFragment(CreateTaskListener listener) {
        this.listener = listener;
        dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
                .withZone(ZoneId.systemDefault());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Theme_App_FullScreenDialog);
    }

    /**
     * Hier wird der Dialog erstellt und die Views initialisiert.
     *
     * Listener werden gesetzt und die Buttons werden mit Aktionen verknüpft.
     *
     * Und ja man hätte das mehr aufteilen können
     */
    @SuppressLint("NewApi") //...
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_task, container, false);
        toolbar = view.findViewById(R.id.toolbar);
        taskContent = view.findViewById(R.id.task_content);
        view.findViewById(R.id.dateButton).setOnClickListener(v -> {
            Button dateButton = (Button) v;
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build();
            datePicker.addOnPositiveButtonClickListener(selection -> {
                long time = (long) selection;
                Instant date = Instant.ofEpochMilli(time);
                dueDate = LocalDate.ofInstant(date, ZoneId.systemDefault());
                dateButton.setText(dateFormatter.format(date));
            });
            datePicker.show(getParentFragmentManager(), "datePicker");
        });
        view.findViewById(R.id.timeButton).setOnClickListener(v -> {
            Button timeButton = (Button) v;
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTitleText("Select time")
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setInputMode(MaterialTimePicker.INPUT_MODE_CLOCK)
                    .build();
            timePicker.addOnPositiveButtonClickListener(selection -> {
                int hour = timePicker.getHour();
                int minute = timePicker.getMinute();
                dueTime = new Pair<>(hour, minute);
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
            });
            timePicker.show(getParentFragmentManager(), "timePicker");
        });
        CheckBox checkBox = view.findViewById(R.id.time_limit);
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Button dateButton = view.findViewById(R.id.dateButton);
            Button timeButton = view.findViewById(R.id.timeButton);
            if (isChecked) {
                dateButton.setEnabled(true);
                timeButton.setEnabled(true);
            } else {
                dateButton.setEnabled(false);
                timeButton.setEnabled(false);
                dueDate = null;
                dueTime = null;
            }
        });
        return view;
    }

    //Noch eine Methode
    @SuppressLint("NewApi") //...
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationOnClickListener(v -> dismiss());
        toolbar.inflateMenu(R.menu.create_task_dialog);
        toolbar.setOnMenuItemClickListener(item -> {
            String text = Objects.requireNonNull(taskContent.getText()).toString();
            if(!text.isEmpty()) {
                long timeMillis = (dueDate != null && dueTime != null) ? //Yes I know this is ugly
                        dueDate.atTime(dueTime.first, dueTime.second).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() :
                        -1;
                Task task = new Task(Objects.requireNonNull(text), false, timeMillis);
                listener.onCreateTask(task);
                dismiss();
            }
            return true;
        });
    }

    //Und noch eine Methode
    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }

}
