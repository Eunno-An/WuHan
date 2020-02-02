package com.example.wuhan.db;

public class Patient {
    private int diagnosis;  //확진자
    private int fatality;   //사망자
    private int ing;        //검사중(유증상)
    private String level;   //위기 경보
    private int out;        //격리해제(유증상)
    private int symptom;    //유증상자

    public Patient(){}

    public Patient(int diagnosis, int fatality, int ing, String level, int out, int symptom){
        this.diagnosis = diagnosis;
        this.fatality = fatality;
        this.ing = ing;
        this.level = level;
        this.out = out;
        this.symptom = symptom;
    }

    public int getDiagnosis(){
        return diagnosis;
    }

    public int getFatality(){
        return fatality;
    }

    public int getIng(){
        return ing;
    }

    public String getLevel(){
        return level;
    }

    public int getOut(){
        return out;
    }

    public int getSymptom(){
        return symptom;
    }
}
