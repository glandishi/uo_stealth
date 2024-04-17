Program Blacksmithing2;
const
	ingot=$1BF2;
	hammer=$13E4;
	fish=$097B;
	orebox=$6408E966;
	okmsg='У Вас не получилось изготовить предмет|Вы изготавливаете и сразу же уничтожаете предмет.';
var resource:cardinal;
procedure Craft(what:string);
begin
	Case what of
		'spear' :
		begin
			UseObject(FindType(hammer,backpack));
			WaitTargetObject(resource);
			if MenuHookPresent then AutoMenu('Spears','(first)')
			else
			begin			
				AutoMenu('Blacksmithing','Weapons');
				AutoMenu('Weapons','Spears');
				AutoMenu('Spears','(first)');
			end;
			CloseMenu;
		end;
		'katana' :
		begin
			UseObject(FindType(hammer,backpack));
			WaitTargetObject(resource);
			if MenuHookPresent then AutoMenu('Swords','Katana')
			else
			begin			
				AutoMenu('Blacksmithing','Weapons');
				AutoMenu('Weapons','Swords');
				AutoMenu('Swords','Katana');
			end;
			CloseMenu;
		end;
		'gorget' : 
		begin
			UseObject(FindType(hammer,backpack));
			WaitTargetObject(resource);
			if MenuHookPresent then AutoMenu('Plate','(first)')
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
	resource:=FindType(ingot,backpack);
	UOSay('''craftcreate 0');
	if MenuHookPresent then CancelMenu;
	if FindType(ingot,backpack) = 0 then
	begin
		UseObject(orebox);
		wait(150);
		MoveItem(FindType(ingot,orebox), 0,backpack, 0, 0, 0);
	end;
	while FindType(ingot,backpack) <> 0 do
	begin
		Craft('gorget');
		wait(4000);
		UOSay('''pc craft 20000000');
		wait(5000);
		if FindType(fish, backpack) <> 0 then UseObject(finditem)
		else
		begin
			UseObject(orebox);
			if FindType(fish, orebox) <> 0 then UseObject(finditem);
		end;
		wait(1800000);
		if FindType(ingot,backpack) = 0 then Break;
	end;
end.