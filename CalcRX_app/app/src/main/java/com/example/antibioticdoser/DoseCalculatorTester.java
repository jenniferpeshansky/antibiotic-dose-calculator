package com.example.antibioticdoser;

public class DoseCalculatorTester {
    public static void main(String[] args) {
        //Testing Values
        double pBodyLiquid = 0.7; //(L/kg)
        double weightPounds = 145;
        double heightInches = 67;
        char sex = 'M';
        int age = 64;
        double SCr = .9; //(mg/dL)
        boolean LD = true;
        double targetPeak = 200; //(mg/L)

        double dose = 1000; //(mg)
        double infusionTime = 1; //(hours)

        DoseCalculator setDoseOnly = new DoseCalculator(pBodyLiquid, weightPounds, heightInches, sex,
                age, SCr, LD, targetPeak, dose, infusionTime);

        System.out.println("with a set dose "+setDoseOnly.getDose()+", "+setDoseOnly.getPeak()+", "+setDoseOnly.getTrough());

        //VancomycinDoseCalculator calcDose = new VancomycinDoseCalculator(pBodyLiquid, weightPounds, heightInches, sex, age, SCr, LD, targetPeak);
        //System.out.println("calculating a dose " + calcDose.getDose()+", "+calcDose.getPeak()+", "+calcDose.getTrough());//*/



    }
}
