//? ??? ? ?????!
Program test3;
var
    done:boolean;
    last:TDateTime;
const
	stopMsg = 'А рыбы больше нет|Рыба покинула эти места|Вся рыба на нересте|Эта местность исчерпала себя|Тут рыба не водится|Тут только тина морская|Вы распугали всю рыбу|Жара, все рыба ушла из лимана|Кроме воды вы тут ничего не поймаете|Вы выловили всю рыбу|Вы вытащили огромную рыбу!|Вы устали от рыбалки, вам необходимо отдохнуть!|Возьмите удочку в руки.|Вы находитесь слишком далеко.|Слишком коротки руки, чтоб дотянуться.|Вам не принадлежит эта вещь.';
begin
 done:=false;
 last:=now;
 repeat
 if InJournal(stopMsg) >= 0 then
 begin
    AddToSystemJournal('NO');
	AddToSystemJournal(IntToStr(InJournal(stopMsg)));
    done:=true;
 end;
 until done;
    
end.   