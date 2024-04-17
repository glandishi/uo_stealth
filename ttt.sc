program ttt;
var d:integer;
begin
	d:=GetDistance($5C8EF662);
	AddToSystemJournal(d);
	if d > 0 then AddToSystemJournal('>0')
	else AddToSystemJournal('no hit');
	
end.