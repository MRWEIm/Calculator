package com.larissa.android.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.larissa.android.quiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity{

    public HistoryRepository repository;

    public MainViewModel mainViewModel;
    private MenuProvider menuProvider = new MenuProvider() {
        @Override
        public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            menuInflater.inflate(R.menu.menu_cal,menu);
        }

        @Override
        public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.menu_history:
                    NavController navController= NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
                    navController.navigate(R.id.action_simpleFragment_to_historyFragment);
                    mainViewModel.current_fragment="HF";
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel= new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        addMenuProvider(menuProvider);
        repository=HistoryRepository.getInstance();

        NavController navController= NavHostFragment.findNavController(getSupportFragmentManager().findFragmentById(R.id.fragment_container));
        if(mainViewModel.current_fragment.equals("HF"))
            navController.navigate(R.id.historyFragment);
    }

}