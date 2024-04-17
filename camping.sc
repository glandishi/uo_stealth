program Camping;
const
	kindling=$0DE1;
	dagger=$0F51;
	chest=$641C90DF;
	chestx=4581;
	chesty=3850;
	treex=4579;
	treey=3850;
	//treeID:array[1..25] of word = [3274,3275,3276,3280,3283,3393,3394,3395,3296,3299,3290,3277,3415,3416,3417,3418,3419,3438,3439,3440,3441,3442,3460,3461,3462];
	radius=5;
	arrlength=25;
var
	treeID:array[1..arrlength] of word;
	maxlim: integer;
procedure InitTrees;
begin
	treeID[1]:=3274;
	treeID[2]:=3275;
	treeID[3]:=3276;
	treeID[4]:=3280;
	treeID[5]:=3283;
	treeID[6]:=3393;
	treeID[7]:=3394;
	treeID[8]:=3395;
	treeID[9]:=3296;
	treeID[10]:=3299;
	treeID[11]:=3290;
	treeID[12]:=3277;
	treeID[13]:=3415;
	treeID[14]:=3416;
	treeID[15]:=3417;
	treeID[16]:=3418;
	treeID[17]:=3419;
	treeID[18]:=3438;
	treeID[19]:=3439;
	treeID[20]:=3440;
	treeID[21]:=3441;
	treeID[22]:=3442;
	treeID[23]:=3460;
	treeID[24]:=3461;
	treeID[25]:=3462;
end;
procedure Store;
begin
	if GetDistance(chest) > 1 then MoveXY(chestx,chesty,false,1,true);
	if FindType(kindling,backpack) > 0 then
	begin
		if not Dead then MoveItem(finditem, 0,chest, 0, 0, 0);
	end;
end;
procedure Chop;
var
	i:integer;
	Tiles: word;
	FoundTiles:TFoundTilesArray;
begin
	for i:=1 to Length(treeID) do
		begin
		Tiles:=GetStaticTilesArray(GetX(self)-radius, GetY(self)-radius, GetX(self)+radius, GetY(self)+radius, 0, treeID[i], FoundTiles);
		AddToSystemJournal('i:'+inttostr(i)+' Tiles:'+inttostr(Tiles));
		if Tiles > 0 then
		begin
			NewMoveXY(FoundTiles[0].X,FoundTiles[0].Y,false,1,true);
			AddToSystemJournal(inttostr(FoundTiles[0].X)+'-'+inttostr(FoundTiles[0].Y));
			UseObject(FindType(dagger,backpack));
			WaitTargetTile(FoundTiles[0].Tile,FoundTiles[0].X,FoundTiles[0].Y,GetZ(self));
			wait(3200);
		end;
		end;
end;
begin
	//InitTrees;
	maxlim:=GetStr(self)*3;
	while not Dead do
	begin
		NewMoveXY(chestx,chesty,false,1,true);
		repeat
			Chop;	
		until Weight > maxlim;
		Store;
	end;
end.