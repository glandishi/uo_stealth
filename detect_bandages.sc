Program detect_bandages;
begin
while true do
 begin
    useskill('Detect Hidden');
    waitfortarget(100);
    TargetToObject(self);
    if (FindType($1766,Backpack) <> 0) then
    begin 
      UseType($0F9E,$0000);
      WaitTargetObject(FindType($1766,Backpack));
    end;             
    wait(3300);
 end;
end.