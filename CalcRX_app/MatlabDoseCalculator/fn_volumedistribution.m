

function Vd = fn_volumedistribution(VD,tbw)
%This function is to calculate the volume of distribution,
%tbw is the true body weight,VD is volume of distribution for vanco.
%0.7 L/kg is avg Vd for vancomycin  
Vd=VD*tbw;
end