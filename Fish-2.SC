Program Auto_Fishing;
Const
////*****/////******/////****////
//Обязательные к изменению настройки скрипта//
////*****/////******/////****////
archer = true; // if char is archer(101/160/80), false if not
arch_count = 7; // needed for archer adjustment
MainBox = $75652C64;
BronBox = $63AD9A46;
ItemBox = $63DC87E0;
MapBox = $63212C33;
TrashBox = $63A8AED5;
ArmorBox = $63DC87DB;
////*****/////******/////****////
//Координаты точки перед сундуком
////*****/////******/////****////
xTileSunduk = 1601;
yTileSunduk = 1436;
////*****/////******/////****////
// Размерности массивов
////*****/////******/////****////
iTTileCount = 5;   // Типы тайлов воды (менять, только при редактировании массива)
iCTileCount = 3;   // Кол-во точек (центров поляны), в которых хотим собирать информацию о воде для ловли (поляна = 30х30 тайлов)
////*****/////******/////****////
// Журнал
////*****/////******/////****////
stopMsg = 'А рыбы больше нет|Рыба покинула эти места|Вся рыба на нересте|Эта местность исчерпала себя|Тут рыба не водится|Тут только тина морская|Вы распугали всю рыбу|Жара, все рыба ушла из лимана|Кроме воды вы тут ничего не поймаете|Вы выловили всю рыбу|Вы вытащили огромную рыбу!|Вы устали от рыбалки, вам необходимо отдохнуть!|Возьмите удочку в руки.|Вы находитесь слишком далеко.|Слишком коротки руки, чтоб дотянуться.|Вам не принадлежит эта вещь.';
////*****/////******/////****////
// Прочее
////*****/////******/////****////
RunSpeed = 95;
iRadiusSearch = 5; // Радиус (не диаметр!) поиска точек в тайлах, относительно персонажа
type
AFishTile = Record
x, y : Integer;
end;
///*****///////******/////****////
var
Monstr: array [1..4] of Cardinal;
Tools : array [1..14] of Cardinal;
Item : array [1..114] of Cardinal;
Quant : array [1..5] of Cardinal;
FoundTilesArray : TFoundTilesArray;
TempFoundTilesArray, FishTilesArray : array of TFoundTile;
FishTile : array [0..iTTileCount] of word;
FishTiles : array[1..iCTileCount] of AFishTile;
i: Integer;
////*****/////******/////****////
//Инициализация массива типов тайлов воды
////*****/////******/////****////
procedure InitTTilesArray;
begin  
  FishTile[0]:=6039;
  FishTile[1]:=6040;
  FishTile[2]:=6041;
  FishTile[3]:=6042;
  FishTile[4]:=6043;
  FishTile[5]:=6044;
end;
////*****/////******/////****////
//Инициализация массива координат для поиска точек ловли
////*****/////******/////****////
procedure InitCTilesArray;
begin
FishTiles[1].x := 1586;  // Координаты точки, на которую идем и ищем точки на воде
FishTiles[1].y := 1433;

FishTiles[2].x := 1586;  // Координаты точки, на которую идем и ищем точки на воде
FishTiles[2].y := 1440;

FishTiles[3].x := 1586;  // Координаты точки, на которую идем и ищем точки на воде
FishTiles[3].y := 1448;
 
end;
////*****/////******/////****////
//Инициализация системных переменных
////*****/////******/////****////
procedure InitSystem;
begin
  SetRunUnmountTimer(RunSpeed);
  SetArrayLength(FishTilesArray, 1);
