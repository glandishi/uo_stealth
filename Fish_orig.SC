Program Auto_Fishing;
Const
////*****/////******/////****////
//������������ � ��������� ��������� �������//
////*****/////******/////****////
MainBox = $76EFFA6B;
BronBox = $72333E9C;
ItemBox = $76F48418;
MapBox = $75496D33;
TrashBox = $7ABABA6F;
////*****/////******/////****////
//���������� ����� ����� ��������
////*****/////******/////****////
xTileSunduk = 4208;
yTileSunduk = 3668;
////*****/////******/////****////
// ����������� ��������
////*****/////******/////****////
iTTileCount = 5;   // ���� ������ ���� (������, ������ ��� �������������� �������)
iCTileCount = 3;   // ���-�� ����� (������� ������), � ������� ����� �������� ���������� � ���� ��� ����� (������ = 30�30 ������)
////*****/////******/////****////
// ������
////*****/////******/////****////
stopMsg = '� ���� ������ ���|���� �������� ��� �����|��� ���� �� �������|��� ��������� ��������� ����|��� ���� �� �������|��� ������ ���� �������|�� ��������� ��� ����|����, ��� ���� ���� �� ������|����� ���� �� ��� ������ �� ��������|�� �������� ��� ����|�� �������� �������� ����!|�� ������ �� �������, ��� ���������� ���������!|�������� ������ � ����.|�� ���������� ������� ������.|������� ������� ����, ���� ����������.|��� �� ����������� ��� ����.';
////*****/////******/////****////
// ������
////*****/////******/////****////
RunSpeed = 95;
iRadiusSearch = 5; // ������ (�� �������!) ������ ����� � ������, ������������ ���������
type
FishTile = Record
x, y : Integer;
end;
///*****///////******/////****////
var
Monstr: array [1..3] of Cardinal;
Tools : array [1..13] of Cardinal;
Item : array [1..114] of Cardinal;
Quant : array [1..4] of Cardinal;
FoundTilesArray : TFoundTilesArray;
TempFoundTilesArray, FishTilesArray : array of TFoundTile;
FishTile : array [0..iTTileCount] of word;
FishTiles : array[1..iCTileCount] of FishTile;
i: Integer;
////*****/////******/////****////
//������������� ������� ����� ������ ����
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
//������������� ������� ��������� ��� ������ ����� �����
////*****/////******/////****////
procedure InitCTilesArray;
begin
FishTiles[1].x := 4196;  // ���������� �����, �� ������� ���� � ���� ����� �� ����
FishTiles[1].y := 3667;

FishTiles[2].x := 4206;  // ���������� �����, �� ������� ���� � ���� ����� �� ����
FishTiles[2].y := 3660;

FishTiles[3].x := 4218;  // ���������� �����, �� ������� ���� � ���� ����� �� ����
FishTiles[3].y := 3657;
 
end;
////*****/////******/////****////
//������������� ��������� ����������
////*****/////******/////****////
procedure InitSystem;
begin
  SetRunUnmountTimer(RunSpeed);
  SetArrayLength(FishTilesArray, 1);
