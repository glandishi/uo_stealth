Program IIdent;
begin
 while true do 
 begin
 useskill('Item Identification');  
 waitfortarget(100);
 TargetToObject(FindType($0F51,Backpack));
 wait(3100);
 end; 
end.