end;
/////////////////////////////////////
// Инициализация итемов и тулзов  //
///////////////////////////////////
procedure InitToolItem;
begin
    Tools[1] := $0DBF; // pole
    Tools[2] := $0E21; // bandage
    Tools[3] := $097B; // food
    Tools[4] := $0F0B; // stamina         
    Tools[5] := $1BFB; // bolts
    Tools[6] := $0F51; // weapon - knife to cut bodies for archer 	
    Tools[7] := $0F4F; // shield
    Tools[8] := $1711; // undepants
	Tools[9] := $13C3; // pants
    Tools[10] := $13EF; // sleeves
    Tools[11] := $13F2; // gloves
    Tools[12] := $13C4; // body
    Tools[13] := $13BB; // cap    
	Tools[14] := $13D6; // gorget
          
    Quant[1] := 1;     // pole
    Quant[2] := 100;   // bandages
    Quant[3] := 5;     // food
    Quant[4] := 1;     // stamina
	Quant[5] := 100;     // bolts

  Item[1] := $0F21;              // Стар сапфиры
  Item[2] := $0F80;              // Демон бон
  Item[3] := $0EED;              // Золото
  Item[4] := $0F7D;              // Виалки
  Item[5] := $0F87;              // Приколы для колец
  Item[6] := $0F85;              // Гинс
  Item[7] := $0F86;              // Мр
  Item[8] := $0F7B;              // Блуд мус
  Item[9] := $0F88;              // Нс
  Item[10] := $0F7A;             // БП
  Item[11] := $0F84;             // Гарлик
  Item[12] := $0F8C;             // Сульфур
  Item[13] := $0F8D;             // СС
  Item[14] := $0F0E;             // Пустые бутылки
  Item[15] := $09CD;             // РЫБА
  Item[16] := $09CC;             // РЫБА
  Item[17] := $09CE;             // РЫБА
  Item[18] := $0E40;             // СУНДУКИ
  Item[19] := $0E41;             // СУНДУКИ
  Item[20] := $0E42;             // СУНДУКИ
  Item[21] := $0E43;             // СУНДУКИ
  Item[22] := $1EA3;             // СЕТКА
  Item[23] := $1EA5;             // СЕТКА
  Item[24] := $1711;             // Сапоги
  Item[25] := $170B;             // Сапоги  
  Item[26] := $1AE4;             // КОСТИ
  Item[27] := $1AD8;             // КОСТИ
  Item[28] := $0FCA;             // КОСТИ
  Item[29] := $0FCB;             // КОСТИ
  Item[30] := $0ECD;             // КОСТИ
  Item[31] := $0FCC;             // КОСТИ
  Item[32] := $0ED2;             // КОСТИ
  Item[33] := $0ECC;             // КОСТИ
  Item[34] := $0ECE;             // КОСТИ
  Item[35] := $0ECF;             // КОСТИ
  Item[36] := $0ECA;             // КОСТИ
  Item[37] := $0ED1;             // КОСТИ
  Item[38] := $0ED0;             // КОСТИ
  Item[39] := $0ECB;             // КОСТИ
  Item[40] := $0ECE;             // КОСТИ
  Item[41] := $1AE0;             // КОСТИ
  Item[42] := $1AE1;             // КОСТИ
  Item[43] := $1AE2;             // КОСТИ
  Item[44] := $1AE3;             // КОСТИ 
  Item[45] := $1AE4;             // КОСТИ
  Item[46] := $0FC4;             // КОСТИ
  Item[47] := $0FC5;             // КОСТИ
  Item[48] := $0FC6;             // КОСТИ
  Item[49] := $0FC7;             // КОСТИ
  Item[50] := $0FC8;             // КОСТИ
  Item[51] := $0FC9;             // КОСТИ
  Item[52] := $1EA3;             // КОСТИ
  Item[53] := $0EA0;             // Картины
  Item[54] := $0EA1;             // Картины
  Item[55] := $0EA2;             // Картины
  Item[56] := $0EA3;             // Картины
  Item[57] := $0EA4;             // Картины
  Item[58] := $0EA5;             // Картины
  Item[59] := $0EA6;             // Картины
  Item[60] := $0EA7;             // Картины
  Item[61] := $0EA8;             // Картины
  Item[62] := $0EA9;             // Картины
  Item[63] := $0EC8;             // Картины
  Item[64] := $0EC9;             // Картина
  Item[65] := $140C;             // АРМОР
  Item[66] := $1B72;             // Щит круглый
  Item[67] := $1C04;             // АРМОР
  Item[68] := $1410;             // АРМОР
  Item[69] := $1408;             // АРМОР
  Item[70] := $141A;             // АРМОР
  Item[71] := $140A;             // АРМОР
  Item[72] := $1414;             // АРМОР  
  Item[73] := $13F1;             // АРМОР
  Item[74] := $13F2;             // АРМОР
  Item[75] := $13EF;             // АРМОР
  Item[76] := $13ED;             // АРМОР
  Item[77] := $13C4;             // АРМОР
  Item[78] := $13BB;             // Чейн шлем
  Item[79] := $13C3;             // АРМОР
  Item[80] := $140E;             // АРМОР
  Item[81] := $1409;             // Шлем 
  Item[82] := $1412;             // Плейт шлем
  Item[83] := $1416;             // Плейт грудина
  Item[84] := $1413;             // Плейт горжетка
  Item[85] := $0E85;             // Кирка
  Item[86] := $13FF;             // Катана  
  Item[87] := $1438;             // Вар хаммер
  Item[88] := $143A;             // Маул  
  Item[89] := $0F45;             // Екзекутор
  Item[90] := $13B8;             // Лонг сворд
  Item[91] := $0F49;             // Аксе
  Item[92] := $0F47;             // Топор для рубки
  Item[93] := $1405;             // Вила двойные
  Item[94] := $13B0;             // Клюшка
  Item[95] := $0F5C;             // Мейс
  Item[96] := $143E;             // Алибарда
  Item[97] := $0F4B;             // Дабл акс   
  Item[98] := $143C;             // Хаммер пик  
  Item[99] := $1400;             // Крис
  Item[100] := $1403;            // шот спир
  Item[101] := $1406;            // Вар мейс
  Item[102] := $13B4;            // Куб
  Item[103] := $13E4;            // БС молоток
  Item[104] := $13B6;            // Ским
  Item[105] := $13FB;            // Ларге батл
  Item[106] := $0F4D;            // Бардишь
  Item[107] := $0F62;            // Спира
  Item[108] := $0F5E;            // Броад сворд
  Item[109] := $13BA;            // Викинг сворд
  Item[110] := $0E87;            // Вила
  Item[111] := $14ED;            // Не рисованая карта        
  Item[112] := $14EB;            // Карты
  Item[113] := $1440;            // Кутлас
  Item[114] := $1B76;            // Щит
