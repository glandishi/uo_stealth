Program Hiding;
begin
 while not Dead do 
 begin
 if Hidden then 
 useskill('Stealth')
 else useskill('Hiding');
 wait(3500);
 end; 
end.