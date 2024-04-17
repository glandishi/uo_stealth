Program Smelting;
const
	chest=$63F618E0;
	ore=$19B9;
	furnace=$0FB1;
	furn=$64BDED23;
procedure Smelt(what:cardinal);
begin
	if (FindType(what,backpack) <> 0) then
	begin
			UseObject(FindType(what,backpack));
			WaitTargetObject(furn);
			wait(3200);
	end
	else Exit;
end;
begin
    UOSay('''resend');
    wait(8000);
	//NewMoveXY(4582,3844,false,0,true);
	while FindType(ore,backpack) <> 0 do
	begin
		if FindType(ore,backpack) <> 0 then Smelt(ore)
		else 
		begin
			if FindType(ore,backpack) = 0 then
			begin
				UseObject(chest);
				wait(100);
				if FindType(ore,chest) <> 0 then MoveItem(Finditem, 0, backpack, 0, 0, 0)
				else Break;
			end;
		end;
	end;
end.