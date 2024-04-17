Program Anatomy;
procedure Bandage();
begin
    UseType($0F9E,$0000);
    WaitTargetObject(FindType($1766,Backpack));  
end;
begin
    while true do    
    begin
        if (FindType($1766,Backpack) <> 0) then Bandage();
        useskill('Anatomy');
        waittargetground($0190); 
        wait(3300); 
    end;
 
end.