program test2;
const chest=$75651F5F;
var a:cardinal;
begin
	a:=chest;
	//UseObject(chest);
	if LastContainer <> chest then AddToSystemJournal('no')
	else AddToSystemJournal('yes');
	AddToSystemJournal(LastContainer);
end.