Program Taming1;
const
  x1 = 991;
  y1 = 1265;
  x2 = 992;
  y2 = 1268;
  anmlID = $079CE7B5; // ID ?????????
  iFood = $097B; 
var 
  k,i,chposition : integer;
  ctime : TDateTime;
  
procedure movto;
  begin
  ClearBadLocationList;
  ClearBadObjectList; 
  If chposition = 1 then
    begin              
    //NewMoveXY(x1o,y1o,true,0,true);
    MoveItem(FindType(iFood,backpack),5,anmlID,0,0,0);
    wait(100);
    NewMoveXY(x2,y2,true,0,true);
    chposition := 2;
    exit; 
    end;
  If chposition = 2 then 
    begin
    //NewMoveXY(x2o,y2o,true,0,true);
    NewMoveXY(x1,y1,true,0,true);
    chposition := 1; 
    exit;
    end;
  end;
begin
  chposition := 1;
  ctime := Now;
  while not dead do 
  begin 
  While i <= 60 do
    begin
    If getHP(anmlID) < 950 then
      begin
       UOSay(Chr(39)+'pc heal lt');
      end;
    If getHP(self) < 130 then
      begin
      UOSay(Chr(39)+'pc heal self');
      end;
    UseSkill('Animal taming');
    WaitForTarget(300);
    TargetToObject(anmlID);
      repeat
      If GetDistance(anmlID) <= 1 then
        movto;
      Wait(100);
      k := k+1;
      Until k >= 30;
    k := 0; 
    i := i+1;
    If (InJournalBetweenTimes('??????, ???????? ???????? ??? ????? ????????!',ctime,now) > 0) or (InJournalBetweenTimes('?? ??? ?????????????',ctime,now) > 0) or (InJournalBetweenTimes('?? ?????????? ? ?? ?????? ?????????.',ctime,now) > 0) then
      begin
      UOSay('All release');
      Wait(2000);
      step(6,false);
      ctime := Now;
      end;
    end;
  i:=0;
  Wait(2900);
  Usetype(iFood,$0000);
  end;
end.