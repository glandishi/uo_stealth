//? ??? ? ?????!
Program test3;
var
    done:boolean;
    last:TDateTime;
const
	stopMsg = '� ���� ������ ���|���� �������� ��� �����|��� ���� �� �������|��� ��������� ��������� ����|��� ���� �� �������|��� ������ ���� �������|�� ��������� ��� ����|����, ��� ���� ���� �� ������|����� ���� �� ��� ������ �� ��������|�� �������� ��� ����|�� �������� �������� ����!|�� ������ �� �������, ��� ���������� ���������!|�������� ������ � ����.|�� ���������� ������� ������.|������� ������� ����, ���� ����������.|��� �� ����������� ��� ����.';
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