Program Poison;
const
    pack=$63DC87DD;
    //pack='Backpack';
	poison=$0F0A;
begin
	repeat
	if FindType(poison,pack) <> 0 then
	begin
		useskill('Poisoning');
        WaitTargetObject(FindType(poison,pack));
		WaitTargetObject(FindType($0F51,Backpack));
		wait(3500);
	end
	until FindType(poison,pack) = 0;
end.