end;
/////////////////////////////////////
// ������������� ������ � ������  //
///////////////////////////////////
procedure InitToolItem;
begin
    Tools[1] := $0DBF; // ������
    Tools[2] := $0E21; // ����
    Tools[3] := $097B; // ��������
    Tools[4] := $0F0B; // �����          
    Tools[5] := $13B6; // ����
    Tools[6] := $1B76; // ���  
    Tools[7] := $13C3; // �����������
    Tools[8] := $1410; // �����������
    Tools[9] := $1414; // ��������
    Tools[10] := $1416; // ����
    Tools[11] := $1412; // �����
    Tools[12] := $141A; // �����
    Tools[13] := $1413; // ��������
          
    Quant[1] := 1;     // ������
    Quant[2] := 200;   // �����
    Quant[3] := 5;     // ���������
    Quant[4] := 1;     // �����

  Item[1] := $0F21;              // ���� �������
  Item[2] := $0F80;              // ����� ���
  Item[3] := $0EED;              // ������
  Item[4] := $0F7D;              // ������
  Item[5] := $0F87;              // ������� ��� �����
  Item[6] := $0F85;              // ����
  Item[7] := $0F86;              // ��
  Item[8] := $0F7B;              // ���� ���
  Item[9] := $0F88;              // ��
  Item[10] := $0F7A;             // ��
  Item[11] := $0F84;             // ������
  Item[12] := $0F8C;             // �������
  Item[13] := $0F8D;             // ��
  Item[14] := $0F0E;             // ������ �������
  Item[15] := $09CD;             // ����
  Item[16] := $09CC;             // ����
  Item[17] := $09CE;             // ����
  Item[18] := $0E40;             // �������
  Item[19] := $0E41;             // �������
  Item[20] := $0E42;             // �������
  Item[21] := $0E43;             // �������
  Item[22] := $0EA0;             // �������
  Item[23] := $0EA1;             // �������
  Item[24] := $0EA2;             // �������
  Item[25] := $0EA3;             // �������
  Item[26] := $0EA4;             // �������
  Item[27] := $0EA5;             // �������
  Item[28] := $0EA6;             // �������
  Item[29] := $0EA7;             // �������
  Item[30] := $0EA8;             // �������
  Item[31] := $0EA9;             // �������
  Item[32] := $0EC8;             // �������
  Item[33] := $0EC9;             // �������
  Item[34] := $1711;             // ������
  Item[35] := $170B;             // ������
  Item[36] := $1EA3;             // �����
  Item[37] := $1EA5;             // �����
  Item[38] := $1408;             // �����
  Item[39] := $141A;             // �����
  Item[40] := $140A;             // �����
  Item[41] := $1414;             // �����  
  Item[42] := $13F1;             // �����
  Item[43] := $13F2;             // �����
  Item[44] := $13EF;             // �����
  Item[45] := $13ED;             // �����
  Item[46] := $13C4;             // �����
  Item[47] := $13BB;             // ���� ����
  Item[48] := $13C3;             // �����
  Item[49] := $140E;             // �����
  Item[50] := $1409;             // ���� 
  Item[51] := $1412;             // ����� ����
  Item[52] := $1416;             // ����� �������
  Item[53] := $1413;             // ����� ��������
  Item[54] := $1C04;             // �����
  Item[55] := $1410;             // �����
  Item[56] := $140C;             // �����
  Item[57] := $1B72;             // ��� �������     
  Item[58] := $1AE4;             // �����
  Item[59] := $1AD8;             // �����
  Item[60] := $0FCA;             // �����
  Item[61] := $0FCB;             // �����
  Item[62] := $0ECD;             // �����
  Item[63] := $0FCC;             // �����
  Item[64] := $0ED2;             // �����
  Item[65] := $0ECC;             // �����
  Item[66] := $0ECE;             // �����
  Item[67] := $0ECF;             // �����
  Item[68] := $0ECA;             // �����
  Item[69] := $0ED1;             // �����
  Item[70] := $0ED0;             // �����
  Item[71] := $0ECB;             // �����
  Item[72] := $0ECE;             // �����
  Item[73] := $1AE0;             // �����
  Item[74] := $1AE1;             // �����
  Item[75] := $1AE2;             // �����
  Item[76] := $1AE3;             // ����� 
  Item[77] := $1AE4;             // �����
  Item[78] := $0FC4;             // �����
  Item[79] := $0FC5;             // �����
  Item[80] := $0FC6;             // �����
  Item[81] := $0FC7;             // �����
  Item[82] := $0FC8;             // �����
  Item[83] := $0FC9;             // �����
  Item[84] := $1EA3;             // �����
  Item[85] := $0E85;             // �����
  Item[86] := $13FF;             // ������  
  Item[87] := $1438;             // ��� ������
  Item[88] := $143A;             // ����  
  Item[89] := $0F45;             // ���������
  Item[90] := $13B8;             // ���� �����
  Item[91] := $0F49;             // ����
  Item[92] := $0F47;             // ����� ��� �����
  Item[93] := $1405;             // ���� �������
  Item[94] := $13B0;             // ������
  Item[95] := $0F5C;             // ����
  Item[96] := $143E;             // ��������
  Item[97] := $0F4B;             // ���� ���   
  Item[98] := $143C;             // ������ ���  
  Item[99] := $1400;             // ����
  Item[100] := $1403;            // ��� ����
  Item[101] := $1406;            // ��� ����
  Item[102] := $13B4;            // ���
  Item[103] := $13E4;            // �� �������
  Item[104] := $13B6;            // ����
  Item[105] := $13FB;            // ����� ����
  Item[106] := $0F4D;            // �������
  Item[107] := $0F62;            // �����
  Item[108] := $0F5E;            // ����� �����
  Item[109] := $13BA;            // ������ �����
  Item[110] := $0E87;            // ����
  Item[111] := $14ED;            // �� ��������� �����        
  Item[112] := $14EB;            // �����
  Item[113] := $1440;            // ������
  Item[114] := $1B76;            // ���
end;
////*****/////******/////****////
//����� ����� �����
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
  AddToSystemJournal('������� �����: ' + IntToStr(iTempFoundTilesArrayCount));
end;
////*****/////******/////****////
// ������ ������ ��������� (Vizit0r :P)
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
  AddToSystemJournal('����� ���������� ����������, �������� ����� �����:' + IntToStr(Length(FishTilesArray)));
end;
////*****/////******/////****////
// �������� � ������� 2 (Shinma)
////*****/////******/////****////
function sqr(a:LongInt):LongInt;
begin
  result:=a*a;
end;
////*****/////******/////****////
// ��������� ����� ������� (Shinma)
////*****/////******/////****////
function vector_length(c_2:TFoundTile):LongInt;
begin
  result:=Round(sqrt(sqr(GetX(self)-c_2.X)+sqr(GetY(self)-c_2.Y)));
end;
////*****/////******/////****////
// �������� ���������� �� ����� �������, �� ������ ��������� ������ �� ���� ��������� ����������� ����� �����
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
//�������, ��������� ���������, ��������� ����� �����
////*****/////******/////****////
procedure MarkPoints;
begin
  for i:= 1 to iCTileCount do
  begin
    NewMoveXY(FishTiles[i].x, FishTiles[i].y, True, 0, True);
    SearchPoint;
    AddToSystemJournal('����� ������� �����: ' + IntToStr(Length(TempFoundTilesArray)));
    ClearDuplicate;
  end;
  QuickSort(FishTilesArray, 0, Length(FishTilesArray) - 1);
end;
////*****/////******/////****////
//���� ����
////*****/////******/////****////
procedure Gate;
begin
  FindDistance := 18;
  FindVertical := 18;
  if FindType($0F6C, ground) <> 0 then
  addtosystemjournal('��������� ����');
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
//��������� ����
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
  FindDistance := 3;
  if FindType($2006, ground) <> 0 then
  begin
    AddToSystemJournal('������ ����: '+GetName(Corpse));  
    Corpse := finditem;
    Wait(3000);
    NewMoveXY(GetX(Corpse), GetY(Corpse), false, 1, false);
    If FindType(Tools[5], self) <> - 1 then
    begin
      AddToSystemJournal('����� ����'); 
      UseObject(finditem);
      Wait(500);
      WaittargetObject(Corpse);
      Wait(4000);              
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
  if FindType(Tools[5], backpack) > 0 then     
  begin
    Equip (RhandLayer, finditem);
    Wait(350);
  end; 
  if FindType(Tools[6], backpack) > 0 then 
  begin
    Equip (LhandLayer, finditem);
    wait(350);
  end;
  NewMoveXY(xTileSunduk, yTileSunduk, true, 0, true);
  AddToSystemJOurnal('��������� ���!!!');
  UseObject(backpack);
  wait(1000);
  AddToSystemJOurnal('��������� ������� ������!!!');
  UseObject(MainBox);
  wait(1000);
  AddToSystemJOurnal('������!!!');
  UseObject(FindType(Tools[3], MainBox));
  wait(1500);
  for g := 1 to 17 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      If not(isDead(self)) then 
      begin
        ItemID := finditem;
        AddToSystemJournal('�������� �������');
        If not(isDead(self)) then
        MoveItem(ItemID, 0,ItemBox, 0, 0, 0);
        wait(350);
      end;
    end;
  end;
  for g := 18 to 109 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      ItemID := finditem;
      AddToSystemJournal('�������� �����');
      If not(isDead(self)) then
      begin
        MoveItem(ItemID, 0, TrashBox, 0, 0, 0);
        wait(250);
      end;
    end;
  end;
  for g := 110 to 112 do
  begin
    while FindType(Item[g], backpack) > 0 do
    begin
      ItemID := finditem;
      AddToSystemJournal('�������� �����');
      ClickOnObject(ItemID);
      wait(500);
      if (InJournal('You see: Treasure map 5') <> -1) OR (InJournal('You see: a magic pitchfork') <> -1) OR (InJournal('You see: Empty map:') <> -1) then 
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
      AddToSystemJournal('�������� ��� � ����');
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
  AddToSystemJOurnal('��������� ���!!!');
  UseObject(backpack);
  wait(1500);
  AddToSystemJOurnal('��������� ������!!!');
  UseObject(MainBox);
  wait(1500);   
  for g := 1 to 4 do
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
  AddToSystemJOurnal('��������� ������1!!!');
  UseObject(MainBox);
  wait(1500);
  AddToSystemJOurnal('��������� ������2!!!');
  UseObject(BronBox);
  wait(1500); 
  for g := 5 to 13 do
  begin
    if FindType(Tools[g],BronBox) > 1 then 
    begin
      AddToSystemJournal('������� �����');
      If g = 5 then Equip (LegsLayer, finditem);
      Wait(250);
      If g = 6 then Equip (LegsLayer, finditem);
      Wait(250);
      If g = 7 then Equip (LegsLayer, finditem);
      Wait(250);
      If g = 8 then Equip (ArmsLayer, finditem);
      Wait(250);
      If g = 9 then Equip (GlovesLayer, finditem);
      Wait(250);
      If g = 10 then Equip (TorsoLayer, finditem);
      Wait(250);
      If g = 11 then Equip (HatLayer, finditem);
      wait(250);
      If g = 12 then Equip (LegsLayer, finditem);
      wait(250);
      If g = 13 then Equip (NeckLayer, finditem);
      wait(250);
    end;     
  end;   
  end; 
