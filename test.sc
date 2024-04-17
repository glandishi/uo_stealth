program Training;
//band_type,chest_type,twoid,twocord:string;
const
 band_type = $0E21;
 chest_type = $0E42;
 one_id = $4D4F2A36;
 one_cord = '6031,1487';
 two_id = $793E0B93;
 two_cord = '6065,1472';
 //player = self;
var
 item:cardinal;
 count:integer;

function Loot(item_type:cardinal;chest:cardinal): Boolean;
var
    t,c,item: cardinal;
    i,cnt: integer;
begin
    t := item_type;
    c := chest;
    UseObject(c);
    item := FindType(t,c);
    cnt := FindCount;
    //AddToSystemJournal('Count: '+IntToStr(cnt));
    for i := 1 to cnt do
    begin
      item := FindType(t,c);    
      DragItem(item,-1);
      wait(100); 
      DropItem(Backpack,0,0,0); 
      wait(800);
    end;
end;
begin	
while true do
begin
    Loot(band_type,one_id);
    wait(30000)
end;
end.