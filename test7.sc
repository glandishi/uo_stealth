Program test7;
const
	hatchet=$0F43;
begin
	useobject(findtype(hatchet,backpack));
	waittargettile(3277,1632,1416,GetZ(self));
end.