end;
////*****/////******/////****////
//Поиск точек ловли
////*****/////******/////****////
procedure SearchPoint;
var
i, j : Integer;
iFoundTilesArrayCount : word;
iTempFoundTilesArrayCount : Integer;
begin
  for i:= 0 to iTTileCount do
  begin
    iFoundTilesArrayCount := GetStaticTilesArray((GetX(Self) - iRadiusSearch), (GetY(Self) - iRadiusSearch), (GetX(Self) + iRadiusSearch), (GetY(Self) + iRadiusSearch), 1, FishTile[i], FoundTilesArray);
    if iFoundTilesArrayCount > 0 then
    begin
      SetArrayLength(TempFoundTilesArray, Length(TempFoundTilesArray) + iFoundTilesArrayCount);
      for j := 0 to iFoundTilesArrayCount - 1 do
      begin
        TempFoundTilesArray[iTempFoundTilesArrayCount + j] := FoundTilesArray[j];
      end;
      iTempFoundTilesArrayCount := iTempFoundTilesArrayCount + iFoundTilesArrayCount;
    end;
  end;
  AddToSystemJournal('Найдено точек: ' + IntToStr(iTempFoundTilesArrayCount));
end;
////*****/////******/////****////
// Чистим записи дубликаты (Vizit0r :P)
////*****/////******/////****////
procedure ClearDuplicate;
var
i, j : Integer;
begin
  FishTilesArray[Length(FishTilesArray) - 1] := TempFoundTilesArray[0];
  for i:=1 to Length(TempFoundTilesArray) - 1 do
  begin
    for j:=0 to Length(FishTilesArray) - 1 do
    if (FishTilesArray[j] = TempFoundTilesArray[i]) then
    break;
    if j > Length(FishTilesArray) - 1 then
    begin
      SetArrayLength(FishTilesArray, Length(FishTilesArray) + 1);
      FishTilesArray[Length(FishTilesArray) - 1] := TempFoundTilesArray[i];
    end;
  end;
  AddToSystemJournal('После отсеивания дубликатов, осталось точек ловли:' + IntToStr(Length(FishTilesArray)));
