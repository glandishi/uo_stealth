Program test6;
procedure CheckAmmo;
////*****/////******/////****////
begin
  FindType($2006,Ground);
  AddToSystemJournal(GetDistance(finditem));

end;
begin
CheckAmmo;
end.