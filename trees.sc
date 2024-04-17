program treearr;
const
	iRadiusSearch=20;
	startx=1609;
	starty=1435;
	RunSpeed = 95;
type
TreeTile = Record
x,y,tile : Integer;
end;
var
	i:integer;
	hatchet:word;
	TreeTiles : array of TreeTile;
	FoundTilesArray : TFoundTilesArray;
	TempFoundTilesArray, TreeTilesArray : array of TFoundTile;
procedure InitSystem;
begin
  SetRunUnmountTimer(RunSpeed);
  SetArrayLength(TreeTilesArray, 1);
end;
function AreTilesEqual(const Tile1, Tile2: TreeTile): Boolean;
begin
  Result := (Tile1.X = Tile2.X) and (Tile1.Y = Tile2.Y);
end;
function IsDuplicate(const Tile: TreeTile; const TileArray: TempFoundTilesArray): Boolean;
var
  i: Integer;
begin
  Result := False;
  for i := 0 to High(TileArray) do
  begin
    if AreTilesEqual(Tile, TileArray[i]) then
    begin
      Result := True;
      Exit;
    end;
  end;
end;
procedure SearchPoint;
var
i, j : Integer;
iFoundTilesArrayCount : word;
iTempFoundTilesArrayCount : Integer;
begin
  for i:= 3276 to 3304 do
  begin
    iFoundTilesArrayCount := GetStaticTilesArray(GetX(Self), GetY(Self), (GetX(Self) + iRadiusSearch), (GetY(Self) - iRadiusSearch), WorldNum, i, FoundTilesArray);
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
  AddToSystemJournal(Length(TempFoundTilesArray),':',TempFoundTilesArray);
end;
procedure ClearDuplicate;
var
i, j : Integer;
begin
  TreeTilesArray[Length(TreeTilesArray) - 1] := TempFoundTilesArray[0]; //TreeTilesArray[0]=TempFoundTilesArray[0]
  for i:=1 to Length(TempFoundTilesArray) - 1 do // 1-34
  begin
    for j:=0 to Length(TreeTilesArray) - 1 do // 0-0
    if (TreeTilesArray[j] = TempFoundTilesArray[i]) then
    break;
    if j > Length(TreeTilesArray) - 1 then
    begin
      SetArrayLength(TreeTilesArray, Length(TreeTilesArray) + 1);
      TreeTilesArray[Length(TreeTilesArray) - 1] := TempFoundTilesArray[i];
    end;
  end;
  AddToSystemJournal('После отсеивания дубликатов, осталось точек ловли:' + IntToStr(Length(TreeTilesArray)));
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
  if l < j then QuickSort(TreeTilesArray, l,j);
  if i < r then QuickSort(TreeTilesArray, i,r);
end;
////*****/////******/////****////
//Находим, исключаем дубликаты, сортируем точки ловли
////*****/////******/////****////
procedure MarkPoints;
begin
  NewMoveXY(startx, starty, True, 0, True);
  SearchPoint;
  AddToSystemJournal('Всего найдено Точек: ' + IntToStr(Length(TempFoundTilesArray)));
  ClearDuplicate;
  QuickSort(TreeTilesArray, 0, Length(TreeTilesArray) - 1);
end;
begin
	InitSystem;
	if FindType($0F47,backpack) <> 0 then hatchet := finditem
	else if FindType($0F43,backpack) <> 0 then hatchet := finditem;
	MarkPoints;
end.