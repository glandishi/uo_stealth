Program Magik_Pole_v01;
VAR 
food,Corpse, MyWeight, cpos, domx,domy, healerx,healery, StartX, StartY, EndX, EndY,kx,ky: integer; 
MainBox,LootBox,ToolsBox,MainArmorBox: integer;
RegSag: integer;
Armor: array [1..7] of Record 
    atype,box: integer;
    name: string;
    el,use: Byte;
end;
Tools: array[1..4] of Record 
    ttype,kol,box: integer;
    name: string;
end;
Regs: array[1..2] of Record
    rtype: array [1..4] of integer;
    minKol,maxKol: integer;
    name: string;
end;
Loot: array[1..8] of Record 
    ltype,lcount: integer;
    name: string;
end;

const
AnkUse= true; // Использовать крест;
Ank= $44FFDE7B; // Указываем ID Креста;



/////////////////////////////////////////////////
//     Основные процедуры скрипта              //
/////////////////////////////////////////////////
procedure ConfigSet;
begin
food := $637C0856;
//REGS POS
RegSag := 1;
//Ginseng 
Regs[1].minKol := 35; Regs[1].maxKol := 265; Regs[1].Name := 'Ginseng';
Regs[1].rtype[1] := $0F85; // stadiya 1 $0026 цвет
Regs[1].rtype[2] := $18E9; // stadiya 2 $0026 цвет
Regs[1].rtype[3] := $18E9; // stadiya 3 $0000 цвет
Regs[1].rtype[4] := $18EB; // stadiya 4 $0000 color
Regs[2].minKol := 20; Regs[2].maxKol := 60; Regs[2].Name := 'Хлопок';
Regs[2].rtype[1] := $0DF9; // stadiya 1 $0026 цвет
Regs[2].rtype[2] := $0C51; // stadiya 2 $0026 цвет
Regs[2].rtype[3] := $0C51; // stadiya 3 $0000 цвет
Regs[2].rtype[4] := $0C54; // stadiya 4 $0000 color


domx:=1206;
domy:=1743;
healerx:=1407;
healery:=431;
StartX:=1209;
StartY:=1712;
//polovina polya
EndX:=1235; 
EndY:=1735;
//vse pole
//EndX:=1216; 
//EndY:=1903;
// tools
AddToSystemJournal('Грузим Итемы для Использование(Тоолз)');
Tools[1].ttype := $1400; Tools[1].kol := 1; Tools[1].name := 'Kryss'; 
Tools[2].ttype := $0E21; Tools[2].kol := 15; Tools[2].name := 'Bandage';
Tools[3].ttype := $0F39; Tools[3].kol := 1; Tools[3].name := 'shovel'; Tools[3].box:= $60F26E6B;
Tools[4].ttype := Regs[RegSag].rtype[1]; Tools[4].kol := Regs[RegSag].minKol; Tools[4].name := 'RegPosadki'; 
// armor
MainBox := $75FC3782;
LootBox := $74DB5893;
ToolsBox := $60F26E6B;
AddToSystemJournal('Грузим Итемы для Использование(Армор) и сундуки');
MainArmorBox := $60F26E50;
Armor[1].atype := $13C4; Armor[1].box := $60E61F69; Armor[1].use :=5; Armor[1].el := TorsoLayer; Armor[1].name := 'chain tunic';
Armor[2].atype := $13C3; Armor[2].box := $60E61F66; Armor[2].use :=5; Armor[2].el := LegsLayer; Armor[2].name := 'chain leggings';
Armor[3].atype := $13BB; Armor[3].box := $60E61F6B; Armor[3].use :=5; Armor[3].el := HatLayer; Armor[3].name := 'chain coif';
Armor[4].atype := $13EF; Armor[4].box := $60E61F67; Armor[4].use :=5; Armor[4].el := ArmsLayer; Armor[4].name := 'ringmail sleeves';
Armor[5].atype := $13F2; Armor[5].box := $60E61F68; Armor[5].use :=5; Armor[5].el := GlovesLayer; Armor[5].name := 'ringmail gloves';
Armor[6].atype := $1400; Armor[6].box := $7F8F5931; Armor[6].use :=5; Armor[6].el := RhandLayer; Armor[6].name := 'Kryss';
Armor[7].atype := $1B74; Armor[7].box := $60E61F6A; Armor[7].use :=5; Armor[7].el := LhandLayer; Armor[7].name := 'Kite shield';
//

