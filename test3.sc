Program test3;
const
    a=$00D3;
begin
AddToSystemJournal('type:',GetHP(FindType(a,Ground)));
AddToSystemJournal('id:',GetHP($08347B9F));
AddToSystemJournal(WarTargetID);
AddToSystemJournal(WarMode);
end.