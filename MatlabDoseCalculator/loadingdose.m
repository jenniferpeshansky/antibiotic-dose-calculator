choice = input('Do you need a loading dose: Y or N: ','s');

switch choice
    case {'Y','y'}
        targetpeak=input('Enter Target Peak (mg/L): ');
        LD = round((targetpeak*Vd)/250)*250; %round to nearest 250
        fprintf('Loading Dose (mg): %5.2f\n',LD');
    case {'N','n'}
         LD=0;
    otherwise
        disp('Please re-enter')
end