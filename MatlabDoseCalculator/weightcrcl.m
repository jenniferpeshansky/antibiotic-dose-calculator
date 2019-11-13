
%Step 1: Figuring out weight and CrCl to use based on male or female

choice = input('M or F: ','s');

switch choice
    case {'M','m'}
        h=input('Enter Height(inches): ');
        ibw = fn_idealbodyweightm(h);
    case {'F','f'}
         h=input('Enter Height(inches): ');
         ibw = fn_idealbodyweightf(h);
    otherwise
        disp('Please re-enter')
end

weight=input('Enter Weight(lbs.): ');
tbw  = fn_truebodyweight(weight);

if(tbw <= ibw)
    w = tbw;
    fprintf('Using TBW(kg): %5.5f\n',w')
else if (tbw < 1.2*ibw) 
        w = ibw;
        fprintf('Using IBW(kg): %5.5f\n',w')
    else
        w = ibw+.4*(tbw -ibw);
        fprintf('Adjusted Weight(kg): %5.5f\n',w')
    end
end

switch choice
    case {'F','f'}
        a=input('Enter Age(in years): ');
        SCr=input('Enter SCr (mg/dL): ');
        CrCl = fn_calcCrClf(a, SCr, w);
        fprintf('CrCl (L/hr): %5.5f\n',CrCl')
    case {'M','m'}
        a=input('Enter Age (in years): ');
        SCr=input('Enter SCr (mg/dL): ');
        CrCl = fn_calcCrClm(a, SCr, w);
        fprintf('CrCl (L/hr): %5.5f\n',CrCl')
    otherwise
        disp('Please re-enter')
end

%Step 2: Figuring out Vd depending on weight using and percent
%body liquid
VD=input('Enter volume of distribution for vancomycin (L/kg): ');
Vd = fn_volumedistribution(VD,tbw);
fprintf('Volume of Distribution, Vd (L): %5.5f\n',Vd');

%Step 3a: Calculating elimination rate, k 
[ClVanco] = fn_calcClVanco(CrCl);
fprintf('ClVanco: %5.5f\n',ClVanco');
k = fn_eliminationconstant(ClVanco, Vd);
fprintf('Elimination constant, k (h^-1): %5.5f\n',k');

%Step 3b: Calculating the half life 
[halflife] = fn_calchalflife(k);
fprintf('Half-Life (t1/2 in hours): %5.5f\n',halflife');

%Step 4: Calculating Loading Dose 
choice = input('Do you need a loading dose: Y or N: ','s');

LD = 0;

switch choice
    case {'Y','y'}
        targetpeak=input('Enter Target Peak (mg/L): ');
        LD = round((targetpeak*Vd)/250)*250+250; %round to nearest 250
        fprintf('Loading Dose (mg): %5.2f\n',LD');
    case {'N','n'}
         LD=0;
    otherwise
        disp('Please re-enter')
end

%Step 5: Calculating peak and trough levels
Dose=input('Enter dose (mg): ');
ti= input('Enter infusion time (hours): ');
tbd=input('Enter time between doses (hours): ');
[Cp, Ct] = calcPeakAndTrough(k, Vd, Dose, ti, tbd);
fprintf('Peak concentration (mg): %5.5f\n',Cp');
fprintf('Trough Concentration (mg): %5.5f\n',Ct');

%Step 6: Plotting the Result
if LD==0
    %If there is no loading dose
    ta=ti*Vd*k*(1-exp(-k*(tbd)));
    
    C=linspace(0,Cp);
    tin=(log(1-((ta*C)/Dose)))/(-k);%Line to get to starting doses (starts at 0, ends at Co)
    
    Ci=linspace(Ct(1),Cp(1));
    t=(log(Ci/Cp(1))/-k)+ti;%Initial exponential plot (starts at LD, ends at Ct)
    
    Cii=linspace(Ct(1),Cp(1));
    tcp=(log(1-((ta*(Cii))/Dose)))/(-k)+tbd;%line to get to Cp after Ct is hit
    tii=(log(Cii/Cp(1))/-k)+(tbd+ti);%Next exponential decay curve after time btn dose passes  
    for n=0:numDoses-2
        plot(tii+(n*tbd),Cii,'m');
        hold on;
    end
    for n=0:numDoses-2
        plot((tcp)+(tbd*n),Cii,'g');
        hold on;
    end
    
    plot(tin,C,'r');
    hold on;
    plot(t,Ci,'b');
    hold on;
    plot(tcp,Cii,'g');
    hold on;
    
    axis([0 (numDoses)*(tbd+ti) 0 Cp(1)*2]);
    ylabel('Concentration (mg/L)');
    xlabel('Time (hours)');
    title('Antibiotic Activity Plot');
    
else
    %If there is a loading dose
    Co=LD/Vd;
    numDoses= input('Enter number of doses: ');
    ta=ti*Vd*k*(1-exp(-k*(tbd)));
    
    C=linspace(0,Co);
    tin=(log(1-((ta*C)/LD)))/(-k);%Line to get to starting doses (starts at 0, ends at Co)
    
    Ci=linspace(Ct(1),Co(1));
    t=(log(Ci/Co(1))/-k)+ti;%Initial exponential plot (starts at LD, ends at Ct)
    
    Cii=linspace(Ct(1),Cp(1));
    tcp=(log(1-((ta*(Cii))/Dose)))/(-k)+tbd;%line to get to Cp after Ct is hit
    tii=(log(Cii/Cp(1))/-k)+(tbd+ti);%Next exponential decay curve after time btn dose passes  
    
    for n=0:numDoses-2
        plot(tii+(n*tbd),Cii,'m');
        hold on;
    end
    
    for n=0:numDoses-2
        plot((tcp)+(tbd*n),Cii,'g');
        hold on;
    end
    
    plot(tin,C,'r');
    hold on;
    plot(t,Ci,'b');
    hold on;
    plot(tcp,Cii,'g');
    hold on;
    
    axis([0 (numDoses)*(tbd+ti) 0 Co(1)*2]);
    ylabel('Concentration (mg/L)');
    xlabel('Time (hours)');
    title('Antibiotic Activity Plot')
end







  
    










