program testlum;
const hat=$0F43;
var 
    Tile0: TStaticCell;
	i:integer;
begin
	if TargetPresent then CancelTarget;
	Tile0:=ReadStaticsXY(GetX(self),GetY(self)-1, WorldNum); 
	AddToSystemJournal('Сканируем Tile');
	AddToSystemJournal(Tile0);
	If Tile0.StaticCount > 1 then
	begin
		//for i:=0 to High(Tile0.Statics) do
		//begin
			i:=Low(Tile0.Statics);
			if TargetPresent then CancelTarget;
			UseObject(Findtype(hat,backpack));
			AddToSystemJournal(Tile0.Statics[i].Tile);
			targettotile(Tile0.Statics[i].Tile,GetX(self),GetY(self)-1,GetZ(self));
			//targettotile(3394,GetX(self),GetY(self)-1,GetZ(self));
			wait(3000);
		//end;
	end;
end.