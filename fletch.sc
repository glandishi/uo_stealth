Program Fletching;
const
	shaft=$1BD4;
	feather=$1BD1;
	ingot=$1BF2;
	logs=$1BE0;
	dagger=$0F51;
procedure Craft(what:string);
var
	resource:cardinal;
begin
	case what of
		"arrow":
		begin
			resource:=feather;
			UseObject(FindType(shaft,backpack));
			WaitTargetObject(FindType(resource,Backpack));
			if MenuHookPresent then AutoMenu('Arrows','(first)')
			else
			begin			
				AutoMenu('Fletching','Arrows');
				AutoMenu('Arrows','(first)');
			end;
			CloseMenu;
		end;
		"bolt": 
		begin
			resource:=ingot;
			UseObject(FindType(shaft,backpack));
			WaitTargetObject(FindType(resource,Backpack));
			if MenuHookPresent then AutoMenu('Crossbow Bolts','(first)')
			else
			begin
				AutoMenu('Fletching','Crossbow Bolts');
				AutoMenu('Crossbow Bolts','(first)');
			end;
			CloseMenu;
		end;
		"shaft":
		begin
			resource:=logs;
			UseObject(FindType(dagger,backpack));
			WaitTargetObject(FindType(resource,Backpack));
			if MenuHookPresent then AutoMenu('Shafts','(first)')
			else 
				AutoMenu('Bowcraft','(first)');
				AutoMenu('Shafts','(first)');
			CloseMenu;
		end;
	end;
end;
begin
	if MenuHookPresent then CancelMenu;
	while FindType(shaft,backpack) <> 0 do
	begin
		Craft("shaft");
		wait(3200);
		if FindType(shaft,backpack) = 0 then
		begin
			CancelMenu;
			Break;
		end;
	end;
end.