end;
////*****/////******/////****////
// Возводим в степень 2 (Shinma)
////*****/////******/////****////
function sqr(a:LongInt):LongInt;
begin
  result:=a*a;
end;
////*****/////******/////****////
// Вычисляем длину вектора (Shinma)
////*****/////******/////****////
function vector_length(c_2:TFoundTile):LongInt;
begin
  result:=Round(sqrt(sqr(GetX(self)-c_2.X)+sqr(GetY(self)-c_2.Y)));
end;
////*****/////******/////****////
// «Быстрая сортировка» по длине вектора, от центра последней поляны ко всем собранным координатам точек ловли
////*****/////******/////****////
procedure QuickSort(A: array of TFoundTile; l,r: integer);
var
i, j: Integer;
x, y: TFoundTile;
begin
  i := l;
  j := r;
  x := A[((l + r) div 2)];
  repeat
    while vector_length(A[i]) < vector_length(x) do inc(i);
    while vector_length(x) < vector_length(A[j]) do dec(j);
    if not (i>j) then
    begin
      y:= A[i];
      A[i]:= A[j];
      A[j]:= y;
      inc(i);
      dec(j);
    end;
  until i>j;
  if l < j then QuickSort(FishTilesArray, l,j);
  if i < r then QuickSort(FishTilesArray, i,r);
end;
////*****/////******/////****////
//Находим, исключаем дубликаты, сортируем точки ловли
////*****/////******/////****////
procedure MarkPoints;
begin
  for i:= 1 to iCTileCount do
  begin
    NewMoveXY(FishTiles[i].x, FishTiles[i].y, True, 0, True);
    SearchPoint;
    AddToSystemJournal('Всего найдено Точек: ' + IntToStr(Length(TempFoundTilesArray)));
    ClearDuplicate;
  end;
  QuickSort(FishTilesArray, 0, Length(FishTilesArray) - 1);
end;
////*****/////******/////****////
//Анти Гейт
////*****/////******/////****////
procedure Gate;
begin
  FindDistance := 18;
  FindVertical := 18;
  if FindType($0F6C, ground) <> 0 then
  addtosystemjournal('Обнаружен гейт');
  begin
    while FindType($0F6C, ground) <> 0 do
    wait(1000);
  end;
end;
///*****///////******/////****////
procedure Resend;
begin
  Wait(1000);
  UOSay(Chr(39)+'resend');
  AddToSystemJournal('Resend');
  Wait(6000);
end;
////*****/////******/////****////
procedure WaitConnection(WaitTime : Integer);
////*****/////******/////****////
begin
  if Connected then Exit;
  while not Connected do Wait(1000);
  wait(WaitTime);
end;
////*****/////******/////****////
procedure CheckLag;
////*****/////******/////****////
var starttime : TDateTime;
begin
  starttime := Now;
  ClickOnObject(backpack);
  repeat
    wait(500);
  until (InJournalBetweenTimes('backpack', starttime, Now)<>-1);
end;
////*****/////******/////****////
//Процедура гост
////*****/////******/////****////
procedure CheckGhost;
begin
  If IsDead(self) then
  begin
    SetARStatus(false); Disconnect;
    ClearJournal;
    exit;
  end;
