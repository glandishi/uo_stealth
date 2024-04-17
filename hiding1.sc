Program Hiding;
begin
    while true do 
      begin
      useskill('Hiding');
      if (FindType($1766,Backpack) <> 0) then
      begin 
        UseType($0F9E,$0000);
        WaitTargetObject(FindType($1766,Backpack));
      end;             
      wait(3000);
      end; 
end.