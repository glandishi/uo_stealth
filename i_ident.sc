program ItemIdent;
begin
    while not Dead do
    begin
	UseSkill('Item Identification');
	waittargetobject(FindType($153B,backpack));
	wait(3300);
    end;
end.