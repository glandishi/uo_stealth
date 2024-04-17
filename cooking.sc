Program Cooking;
const
	fish=$097A;
	bird=$09B9;
	kindling=$0DE1;
	fire=$0DE3;
	chest=$64138EA1;
var
	camp: boolean;
	food: cardinal;
procedure Camping;
begin
	if FindType(fire,Ground) = 0 then
	begin
		UseObject(chest);
		wait(100);
		UseObject(FindType(kindling,chest));
		wait(3200);
	end;
end;
procedure Cook(what:cardinal);
begin
	if (FindType(what,backpack) <> 0) and (FindType(fire,Ground) <> 0) then
	begin
			UseObject(FindType(what,backpack));
			WaitTargetGround(fire);
			wait(3200);
	end
	else Exit;
end;
procedure Separate(what:cardinal);
begin
	if (FindType(what,chest) <> 0 ) and (FindQuantity > 0) then
	begin
		UseObject(chest);
		wait(100);
		MoveItem(Finditem, 1, backpack, 0, 0, 0);
		wait(100);
	end;
end;
begin
	food:=fish;
	UseObject(chest);
	wait(100);
	while FindType(kindling,chest) <> 0 do
	begin
		if FindType(fire,Ground) = 0 then
		begin
			if FindType(kindling ,chest) <> 0 then Camping
			else Break;
		end
		else
		begin
			if FindType(food,backpack) <> 0 then Cook(food)
			else 
			begin
				if FindType(food,chest) <> 0 then
				begin
					Separate(food);
					Cook(food);
				end
				else Break;
			end;
		end;
	end;
end.