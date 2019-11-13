
function[CrCl] = fn_calcCrClm(a, SCr, w)
%This function will calculate the creatine clearance for males
%where a is the age, w is the weight, SCr is serum creatinine
%concentration.
%If SCr is less than 0.7 mg/dL, round up to 0.8 mg/dL to
%compensate for low muscle mass.
if SCr<0.7
    CrCl = (((140-a)* w)/(72*0.8))* (60/1000);
else
    CrCl = (((140-a)* w)/(72*SCr))* (60/1000);
end
end