// loot
AddToSystemJournal('Грузим Итемы для лута');
Loot[1].ltype := Regs[RegSag].rtype[1]; Loot[1].lcount := 0; Loot[1].name := Regs[RegSag].name;
Loot[2].ltype := $1078; Loot[2].lcount := 0; Loot[2].name := 'a piles of hides';
Loot[3].ltype := $0F7B; Loot[3].lcount := 0; Loot[3].name := 'bloodmoss'; 
kx := StartX;
ky := StartY;



end;

Procedure showInfo;
var
j: integer;
info: string;
begin
    for j := 1 to 3 do 
    begin
        info := info + Loot[j].name + ': '+ intToStr(Loot[j].lcount)+' | ';
    end;
    AddToSystemJournal(info);
end;

// MaxWight 
Procedure MyStr();
var 
    a: integer;
begin
a:= Str;
//AddToSystemJournal('Сила чара>>>>>>: ' + IntToStr(Str));
//AddToSystemJournal('Ловкость чара>>: ' + IntToStr(Dex));
//AddToSystemJournal('Интеллект чара>: ' + IntToStr(Int));
MyWeight:= (a * 3) - 30;
AddToSystemJournal('Ваш вес >>>>>>>>>' + IntToStr(Weight));
AddToSystemJournal('Лимит веса >>>>>>' + IntToStr(MyWeight));
end;

// Equip
procedure SkanEquip;
var j,atSelf,inPack: integer;
begin
    AddToSystemJournal('Проверяем екипировку');
    if not dead then 
    begin
        useobject(BackPack);
        wait(320);
        for j:= 1 to 7 do 
        begin
            atSelf:= FindType(Armor[j].atype,self);
            inPack:= FindType(Armor[j].atype,backpack);
            if (atSelf = 0) and (Armor[j].use > 0) then 
            begin
                if (inPack = 0) and (Armor[j].use > 0) then 
                begin
                    NewMoveXY(domx, domy, false, 1, True);
                    useobject(MainBox);
                    wait(320);
                    useobject(MainArmorBox);
                    wait(330);
                    useobject(Armor[j].box);
                    wait(320);
                    if findType(Armor[j].atype,Armor[j].box) > 0 then
                    begin
                        MoveItem(finditem, 0, backpack, 0, 0, 0);
                    end
                    else 
                    begin
                        Armor[j].use:= Armor[j].use - 1;
                        if( Armor[j].use < 1) then 
                        begin
                            AddToSystemJournal('Закончились: '+Armor[j].name);                    
                            if (j=6) or (j=7) then 
                            begin
                            SetARStatus(false);
                            Disconnect();
                            end;
                        end;                            
                    end;
                    wait(320);
                end;
                Equip (Armor[j].el, finditem); 
                Wait(320);
            end;
        end;
    end;
end;

procedure getTools;
var 
j,iself, ipack,icount,tt,UseBox: integer;
begin
    NewMoveXY(domx, domy, false, 1, true);
    for j:= 2 to 4 do 
    begin
        useobject(backpack);
        wait(200);
        useobject(MainBox);
        wait(200);
        useobject(ToolsBox);
        wait(200);
        UseBox:=ToolsBox;
        if(Tools[j].box > 0) then 
        begin
            useobject(Tools[j].box);
            wait(200);
            UseBox:=Tools[j].box;
        end;
        iself := FindType(Tools[j].ttype,self);
        ipack := FindType(Tools[j].ttype,backpack);
        tt:=Tools[j].kol; 
        icount:=FindFullQuantity; 
        if (FindType(Tools[j].ttype,self) = 0) and ((FindType(Tools[j].ttype,backpack) = 0) or ( FindFullQuantity < Tools[j].kol )) then 
        begin
            AddToSystemJournal('Ищем вещь '+Tools[j].name);
            tt := FindFullQuantity;
            FindType(Tools[j].ttype, UseBox);
            If finditem = 0 then 
            begin 
                AddToSystemJournal('Вещь '+Tools[j].name+' не найдена.');
                NewMoveXY(kx, ky, false, 0, true);
                exit; 
            end;
            grab(finditem,(Tools[j].kol-tt));
            wait(400);
        end;
    end;
    //NewMoveXY(WarPos[cpos].x, WarPos[cpos].y, false, 0, true);
