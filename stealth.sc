Program Stealth;

procedure Bandage();
begin
    UseType($0F9E,$0000);
    WaitTargetObject(FindType($1766,Backpack));
    wait(3300);    
end;

begin
    while true do 
    begin
        if Hidden then
        begin
            useskill('Stealth');
            MoveXY(GetX(self)+1,GetY(self)+1,false,0,false);
            MoveXY(GetX(self)-1,GetY(self)-1,false,0,false);
            wait(3500);    
        end
        else
        begin
            useskill('Hiding');
            wait(3300);
        end;
      if (FindType($1766,Backpack) <> 0) then Bandage();
    end; 
end.