end;
////*****/////******/////****////
procedure FindGround;
////*****/////******/////****////
VAR
Corpse: Cardinal;
ItemID: Cardinal;
i, j: Integer;  
Begin
  FindDistance := 5;
  if FindType($2006, ground) <> 0 then
  begin
    AddToSystemJournal('Найден труп: '+GetName(Corpse));  
    Corpse := finditem;
    Wait(3000);
    NewMoveXY(GetX(Corpse), GetY(Corpse), false, 1, false);
	if GetDistance(Corpse) < 2 then
	begin
		if archer then
		begin
			If FindType(Tools[6], Backpack) <> - 1 then
			begin
			  AddToSystemJournal('Режем труп'); 
			  UseObject(FindType(Tools[6], Backpack));
			  Wait(500);
			  WaittargetObject(Corpse);
			  Wait(4000);              
			end;
		end
		else
		begin
			If FindType(Tools[6], self) <> - 1 then
			begin
			  AddToSystemJournal('Режем труп'); 
			  UseObject(finditem);
			  Wait(500);
			  WaittargetObject(Corpse);
			  Wait(4000);              
			end;
		end;
		UseObject(Corpse);
		Wait(1000);
		Ignore(Corpse);
		i := 1;
		while i < 14 do
		begin
		  If FindType(Item[i], Corpse) > 0 then
		  begin
			ItemID := finditem;
			If not(isDead(self)) then MoveItem(ItemID, 0, backpack, 0, 0, 0);
			Wait(250);
		  end
		  else
		  begin
			i := i + 1;
		  end;
		end;
	end
	else
	begin
		AddToSystemJournal('Слишком далеко для резки');
		Ignore(Corpse);
		Wait(1000);
	end;
    
  end;
  for j := 15 to 17 do
  begin
    If FindType(Item[j], ground) <> 0 then
    begin
      while FindType(Item[j], ground) <> 0 do
      begin
        NewMoveXY(GetX(FindType(Item[j],ground)), GetY(FindType(Item[j],ground)), false, 1, false);
        If not(isDead(self)) then grab(findtype(Item[j], ground), 0);
        Wait(250);
      end;
    end;
  end;
end;
////*****/////******/////****////
Procedure StoreItems;
////*****/////******/////****////
var
g : integer;
ItemID : Cardinal;
Begin
  If isDead(self) then CheckGhost;
  Gate;
  begin
    UnEquip(LhandLayer);
    wait(350);
  end;
  if archer then
  begin
  	  if FindType(Tools[6], backpack) > 0 then AddToSystemJOurnal('Нож в карманчике');    
  end
  else
  begin
	  if FindType(Tools[6], backpack) > 0 then     
	  begin
		Equip (RhandLayer, finditem);
		Wait(350);
	  end;
  end;
  if FindType(Tools[7], backpack) > 0 then 
  begin
    Equip (LhandLayer, finditem);
    wait(350);
  end;
  NewMoveXY(xTileSunduk, yTileSunduk, true, 0, true);
  AddToSystemJOurnal('Открываем Пак!!!');
  UseObject(backpack);
  wait(1000);
  AddToSystemJOurnal('Открываем Главный Сундук!!!');
  UseObject(MainBox);
  wait(1000);
  AddToSystemJOurnal('Хаваем!!!');
  UseObject(FindType(Tools[3], MainBox));
  wait(1500);
  for g := 1 to 17 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      If not(isDead(self)) then 
      begin
        ItemID := finditem;
        AddToSystemJournal('Скидываю Приколы');
        If not(isDead(self)) then
        MoveItem(ItemID, 0,ItemBox, 0, 0, 0);
        wait(350);
      end;
    end;
  end;
  for g := 18 to 52 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      ItemID := finditem;
      AddToSystemJournal('Скидываю Мусор');
      If not(isDead(self)) then
      begin
        MoveItem(ItemID, 0, TrashBox, 0, 0, 0);
        wait(250);
      end;
    end;
  end;
  for g := 53 to 84 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
	  UseObject(ArmorBox);
	  wait(1000);
      ItemID := finditem;
      AddToSystemJournal('Скидываю Армор');
      If not(isDead(self)) then
      begin
        MoveItem(ItemID, 0, ArmorBox, 0, 0, 0);
        wait(250);
      end;
    end;
  end;
  for g := 85 to 109 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      ItemID := finditem;
      AddToSystemJournal('Скидываю Валыны');
      If not(isDead(self)) then
      begin
        MoveItem(ItemID, 0, BronBox, 0, 0, 0);
        wait(250);
      end;
    end;
  end;
  for g := 110 to 112 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      ItemID := finditem;
      AddToSystemJournal('Скидываю Карты');
      ClickOnObject(ItemID);
      wait(500);
      if (InJournal('You see: Treasure map') <> -1) OR (InJournal('You see: a magic pitchfork') <> -1) OR (InJournal('You see: Empty map:') <> -1) then 
      begin
        MoveItem(ItemID, 0, MapBox, 0, 0, 0);
        ClearJournal;
        wait(350);
      end
      else
      begin
        MoveItem(ItemID, 0, TrashBox, 0, 0, 0);
        ClearJournal;
        wait(250);
      end;
    end;
  end;   
