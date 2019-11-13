

function[IntervalTime] = calcIntervalTime(CrCl) 
if(CrCl < 60*60/1000)
    time = 24;
else if (CrCl < 100*60/1000)
        time = 12; 
    else
        time = 8;
    end
end
end