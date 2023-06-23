package com.larissa.android.quiz;

public class EandR {
    private String equation;
    private String result;

    EandR(String equation,String result){
        this.equation=equation;
        this.result=result;
    }
    public String getEquation() {
        return equation;
    }
    public String getResult() {
        return result;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }
    public void setResult(String result) {
        this.result = result;
    }


}
