program Lumber;
const
    axe = $0F43;
	house_x = 1609;
	house_y = 1434;
	t1x = 1608;
	t1y = 1437;
    t1id = 3299;
    t2x=1604;
    t2y=1440;
    t2id=3302;
    t3x=1604;
    t3y=1443;
    t3id=3280;
    t4x=1604;
    t4y=1446;
    t4id=3286;
    t5x=1608;
    t5y=1449;
    t5id=3299;
	
    

begin
    MoveXY(house_x,house_y,false,1,true);
    wait(1000);
    MoveXY(t1x,t1y,false,1,true);
    UseObject(FindType(axe,Backpack));
    wait(100);
	TargetToTile(t1id,t1x,t1y,0);
    wait(10000);
    MoveXY(t2x,t2y,false,1,true);
    UseObject(FindType(axe,Backpack));
    wait(100);
	TargetToTile(t2id,t2x,t2y,1);
    wait(10000);
    MoveXY(t3x,t3y,false,1,true);
    UseObject(FindType(axe,Backpack));
    wait(100);
	TargetToTile(t3id,t3x,t3y,0);
    wait(10000);
    MoveXY(t4x,t4y,false,1,true);
    UseObject(FindType(axe,Backpack));
    wait(100);
	TargetToTile(t4id,t4x,t4y,0);
    wait(10000);
    MoveXY(t5x,t5y,false,1,true);
    UseObject(FindType(axe,Backpack));
    wait(100);
	TargetToTile(t5id,t5x,t5y,0);
    wait(10000);
end.