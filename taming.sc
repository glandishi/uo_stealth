Program Taming;
const
    //bear=$00D3;
    bear=$00EA;
    band=$0E21;
begin
 //AddToSystemJournal(IntToStr(GetHP($08343AF0)));
 //AddToSystemJournal(IntToStr(GetMaxHP($08343AF0)));
 while true do
 begin
    FindDistance:=10;
    MoveXY(GetX(FindType(bear,Ground)),GetY(FindType(bear,Ground)),false,1,true);
    if (WarTargetID = FindType(bear,Ground)) and (WarMode) then
    begin        
        useskill('Peacemaking');
        wait(3200);
        SetWarMode(false);
    end
    else
    begin
        if GetHP(FindType(bear,Ground)) < GetMaxHP(FindType(bear,Ground)) then
        begin
          UseType(band,$0000);
          WaitTargetGround(bear);
          wait(8000);
        end
        else if HP < (MaxHP/2) then
        begin
            UOSay('''pc heal self');
            wait(8000);   
        end
        else
        begin
          useskill('Animal Taming');
          WaitTargetGround(bear);
          wait(3200);
        end;
    end;    
 end; 
end.