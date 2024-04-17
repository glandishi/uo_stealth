Program Mining;
const
	chest=$75651F5F;
	chestx=4585;
	chesty=3843;
	orebox=$72B8A4F6;
	resbox=$75382CA7;
	gembox=$63AE01D9;
	weapbox=$72B8A4F5;
	shieldbox=$7F07574F;
	scim=$13B6;
	heater=$1B76;
	qty=30;
	directions: array[1..8] of string = ['s','sw','w','nw','n','ne','e','se'];
	okmsg='Вы выкопали';
	stopMsg='Здесь нет больше руды..|pickaxe:|Your are overloaded|Вы не можете копать в этом месте.';
	limit=350;
type
AMineTile = Record
x, y : Integer;
end;
var
	maxlim: integer;
	Monstr: array [1..4] of Cardinal;
	MinePoint: array[1..8] of AMineTile;
	i: integer;
	Tools : array [1..3] of Cardinal;
	Items : array [1..26] of Cardinal;
procedure InitToolItem;
begin
    Tools[1] := $0E85; // pickaxe
    Tools[2] := $0E21; // bandage
    Tools[3] := $097B; // food
	Items[1] := $19B9; // iron ore
	Items[2] := $0F8D; // spider silk
	Items[3] := $0F8C; // sulfur
	Items[4] :=$0F85; // ginseng
	Items[5] :=$0F84; // garlic
	Items[6] :=$0F86; // mandrake
	Items[8] :=$0F88; // nightshade
	Items[9] :=$0EED; // gold
	Items[10] :=$0F7B; // blood moss
	Items[11] :=$0F21; // star sapfire
	Items[12] :=$0F7A; // black pearl
	Items[13] :=$0F2E; // gem
	Items[14] :=$0F2B; // gem
	Items[15] :=$0F2F; // gem
	Items[16] :=$0F29; // gem
	Items[17] :=$0F30; // gem
	Items[18] :=$0F22; // gem
	Items[19] :=$0F27; // gem
	Items[20] :=$0F24; // gem
	Items[21] :=$0F25; // gem
	Items[22] :=$0F2C; // gem
	Items[23] :=$0F2D; // gem
	Items[24] :=$0F2A; // gem
	Items[25] :=$0F23; // gem
	Items[26] :=$0F28; // gem
end;
procedure MinePointInit;
begin
	MinePoint[1].x:=4601;
	MinePoint[1].y:=3820;
	MinePoint[2].x:=4601;
	MinePoint[2].y:=3817;
	MinePoint[3].x:=4601;
	MinePoint[3].y:=3814;
	MinePoint[4].x:=4598;
	MinePoint[4].y:=3814;
	MinePoint[5].x:=4598;
	MinePoint[5].y:=3811;
	MinePoint[6].x:=4601;
	MinePoint[6].y:=3811;
	MinePoint[7].x:=4604;
	MinePoint[7].y:=3814;
	MinePoint[8].x:=4600;
	MinePoint[8].y:=3810;
end;
procedure Bandages;
begin
	FindType(Tools[2],Backpack);
	if ((FindType(Tools[2],Backpack) = 0) or (FindQuantity < 10)) then
	begin
		if GetDistance(chest) > 1 then
		begin
			MoveXY(chestx,chesty,false,1,true);
			wait(100);
		end;
		UseObject(chest);
		wait(1500);
		UseObject(resbox);
		wait(350);
		MoveItem(FindType(Tools[2], resbox), qty, backpack, 0, 0, 0);
		wait(350);	
	end;
end;
procedure DropWeight;
var
	k:integer;
begin
	if Weight > maxlim then
	begin
		k:=Round((Weight-maxlim)/12); 
		MoveItem(FindType(Items[1],backpack), k,Ground, 0, 0, 0);
	end;
