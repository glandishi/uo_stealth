Program ALore_Healing;
begin
 while true do 
 begin
 if HP < (MaxHP/2) then UOSay('''pc heal self');
 useskill('Arms Lore');  
 WaitTargetObject(FindType($0F51,Backpack));
 wait(3200);
 end; 
end.