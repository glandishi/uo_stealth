///////////////////////////Настройка Скрипта:///////////////////////////////////
//В Основе Скрипта вводим параметры, которые вам нужны.                       //
//Dropruda:= false;                                                           //     
//DropColor:= false;                                                          //
//GrabColor:= false;                                                          //
//Armor:= true;                                                               //
//Findpack:= true;                                                            //
//FindCorpse:= true;                                                          //
//Murder:= true;                                                              //
//FindAttack:=true;                                                           //
//С помощью команды (,info).                                                  //
//Вводим айдишники(ID) сундуков.                                              //
//Вводим координаты сундука.                                                  //
//Вводим координаты хилера.                                                   //
//Устанавливаем галочку (Pause scripts on disconnect).                        //
//Устанавливаем (Auto reconnect)от 5 секунд и более                           //                  
//ВНИМАНИЕ X2 ДОЛЖЕН БЫТЬ БОЛЬШЕ ЧЕМ X1,Y2 ДОЛЖЕН БЫТЬ БОЛЬШЕ ЧЕМ Y1.         //                                                                  
//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Удачи!!!<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<//    
Program LamberOFF;
var ChPosX, ChPosY, MyWight, MaxMyWight, PackWight, a: Word;
healing, f : Integer;
rudapack,dreva, PackKirkaID, Corpse, KatanaCorpse,Monst :Cardinal;
Guard, AttackFindID, GrabColor, Armor, Murder, FindCorpse: Boolean;
Findpack, FindAttack: Boolean;
Item:array[1..8] of Cardinal; // Объявление Массива;
const
//Слой дерева;
layer= 0; // Слой дерева. 1 верхний, 0 нижний. Изменить, если не рубит дерево;
//сундуки для работы с скриптом;
BoxBig= $75670D92; // Указываем ID основного Сундука;
BoxIte= $63DC87DF; // Указываем ID Сундука для Дров;
BoxReg= $63DC87DF; // Указываем ID Сундука для Реагентов;
BoxBan= $63DC87DE; // Указываем ID Сундука для Топоров, Бинтиков,Банок,Еды;
BoxRub= $63DC87D8; // Указываем ID Сундука для Камней и Золота;
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
        BoxArm=    $65969FF3; // Указываем ID Сундука для армора ;   ///
////////////////////////////////////////////////////////////////////////        
        LhandPL=   $7742E917;// Указываем ID Сундука для Щит Плейт   ///
////////////////////////////////////////////////////////////////////////
        HatPL=     $765BE674;// Указываем ID Сундука для  шапку;     ///
        NeckPL=    $765BE641;// Указываем ID Сундука для горжетка;   ///
        ArmsPL=    $765BE630;// Указываем ID Сундука для локти;      ///
        GlovesPL=  $765BE620;// Указываем ID Сундука для Перчатки;   ///
        TorsoPL=   $770D31F4;// Указываем ID Сундука для нагрудник;  ///
        LegsPL=    $765BE607;// Указываем ID Сундука для штаны;      ///
////////////////////////////////////////////////////////////////////////
        LhandCH=   $7742E917;// Указываем ID Сундука для Щит арчера; ///
////////////////////////////////////////////////////////////////////////
        HatCH=     $75C9BBA2;// Указываем ID Сундука для  шапку;     ///
        NeckCH=    $75C9BB63;// Указываем ID Сундука для горжетка;   ///
        ArmsCH=    $75C9BB50;// Указываем ID Сундука для локти;      ///        
        GlovesCH=  $75C9BAC3;// Указываем ID Сундука для перчатки;   ///
        TorsoCH=   $770D31F4;// Указываем ID Сундука для нагрудник;  ///
        PantsCH=   $79B6659C;// Указываем ID Сундука для штаны;      ///
////////////////////////////////////////////////////////////////////////        
        ShoesCH=   $765BE836;// Указываем ID Сундука для сапоги;     ///
