program Kindling;
const
	dagger=$0F51;
	kryss=$1400; 
    katana=$13FF;
begin
	while not Dead do
	begin
        if FindType(dagger,backpack) > 0 then UseObject(FindType(dagger,backpack))
        else if FindType(katana,backpack) > 0 then UseObject(FindType(katana,backpack))
		else if FindType(kryss,backpack) > 0 then UseObject(FindType(kryss,backpack));
		WaitTargetTile(3274,GetX(self)-1,GetY(self),GetZ(self));
		wait(3200);
	end;
end.