Program ALore;
begin
 while true do 
 begin
 useskill('Arms Lore');  
 waitfortarget(100);
 TargetToObject(FindType($0EC4,Backpack));
 wait(3100);
 end; 
end.