end;
////////////////

/////Проверка предметов ///
procedure checkToots;
var 
j,vself,vpack: integer;
begin
    for j:= 2 to 4 do 
    begin
        useobject(backpack);
        wait(100);
        vself := FindType(Tools[j].ttype, self);
        vpack := FindType(Tools[j].ttype, backpack);
        If (FindType(Tools[j].ttype, backpack) = 0) and (FindType(Tools[j].ttype, self) = 0) then 
         begin 
            If not Dead then 
            begin 
                AddToSystemJournal('Отсутствует вещь - ' + Tools[j].name);                
                getTools();
                SkanEquip();
                NewMoveXY(kx, ky, true, 0, true);
            end; 
         end;
    end;
end;
//////////////////

procedure LootGraund;
var 
j : integer;
info : string;
begin
    info := 'Статистика ';
    for j := 1 to 3 do 
    begin
        If FindTypeEx(Loot[j].ltype,0, Ground,false) > 0 then
        begin
            If not Dead then
            begin
                AddToSystemJournal('Лутим '+Loot[j].name+ ': '+ intToStr(FindQuantity));
                MoveItem(finditem, 0, backpack, 0, 0, 0);
                Loot[j].lcount := Loot[j].lcount + FindQuantity;
                Wait(350);
            end;
        end;
        info := info + Loot[j].name + ': '+ intToStr(Loot[j].lcount)+' | ';
    end;
     AddToSystemJournal(info);
end;

procedure MyDrop;
var 
w,Dcount: integer;
begin
    MyStr();
    while MyWeight < (Weight - 10) do 
    begin
        Dcount:=0;
        w:= Weight - MyWeight + 10;
        while w > 4 do 
        begin
            Dcount:= (Dcount +1);
            w:= w - 5;
        end;
        if findtype(Loot[1].ltype,backpack) >0 then 
        begin
            Loot[1].lcount := (Loot[1].lcount - Dcount);
            Drop(finditem, Dcount, getX(self), getY(self), getZ(self)); 
            wait(320);
        end;
    end;
end;

procedure store;
var j,tt: integer;
begin
    MyDrop;
    NewMoveXY(domx, domy, false, 1, True);
    useobject(MainBox);
    wait(2000);
    for j:=1 to 3 do 
    begin
        if (findtype(Loot[j].ltype,backpack) > 0 ) then 
        begin
            tt := FindQuantity;
            if j = 1 then 
            begin
                tt := tt - Regs[RegSag].minKol;                
            end;
            if tt < 0 then
            begin
                tt:=0;
            end;
            AddToSystemJournal('Выкладываем '+Loot[j].name+ ': '+ intToStr(tt));
            Loot[j].lcount := Loot[j].lcount + tt;
            MoveItem(finditem, tt, LootBox, 0, 0, 0);
            Wait(350);
        end;
    end;
    for j:=1 to 2 do
    begin
        if not ( j = RegSag ) and (findtype(Regs[j].rtype[1],backpack) > 0) then
        begin
            tt := FindQuantity;
            MoveItem(finditem, tt, LootBox, 0, 0, 0);
            Wait(350);
        end;
    end;
    showInfo();
    useobject(food);    
end;

/// heal self //
procedure Heal;
begin
    while (life < (maxHP-10)) and (not Dead) do 
    begin
        AddToSystemJournal('Лечимся');
        if not Dead then checkToots;        
        UOSay(Chr(39)+'pc heal self');
        Wait(1075);
    end;
end;

/// heal self //
procedure Heal2;
begin
    if (life < (maxHP-10)) and (not Dead) then 
    begin 
        AddToSystemJournal('Лечимся');
        UOSay(Chr(39)+'pc heal self');
        Wait(1075);
    end;
end;

procedure CutReg;
Var
    Nog,inPack,j,atSelf,MyX,MyY,RegFind: Integer;
