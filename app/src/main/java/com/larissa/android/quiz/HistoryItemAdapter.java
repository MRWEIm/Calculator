package com.larissa.android.quiz;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.larissa.android.quiz.databinding.DetailBinding;

import java.util.List;

public class HistoryItemAdapter extends RecyclerView.Adapter<HistoryItemAdapter.HistoryItemViewHolder> {

    private List<EandR> list;
    private MainViewModel mainViewModel;
    public HistoryItemAdapter(List<EandR> list,MainViewModel mainViewModel){
        super();
        this.list=list;
        this.mainViewModel=mainViewModel;
    }

    @NonNull
    @Override
    public HistoryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.detail,parent,false);
        return new HistoryItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryItemAdapter.HistoryItemViewHolder holder, int position) {
        holder.detailBinding.historyEq.setText(list.get(position).getEquation());
        holder.detailBinding.historyRe.setText(list.get(position).getResult());
        holder.idx=holder.getAdapterPosition();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HistoryItemViewHolder extends RecyclerView.ViewHolder {
        public DetailBinding detailBinding;
        public int idx;
        public HistoryItemViewHolder(View view){
            super(view);
            detailBinding=DetailBinding.bind(view);
            detailBinding.historyRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.convey_flag=true;
                    NavOptions navOptions = new NavOptions.Builder()
                            .setPopUpTo(R.id.historyFragment, true)
                            .build();
                    NavDirections action=HistoryFragmentDirections.actionHistoryFragmentToSimpleFragment(idx);
                    Navigation.findNavController(view).navigate(action,navOptions);
                }
            });
            detailBinding.historyEq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mainViewModel.convey_flag=true;
                    NavDirections action=HistoryFragmentDirections.actionHistoryFragmentToSimpleFragment(idx);
                    Navigation.findNavController(view).navigate(action);
                }
            });
        }
    }
}
