
function [halflife] = fn_calchalflife(k);
%Function to calculate half life from k (elimination constant)
halflife=log(2)/k;
end