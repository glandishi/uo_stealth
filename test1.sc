Program Test1;
var
	hatchet:cardinal;
begin
	if FindType($0F47,backpack) <> 0 then hatchet := FindType($0F47,backpack)
	else if FindType($0F43,backpack) <> 0 then hatchet := FindType($0F43,backpack)
	else hatchet:=FindType($0F43,backpack);
	AddToSystemJournal('hatchet:',hatchet);
	UseObject(hatchet);
end.