////////////////////////////////////////////////////////////////////////
BoxKat= $63DC87DE; // Указываем ID Сундука для Катан;
BoxHit= $63DC87DE; // Указываем ID Сундука для Щитов;
Musor=  $63DC87D8; // Указываем ID Сундука для пустых сундуков;
//диогональ  квадрата от ВЕРХНЕЙ части экрана (слева) 
//к НИЖНЕЙ части экрана (справа);
posx1=1609;        // Начало Х;
posx2=1629;        // Конец Х;
posy1=1400;        // Начало Y;
posy2=1420;        // Конец Y;
////////////////////////////////////////////////////////////////////////////////
HouseX= 1609; //координата Х возле основного сундука или самого сундука;
HouseY= 1434;  //координата Y возле основного сундука или самого сундука;
HealerX= 4728;     //координата X хилера ;
HealerY= 3817;      //координата Y хилера ;
Krest=$5C8EF650;   //айдишник креста;
Radius=30;         //Радиус поиска предметов от 0 до 30 не более;
Radiuscorpse=30;   //Радиус поиска трупа от 0 до 30 не более;
PackKirka=$0E75;   // Type сумок в котрых лежат кирки(должны лежать в BoxBan);
////////////////////////////////////////////////////////////////////////////////
////////////////////   ПРОЦЕДУРЫ   /////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
Procedure ScanItems(); // Процедура разгрузки персонажа;
var ItemsID : Cardinal;
Items:array[1..24] of Cardinal; // Объявление Массива; 
  begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;         
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    // Ячейки Массива;
    Items[1]:= $1BE0;    // Бревна;
    Items[2]:= $0F7A;    // Реагент Блек Перл;
    Items[3]:= $0F84;    // Реагент Гарлик;
    Items[4]:= $0F8D;    // Реагент Спайдерс Силк;
    Items[5]:= $0F8C;    // Реагент Сульфуруос Ашш;
    Items[6]:= $0F7B;    // Реагент БлудМос;
    Items[7]:= $0F86;    // Реагент Мандрейк Рутс;
    Items[8]:= $0F88;    // Реагент Нагтшейдс;
    Items[9]:= $0F85;   // Реагент Гинсинг;
    Items[10]:= $0F24;    // Драгоценность;
    Items[11]:= $0F2E;   // Драгоценность;
    Items[12]:= $0F21;   // Драгоценность;
    Items[13]:= $0F23;   // Драгоценность;
    Items[14]:= $0F2F;   // Драгоценность;
    Items[15]:= $0F28;   // Драгоценность;
    Items[16]:= $0F22;   // Драгоценность;
    Items[17]:= $0F27;   // Драгоценность;
    Items[18]:= $0F29;   // Драгоценность;
    Items[19]:= $0F30;   // Драгоценность;
    Items[20]:= $0F2D;   // Драгоценность;
    Items[21]:= $0F25;   // Драгоценность;
    Items[22]:= $0F2A;   // Драгоценность; 
    Items[23]:= $0F2B;   // Драгоценность;
    Items[24]:= $0F2C;   // Драгоценность;
    //Разгрузка персонажа;
    IF FindType(Items[1], Backpack) <> 0 THEN
    begin
    repeat
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                            
    until Dead = false
    end;                 
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    IF FindType(PackKirka, BoxBan) = 0 THEN
    begin
    UOSay(Chr(39)+'resend');
    wait(6000);
    end;
    AddToSystemJournal('Выкладываем Бревна!!!');
    FindType(Items[1], Backpack);
    wait (350);
    if finditem <> 0 then
    begin
    MoveItem(FindItem,0,BoxIte,0,0,0);
    end;
    until FindItem = 0
    end;
    for  ItemsID:= 2 to 9 do
    begin
    IF FindType(Items[ItemsID], Backpack) <> 0 THEN
    begin
    repeat
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;        
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    IF FindType(PackKirka, BoxBan) = 0 THEN
    begin
    UOSay(Chr(39)+'resend');
    wait(6000);
    end;
    AddToSystemJournal('Выкладываем реагенты!!!');
    FindType(Items[ItemsID], Backpack);
    wait (350);
    if finditem <> 0 then
    begin
    MoveItem(FindItem,0,BoxReg,0,0,0);
    end;
    until FindItem = 0
    end;
    end;
    IF FindType($0EED, Backpack) <> 0 THEN
    begin
    repeat
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                      
    until Dead = false
    end;        
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100); 
    IF FindType(PackKirka, BoxBan) = 0 THEN
    begin
    UOSay(Chr(39)+'resend');
    wait(6000);
    end;
    AddToSystemJournal('Выкладываем Золото!!!');
    FindType($0EED, Backpack);
    wait (350);
    if finditem <> 0 then
    begin
    MoveItem(FindItem,0,BoxReg,0,0,0);
    end;
    until FindItem = 0
    end;      
    for  ItemsID:= 10 to 24 do
    begin
    IF FindType(Items[ItemsID], Backpack) <> 0 THEN
    begin
    repeat 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                       
    until Dead = false
    end;        
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    IF FindType(PackKirka, BoxBan) = 0 THEN
    begin
    UOSay(Chr(39)+'resend');
    wait(6000);
    end;
    AddToSystemJournal('Выкладываем драгоценности!!!');
    FindType(Items[ItemsID], Backpack);
    wait (350);
    if finditem <> 0 then
    begin
    MoveItem(FindItem,0,BoxRub,0,0,0);
    end;
    until FindItem = 0
    end;
    end;

 if FindType($0F43, Backpack) = 0 THEN 
    begin
    if FindType(PackKirka, BoxBan) <> 0 THEN 
    begin
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                       
    until Dead = false
    end;        
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    if FindType(PackKirka, BoxBan) = 0 THEN IgnoreReset;
    PackKirkaID:=FindType(PackKirka, BoxBan);
    if PackKirkaID <> 0 then
    begin
    UseObject(PackKirkaID);
    wait(1000);
    AddToSystemJournal('Берём топор!!!');
    FindType($0F43, PackKirkaID);
    if Finditem <> 0 then grab(FindItem,0);
    wait (350);
    //if FindItem = 0 then ignore(PackKirkaID);
    if FindItem = 0 then MoveItem(PackKirkaID,0,Musor,0,0,0);
    end;
    end;
    end;
  end; 
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////    
//Процедура проверки стамины и HP  персонажа;
Procedure HitsStamina(); 
   var fh: Integer;
       Band: Cardinal;
    begin
    fh:= 1;
   repeat
    repeat
    //проверка наличия катаны и щита;
    if Str > 79 then
    begin
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;       
    if ObjAtLayer(RhandLayer) = 0 then
    begin
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxKat);
    wait(100); 
    if FindType($13B6, BoxKat) <> 0 THEN
    begin
    FindType($13B6, BoxKat);
    wait (100);   
    Equip(RhandLayer, finditem);
    wait (250);
    AddToSystemJournal('Берем с сундука Катану!!!');
    end;
    end;
    end;
    ////////////////////////////////////////////////////////////////////////////  
    if Str > 119 then
    begin
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;       
    if ObjAtLayer(LhandLayer) = 0 then
    begin
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxHit);
    wait(100); 
    UseObject(LhandPL);
    wait(100); 
    if FindType($1B76, LhandPL) <> 0 THEN
    begin
    FindType($1B76, LhandPL);
    wait (350);   
    Equip(LhandLayer, finditem);
    AddToSystemJournal('Берем с сундука Щит!!!');
    end;
    end;
    end;
    //////////////////////////////////////////////////////////////////////////// 
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;      
    if ObjAtLayer(LhandLayer) = 0 then
    begin
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxHit);
    wait(100);
    UseObject(LhandCH);
    wait(100);  
    if FindType($1B74, LhandCH) <> 0 THEN
    begin
    FindType($1B74, LhandCH);
    wait (350);   
    Equip(LhandLayer, finditem);
    AddToSystemJournal('Берем с сундука Щит!!!');
    end;
    end;
    end;
    //////////////////////////////////////////////////////////////////////////// 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;       
    if FindType($3FF8, BackPack) <> 0 THEN
    begin
    FindType($3FF8, BackPack);
    wait (100);   
    Useobject(finditem);
    AddToSystemJournal('Одеваем плащь!!!');
    wait(3000);
    end;  
    ////////////////////////////////////////////////////////////////////////////                
    ////////////////////////////////////////////////////////////////////////////
    /////////////////////Dress//////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    // Одеваем перчатки плейт;
    if Armor = true THEN
    begin
    if Str > 119 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(GlovesLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100); 
        UseObject(GlovesPL);
        wait(100); 
            if FindType($1414, GlovesPL) <> 0 THEN
            begin
            FindType($1414, GlovesPL);
            wait (350);   
            Equip(GlovesLayer, finditem);
            AddToSystemJournal('Одеваем перчатки!!!');
            end;
        end;                
    end;
    
   // Одеваем головной убор плейт;
    if Str > 119 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(HatLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(HatPL);
        wait(100);  
            if FindType($1412, HatPL) <> 0 THEN
            begin
            FindType($1412, HatPL);
            wait (350);   
            Equip(HatLayer, finditem);
            AddToSystemJournal('Одеваем головной убор!!!');
            end;
        end;                
    end; 
    
   // Одеваем штаны плейт;
    if Str > 119 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(LegsLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(LegsPL);
        wait(100);  
            if FindType($141A, LegsPL) <> 0 THEN
            begin
            FindType($141A, LegsPL);
            wait (350);   
            Equip(LegsLayer, finditem);
            AddToSystemJournal('Одеваем штаны!!!');
            end;
        end;                
    end;
   
   // Одеваем нагрудник плейт;
    if Str > 119 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(TorsoLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(TorsoPL);
        wait(100);  
            if FindType($1C04 , TorsoPL) <> 0 THEN
            begin
            FindType($1C04 , TorsoPL);
            wait (350);   
            Equip(TorsoLayer, finditem);
            AddToSystemJournal('Одеваем нагрудник!!!');
            end;
        end;                
    end;      
    
   // Одеваем горжетку плейт;
    if Str > 119 then
    begin 
     if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(NeckLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(NeckPL);
        wait(100);  
            if FindType($1413, NeckPL) > 0 THEN
            begin
            FindType($1413, NeckPL);
            wait (350);   
            Equip(NeckLayer, finditem);
            AddToSystemJournal('Одеваем горжетку!!!');
            end;
        end;                
    end;
    
   // Одеваем локти плейт;
    if Str > 119 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(ArmsLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(ArmsPL);
        wait(100);  
            if FindType($1410, ArmsPL) <> 0 THEN
            begin
            FindType($1410, ArmsPL);
            wait (350);   
            Equip(ArmsLayer, finditem);
            AddToSystemJournal('Одеваем локти!!!');
            end;
        end;                
    end;  
    
    // Одеваем перчатки чейн;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(GlovesLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(GlovesCH);
        wait(100);  
            if FindType($13F2, GlovesCH) <> 0 THEN
            begin
            FindType($13F2, GlovesCH);
            wait (350);   
            Equip(GlovesLayer, finditem);
            AddToSystemJournal('Одеваем перчатки!!!');
            end;
        end;                
    end;
    ////////////////////////////////////////////////////////////////////////////    
    // Одеваем головной убор;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(HatLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(HatCH);
        wait(100);  
            if FindType($13BB, HatCH) <> 0 THEN
            begin
            FindType($13BB, HatCH);
            wait (350);   
            Equip(HatLayer, finditem);
            AddToSystemJournal('Одеваем головной убор!!!');
            end;
        end;                
    end;
    ////////////////////////////////////////////////////////////////////////    
    // Одеваем штаны;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(PantsLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(PantsCH);
        wait(100);  
            if FindType($13C3, PantsCH) <> 0 THEN
            begin
            FindType($13C3, PantsCH);
            wait (350);   
            Equip(PantsLayer, finditem);
            AddToSystemJournal('Одеваем штаны!!!');
            end;
        end;                
    end;

    ////////////////////////////////////////////////////////////////////////////
    // Одеваем сапоги;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(ShoesLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100); 
        UseObject(ShoesCH);
        wait(100); 
            if FindType($1711, ShoesCH) <> 0 THEN
            begin
            FindType($1711, ShoesCH);
            wait (350);   
            Equip(ShoesLayer, finditem);
            AddToSystemJournal('Одеваем сапоги!!!');
            end;
        end;                
    end;
  
////////////////////////////////////////////////////////////////////////////
    // Одеваем нагрудник;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(TorsoLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(TorsoCH);
        wait(100);  
            if FindType($13C4 , TorsoCH) <> 0 THEN
            begin
            FindType($13C4 , TorsoCH);
            wait (350);   
            Equip(TorsoLayer, finditem);
            AddToSystemJournal('Одеваем нагрудник!!!');
            end;
        end;                
    end;      
    ////////////////////////////////////////////////////////////////////////////
    // Одеваем горжетку;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(NeckLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(NeckCH);
        wait(100);  
            if FindType($13D6, NeckCH) <> 0 THEN
            begin
            FindType($13D6, NeckCH);
            wait (350);   
            Equip(NeckLayer, finditem);
            AddToSystemJournal('Одеваем горжетку!!!');
            end;
        end;                
    end;
    ////////////////////////////////////////////////////////////////////////////
     // Одеваем локти;
    if Str > 79 then
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
        if ObjAtLayer(ArmsLayer) = 0 then
        begin
        NewMoveXY(HouseX, HouseY, false, 1, true );
        wait(100);
        UseObject(Backpack); //Открываем Рюкзак;
        wait(100);
        UseObject(BoxBig);
        wait(100);
        UseObject(BoxArm);
        wait(100);
        UseObject(ArmsCH);
        wait(100);  
            if FindType($13EF, ArmsCH) <> 0 THEN
            begin
            FindType($13EF, ArmsCH);
            wait (350);   
            Equip(ArmsLayer, finditem);
            AddToSystemJournal('Одеваем локти!!!');
            end;
        end;                
    end;
    
 end;//конец проверки армора;
    ////////////////////////////////////////////////////////////////////////////
    //Кушаем Фишстейк; 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;       
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    if fh = 1 then
    begin
    if FindType($097B, BoxBan) <> 0 THEN
    begin
    UseObject(finditem);
    wait(3000);
    fh:= 0;
    end;
    end;
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////////////////// 
    //Проверка количества бинтиков в рюкзаке;
    //Count(FindType($0E21, Backpack));
    if Count($0E21) < 5 then
    begin
    Band:=FindType($0E21, BoxBan);
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;        
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    AddToSystemJournal('Берем бинты!!!');
    wait (350);
    grab(Band,5-Count($0E21));
    end;
    ////////////////////////////////////////////////////////////////////////////
    //Если HP не полное лечимся бинтами,если хп меньше 60 пьем банку;  
    if (Life < MaxLife) then
    begin
    if (Life <= 60) then
    begin
    if FindType( $0F0C, Backpack) = 0 THEN
    begin
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);
    if FindType($0F0C, BoxBan) > 0 THEN
    begin 
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;
    NewMoveXY(HouseX, HouseY, false, 1, true );
    wait(100);
    UseObject(Backpack); //Открываем Рюкзак;
    wait(100);
    UseObject(BoxBig);
    wait(100);
    UseObject(BoxBan);
    wait(100);        
    FindType( $0F0C, BoxBan);
    AddToSystemJournal('Берём ГХ!!!');
    wait (350);
    grab(FindItem,1);
    end; 
    UOSay(Chr(39)+'pc heal self' );
    AddToSystemJournal('Востанавливаем Здоровье!!!');
    wait(8000);
    end;
    end;
    if (Life <= 60) then 
    begin
    if FindType( $0F0C,backpack) <> 0 then
    begin
    UOSay(Chr(39)+'pc quaf heal' );
    AddToSystemJournal('Востанавливаем Здоровье,пьем хилку!!!');
    wait(8000);
    end;
    end;
    if (Life > 60) then 
    begin
    UOSay(Chr(39)+'pc heal self' );
    AddToSystemJournal('Востанавливаем Здоровье!!!');
    wait(8000);
    end; 
    end;
   

     //Берем рефреш если Stam меньше 100, и используем;
     if Stam < 100 THEN 
     begin
		 NewMoveXY(HouseX, HouseY, false, 1, true );
		 wait(100);
		 UseObject(Backpack); //Открываем Рюкзак;
		 wait(100);
		 UseObject(BoxBig);
		 wait(100);
		 UseObject(BoxBan);
		 wait(100);
		 if FindType($0F0B, Backpack) = 0 THEN 
		 begin
			if FindType($0F0B, BoxBan) <> 0 THEN
			begin 
				if Dead = true then
				begin
					Disconnect;
					repeat
						NewMoveXY(HealerX, HealerY, false, 0, true );
						if Murder = true  then
						begin
							useobject(Krest);
							if Guard = true then
							begin
								if (Life >= 1) then
								begin
									wait(500);
									UOSay('Guards' );
								end;
							end;
							wait(3000);
						end;
						if Murder = false  then
						begin
							if Guard = true then
							begin
								if (Life >= 1) then
								begin
									wait(500);
									UOSay('Guards' );
								end;
							end;
						end;                   
					until Dead = false
				end;       
				NewMoveXY(HouseX, HouseY, false, 1, true );
				wait(100);
				UseObject(Backpack); //Открываем Рюкзак;
				wait(100);
				UseObject(BoxBig);
				wait(100);
				UseObject(BoxBan);
				wait(100);
				FindType( $0F0B, BoxBan);
				AddToSystemJournal('Берём рефреш!!!');
				wait (350);
				grab(FindItem,1);
				UOSay(Chr(39)+'pc quaf refresh' );
				wait (8100);
			end;
		end;
    end;
	until Stam > 100
    //Выкладываем пустую бутылку
     if FindType($0F0E, Backpack) <> 0 THEN  
     begin
     repeat
    if Dead = true then
    begin
    Disconnect;
    repeat
    NewMoveXY(HealerX, HealerY, false, 0, true );
    if Murder = true  then
    begin
    useobject(Krest);
    if Guard = true then
    begin
    if (Life >= 1) then
    begin
    wait(500);
    UOSay('Guards' );
    end;
    end;
    wait(3000);
    end;
    if Murder = false  then
     begin
     if Guard = true then
     begin
     if (Life >= 1) then
     begin
     wait(500);
     UOSay('Guards' );
     end;
     end;
     end;                   
    until Dead = false
    end;        
     NewMoveXY(HouseX, HouseY, false, 1, true );
     wait(100);
     UseObject(Backpack); //Открываем Рюкзак;
     wait(100);
     UseObject(BoxBig);
     wait(100);
     UseObject(BoxBan);
     wait(100);
     AddToSystemJournal('Выкладываем бутылку-и!!!');
     FindType($0F0E, Backpack);
     wait (350);
     MoveItem(FindItem,0,BoxBan,0,0,0);
     until FindItem = 0
     end;
     if FindType( $0F0C, Backpack) = 0 THEN
     begin
     if FindType($0F0C, BoxBan) > 0 THEN
     begin 
     if Dead = true then
     begin
     Disconnect;
     repeat
     NewMoveXY(HealerX, HealerY, false, 0, true );
     if Murder = true  then
     begin
     useobject(Krest);
     wait(3000);
     end;                     
     until Dead = false
     end;        
     NewMoveXY(HouseX, HouseY, false, 1, true );
     wait(100);
     UseObject(Backpack); //Открываем Рюкзак;
     wait(100);
     UseObject(BoxBig);
     wait(100);
     UseObject(BoxBan);
     wait(100);
     FindType( $0F0C, BoxBan);
     AddToSystemJournal('Берём ГХ!!!');
     wait (350);
     grab(FindItem,1);
     end; 
     end;
  until Life = MaxLife   
 end;  
//Процедура Востанавливать Здоровья;
Procedure MyHits();
    begin
    if (Life < MaxLife) then 
    begin
    if (Life <= 75) then 
    begin
    if FindType( $0F0C, Backpack) <> 0 THEN
    begin
    AddToSystemJournal('Здоровье чара>>>>>>>>>: ' + IntToStr(Life));
    AddToSystemJournal('Максимум здоровья чара: ' + IntToStr(MaxLife));
    UOSay(Chr(39)+'pc quaf heal' );
    end;
    AddToSystemJournal('Здоровье чара>>>>>>>>>: ' + IntToStr(Life));
    AddToSystemJournal('Максимум здоровья чара: ' + IntToStr(MaxLife));     
    UOSay(Chr(39)+'pc heal self' );
    end;
    if (Life > 75) then wait (350);
    begin
    AddToSystemJournal('Здоровье чара>>>>>>>>>: ' + IntToStr(Life));
    AddToSystemJournal('Максимум здоровья чара: ' + IntToStr(MaxLife));     
    UOSay(Chr(39)+'pc heal self' );
    end;
    end;
end;
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
Procedure UseKirkaTile;
var Pickaxe: Cardinal;
    Tile0: TStaticCell;
    mytile: Word;
 begin
   UseObject(Backpack); //Открываем Рюкзак;
   Tile0:=ReadStaticsXY(ChPosX, ChPosY, WorldNum); 
   AddToSystemJournal('Сканируем Tile');
    If Tile0.StaticCount > 1 then
    begin
      mytile := Tile0.Statics[0].Tile;// 1 или ноль , если не рубит . 
      if (mytile > 3275) and (mytile < 3305) then
      begin
      
      AddToSystemJournal('Tile найден');
      Addtosystemjournal('Tile   = '+IntToStr(mytile));
      Addtosystemjournal('Layers = '+IntToStr(Tile0.StaticCount));
      AddToSystemJournal('Направляемся на координату||'+ IntToStr(ChPosX)+('||')+ IntToStr(ChPosY)+('||'));
      NewMoveXY(ChPosX, ChPosY, false, 1, True );
        if FindType($0F43, Backpack) <> 0 THEN  
        begin
        If TargetPresent Then CancelTarget;
        begin
        Pickaxe:=FindType($0F43, Backpack); 
        useobject(Pickaxe);
        WaitForTarget(3000);
        AddToSystemJournal('Рубим');
        end;
        end;
            if targetpresent then
            begin
            TargetToTile(mytile ,ChPosX,ChPosY,GetZ(self));
            wait(3000);
            end;
       end;
    end;  
 end;    
////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////// 
Procedure FindMonstr;
 var i: Integer;
   begin
   ClearBadLocationList;
   ClearBadObjectList;
   dreva:=0;
   Monst:=0;
       //1;
        FindDistance := 30;
        dreva:=FindTypeEx($0191,$07D6,Ground,false);
        if dreva <> 0 then
        begin
        for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(dreva), GetY(dreva), false, 1, True );
        end;
        if AttackFindID = true then Attack(dreva);
        AddToSystemJournal('Древодреада найдена направляемся  к ней!!!  ');
        end;
        //2;
        if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($002F,ground);
        if Monst <> 0 then
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );
        end;
        end; 
        end;
        //3;
         if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($0008,ground);
        if Monst <> 0 then 
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );
        end; 
        end;
        //4;
        if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($0032,ground);
        if Monst <> 0 then
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );;
        end;    
        end; 
        end;
        //5;
        if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($001A,ground);
        if Monst <> 0 then 
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );
        end; 
        end;
        end;
        //6;
        if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($0018,ground);
        if Monst <> 0 then
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );
        end;
        end; 
        end;
        //7;
        if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($0039,ground);
        if Monst <> 0 then
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );
        end;
        end; 
        end;
        //8;
        if Finditem = 0 then
        begin
        FindDistance := 30;
        Monst:=FindType($0012,ground);
        if Monst <> 0 then
         begin
         AddToSystemJournal('Монстр найден направляемся  к нему!!!  ');
         if AttackFindID = true then Attack(Monst);
         for i:=1 to 10 do 
        begin
        wait(100);
   if (not Paralyzed) then NewMoveXY(GetX(Monst), GetY(Monst), false, 1, True );
        end;
        end; 
        end;
    end;
