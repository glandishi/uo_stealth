program Test;
const
	//dagger=$0F51;
	dagger=$1400;
	treex=4578;
	treey=3850;
	//treeID: array[1..26] of word = [3274,3275,3276,3280,3283,3393,3394,3395,3296,3299,3290,3277,3415,3416,3417,3418,3419,3438,3439,3440,3441,3442,3438,3460,3461,3462];
begin
	while not Dead do
	begin
		UseObject(FindType(dagger,backpack));
		WaitTargetTile(3274,treex,treey,GetZ(self));
		wait(3200);
	end;
end.