Program Cloth;
const
    cloth=$0F96;
	cloth2=$0F97;
    cloth3=$0F95;
    scissors=$0F9E;
begin
 while ((FindType(cloth,backpack) > 0) or (FindType(cloth2,backpack) > 0) or (FindType(cloth3,backpack) > 0)) do
 begin    
    useobject(FindType(scissors,backpack));
   if FindType(cloth,backpack) <> 0 then
   begin
      WaitTargetObject(finditem);
      wait(3200);
   end
   else if FindType(cloth2,backpack) <> 0 then
   begin
      WaitTargetObject(finditem);
      wait(3200);
   end   
   else if FindType(cloth3,backpack) <> 0 then
   begin
      WaitTargetObject(finditem);
      wait(3200);
   end
   else Break;
 end;
end.