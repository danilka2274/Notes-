package ru.geekbrains.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private Note currentNote;
    private boolean isLandscape;

    // При создании фрагмента укажем его макет
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    // вызывается после создания макета фрагмента, здесь мы проинициализируем список
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
    }

    // создаём список заметок на экране из массива в ресурсах
    private void initList(View view) {
        LinearLayout layoutView = (LinearLayout)view;
        String[] notes = (getResources().getStringArray(R.array.notes));

        // В этом цикле создаём элемент TextView,
        // заполняем его значениями,
        // и добавляем на экран.
        // Кроме того, создаём обработку касания на элемент
        for(int i=0; i < notes.length; i++){
            String note = notes[i];
            TextView tv = new TextView(getContext());
            tv.setText(note);
            tv.setTextSize(30);
            tv.setTextColor(Color.rgb(255,255,255));
            layoutView.addView(tv);
            tv.setPadding(0,22,0,0);
            final int fi = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = new Note(fi, getResources().getStringArray(R.array.notes) [fi], getResources().getStringArray(R.array.content_for_notes) [fi]);
                    showImageForNotes(currentNote);
                }
            });
        }
    }

    // Сохраним текущую позицию (вызывается перед выходом из фрагмента)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Определение, можно ли будет расположить рядом герб в другом фрагменте
        isLandscape = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        // Если это не первое создание, то восстановим текущую позицию

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            // Если восстановить не удалось, то сделаем объект с первым индексом
            currentNote = new Note(0, getResources().getStringArray(R.array.notes)[0], getResources().getStringArray(R.array.content_for_notes)[0]);
        }

        // Если можно нарисовать рядом герб, то сделаем это
        if (isLandscape) {
            showLandImageForNotes(currentNote);
        }
    }

    private void showImageForNotes(Note currentNote) {
        if (isLandscape) {
            showLandImageForNotes(currentNote);
        } else {
            showPortImageForNotes(currentNote);
        }
    }

    // Показать герб в ландшафтной ориентации
    private void showLandImageForNotes(Note currentNote) {
        // Создаём новый фрагмент с текущей позицией для вывода герба
        ImageForNotesFragment detail = ImageForNotesFragment.newInstance(currentNote);

        // Выполняем транзакцию по замене фрагмента
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.imgs_for_notes, detail);  // замена фрагмента
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    // Показать цифру в портретной ориентации.
    private void showPortImageForNotes(Note currentNote) {
        // Откроем вторую activity
        Intent intent = new Intent();
        intent.setClass(getActivity(), ImageForNotesActivity.class);
        // и передадим туда параметры
        intent.putExtra(ImageForNotesFragment.ARG_NOTE, currentNote);
        startActivity(intent);
    }
}
