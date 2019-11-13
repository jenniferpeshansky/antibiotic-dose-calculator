

function[ClVanco] = fn_calcClVanco(CrCl)
%Function to calculate the clearance rate of vancomycin
%mean clearance is 70% to 90%, so we use 80%
ClVanco = .8*CrCl;
end