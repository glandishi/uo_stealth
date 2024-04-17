program Lumber;
const
	//treeID: array[1..26] of word = [3274,3275,3276,3280,3283,3393,3394,3395,3296,3299,3290,3277,3415,3416,3417,3418,3419,3438,3439,3440,3441,3442,3438,3460,3461,3462];
	chest=$75670D92;
	chestx=1609;
	chesty=1434;
	lootbox=$63DC87DF;
	resbox=$63DC87DE;
	okmsg='Вы положили несколько бревен в сумку.|Вы рубите, но бревна у Вас не получаются.';
	stopMsg='Здесь нет больше дерева для вырубки.|Вы находитесь слишком далеко!';	
	radius=2;
	qty=20;
	posx1=1609;        // Начало Х;
	posx2=1629;        // Конец Х;
	posy1=1400;        // Начало Y;
	posy2=1420;        // Конец Y;
type
	LumberTile = Record
	x, y : Integer;
end;
var
	Items : array[1..13] of cardinal;
	Tools : array[1..3] of cardinal;
	Monstr : array[1..8] of cardinal;
	limit,h:integer;
	LumberPoints : array[1..9] of LumberTile;
	ChPosX,ChPosY: word;
procedure InitLumberPoints;
begin
    LumberPoints[1].x := 1608;
    LumberPoints[1].y := 1436;
	LumberPoints[2].x := 1603;
    LumberPoints[2].y := 1440;
	LumberPoints[3].x := 1603;
    LumberPoints[3].y := 1445;
    LumberPoints[4].x := 1608;
    LumberPoints[4].y := 1448;
	LumberPoints[5].x := 1608;
    LumberPoints[5].y := 1456;
	LumberPoints[6].x := 1604;
    LumberPoints[6].y := 1482;
    LumberPoints[7].x := 1612;
    LumberPoints[7].y := 1462;
	LumberPoints[8].x := 1616;
    LumberPoints[8].y := 1459;
	LumberPoints[9].x := 1616;
    LumberPoints[9].y := 1465;
end;
procedure InitToolItem;
begin
    Tools[1] := $0F47; // hatchet
    Tools[2] := $0E21; // bandage
    Tools[3] := $097B; // food
	Items[1] := $1BE0; // logs
	Items[2] := $0F8D; // spider silk
	Items[3] := $0F8C; // sulfur
	Items[4] :=$0F85; // ginseng
	Items[5] :=$0F84; // garlic
	Items[6] :=$0F86; // mandrake
	Items[8] :=$0F88; // nightshade
	Items[9] :=$0EED; // gold
	Items[10] :=$0F7B; // blood moss
	Items[11] :=$0F7A; // black pearl
	Items[12] :=$1BD1; // feathers
	Items[13] :=$1078; // skin
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
procedure CheckHeal;
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
procedure Store;
var i:integer;
begin
	if GetDistance(chest) > 1 then MoveXY(chestx,chesty,false,1,true)
	else if GetDistance(chest) = -1 then MoveXY(chestx,chesty,false,1,true);
	UseObject(chest);
	wait(350);
	UseObject(lootbox);
	for i:=Low(Items) to High(Items) do
	begin
		if FindType(Items[i],backpack) > 0 then
		begin
			if not Dead then MoveItem(finditem, 0,lootbox, 0, 0, 0);
		end;
	end;
end;
procedure DropWeight;
var
	k:integer;
begin
	if Weight > limit then
	begin
		k:=Round((Weight-limit)/3); 
		MoveItem(FindType(Items[1],backpack), k,Ground, 0, 0, 0);
	end;
end;
procedure CheckWeight;
begin
	if Weight > (limit+30) then DropWeight;
	if Weight > limit then Store;
