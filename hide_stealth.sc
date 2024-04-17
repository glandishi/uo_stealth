Program HS;
begin
	while not Dead do
	begin
		useskill('Hiding');
		wait(3300);
		if Hidden then
		begin
			if GetSkillValue('Stealth') < 100 then useskill('Stealth')
			else useskill('Hiding');
			wait(3300);
		end;
	end;
end.