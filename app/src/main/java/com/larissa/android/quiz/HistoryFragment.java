package com.larissa.android.quiz;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.larissa.android.quiz.databinding.FragmentHistoryBinding;

import java.util.List;


public class HistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    private HistoryItemAdapter historyItemAdapter;
    private List<EandR> list;
    private FragmentHistoryBinding fragmentHistoryBinding;
    private String TAG="222";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        fragmentHistoryBinding=FragmentHistoryBinding.inflate(inflater,container,false);
        return fragmentHistoryBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list=((MainActivity)requireActivity()).repository.history;
        recyclerView=fragmentHistoryBinding.recyclerView;
        historyItemAdapter = new HistoryItemAdapter(list,((MainActivity)requireActivity()).mainViewModel);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireActivity());;
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyItemAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentHistoryBinding=null;
        ((MainActivity)requireActivity()).mainViewModel.current_fragment="SF";
        Log.d(TAG, "onDestroyView: ");
    }


}