for g := 113 to 114 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      ItemID := finditem;
      AddToSystemJournal('Скидываю Щит и Ским');
      ClickOnObject(ItemID);
      wait(500);
      if (InJournal('You see: heater shield forged ') <> -1) OR (InJournal('You see: cutlass forged') <> -1) then 
      begin
        MoveItem(ItemID, 0, BronBox, 0, 0, 0);
        ClearJournal;
        wait(350);
      end
      else
      begin
        MoveItem(ItemID, 0, TrashBox, 0, 0, 0);
        ClearJournal;
        wait(250);
      end;
    end;
  end;
end;  
////*****/////******/////****////
procedure TakeOnEquip(ID: Cardinal);
////*****/////******/////****////
var
g : integer;
begin 
  NewMoveXY(xTileSunduk, yTileSunduk, true, 0, true);
  AddToSystemJOurnal('Открываем Пак!!!');
  UseObject(backpack);
  wait(1500);
  AddToSystemJOurnal('Открываем Сундук!!!');
  UseObject(MainBox);
  wait(1500);   
  for g := 1 to (arch_count-2) do
  begin
    if FindType(Tools[g], backpack) = 0 then
    begin
      MoveItem(FindType(Tools[g], MainBox), Quant[g], backpack, 0, 0, 0);
      wait(350);    
    end;    
  end;
end;      
////*****/////******/////****////
procedure TakeOnEquip1(ID: Cardinal);
////*****/////******/////****////
var
g : integer;
begin 
  NewMoveXY(xTileSunduk, yTileSunduk, true, 0, true);
  AddToSystemJOurnal('Открываем Сундук1!!!');
  UseObject(MainBox);
  wait(1500);
  AddToSystemJOurnal('Открываем Сундук2!!!');
  UseObject(BronBox);
  wait(1500); 
  for g := arch_count to 14 do
  begin
    if FindType(Tools[g],BronBox) > 1 then 
    begin
	  AddToSystemJournal('Одеваем арбаль');
	  If (g = 7) and (ObjAtLayer(LhandLayer) = 0) then Equip (LhandLayer, FindType(Tools[g],BronBox));
      Wait(250);
      AddToSystemJournal('Одеваем армор');
      If g = 8 then Equip (LegsLayer, finditem);
      Wait(250);
      If g = 9 then Equip (LegsLayer, finditem);
      Wait(250);
      If g = 10 then Equip (ArmsLayer, finditem);
      Wait(250);
      If g = 11 then Equip (GlovesLayer, finditem);
      Wait(250);
      If g = 12 then Equip (TorsoLayer, finditem);
      Wait(250);
      If g = 13 then Equip (HatLayer, finditem);
      wait(250);
      If g = 14 then Equip (NeckLayer, finditem);
      wait(250);
    end;     
  end;   
  end; 
