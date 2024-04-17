Program Blacksmithing;
const
	ingot=$1BF2;
	hammer=$13E4;
procedure Craft(what:string);
begin
	Case what of
		'spear' :
		begin
			UseObject(FindType(hammer,backpack));
			WaitTargetObject(FindType(ingot,Backpack));
			if MenuHookPresent then AutoMenu('Spears','(first)')
			else
			begin			
				AutoMenu('Blacksmithing','Weapons');
				AutoMenu('Weapons','Spears');
				AutoMenu('Spears','(first)');
			end;
			CloseMenu;
		end;
		'gorget' : 
		begin
			UseObject(FindType(hammer,backpack));
			WaitTargetObject(FindType(ingot,Backpack));
			if MenuHookPresent then AutoMenu('Crossbow Bolts','(first)')
			else
			begin
				AutoMenu('Blacksmithing','Armor');
				AutoMenu('Armor','Plate');
				AutoMenu('Plate','(first)');
			end;
			CloseMenu;
		end;
		else AddToSystemJournal('Unknown option');
	end;
end;
begin
	UOSay('''craftcreate 0');
	if MenuHookPresent then CancelMenu;
	while FindType(ingot,backpack) <> 0 do
	begin
		Craft('spear');
		wait(3200);
		if FindType(ingot,backpack) = 0 then
		begin
			CancelMenu;
			Break;
		end;
	end;
end.