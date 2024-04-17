procedure OpenContainer(serial : integer);
var
	startTime, waitTime : integer;

begin
	SetGlobal('char', 'Container', 'Close');
	SetEventProc(evDrawContainer, 'WaitOpenContainer');
	waitTime := 1000;
	startTime := GetTickCount();
	UseObject(serial);
	
	while (GetGlobal('char', 'Container') = 'Close') and (GetTickCount() < (startTime + waitTime)) do
	begin
		Wait(1);
	end;
	
	Wait(100);
end;

procedure WaitOpenContainer(serial, modelGump : integer);
begin
	SetEventProc(evDrawContainer, '');
	SetGlobal('char', 'Container', 'Open');
end;