//Функция максимальный вес;
Procedure MyStr();
begin
a:= Str;
AddToSystemJournal('Сила чара>>>>>>: ' + IntToStr(Str));
AddToSystemJournal('Ловкость чара>>: ' + IntToStr(Dex));
AddToSystemJournal('Интеллект чара>: ' + IntToStr(Int));
MyWight:= (a * 3) - 30;
AddToSystemJournal('Ваш вес >>>>>>>>>' + IntToStr(Weight));
AddToSystemJournal('Лимит веса >>>>>>' + IntToStr(MyWight));
end;
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////ГЛАВНАЯ ПРОГРАММА///////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////// 
 begin                                                                        //
 ///////////////////////////////////////////////////////////////////////////////
 /////////////////////Настройки Скрипта/////////////////////////////////////////
 ///////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////                                                                         
//                     не трогать....                                       ////
SetARStatus(true);                                                          ////
//Возвращает режим реконнектора: True - реконектор включен, False - выключен////
////////////////////////////////////////////////////////////////////////////////
//                     не трогать....                                       ////
SetPauseScriptOnDisconnectStatus(true);                                     ////
//Задает поведение скрипта при дисконнекте. если True то при дисконнекте,   ////
//все запущенные скрипты встанут на паузу.;                                 ////
////////////////////////////////////////////////////////////////////////////////
GetRunUnMountTimer;                                                           //
GetWalkUnmountTimer;                                                          //
 ///////////////////////////////////////////////////////////////////////////////
 GrabColor:= true; //если true будет подымать с земли логи;                 
 Armor:= false; //если true будет одевать армор,если false не одевает;
 FindCorpse:= true;//если true будет искать труп резать;
 Findpack:= false;//если true сразу несет в дом. false дальше по координатам; 
 Murder:= false; //если true будет ресаться от креста(Krest),если false нет;           
 AttackFindID:= true;//если true  атакует монстра,false нет;
 FindAttack:= true;//если true  сразу атакует монстра,felse нет;
 Guard:= false;//если true кричит после воскрешения Guards; 
 ///////////////////////////////////////////////////////////////////////////////
 UOSay(Chr(39)+'pc Repeat off' );                                             //
 AddToSystemJournal('pc Repeat off!!!');                                      //
 wait(5000);                                                                  //
 // Ячейки Массива;                                                           //
    Item[1]:= $0F7A;    // Реагент Блек Перл;                                 //
    Item[2]:= $0F84;    // Реагент Гарлик;                                    //
    Item[3]:= $0F8D;    // Реагент Спайдерс Силк;                             //
    Item[4]:= $0F8C;    // Реагент Сульфуруос Ашш;                            //
    Item[5]:= $0F7B;    // Реагент БлудМос;                                   //
    Item[6]:= $0F86;    // Реагент Мандрейк Рутс;                             //
    Item[7]:= $0F88;    // Реагент Нагтшейдс;                                 //
    Item[8]:= $0EED;    // Золото;                                            //                                                                                                                                                     
  while true do                                                               //
  begin                                                                       //
  //AddToSystemJournal(IntToStr(timer));                                      //
    for ChPosX:= Posx1 to Posx2 do                                            //
    begin                                                                     //
     for ChPosY:= Posy1 to Posy2 do                                           //
     begin                                                                    //
      repeat                                                                  //
      PackWight:= Weight;                                                     //
      AddToSystemJournal('|||||||||||||||||||||||||||||||||||||');            //                                                                                                   
                if GrabColor = true then                                      //
                 begin                                                        //
                 if FindType($1BE0, Ground) > 0 then                          //
                 begin                                                        //
                 repeat                                                       //
                 FindDistance := 2;                                           //
                 FindType($1BE0, Ground);                                     //
                 wait(100);                                                   //
                 Grab(finditem,0);                                            //          
                 Ignore(finditem);                                            //
                 wait(250);                                                   //
                 until FindItem = 0                                           //
                 end;                                                         //
                 end;                                                         //
           MyStr();                                                           //
      //////////////////////////////////////////////////////////////////////////                                
            if MyWight < Weight then                                          //
         begin                                                                //
         IgnoreReset;                                                         //
         MaxMyWight:= MyWight + 30;                                           //
         if MaxMyWight < Weight then                                          //
          begin                                                               //
          UseObject(Backpack); //Открываем Рюкзак;                            //
          wait(350);                                                          //
            if FindTypeEx($1BE0,$0000 ,Backpack,false) > 0 then               //
            begin                                                             //
            repeat                                                            //
            rudapack:=FindTypeEx($1BE0,$0000 ,Backpack,false);                //
            wait (100);                                                       //
            Drop(rudapack, 10, GetX(self),GetY(self),GetZ(self));             //
            wait(350);                                                        //
            until Weight < MaxMyWight                                         //
            end;                                                              //
          end;                                                                //
        AddToSystemJournal('Возвращаемся домой для выгрузки');                //
        ScanItems;                                                            //
        HitsStamina;                                                          //
        end;                                                                  //
      //////////////////////////////////////////////////////////////////////////  
        if FindType($0F43, Backpack) = 0 THEN                                 //
        begin                                                                 //
        IgnoreReset;                                                          //
        ScanItems;                                                            //
        HitsStamina;                                                          //
        end;                                                                  //
      //////////////////////////////////////////////////////////////////////////
        if FindType($0E21, Backpack) = 0 THEN                                 //
        begin                                                                 //
        IgnoreReset;                                                          //
        ScanItems;                                                            //
        HitsStamina;                                                          //
        end;                                                                  //
        if FindAttack = false then UseKirkaTile;                              //
        if FindAttack = true then                                             //
        begin                                                                 //
        FindMonstr;                                                           //
        if (dreva = 0)and (Monst = 0) then UseKirkaTile;                      //                                                     
        end;                                                                  //                                                        
        ////////////////////////////////////////////////////////////////////////
       //Поиск Трупа в радиусе одной клетки,чистит и игнорирует после этого;  //
       if FindCorpse = True then                                              //
        begin                                                                 //                                                             
          if ObjAtLayer(RhandLayer) = 0 then                                  //
          begin                                                               //
              if FindType($13B6, Backpack) = 0 THEN                           //
              begin                                                           //
              ScanItems;                                                      //
              HitsStamina;                                                    //
              end;                                                            //
          end;                                                                //
       /////////////////////////////////////////////////////////////////////////
          if Life = Maxlife then                                              //
          begin                                                               //                                                                                                                          
          FindDistance := Radiuscorpse;                                       //
          Corpse:=FindType($2006 ,ground);                                    //
              if Corpse <> 0 then                                             //
              begin                                                           //
              wait(3200);                                                     //
              NewMoveXY(GetX(Corpse), GetY(Corpse), false, 1, True );         //
                  if ObjAtLayer(RhandLayer) > 0 THEN                          //
                  begin                                                       //
                  If TargetPresent Then CancelTarget;                         //
                      begin                                                   //
                      KatanaCorpse:=ObjAtLayer(RhandLayer);                   //
                      useobject(KatanaCorpse);                                //
                      WaitForTarget(3000);                                    //
                      AddToSystemJournal('Режим труп');                       //
                      end;                                                    //
                  end;                                                        //
          if targetpresent then                                               //
          begin                                                               //
          TargetToObject(Corpse);                                             //
          //задержка на использование Катаны(разрезаем труп);                 //
          wait(3200);                                                         //
          useobject(Corpse);                                                  //
          wait(200);                                                          //
        ////////////////////////////////////////////////////////////////////////
        //Поиск предметов в трупе без дополнительной проверки;                //
        for f:= 1 to 8 do                                                     //
        begin                                                                 //
          IF FindType($2006, ground) <> 0 THEN                                //
          begin                                                               //
          AddToSystemJournal('Нашли что-то кладём в рюкзак!!!');              //
          NewMoveXY(GetX(Corpse), GetY(Corpse), false, 1, True );             //
          FindDistance := 30;                                                 //
          if FindType(Item[f], Corpse) <> 0 then                              //
          begin                                                               //
          wait(350);                                                          //
          Grab(finditem,0);                                                   //
          end;                                                                //
          end;                                                                //                                                                   
        end;                                                                  //                                                                   
        ignore(Corpse);                                                       //
        end;                                                                  //
       end;                                                                   //
       end;                                                                   //
       end;                                                                   //
       /////////////////////////////////////////////////////////////////////////                  
       /////////////////////////////////////////////////////////////////////////
        // Поиск предметов на земле;                                          //
         for f:= 1 to 8 do                                                    //
         begin                                                                //
         FindDistance := Radius;                                              //
         IF FindType(Item[f], ground) <> 0 THEN                               //
         begin                                                                //
         FindDistance := Radius;                                              //
         FindType(Item[f], ground);                                           //
         NewMoveXY(GetX(finditem), GetY(finditem), false, 1, True );          //
          AddToSystemJournal('Нашли что-то кладём в рюкзак!!!');              //
         if FindType(Item[f], ground) <> 0 then                               //
         begin                                                                //
          wait(350);                                                          //
          Grab(finditem,0);                                                   //
          end;                                                                //
          end;                                                                //
         end;                                                                 //
        if (Stam < 40) then                                                   //
        begin                                                                 //
        AddToSystemJournal('Количество стамины критическое возвращаемся домой');
        ScanItems;                                                            //
        HitsStamina;                                                          //
        end;                                                                  //
          if FindAttack = false then UseKirkaTile;                            //
          if FindAttack = true then                                           //
             begin                                                            //
             FindMonstr;                                                      //
             if (dreva = 0)and (Monst = 0) then UseKirkaTile;                 //                                                     
             end;                                                             //                                          
      ////////////////////////////////////////////////////////////////////////// 
       if (Life < MaxLife) then                                               //
       begin                                                                  //
       wait(3100);                                                            //
        repeat                                                                //
        MyStr();                                                              //
        MaxMyWight:= MyWight + 30;                                            //
        ////////////////////////////////////////////////////////////////////////
         if MaxMyWight < Weight then                                          //
          begin                                                               //
          UseObject(Backpack); //Открываем Рюкзак;                            //
          wait(350);                                                          //
            if FindType($1BE0,Backpack) <> 0 then                             //
            begin                                                             //
            repeat                                                            //
            rudapack:=FindType($1BE0,Backpack);                               //
            wait (100);                                                       //
            Drop(rudapack, 5, GetX(self),GetY(self),GetZ(self));              //
            wait(350);                                                        //
            until Weight < MaxMyWight                                         //
            end;                                                              //
          end;                                                                //
        AddToSystemJournal('Персонаж Атакован!!!');                           //
        MyHits;                                                               //
        ////////////////////////////////////////////////////////////////////////
        if (Life < MaxLife) then                                              //
        begin                                                                 //
        for healing:= 1 to 10 do                                              //
          begin                                                               //
          FindMonstr;                                                         //
          if (Life < 50) then                                                 //
          begin                                                               //
          AddToSystemJournal('Количество здоровья критическое возвращаемся домой');
          IgnoreReset;                                                        //
          ScanItems;                                                          //
          HitsStamina;                                                        //
          end;                                                                //
          //////////////////////////////////////////////////////////////////////     
           if (Stam < 40) then                                                //
           begin                                                              //
           AddToSystemJournal('Количество стамины критическое возвращаемся домой');
           IgnoreReset;                                                       //
           ScanItems;                                                         //
           HitsStamina;                                                       //
           end;                                                               //
          if FindType($0E21, Backpack) = 0 THEN                               //
          begin                                                               //
          IgnoreReset;                                                        //
          ScanItems;                                                          //
          HitsStamina;                                                        //
          end;                                                                //               
          end;                                                                //
        end;                                                                  //
        ////////////////////////////////////////////////////////////////////////        
          if FindType($0E21, Backpack) = 0 THEN                               //
          begin                                                               //
          IgnoreReset;                                                        //
          ScanItems;                                                          //
          HitsStamina;                                                        //
          end;                                                                //
        until (Life = MaxLife)                                                //
       end;                                                                   //
      until PackWight = Weight;                                               //
       /////////////////////////////////////////////////////////////////////////
       if Findpack = true then                                                //
       begin                                                                  //
       if FindType($1BE0, Backpack) <> 0 THEN                                 //
       begin                                                                  //      
       ScanItems;                                                             //
       HitsStamina;                                                           //
       end;                                                                   //
       end;                                                                   //
 ///////////////////////////////////////////////////////////////////////////////                                               
     end;                                                                     //
    end;                                                                      //
  end;                                                                        //
 end.                                                                         //
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////