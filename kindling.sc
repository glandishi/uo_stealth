program Test;
const
	dagger=$0F51;
	kryss=$1400; 
    katana=$13FF;
	treex=1399;
	treey=1710;
	//treeID: array[1..26] of word = [3274,3275,3276,3280,3283,3393,3394,3395,3296,3299,3290,3277,3415,3416,3417,3418,3419,3438,3439,3440,3441,3442,3438,3460,3461,3462];
begin
	while not Dead do
	begin
        if FindType(dagger,backpack) > 0 then UseObject(FindType(dagger,backpack))
        else UseObject(FindType(katana,backpack));
		WaitTargetTile(3274,treex,treey,GetZ(self));
		wait(3200);
	end;
end.