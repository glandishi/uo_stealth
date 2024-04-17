Program SpiritSpeak;

procedure Bandage();
begin
    UseType($0F9E,$0000);
    WaitTargetObject(FindType($1766,Backpack));
    wait(3300);    
end;

begin
    while true do
    begin
      useskill('Spirit Speak');
      if (FindType($1766,Backpack) <> 0) then Bandage();
      wait(3300);
    end;
end.