////*****/////******/////****////
//Проверка на приколы
////*****/////******/////****////
procedure CheckEquip;
var
	j: integer;
begin
for j := 1 to 14 do
begin
		if j < arch_count then
		begin
			If FindType(Tools[j], backpack) = 0 then
			begin
				If not(isDead(self)) then
				begin
					AddToSystemJournal('Закончились приколы(расходники) - ' + IntToStr(j));
					TakeOnEquip(backpack);
				end;
			end;
		end
else
begin
	If ((FindType(Tools[j], self) = 0) and (j <> 8)) then
	begin
		If not(isDead(self)) then
		begin
			AddToSystemJournal('Закончились приколы(снаряга) - ' + IntToStr(j));
			TakeOnEquip1(self);
		end;
	end;
end;
end;
end;
procedure CheckHeal;
////*****/////******/////****////
begin
  while Life < MaxHP do  
  begin
    repeat
      If InJournal('System: У вас нет бинтов') <> -1 then CheckEquip;  
      UOSay(Chr(39)+'pc heal self');   
      addtosystemjournal('Лечимся');
      Wait(8200);
      if FindType(Tools[2], backpack) = 0 then CheckEquip;  
    until Life = MaxHP;   
    If isDead(self) then CheckGhost;
  end;
end;
////*****/////******/////****////
procedure CheckStam;
////*****/////******/////****////
begin
  if Stam < 20 then
  begin
    repeat  
      If InJournal('System: У вас нет бутылочек') <> -1 then CheckEquip;
      addtosystemjournal('Пьем банку');
      UOSay(Chr(39)+'pc quaf refresh');
      wait(8200);
      if FindType(Tools[4], backpack) = 0 then CheckEquip;
    until Stam > 80;
    If isDead(self) then CheckGhost;
  end;
end;
////*****/////******/////****////
procedure CheckAmmo;
////*****/////******/////****////
begin
  if FindType(Tools[5],Backpack) = 0 then
  begin 
    CheckEquip;
    If isDead(self) then CheckGhost;
  end
  else
  begin
	FindType(Tools[5],Backpack);
	if FindQuantity < Quant[5]/2 then CheckEquip;	
  end;
end;
////*****/////******/////****////
//Поиск Моба
////*****/////******/////****////
procedure ScanMobs;
var
j: integer;
begin
  CheckGhost;
  Monstr[1] := $000D;  //Змейка
  Monstr[2] := $0010;  //Блуд елементаль
  Monstr[3] := $0096;  //Ватер елементаль
  Monstr[4] := $00F9;  //Poison елементаль
  FindDistance := 7;
  FindVertical := 255;
  begin
    for j := 1 to 4 do
    while FindType(Monstr[j], Ground) > 0 do
    begin
      Monstr[j]:=finditem;
      While (GetHp(Monstr[j])>0) do
      begin
        if GetHp(Monstr[j])<>0 then attack(Monstr[j]);
		if ObjAtLayer(LhandLayer) = 0 then
		begin
			UOSay(Chr(39)+'resend');
			if FindType(Tools[7], backpack) > 0 then 
			begin
				Equip (LhandLayer, finditem);
				wait(350);
			end;
		end;
        SetWalkUnmountTimer(300);
        SetRunMountTimer(95);
        Gate;
        if GetDistance(Monstr[j])<2 then NewMoveXY(GetX(Monstr[j])+3,GetY(Monstr[j])+3,true,1,true);
        If InJournal('System: У вас нет бинтов') <> -1 then CheckEquip; 
        UOSay(Chr(39)+'pc heal self');
        wait(1000);
		if FindType(Tools[5],Backpack) = 0 then CheckEquip; 
        If isDead(self) then CheckGhost;
      end;
    end; 
  end;
  CheckHeal;
  FindGround;
