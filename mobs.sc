Program Mob;
var
	Monstr: array [1..4] of Cardinal;
procedure ScanMobs;
var
j: integer;
begin
  Monstr[1] := $000D;  //Змейка
  Monstr[2] := $0010;  //Блуд елементаль
  Monstr[3] := $0096;  //Ватер елементаль
  Monstr[4] := $0032;  //Poison елементаль
  FindDistance := 7;
  FindVertical := 255;
  begin
    for j := 1 to 4 do
    while FindType(Monstr[j], Ground) > 0 do
    begin
      Monstr[j]:=finditem;
      While (GetHp(Monstr[j])>0) do
      begin
        if GetHp(Monstr[j])<>0 then attack(Monstr[j]);
        SetWalkUnmountTimer(300);
        SetRunMountTimer(95);
		if GetDistance(Monstr[j])>1 then NewMoveXY(GetX(Monstr[j]),GetY(Monstr[j]),true,1,true);
        //If InJournal('System: У вас нет бинтов') <> -1 then CheckEquip; 
        UOSay(Chr(39)+'pc heal self');
        wait(1000);
		//if FindType($0E21,Backpack) = 0 then CheckEquip; 
      end;
    end; 
  end;
  //CheckHeal;
  //FindGround;
end;
begin
	ScanMobs;
end.