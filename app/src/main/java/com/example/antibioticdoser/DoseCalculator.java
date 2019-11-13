package com.example.antibioticdoser;

public class DoseCalculator {

/**Translation of Alex Nerantzinis's MatLab code
 * which takes in values, calculates, and graphs
 * the presence of an antibiotic in the bloodstream.
 * @author Jenny Peshansky
 */

    /**
     * Creates an AntibioticDose object and calculates the peak and trough
     * if a dose and SOI was given.
     * @param pBodyLiquid the percentage body liquid
     * @param weightPounds the weight in pounds
     * @param heightInches the height in inches
     * @param sex the sex, M or F
     * @param age the age in years
     * @param SCr the SCr in mg/dl
     * @param LD whether or not a loading dose is required
     * @param targetPeak the target peak, used to calculate loading dose
     * @param defaultDose the dose of the antibiotic
     * @param defaultSOI the speed of infusion of the antibiotic
     */
    public DoseCalculator(double pBodyLiquid, double weightPounds, double heightInches,
                          char sex, int age, double SCr, boolean LD, double targetPeak,
                          double defaultDose, double defaultSOI) {
        this.pBodyLiquid = pBodyLiquid;
        this.trueWeight = weightPounds/2.2046226218;
        this.height = heightInches;
        this.sex = sex;
        this.age = age;
        this.SCr = SCr;
        this.LD = LD;
        this.targetPeak = targetPeak;
        this.dose = defaultDose;
        this.speedOfInfusion = defaultSOI;
        this.peak = -1;
        this.trough = -1;

        baseCalculations();
    }

    /**
     * Performs all necessary calculations to find the peak and trough of a dose.
     * Sets the appropriate instance fields to the appropriate values.
     */
    public void baseCalculations() {
        /** STEP 1: Figuring out weight and CrCl to use based on male or female */
        double weight = calcWeightToUse(height, sex, trueWeight);
        CrCl = calcCrCl(age, weight, SCr, sex); //(L/hr)

        /** STEP 2: Figuring out VD depending on weight using and percent body liquid */
        VD = pBodyLiquid*weight; //calculate the volume of distribution, 0.7 L/kg is avg VD for vancomycin

        /** STEP 3a: Calculating elimination rate, k */
        double ClVanco = .8*CrCl; //calculate the clearance rate of vanco
        //mean clearance is 70%-90%, so we use 80%
        k = ClVanco/VD; //calculate the elimination constant

        /** STEP 3b: Calculating the half life */
        halfLife = (Math.log(2.0))/k; //calculate half life

        /** STEP 4: Calculating Loading Dose */
        loadingDose = 0; //mg
        if(LD) {
            //round to one above the nearest multiple of 250
            loadingDose = (Math.round((targetPeak*VD)/250)+1)*250;
        }

        /** STEP 5: Calculating peak and trough levels  */ //TODO
        double[] pAndT = calcPeakAndTrough(k, VD, dose, speedOfInfusion);
        peak = pAndT[0];
        trough = pAndT[1];
    }

    /**
     * This function will calculate the ideal body weight.
     * @param h the height in inches
     * @param sex M or F
     * @return the ideal body weight
     */
    private double calcIdealBodyWeight(double h, char sex) {
        if (sex == 'M')
            return 2.3 * (h - 60) + 50;
        else if (sex == 'F')
            return 2.3 * (h - 60) + 45.5;
        else
            return -1; //error
    }

    /**
     * This function decides the weight to use in calculations.
     * @param h the height
     * @param sex the sex
     * @param tbw the true body weight
     * @return the weight to use
     */
    private double calcWeightToUse(double h, char sex, double tbw) {
        double ibw = calcIdealBodyWeight(h, sex);
        if(tbw <= ibw)
            return tbw;//Use True Body Weight
        else if (tbw < 1.2*ibw)
            return ibw;//Use I Body Weight
        else
            return ibw+.4*(tbw-ibw); //Use Adjusted Weight
    }

    /**
     * This function will calculate the creatinine clearance
     * @return the creatinine clearance
     * @param age the age
     * @param weight the (adjusted) weight
     * @param SCr the serum creatinine concentration
     */
    private double calcCrCl(int age, double weight, double SCr, char sex) {
        //If SCr is less than 0.7 mg/dL, round up to 0.8 mg/dL to compensate for low muscle mass.
        double CrCl = ((140-age)*weight)/(72*SCr);
        if (sex == 'F')
            CrCl *= .85;
        CrCl *= (60.0/1000);
        return CrCl;
    }

    /**
     * Calculates peak and trough values based on a dose.
     * @param k the elimination constant
     * @param vD the volume of distribution
     * @param dose the dose guess
     * @param soi the infusion speed guess
     * @return peak and trough values resulting from these guesses
     */
    protected double[] calcPeakAndTrough(double k, double vD, double dose, double soi) {
        double numeratorPeak = (dose/soi) * (1- Math.exp(-1*k*soi));
        double denominatorPeak = vD*k*(1- Math.exp(-1*k*timeBetweenDoses));
        double peak = numeratorPeak/denominatorPeak;
        double trough = peak*Math.exp(-1*k*(timeBetweenDoses-soi));
        return new double[] {peak, trough};
    }

    /**
     *  Getters
     */
    public double getDose() { return dose; }
    public double getPeak() { return peak; }
    public double getTrough() { return trough; }
    public boolean getLD() { return LD; }
    public double getVD() { return VD; }
    public double getK() { return k; }
    public double getTBD() { return timeBetweenDoses; }
    public double getSOI() { return speedOfInfusion; }
    public double getHalfLife() { return halfLife; }
    public double getLoadingDose() { return loadingDose; }

    /**
     * Instance fields
     */
    private double pBodyLiquid;//(L/kg)
    private double trueWeight; //(kg)
    private double height;     //(in)
    private char sex;		   //(M or F)
    private int age;		   //(y)
    private double SCr;		   //(mg/dL)
    protected boolean LD;      //(L/kg)
    private double targetPeak; //(mg/L)

    protected double VD;
    protected double k;
    protected double CrCl;

    protected double dose;                 //(mg)
    protected double speedOfInfusion = 3;  //(hours)
    protected double timeBetweenDoses = 3; //(hours)
    protected double peak;				   //(mg)
    protected double trough;		       //(mg)

    private double halfLife;
    private double loadingDose;	//useful for plotting


}