Program Bandages;
const 
    cloth=$1766;
    scissors=$0F9E;
begin
    while FindType(cloth,Backpack) <> 0 do
    begin 
        UseObject(FindType(scissors,backpack));
        WaitTargetObject(FindType(cloth,Backpack));
		wait(3300);
    end;             

end.