end;
Procedure StoreItems;
////*****/////******/////****////
var
g : integer;
ItemID : Cardinal;
Begin
  if GetDistance(chest) = -1 then NewMoveXY(chestx, chesty, false, 0, true)
  else if GetDistance(chest) > 1 then NewMoveXY(chestx, chesty, false, 0, true);
  AddToSystemJOurnal('Открываем Пак!!!');
  UseObject(backpack);
  wait(500);
  AddToSystemJournal('Открываем Главный Сундук!!!');
  UseObject(chest);
  While LastContainer <> chest do 
  begin
	UOSay('''resend');
	wait(6000);
	UseObject(chest);
  end;
  wait(500);
  for g := 1 to 13 do
  begin
    while FindType(Items[g], backpack) > 0 do
    begin
      If not Dead then 
      begin
        ItemID := finditem;
        AddToSystemJournal('Скидываю Приколы');
        MoveItem(ItemID, 0,orebox, 0, 0, 0);
        wait(350);
      end;
    end;
  end;
  for g := 14 to Length(Items) do
  begin
    while FindType(Items[g], backpack) > 0 do
    begin
      If not Dead then 
      begin
        ItemID := finditem;
        AddToSystemJournal('Скидываю гемы и лут');
        MoveItem(ItemID, 0,gembox, 0, 0, 0);
        wait(350);
      end;
    end;
  end;
  Bandages;
end;
procedure CheckHeal;
////*****/////******/////****////
begin
  while Life < MaxHP do  
  begin
    repeat
      If InJournal('System: У вас нет бинтов') <> -1 then Bandages;  
      UOSay(Chr(39)+'pc heal self');   
      addtosystemjournal('Лечимся');
      Wait(8200);
      if FindType(Tools[2], backpack) = 0 then Bandages;  
    until Life = MaxHP;   
    If Dead then Exit;
  end;
end;

procedure CheckEquip;
var
	j: integer;
begin
	j:=1;
	if FindType(Tools[j], backpack) = 0 then
	begin
		If not Dead then
		begin
			UseObject(chest);
			wait(300);
			UseObject(resbox);
			wait(350);
			AddToSystemJOurnal('Хаваем!!!');
			UseObject(FindType(Tools[3], resbox));
			wait(1000);
			AddToSystemJournal('Закончились кирки');
			MoveItem(FindType(Tools[j],resbox), 0,backpack, 0, 0, 0);
		end;
	end;
		if ObjAtLayer(RhandLayer) = 0 then
		begin
			If not Dead then
			if FindType(scim, backpack) > 0 then Equip(RhandLayer,FindType(scim,Backpack))
			else
			begin
				UseObject(chest);
				wait(300);
				UseObject(weapbox);
				wait(350);
				AddToSystemJournal('Сломалось оружие');
				MoveItem(FindType(scim,weapbox), 0,backpack, 0, 0, 0);
				wait(100);
				Equip(RhandLayer,FindType(scim,Backpack));
			end;
		end;
		if ObjAtLayer(LhandLayer) = 0 then
		begin
			If not Dead then
			if FindType(heater, backpack) > 0 then Equip(LhandLayer,FindType(heater,Backpack))
		else
		begin
			UseObject(chest);
			wait(300);
			UseObject(shieldbox);
			wait(350);
			AddToSystemJournal('Сломался щит');
			MoveItem(FindType(heater,shieldbox), 0,backpack, 0, 0, 0);
			wait(100);
			Equip(LhandLayer,FindType(heater,Backpack));
		end;
	end;
end;
procedure FindGround;
////*****/////******/////****////
var
	Corpse: Cardinal;
	ItemID: Cardinal;
	i, j: Integer;  
Begin
  FindDistance := 5;
  if FindType($2006, ground) <> 0 then
  begin
    AddToSystemJournal('Найден труп: '+GetName(Corpse));  
    Corpse := finditem;
    Wait(3000);
    NewMoveXY(GetX(Corpse), GetY(Corpse), false, 1, false);
	UseObject(Corpse);
	Wait(1000);
	i := 1;
	while i < 13 do
	begin
		If FindType(Items[i], Corpse) > 0 then
		begin
			ClearJournal;
			ItemID := finditem;
			If not Dead then MoveItem(ItemID, 0, backpack, 0, 0, 0);
			Wait(250);
			If InJournal('System: You don''t drag anything') <> -1 then Break;
		end
		else
		begin
			i := i + 1;
		end;
	end;
	Ignore(Corpse);	
  end;
  for j := 1 to 12 do
  begin
    If FindType(Items[j], ground) <> 0 then
    begin
		ClearJournal;
        NewMoveXY(GetX(FindType(Items[j],ground)), GetY(FindType(Items[j],ground)), false, 1, false);
        If not Dead then grab(findtype(Items[j], ground), 0);
		DropWeight;
        Wait(250);
		If InJournal('System: You don''t drag anything') > 0 then Exit;
    end;
  end;
end;
procedure ScanMobs;
var
j: integer;
begin
  Monstr[1] := $000D;  //Змейка
  Monstr[2] := $0010;  //Блуд елементаль
  Monstr[3] := $0018;  //lich
  Monstr[4] := $0032;  //bone magi
  FindDistance := 20;
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
        if FindType(Tools[2],Backpack) <> 0 then UOSay(Chr(39)+'pc heal self')
		else Bandages;
        wait(1000);
      end;
    end; 
  end;
  CheckHeal;
  FindGround;
end;
procedure GroundOre;
begin
	if FindType(Items[1],Ground) <> 0 then 
	begin
		NewMoveXY(GetX(finditem),GetY(finditem),false,1,true);
		grab(findtype(finditem, ground), 0);
	end;
	DropWeight;
end;
function MineIt(x,y:integer): Boolean;
var 
	j,t: integer;
	dirdone: boolean;
	time : integer;
	lastTime : TDateTime;
begin
	Result:=false;
	t := 0;
	for j:=1 to Length(directions) do
	begin
		lastTime := now;  
		time := 0;
		dirdone:=false;
		ScanMobs;
		CheckHeal;
		NewMoveXY(x,y,false,0,true);
		GroundOre;
		if (FindType(Tools[1],backpack) = 0) or (ObjAtLayer(RhandLayer) = 0) or (ObjAtLayer(LhandLayer) = 0) then
		begin
			NewMoveXY(chestx,chesty,false,1,true);
			CheckEquip;
			NewMoveXY(x,y,false,0,true);
		end;
		if (GetX(self) <> x) or (GetY(self) <> y) then NewMoveXY(x,y,false,0,true);
		AddToSystemJOurnal(j,' :Mining direction-'+directions[j]);
		wait(1000);
		UOSay('''pc mine '+directions[j]);
		Repeat
			wait(500);
			time := time + 500;
			if ((inJournalBetweenTimes(stopMsg, lastTime, now) >= 0) or (((now - lastTime) >= (1.0/86400)*60.0))) then 
			begin
				dirdone := true;
				wait(3600);
			end;
		until dirdone;
		DropWeight;
		ScanMobs;
		if Weight > 90 then StoreItems;
	end;
	NewMoveXY(x,y-1,false,0,true);
	dirdone:=false;
	lastTime := now;  
	AddToSystemJOurnal('Mining last point');
	UOSay('''pc mine s');
	Repeat
		wait(500);
		time := time + 500;
		if ((inJournalBetweenTimes(stopMsg, lastTime, now) >= 0) or (((now - lastTime) >= (1.0/86400)*60.0))) then 
		begin
			dirdone := true;
			wait(3600);
		end;
	until dirdone;
	DropWeight;
	ScanMobs;
	if Weight > 90 then StoreItems;
	Result:=true;
end;
begin
	maxlim:=GetStr(self)*3;	
	MinePointInit;
	InitToolItem;
	StoreItems;
	CheckEquip;
	Bandages;
	while not Dead do
	begin
		for i:=1 to Length(MinePoint) do
		begin
			FindGround();
			GroundOre();
			NewMoveXY(MinePoint[i].x,MinePoint[i].y,false,0,true);
			AddToSystemJournal('arrived to point #',i,':',MinePoint[i].x,',',MinePoint[i].y);
			if not MineIt(MinePoint[i].x,MinePoint[i].y) then wait(1000);
		end;
	end;
end.