end;
////*****/////******/////****////
//Идем к сундуку и выгружаемся
////*****/////******/////****////
procedure CheckWeight;
begin
  If Weight > 250 then
  begin
    If not(isDead(self)) then StoreItems;
  end;
end;
////*****/////******/////****////
//ловим указанный тайл. Возвращаем false если перевес или чар мертв.
////*****/////******/////****////
function FishPoints(tile,x,y,z : Integer) : Boolean;
var
done : boolean;       
time : integer;
lastTime : TDateTime;
begin
  WaitConnection(3000);      
  Result := true;
  done := false;
  dropdelay := 500;
  while (ObjAtLayer(RhandLayer) > 0) or (ObjAtLayer(LhandLayer) > 0) do
  begin
    WaitConnection(3000);
    if ObjAtLayer(RhandLayer) > 0 then
    begin
      UnEquip(RhandLayer);
      wait(350);
    end;
    if ObjAtLayer(LhandLayer) > 0 then
    begin
      UnEquip(LhandLayer);
      wait(350);
    end;
  end;
  if Dead then
  begin 
    Result := false; 
    exit; 
  end;
  CancelTarget;
  wait(500);
  if ObjAtLayer(LhandLayer) = 0 then 
  begin
    if FindType(Tools[1],Backpack) > 0 then
    begin
      Equip(LhandLayer,FindType(Tools[1],BackPack));
      wait(350);
    end;  
  end;
  if UseType(Tools[1],$FFFF) = 0 then
  begin
    Result := false;      
    exit;
  end;
  WaitForTarget(5000);
  while ObjAtLayer(LhandLayer) > 0 do
  begin
    UnEquip(LhandLayer);
    wait(350);
  end;
  //if FindType(Tools[5], backpack) > 0 then     
  //begin
    //Equip (RhandLayer, finditem);
    //Wait(350);
  //end; 
  if FindType(Tools[7], backpack) > 0 then 
  begin
    Equip (LhandLayer, finditem);
    wait(350);
  end;
  If TargetPresent then 
  begin
    TargetToTile(tile, x, y, z);
  end
  else
  begin
    Result := false;
  end;
  lastTime := now;  
  time := 0;
  repeat
    wait(500);
    time := time + 500;
    if ((inJournalBetweenTimes(stopMsg, lastTime, now) >= 0) or (((now - lastTime) >= (1.0/86400)*60.0))) then done := true;
    if Stam < 25 then
    begin
      CheckStam;
      done := true;
    end;  
  until done;
  Result := false; 
end; 
///*****///////******/////****////
Begin
SetPauseScriptOnDisconnectStatus(True);
SetARStatus(true); 
  WaitConnection(3000);
  InitTTilesArray;
  InitCTilesArray;
  InitSystem;
  InitToolItem;
  MarkPoints;
  moveThroughNPC := 10;
  moveBetweenTwoCorners := true;
  moveCheckStamina := 5;
  If not(isDead(self)) then CheckEquip;
  If not(isDead(self)) then CheckAmmo;
  repeat
    for i:= 0 to Length(FishTilesArray) - 1 do
    begin
      AddToSystemJournal(intToStr(i));
      ScanMobs;
      Gate;
      CheckWeight;
      NewMoveXY(FishTilesArray[i].x, FishTilesArray[i].y, True, 5, True);
      if not FishPoints(FishTilesArray[i].tile, FishTilesArray[i].x, FishTilesArray[i].y, FishTilesArray[i].z) then
      begin
        CheckGhost;
        If not(isDead(self)) then ScanMobs;
        If not(isDead(self)) then FindGround;
        If not(isDead(self)) then CheckEquip;
        If not(isDead(self)) then CheckHeal;
        begin  
			WaitConnection(3000); 
        end;
      end;
    end;
  until Dead;
End.
