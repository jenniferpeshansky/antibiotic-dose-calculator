
%ti=infusion time
%tbd=time between doses (interval time)
function [Cp, Ct] = calcPeakAndTrough(k, Vd, Dose, ti, tbd); 
numeratorPeak = (Dose/ti) * (1- exp(-1*k*ti) ); 
denominatorPeak = Vd*k*(1- exp(-1*k*tbd));
Cp = numeratorPeak/denominatorPeak;
Ct = Cp*exp(-1*k*(tbd-ti)); 
end