////*****/////******/////****////
//�������� �� �������
////*****/////******/////****////
procedure CheckEquip;
var
j: integer;
begin
for j := 1 to 13 do
begin
if j < 5 then
begin
If FindType(Tools[j], backpack) = 0 then
begin
If not(isDead(self)) then
begin
AddToSystemJournal('����������� ������� - ' + IntToStr(j));
TakeOnEquip(backpack);
end;
end;
end
else
begin
If FindType(Tools[j], self) = 0 then
begin
If not(isDead(self)) then
begin
AddToSystemJournal('����������� ������� - ' + IntToStr(j));
TakeOnEquip1(self);
end;
end;
end;
end;
end;
procedure CheckHeal;
////*****/////******/////****////
begin
  while Life < 190 do  
  begin
    repeat
      If InJournal('System: � ��� ��� ������') <> -1 then CheckEquip;  
      UOSay(Chr(39)+'pc heal self');   
      addtosystemjournal('�������');
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
  if Stam < 40 then
  begin
    repeat  
      If InJournal('System: � ��� ��� ���������') <> -1 then CheckEquip;
      addtosystemjournal('���� �����');
      UOSay(Chr(39)+'pc quaf refresh');
      wait(8200);
      if FindType(Tools[4], backpack) = 0 then CheckEquip;
    until Stam = MaxStam;
    If isDead(self) then CheckGhost;
  end;
end;
////*****/////******/////****////
//����� ����
////*****/////******/////****////
procedure ScanMobs;
var
j: integer;
begin
  CheckGhost;
  Monstr[1] := $000D;  //������
  Monstr[2] := $0010;  //���� ����������
  Monstr[3] := $0096;  //����� ����������
  FindDistance := 5;
  FindVertical := 255;
  begin
    for j := 1 to 3 do
    while FindType(Monstr[j], Ground) > 0 do
    begin
      Monstr[j]:=finditem;
      While (GetHp(Monstr[j])>0) do
      begin
        if GetHp(Monstr[j])<>0 then attack(Monstr[j]);
        SetWalkUnmountTimer(300);
        SetRunMountTimer(95);
        Gate;
        if GetDistance(Monstr[j])>1 then NewMoveXY(GetX(Monstr[j]),GetY(Monstr[j]),true,1,true);
        If InJournal('System: � ��� ��� ������') <> -1 then CheckEquip; 
        UOSay(Chr(39)+'pc heal self');
        wait(1000);
        If isDead(self) then CheckGhost;
      end;
    end; 
  end;
  CheckHeal;
  FindGround;
end;
////*****/////******/////****////
//���� � ������� � �����������
////*****/////******/////****////
procedure CheckWeight;
begin
  If Weight > 250 then
  begin
    If not(isDead(self)) then StoreItems;
  end;
end;
////*****/////******/////****////
//����� ��������� ����. ���������� false ���� ������� ��� ��� �����.
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
  if FindType(Tools[5], backpack) > 0 then     
  begin
    Equip (RhandLayer, finditem);
    Wait(350);
  end; 
  if FindType(Tools[6], backpack) > 0 then 
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
	AddToSystemJournal(intToStr(time));
    if Stam < 40 then
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
