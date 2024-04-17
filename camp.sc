program Camp;
const kindling=$0DE1;
begin
	while FindType(kindling,backpack) <> 0 do
		begin
			UseObject(FindType(kindling,backpack));
			wait(3400);
		end;
end.