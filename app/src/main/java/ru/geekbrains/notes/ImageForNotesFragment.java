package ru.geekbrains.notes;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ImageForNotesFragment extends Fragment {

    public static final String ARG_NOTE = "note";
    private Note note;

    // Фабричный метод создания фрагмента
    // Фрагменты рекомендуется создавать через фабричные методы.
    public static ImageForNotesFragment newInstance(Note note) {
        ImageForNotesFragment f = new ImageForNotesFragment();    // создание

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Таким способом можно получить головной элемент из макета
        View view = inflater.inflate(R.layout.fragment_images_for_notes, container, false);
        // найти в контейнере элемент-изображение
        AppCompatImageView imageCoatOfArms = view.findViewById(R.id.imgs_for_notes);
        // Получить из ресурсов массив указателей на изображения гербов
        TypedArray images = getResources().obtainTypedArray(R.array.images_for_notes);
        // Выбрать по индексу подходящий
        imageCoatOfArms.setImageResource(images.getResourceId(note.getImageIndex(), -1));
        // Установить название города
        TextView noteNameView = view.findViewById(R.id.textView);
        noteNameView.setText(note.getNoteName2());

        return view;
    }
}