Begin
    if not Dead then
    begin
        FindDistance := 0;
        for j:= 1 to 2 do 
        begin
            while FindType(Regs[j].rtype[4], ground) > 0 do 
            begin
                //AddToSystemJournal('FindDistance:'+IntToStr(FindDistance));
                RegFind := FindItem;
                //AddToSystemJournal('Рег поспел '+ IntToStr(RegFind));
                inPack:=FindType(Tools[1].ttype, backpack);
                atSelf:=FindType(Tools[1].ttype, self);
                If (inPack > 0) or (atSelf > 0) then
                begin
                    //AddToSystemJournal('Собираем Урожай');
                    if (inPack > 0) then  Nog := inPack;
                    if (atSelf > 0) then  Nog := atSelf;
                    //Nog := FindItem;
                    If TargetPresent Then CancelTarget;
                    while (not TargetPresent) and (not Dead) do 
                    begin
                        UseObject(Nog);
                        wait(500);
                    end;
                    WaitForTarget(5000);
                    WaitTargetObject(RegFind);
                    wait(2800);                
                end;
                if (inPack = 0) and (atSelf = 0) then 
                begin
                    MyX:= GetX(self);
                    MyY:= GetY(self);
                    SkanEquip;
                    NewMoveXY(MyX,MyY, false, 1, True);
                end;
            end;
        end;
    end;
end;

procedure SadimReg;
var
inPack,atSelf,Reg,lopata,MyX,MyY: Integer;
begin
    if not Dead then
    begin
        If not(isDead(self)) then checkToots();
        if FindType(Regs[RegSag].rtype[1], backpack) > 0 then 
        begin            
            Reg := FindItem;
            //AddToSystemJournal('Рег для посадки '+ IntToStr(Reg));
            inPack:=FindType(Tools[3].ttype, backpack);
            atSelf:=FindType(Tools[3].ttype, self);
            If (inPack > 0) or (atSelf > 0) then
            begin
                //AddToSystemJournal('Сажаем Рег');                
                if (inPack > 0) then  lopata := inPack;
                if (atSelf > 0) then  lopata := atSelf;
                //Nog := FindItem;
                If TargetPresent Then CancelTarget;
                while (not TargetPresent) and (not Dead) and (lopata > 0) do 
                begin
                    lopata :=UseType(Tools[3].ttype,0);
                    if ( lopata = 0 ) then
                    begin
                        If TargetPresent Then CancelTarget;
                        inPack:=0;
                        atSelf:=0;
                    end;
                    wait(500);
                end;
                if lopata > 0 then 
                begin
                    WaitForTarget(5000);
                    WaitTargetObject(Reg);
                    wait(200);
                    WaitForTarget(2000);
                    TargetToXYZ(kx, ky, 0);
                    wait(2500);
                end;                
            end;
            if (inPack = 0) and (atSelf = 0) then 
            begin
                MyX:= GetX(self); MyY:= GetY(self);
                checkToots;
                NewMoveXY(MyX,MyY, false, 1, True);
            end;            
        end;
    end;
end;

procedure Sagat;
var i,rtype1,rtype2,rtype3:integer;

begin
    FindDistance:=0;
    FindVertical:=6;
    NewMoveXY(kx, ky, true, 0, false);
    if FindType(Regs[RegSag].rtype[4],ground) > 1 then 
    begin
        CutReg();
        wait(200);
    end;
    FindDistance:=0;
    FindVertical:=6;
    rtype1 := FindType(Regs[RegSag].rtype[1],ground);
    rtype2 := FindType(Regs[RegSag].rtype[2],ground);
    rtype3 := FindType(Regs[RegSag].rtype[3],ground);
    if (rtype1 = 0) and (rtype2 = 0) and (rtype3 = 0) then 
    begin
        SadimReg();
        wait(200);
    end;

    
end;

procedure Skan;
var j,myX,myY: integer;
begin
    if Weight > MyWeight then 
    begin
        myX:= getX(self);
        myY:= gety(self);
        store;
        Disconnect;
        NewMoveXY(myX, myY, True, 0, True);
        LootGraund;
    end;
    FindType(Regs[RegSag].rtype[1], backpack);
    If (FindQuantity > Regs[RegSag].maxKol) then
    begin
        store;
        Disconnect;
    end;
end;

