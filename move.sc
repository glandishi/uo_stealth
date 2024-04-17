program Move;
const
	knife=$0EC4;
	to=$758B70B8;
var
	what:word;
begin
	what:=knife;
	useobject(to);
	while FindTypeEx(what,$0000,backpack,true) <> 0 do
	begin
		MoveItem(finditem, 0, to, 0, 0, 0);
	end;
end.