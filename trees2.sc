program treearr;
const
	iRadiusSearch=20;
	startx=1609;
	starty=1435;
	RunSpeed = 95;
type
TreeTile = Record
Tile,X,Y,Z : Integer;
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
function IsDuplicate(const Tile: TreeTile; const TileArray: TreeTiles): Boolean;
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
procedure RemoveDuplicates(var TileArray: TreeTiles);
var
  UniqueTiles: TreeTiles;
  i: Integer;
begin
  SetLength(UniqueTiles, 0);
  for i := 0 to High(TileArray) do
  begin
    if not IsDuplicate(TileArray[i], UniqueTiles) then
    begin
      SetLength(UniqueTiles, Length(UniqueTiles) + 1);
      UniqueTiles[High(UniqueTiles)] := TileArray[i];
    end;
  end;
  TileArray := UniqueTiles;
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
/////******/////****////
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
  RemoveDuplicates(TempFoundTilesArray);
  QuickSort(TreeTilesArray, 0, Length(TreeTilesArray) - 1);
end;
begin
	InitSystem;
	if FindType($0F47,backpack) <> 0 then hatchet := finditem
	else if FindType($0F43,backpack) <> 0 then hatchet := finditem;
	MarkPoints;
end.