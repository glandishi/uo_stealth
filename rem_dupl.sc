program RemoveDuplicates;
const 
	iRadiusSearch=40;
	chest=$63F89B04;
	chestx=1608;
	chesty=1429;
	lootbox=$63F89B04;
	resbox=$63DC87DE;
	weapbox=$63DC4CF5;
	shieldbox=$63DC4CF5;
	scim=$13B6;
	heater=$1B76;
	okmsg='Вы положили несколько бревен в сумку.|Вы рубите, но бревна у Вас не получаются.';
	stopMsg='Здесь нет больше дерева для вырубки.|Вы находитесь слишком далеко!';	
	qty=30;
type
  TTile = record
    Tile, X, Y, Z: Integer;
  end;

  TTileArray = array of TTile;
var 
	Tiles: TTileArray;
	FoundTilesArray : TFoundTilesArray;
	TempFoundTilesArray : array of TFoundTile;
	Items : array[1..13] of cardinal;
	Tools : array[1..3] of cardinal;
	Monstr : array[1..9] of cardinal;
	limit,h,b:integer;
	hatchet: cardinal;
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
function AreTilesEqual(const Tile1, Tile2: TTile): Boolean;
begin
  Result := (Tile1.X = Tile2.X) and (Tile1.Y = Tile2.Y);
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
    NewMoveXY(GetX(Corpse), GetY(Corpse), false, 1, false);
	AddToSystemJournal('Режем труп'); 
    UseObject(hatchet);
    Wait(500);
    WaittargetObject(Corpse);
    Wait(4000);   
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
procedure ScanMobs;
var
j: integer;
begin
  Monstr[1] := $0191;  //dryad
  Monstr[2] := $002F;  //reaper
  Monstr[3] := $0008;  //swamp tentacle
  Monstr[4] := $0032;  //?
  Monstr[5] := $001A;  //?
  Monstr[6] := $0018;  //?
  Monstr[7] := $0039;  //?
  Monstr[8] := $0012;  //?
  Monstr[9] := $00D4;  //grizzly
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
		if GetDistance(Monstr[j])>0 then NewMoveXY(GetX(Monstr[j]),GetY(Monstr[j]),true,0,true);
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
procedure CheckEquip;
begin
	if FindType(hatchet, backpack) = 0 then
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
			
			MoveItem(FindType(hatchet,resbox), 0,backpack, 0, 0, 0);
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
function Chop(tt,x,y:word):boolean;
var 
	ttile,treex,treey:word;
	treedone: boolean;
	time : integer;
	lastTime : TDateTime;
begin
	ttile:=tt;
	treex:=x;
	treey:=y;
	if TargetPresent then CancelTarget;
	NewMoveXY(treex, treey, false, 1, True);
	wait(1000);
	UseObject(Backpack); //Открываем Рюкзак;
	//AddToSystemJournal('Chopping ',ttile,':',treex,',',treey);
    If not Dead then
    begin
		treedone:=false;
		UseObject(hatchet);
		wait(100);
		if targetpresent then
		begin
			lastTime := now;  
			time := 0;
			ClearJournal;
			TargetToTile(ttile,treex,treey,GetZ(self));	
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
		CheckWeight;
		ScanMobs;
		Result:=true;
    end;  
end;    
function IsDuplicate(const Tile: TTile; const TileArray: TTileArray): Boolean;
var
  i: Integer;
begin
  Result := False;
  for i := 0 to High(TileArray) do
  begin
    if AreTilesEqual(Tile, TileArray[i]) then
    begin
      Result := True;
      Exit;
    end;
  end;
end;
procedure RemoveDuplicates(var TileArray: TTileArray);
var
  UniqueTiles: TTileArray;
  i: Integer;
begin
  SetLength(UniqueTiles, 0);
  for i := 0 to High(TileArray) do
  begin
    if not IsDuplicate(TileArray[i], UniqueTiles) then
    begin
      SetLength(UniqueTiles, Length(UniqueTiles) + 1);
      UniqueTiles[High(UniqueTiles)] := TileArray[i];
    end;
  end;
  TileArray := UniqueTiles;
end;
procedure PrintTileArray(const TileArray: TTileArray);
var
  i: Integer;
begin
  for i := 0 to High(TileArray) do
  begin
    AddToSystemJournal('Tile: ', TileArray[i].Tile, ', X: ', TileArray[i].X, ', Y: ', TileArray[i].Y, ', Z: ', TileArray[i].Z);
  end;
end;
procedure SearchPoint;
var
i, j : Integer;
iFoundTilesArrayCount : word;
iTempFoundTilesArrayCount : Integer;
begin
  for i:= 3276 to 3304 do
  begin
    iFoundTilesArrayCount := GetStaticTilesArray(GetX(Self), GetY(Self), (GetX(Self) + iRadiusSearch), (GetY(Self) - iRadiusSearch), WorldNum, i, FoundTilesArray);
    if iFoundTilesArrayCount > 0 then
    begin
      SetArrayLength(TempFoundTilesArray, Length(TempFoundTilesArray) + iFoundTilesArrayCount);
      for j := 0 to iFoundTilesArrayCount - 1 do
      begin
        TempFoundTilesArray[iTempFoundTilesArrayCount + j] := FoundTilesArray[j];
      end;
      iTempFoundTilesArrayCount := iTempFoundTilesArrayCount + iFoundTilesArrayCount;
    end;
  end;
  AddToSystemJournal('Найдено точек: ' + IntToStr(iTempFoundTilesArrayCount));
  AddToSystemJournal(Length(TempFoundTilesArray),':',TempFoundTilesArray);
end;
begin
	InitToolItem;
	NewMoveXY(chestx,chesty,false,1,true);
	SearchPoint;
	SetLength(Tiles, Length(TempFoundTilesArray)); // set the length of the array to the number of elements provided
	  // initialize the array with the provided elements
	for b:=0 to High(TempFoundTilesArray) do
	begin
		Tiles[b].Tile:=TempFoundTilesArray[b].Tile;
		Tiles[b].X:=TempFoundTilesArray[b].X;
		Tiles[b].Y:=TempFoundTilesArray[b].Y;
		Tiles[b].Z:=TempFoundTilesArray[b].Z;
	end;
	AddToSystemJournal('Original Tile Array:');
	PrintTileArray(Tiles);
	RemoveDuplicates(Tiles);
	AddToSystemJournal('Tile Array after removing duplicates:',Length(Tiles));
	AddToSystemJournal('Highest #:',High(Tiles));
	limit:=(GetStr(self)*3)-50;
	if FindType($0F47,backpack) <> 0 then hatchet := $0F47
	else if FindType($0F43,backpack) <> 0 then hatchet := $0F43
	else hatchet:=$0F43;
	//AddToSystemJournal('hatchet:',hatchet);
	CheckEquip;
	Bandages;
	Store;
	while not Dead do
	begin
		for h:=0 to Length(Tiles)-1 do
		begin
			CheckWeight;
			ScanMobs;
			AddToSystemJournal('#',h,':Chopping ',Tiles[h].Tile,'@',Tiles[h].X,',',Tiles[h].Y);
			if not Chop(Tiles[h].Tile,Tiles[h].X,Tiles[h].Y) then wait(1000);
		end;
	end;
end.