package ru.geekbrains.notes;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

//1.	Подумайте о функционале вашего приложения заметок. Какие экраны там могут быть, помимо основного со списком заметок?
// Как можно использовать меню и всплывающее меню в вашем приложении? Не обязательно сразу пытаться реализовать весь этот функционал,
// достаточно создать макеты и структуру, а реализацию пока заменить на заглушки или всплывающие уведомления (Toast).
// Используйте подход Single Activity для отображения экранов.
// В качестве примера: на главном экране приложения у вас список всех заметок, при нажатии на заметку открывается экран
// с этой заметкой. В меню главного экрана у вас есть иконка поиска по заметкам и сортировка. В меню «Заметки» у вас
// есть иконки «Переслать» (или «Поделиться»), «Добавить ссылку или фотографию к заметке».
//  2.	Создайте боковое навигационное меню для своего приложения и добавьте туда хотя бы один экран, например «Настройки»
//  или «О приложении».
//  3.	* Создайте полноценный заголовок для NavigationDrawer’а. К примеру, аватарка пользователя, его имя и какая-то дополнительная
//  информация.


public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(savedInstanceState == null) {
            initStartFragment();
        }

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_about) {
                Snackbar.make(findViewById(R.id.drawer_layout), "О приложении", Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(findViewById(R.id.drawer_layout), "Настройки", Snackbar.LENGTH_SHORT).show();
            }
            drawer.closeDrawer(GravityCompat.START);
            return true;
        });
        toggle.syncState();
    }

    private void initStartFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        NotesFragment listFragment = new NotesFragment();
        fragmentTransaction.add(R.id.list_of_notes_fragment_container, listFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        MenuItem sort = menu.findItem(R.id.menu_sort);
        MenuItem addPhoto = menu.findItem(R.id.menu_add_photo);
        MenuItem send = menu.findItem(R.id.menu_send);
        send.setOnMenuItemClickListener(this);
        addPhoto.setOnMenuItemClickListener(this);
        sort.setOnMenuItemClickListener(this);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.drawer_layout), query, Snackbar.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Snackbar.make(findViewById(R.id.drawer_layout), item.getTitle().toString(), Snackbar.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}