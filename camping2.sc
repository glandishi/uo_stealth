program Camping;
const
	kindling=$0DE1;
	dagger=$0F51;
	chest=$641C90DF; //id сундука
	chestx=4581; // х координата точки рядом с сундуком
	chesty=3850; // у координата точки рядом с сундуком
	radius=5;
	arrlength=25;
var
	treeID:array[1..arrlength] of integer;
	maxlim: integer;
	itemlim: integer;
	start: integer;
procedure InitWeight;
begin
	start:=Weight;
	itemlim:=Round((maxlim-start)/5);
end;
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
	if GetDistance(chest) > 1 then MoveXY(chestx,chesty,false,1,false);
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
	for i:=1 to Length(treeID)-1 do
		Tiles:=GetStaticTilesArray(GetX(self)-radius, GetY(self)-radius, GetX(self)+radius, GetY(self)+radius, WorldNum, treeID[i], FoundTiles);
		if Tiles > 0 then
		begin
			NewMoveXY(FoundTiles[0].X,FoundTiles[0].Y,false,1,true);
			UseObject(FindType(dagger,backpack));
			WaitTargetTile(FoundTiles[0].Tile,FoundTiles[0].X,FoundTiles[0].Y,GetZ(self));
			wait(3200);
		end;
end;
begin
	maxlim:=(GetStr(self)*3);
	Store;
	InitTrees;
	InitWeight;
	while not Dead do
	begin
		repeat
			Chop;
			FindType(kindling,backpack);
		until FindQuantity >= itemlim;
		Store;
	end;
end.