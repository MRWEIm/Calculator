package com.larissa.android.quiz;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.larissa.android.quiz.databinding.FragmentSimpleBinding;

public class SimpleFragment extends Fragment implements View.OnClickListener{

    private FragmentSimpleBinding binding;

    private int ori;
    private String TAG="111";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding=FragmentSimpleBinding.inflate(inflater,container,false);
        // Log.d(TAG, String.format("%s ",SimpleFragmentArgs.fromBundle(getArguments()).getIdx()));
        binding.upper.formula.setText(((MainActivity)requireActivity()).mainViewModel.equation);
        binding.upper.result.setText(((MainActivity)requireActivity()).mainViewModel.result);
        if(((MainActivity)requireActivity()).mainViewModel.convey_flag){
            ((MainActivity)requireActivity()).mainViewModel.convey_flag=false;
            ((MainActivity)requireActivity()).mainViewModel.eq_flag=false;
            int idx=SimpleFragmentArgs.fromBundle(getArguments()).getIdx();
            binding.upper.formula.setText(((MainActivity)requireActivity()).repository.history.get(idx).getResult());
            binding.upper.result.setText("");
            ((MainActivity)requireActivity()).mainViewModel.equation=binding.upper.formula.getText().toString();
            ((MainActivity)requireActivity()).mainViewModel.result=binding.upper.result.getText().toString();
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ori=getResources().getConfiguration().orientation;
        setClickListener();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }

    @Override
    public void onClick(View view) {
        String fo=binding.upper.formula.getText().toString();
        switch (view.getId()){
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
            case R.id.btn_dot:
            case R.id.btn_pi:
            case R.id.btn_e:
            case R.id.btn_lb:
            case R.id.btn_rb:
            case R.id.btn_invo:
            case R.id.btn_fact:
                if(((MainActivity)requireActivity()).mainViewModel.eq_flag){
                    fo="";
                    binding.upper.formula.setText("");
                    binding.upper.result.setText("");
                    ((MainActivity)requireActivity()).mainViewModel.eq_flag=false;
                }
                binding.upper.formula.setText(fo+((Button)view).getText());
                break;
            case R.id.btn_min:
                if(((MainActivity)requireActivity()).mainViewModel.eq_flag){
                    fo="";
                    binding.upper.formula.setText("");
                    binding.upper.result.setText("");
                    ((MainActivity)requireActivity()).mainViewModel.eq_flag=false;
                }
                if (binding.upper.formula.getText().toString().length()==0||!isOp(binding.upper.formula.getText().toString().charAt(binding.upper.formula.getText().toString().length()-1))) {
                    binding.upper.formula.setText(fo+((Button)view).getText());
                    break;
                }
                else break;
            case R.id.btn_plus:
            case R.id.btn_mul:
            case R.id.btn_dvi:
                if(((MainActivity)requireActivity()).mainViewModel.eq_flag){
                    fo="";
                    binding.upper.formula.setText("");
                    binding.upper.result.setText("");
                    ((MainActivity)requireActivity()).mainViewModel.eq_flag=false;
                }
                if(binding.upper.formula.getText().toString().length()==0)
                    break;
                else if (!isOp(binding.upper.formula.getText().toString().charAt(binding.upper.formula.getText().toString().length()-1))) {
                    binding.upper.formula.setText(fo+((Button)view).getText());
                    break;
                }
                else break;
            case R.id.btn_sin:
            case R.id.btn_cos:
            case R.id.btn_tan:
            case R.id.btn_log:
            case R.id.btn_ln:
            case R.id.btn_squ:
                if(((MainActivity)requireActivity()).mainViewModel.eq_flag){
                    fo="";
                    binding.upper.formula.setText("");
                    binding.upper.result.setText("");
                    ((MainActivity)requireActivity()).mainViewModel.eq_flag=false;
                }
                binding.upper.formula.setText(fo+((Button)view).getText()+'(');
                break;
            case R.id.btn_del:
                if(binding.upper.formula.length()>0)binding.upper.formula.setText(fo.substring(0,fo.length()-1));
                break;
            case R.id.btn_equ:
                binding.upper.result.setText(Calculate.calculate(fo));
                ((MainActivity)requireActivity())
                        .repository.history
                        .add(new EandR(binding.upper.formula.getText().toString(),binding.upper.result.getText().toString()));
                ((MainActivity)requireActivity()).mainViewModel.eq_flag=true;
                Log.d(TAG, String.format("%s",((MainActivity)requireActivity()).mainViewModel.eq_flag));
                break;
            case R.id.btn_clear:
                binding.upper.result.setText("");
                binding.upper.formula.setText("");
            default:break;
        }
        ((MainActivity)requireActivity()).mainViewModel.equation=binding.upper.formula.getText().toString();
        ((MainActivity)requireActivity()).mainViewModel.result=binding.upper.result.getText().toString();
    }

    private void setClickListener(){
        binding.btn0.setOnClickListener(this);
        binding.btn1.setOnClickListener(this);
        binding.btn2.setOnClickListener(this);
        binding.btn3.setOnClickListener(this);
        binding.btn4.setOnClickListener(this);
        binding.btn5.setOnClickListener(this);
        binding.btn6.setOnClickListener(this);
        binding.btn7.setOnClickListener(this);
        binding.btn8.setOnClickListener(this);
        binding.btn9.setOnClickListener(this);

        binding.btnPlus.setOnClickListener(this);
        binding.btnMin.setOnClickListener(this);
        binding.btnMul.setOnClickListener(this);
        binding.btnDvi.setOnClickListener(this);

        binding.btnDot.setOnClickListener(this);
        binding.btnLb.setOnClickListener(this);
        binding.btnRb.setOnClickListener(this);

        binding.btnDel.setOnClickListener(this);
        binding.btnEqu.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);

        if(ori == Configuration.ORIENTATION_LANDSCAPE){
            binding.btnSin.setOnClickListener(this);
            binding.btnCos.setOnClickListener(this);
            binding.btnTan.setOnClickListener(this);
            binding.btnLog.setOnClickListener(this);
            binding.btnLn.setOnClickListener(this);

            //最左一栏
            binding.btnInvo.setOnClickListener(this);
            binding.btnFact.setOnClickListener(this);
            binding.btnSqu.setOnClickListener(this);
            binding.btnPi.setOnClickListener(this);
            binding.btnE.setOnClickListener(this);
        }
    }

    private boolean isOp(char unit){
        return unit=='+'||unit=='-'||unit=='*'||unit=='÷';
    }
}
