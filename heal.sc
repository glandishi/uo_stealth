Program healing;
begin
 while not Dead do
 begin
    UseObject(FindType($0E21,backpack)); 
    WaitTargetSelf;
    wait(8300);
 end;
end.