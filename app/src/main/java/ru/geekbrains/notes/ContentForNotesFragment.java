package ru.geekbrains.notes;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ContentForNotesFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private Note note;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static ContentForNotesFragment newInstance(Note note) {
        ContentForNotesFragment f = new ContentForNotesFragment();    // создание
        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        TextView titleText = view.findViewById(R.id.note_title);
        TextView noteContentText = view.findViewById(R.id.note_content);
        TextView dateOfCreationText = view.findViewById(R.id.note_date_of_creation);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
        dateOfCreationText.setText(String.format("%s", formatter.format(note.getCreationDate().getTime())));
        titleText.setText(note.getTitle());
        noteContentText.setText(note.getContent());
        return view;
    }
}