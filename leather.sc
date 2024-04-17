program Leather;
begin
	while true do
	begin
		while FindType($1078,backpack) <> 0 do
		begin
			UseObject(FindType($0F9E,backpack));
			waittargetobject(FindType($1078,backpack));
			wait(3000);
		end;
	end;
end.