Program Anatomy;
var
    horse:boolean;
begin
    horse:=true;
        begin
        while horse do
        begin
          useskill('Anatomy');
          if TargetPresent then
          begin 
            WaitTargetGround($0190);
            wait(3200);
          end
          else
            begin 
            horse:=false;
            end
          end;
        end;
    if not horse then
        begin
            while true do
            begin
              if not Hidden then
              begin
                useskill('Hiding');
                wait(3200);
              end;
              if Hidden then
                  useskill('Stealth');
                  wait(3200);
            end
        end;
end.