Program Sparring;
const
	opponent=$083217DE;  
	bandage=$0E21;
	qty=600;
	main_chest=$75FC0EA6;
	band_pouch=$63AD9A42;
	armor_box=$758B70B8;
	mace=$0DF2;
	dagger=$0F51;
	knife=$0EC4;
	kite=$1B74;
	heater=$1B76;
	food=$097B;
	wood=$1B7A;
	fightx=1603;
	fighty=1432;
	breakx=1602;
	breaky=1433;
//left 1603 1433
//right 1606 1430
var
	moved:boolean;
procedure Heal(target: cardinal);
begin
	if not moved then
	begin
	TargetToObject(target);
	//UOSay(Chr(39)+'pc heal lt');
	UOSay(Chr(39)+'pc heal self');
	wait(1000);
	end
    else if (moved) and (HP < MaxHP) then
    begin    
    UOSay(Chr(39)+'pc heal self');
	wait(1000);
    end;
end;
procedure Bandages(chest: cardinal;pouch: cardinal);
begin
	FindType(bandage,Backpack);
	if ((FindType(bandage,Backpack) = 0) or (FindQuantity < 10)) then
	begin
		if GetDistance(chest) > 1 then
		begin
			MoveXY(GetX(chest),GetY(chest),false,1,false);
			moved:=true;
			wait(100);
		end;
		UseObject(chest);
		wait(1500);
		UseObject(pouch);
		wait(350);
		MoveItem(FindType(bandage, pouch), qty, backpack, 0, 0, 0);
		wait(350);	
	end;
end;
procedure HealthCheck(target:cardinal);
begin
	if GetHP(target) < 20 then
	begin
		MoveXY(breakx,breaky,true,0,true);
		moved:=true;
	end;
end;
procedure FBreak(target: cardinal);
begin
	if ((GetDistance(target) > 1) and not (moved)) then
	begin
		wait(3000);
	end
	else if ((GetDistance(target) > 1) and (moved)) then
	begin
		if GetHP(target) > GetMaxHP(target)/2 then
		begin
			MoveXY(fightx,fighty,false,0,false);
			moved:=false;
		end;
	end;
end;
procedure CheckWeapon(weap:cardinal);
begin
	if ObjAtLayer(RhandLayer) = 0 then
	begin
		if FindType(weap,Backpack) > 0 then
		begin
			Equip(RhandLayer,FindType(weap,Backpack));
		end
		else
		begin
			if GetDistance(armor_box) > 1 then MoveXY(GetX(armor_box),GetY(armor_box),false,1,false);
			UseObject(armor_box);
			wait(100);
			MoveItem(FindType(weap,armor_box), 0, backpack, 0, 0, 0);
			wait(100);
			Equip(RhandLayer,FindType(weap,Backpack));
			wait(100);
			MoveXY(fightx,fighty,false,0,false);
		end;
	end;
end;
procedure CheckShield(shield:cardinal);
begin
	if ObjAtLayer(LhandLayer) = 0 then
	begin
		if FindType(shield,Backpack) > 0 then
		begin
			Equip(LhandLayer,FindType(shield,Backpack));
		end
		else
		begin
			MoveXY(GetX(armor_box),GetY(armor_box),false,1,false);
			UseObject(armor_box);
			wait(100);
			MoveItem(FindType(shield,armor_box), 0, backpack, 0, 0, 0);
			wait(100);
			Equip(LhandLayer,FindType(shield,Backpack));
			wait(100);
			MoveXY(fightx,fighty,false,0,false);
		end;
	end;
end;
begin
	moved:=false;  
    //if not moved then MoveXY(GetX(opponent),GetY(opponent),false,1,false);
	while true do
	begin
		if ObjAtLayer(RhandLayer) = 0 then
		begin
			if ((GetStr(self) >= 130) and (GetStr(self) < 150)) then CheckWeapon(mace)
			else if ((GetSkillValue('Swordsmanship') < 100) and (GetStr(self) >= 150)) then CheckWeapon(knife)
			else CheckWeapon(dagger);
		end;
		if ObjAtLayer(LhandLayer) = 0 then
		begin
			if GetStr(self) >= 120 then CheckShield(heater)
			else CheckShield(kite);
		end;
		if HP > MaxHP/2 then attack(opponent);		
		Heal(opponent);
		Bandages(main_chest,band_pouch);
		wait(500);
		//CheckShield(heater);		
		HealthCheck(opponent);
		FBreak(opponent);
	end;
end.