end;
procedure ScanMobs;
var
j: integer;
begin
  Monstr[1] := $0191;  //dryad
  Monstr[2] := $002F;  //reaper
  Monstr[3] := $0008;  //?
  Monstr[4] := $0032;  //?
  Monstr[5] := $001A;  //?
  Monstr[6] := $0018;  //?
  Monstr[7] := $0039;  //?
  Monstr[8] := $0012;  //?
  FindDistance := 15;
  FindVertical := 255;
  begin
    for j := 1 to High(Monstr) do
    while FindType(Monstr[j], Ground) > 0 do
    begin
      Monstr[j]:=finditem;
      While (GetHp(Monstr[j])>0) do
      begin
		if Weight > limit then MoveItem(FindType(Items[1],backpack), 8,Ground, 0, 0, 0);
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
end;
function Chop:boolean;
var 
    Tile0: TStaticCell;
    mytile,hatchet: Word;
	treedone: boolean;
	time : integer;
	lastTime : TDateTime;
begin
	if FindType($0F47,backpack) <> 0 then hatchet := finditem
	else if FindType($0F43,backpack) <> 0 then hatchet := finditem;
	UseObject(Backpack); //Открываем Рюкзак;
	Tile0:=ReadStaticsXY(ChPosX, ChPosY, WorldNum); 
	//AddToSystemJournal('Сканируем Tile');
	AddToSystemJournal(Tile0);
    If Tile0.StaticCount > 1 then
    begin
		mytile := Tile0.Statics[0].Tile;// 1 или ноль , если не рубит . 
		if (mytile > 3275) and (mytile < 3305) then
		begin
			treedone:=false;
			//AddToSystemJournal('c:',c);
			AddToSystemJournal('Tile найден');
			Addtosystemjournal('Tile   = '+IntToStr(mytile));
			Addtosystemjournal('Layers = '+IntToStr(Tile0.StaticCount));
			AddToSystemJournal('Направляемся на координату||'+ IntToStr(ChPosX)+('||')+ IntToStr(ChPosY)+('||'));
			NewMoveXY(ChPosX, ChPosY, false, 1, True );
			if FindType(hatchet, Backpack) <> 0 THEN  
			begin
				If TargetPresent Then CancelTarget;
				begin	
					useobject(FindType(hatchet,backpack));
					WaitForTarget(3000);
					AddToSystemJournal('Рубим');
				end;
			end;
			if targetpresent then
			begin
				lastTime := now;  
				time := 0;
				ClearJournal;
				TargetToTile(mytile,ChPosX,ChPosY,GetZ(self));	
				Repeat
					wait(500);
					time := time + 500;
					if not InJournal(okmsg) >= 0 then treedone;
					if ((inJournalBetweenTimes(stopMsg, lastTime, now) >= 0) or (((now - lastTime) >= (1.0/86400)*60.0))) then 
					begin
						treedone := true;
						wait(3600);
					end;
				until treedone;
			end;
		end;
		CheckWeight;
		ScanMobs;
		Result:=true;
    end;  
end;    


procedure FindGround;
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
	while i < High(Items)+1 do
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
  for j := 1 to High(Items) do
  begin
    If FindType(Items[j], ground) <> 0 then
    begin
		ClearJournal;
        if Weight < limit then NewMoveXY(GetX(FindType(Items[j],ground)), GetY(FindType(Items[j],ground)), false, 1, false)
		else 
		begin
			DropWeight;
			NewMoveXY(GetX(FindType(Items[j],ground)), GetY(FindType(Items[j],ground)), false, 1, false);
		end;
        If not Dead then grab(findtype(Items[j], ground), 0);
		DropWeight;
        Wait(250);
		If InJournal('System: You don''t drag anything') > 0 then Exit;
    end;
  end;
  //DropWeight;
end;

begin
	InitToolItem;
	limit:=(GetStr(self)*3)-30;
	Store;
	MoveXY(posx1,posy1,false,0,true);
	While not Dead do
	begin
		for ChPosX:= Posx1 to Posx2 do                                            //
		begin                                                                     //
			for ChPosY:= Posy1 to Posy2 do
			begin
				if not Chop then wait(1000);
				ScanMobs;
				CheckWeight;
				CheckHeal;
			end;
		end;
	end;
end.