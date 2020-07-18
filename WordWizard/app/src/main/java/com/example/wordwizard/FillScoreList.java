package com.example.wordwizard;

public class FillScoreList {
    private String Holder1;
    private String Holder2;
    private String Holder3;

    public FillScoreList(String Holder1, String Holder2, String Holder3){
        this.Holder1 = Holder1;
        this.Holder2 = Holder2;
        this.Holder3 = Holder3;
    }

    public String getHolder1() {
        return Holder1;
    }

    public void setHolder1(String holder1) {
        Holder1 = holder1;
    }

    public String getHolder2() {
        return Holder2;
    }

    public void setHolder2(String holder2) {
        Holder2 = holder2;
    }

    public String getHolder3() {
        return Holder3;
    }

    public void setHolder3(String holder3) {
        Holder3 = holder3;
    }
}