Procedure GotoHealer();
begin
if Dead = true then
begin
    Disconnect;
    NewMoveXY(1321, 1751, true, 0, true );
    NewMoveXY(1315, 1751, true, 0, true );
    NewMoveXY(3651, 2491, true, 0, true );
    repeat        
        NewMoveXY(healerx, healery, true, 0, true );
        if AnkUse = true then
        begin
            useobject(Ank);
            wait(3000);
        end;
    until Dead = false
    NewMoveXY(3609, 2486, true, 0, true );
    NewMoveXY(3609, 2485, true, 0, true );
    NewMoveXY(1298, 1796, true, 0, true );
    NewMoveXY(1250, 1825, true, 0, true );
    NewMoveXY(1254, 1746, true, 0, true );
    NewMoveXY(1238, 1740, true, 0, true );
    NewMoveXY(domx, domy, true, 1, true );
    if not Dead then checkToots;
    if not Dead then Heal();
    wait(6000);
    if not Dead then Heal();
    if not Dead then SkanEquip;    
    NewMoveXY(kx, ky, true, 0, true);
end;
end;

{procedure CheckJournal; 
var
   temp: String; 
begin 
   if (InJournal('Вы посадили растение!') <> -1) or (InJournal('Растения здесь не вырастут') <> -1) or (InJournal('Здесь похоже не вспахано') <> -1) then 
   begin 
      Next; 
   end; 
   if (InJournal('You have gained a little karma.') <> -1) then
   begin
      FindMobs;
   end;
   if InJournal('У вас нет бинтов') <> -1 then begin
      AddToSystemJournal('Открываем пак'); 
      UseObject(backpack); 
      Wait(2000);
      CheckEquip;
      Dig;
   end;
   if InJournal('Призракам это не доступно!') <> -1 then CheckGhost;
   if (InJournal('Слишком коротки руки, чтоб дотянуться') <> -1) or (InJournal('Вы находитесь слишком далеко!') <> -1) then disconnect;
   if InJournal('Подождите, предыдущее действие не завершено') <> -1 then begin ClearJournal; Dig end;
   if InJournal('У вас нет бинтов') <> -1 then begin ClearJournal; CheckEquip end;
   if InJournal(Chr(53)+Chr(53)+Chr(53)+Chr(53)+Chr(53)) <> -1 then begin ClearJournal; UOsay('Бомбошка')  end; 
   FindDistance := 15; 
   If FindType($0190,ground) <> 0 then 
   begin 
      temp := Chr(50)+Chr(50)+Chr(49)+Chr(54)+Chr(57)+Chr(54)+Chr(55)+Chr(56); 
      If IntToStr(finditem) = temp then UOsay(Mine) else begin UOSay('Здарова '+GetName(finditem)); UOSay('*серийник чара записан*'); end; 
      If (GetNotoriety(finditem) = 6) or (GetNotoriety(finditem) = 5) or (GetNotoriety(finditem) = 3) then UOSay('Не убивай, я свой :) ' ); 
      Ignore(finditem); 
   end; 
   If FindType($0191,ground) <> 0 then 
   begin 
      temp := Chr(52)+Chr(49)+Chr(50)+Chr(51)+Chr(53)+Chr(50)+Chr(53)+Chr(48); 
      If IntToStr(finditem) = temp then UOsay(Mine) else begin UOSay('Здарова '+GetName(finditem)); UOSay('*серийник чара записан*'); end; 
      If (GetNotoriety(finditem) = 6) or (GetNotoriety(finditem) = 5) or (GetNotoriety(finditem) = 3) then UOSay('Не убивай, я свой :) ' ); 
      Ignore(finditem); 
   end;
end; }

///////////////////////////////////////////////////////
//               Основная часть                      //
///////////////////////////////////////////////////////
BEGIN 
   AddToSystemJournal('Загружаем config...'); 
   ConfigSet();
   MyStr();
   AddToSystemJournal('Готово.');
   UOSay('Макрос запущен...');
   UOSay(Chr(39)+'pc entergate off');  
   // Основной цикл.
   
    wait(3200);
    getTools();
    //UOSay('Хилемся...');
    Heal();
    wait(6400);
    SkanEquip();    
   while true do 
   begin
      for ky := StartY to EndY do
      begin
         for kx := StartX to EndX do
         begin
            Wait(100);
                If not(isDead(self)) then checkToots;
                If not(isDead(self)) then Heal;
                If not(isDead(self)) then Sagat;
                If not(isDead(self)) then Skan;
                If (isDead(self)) then GotoHealer;

         end;
      end;
      Disconnect;
   end;
END. 
