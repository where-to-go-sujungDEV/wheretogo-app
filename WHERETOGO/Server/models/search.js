import db from "../config/dbConnection.js";

export const getSearchResults = (data, result) => {
    var qr = 'select eventID, eventName, (select cName from CategoryTBL where cCode = EventTBL.kind) as kind, startDate, endDate, pic, (select count(*) from UserSavedTBL where UserSavedTBL.eventID = EventTBL.eventID) as savedNum, (select count(*) from UserVisitedTBL where UserVisitedTBL.eventID = EventTBL.eventID)as visitedNum from EventTBL ';
    qr += ' where ';
    var kind = '';           
                
    if(data.aCode != 0){
        qr += ' areacode = ';
        qr += data.aCode;
    }
                    

    if(data.aDCode != 0){
        qr += ' and sigungucode = ';
        qr += data.aDCode;
    }

    if((data.aCode != 0)||(data.aCode != 0)){
        qr += ' and ';
    }

    if((data.fromD == "")&&(data.toD == "")) {
        qr += ' endDate >= now() ';
    }
    else if((data.fromD == "")&&(data.toD != "")){
        qr += ' endDate >= now() and startDate <= \'';
        qr += data.toD;
        qr += '\' ';
    }
    else if((data.fromD != "")&&(data.toD == "" )){
        qr += ' endDate >= \'';
        qr += data.fromD;
        qr += '\' ';
    }
    else if((data.fromD != "")&&(data.toD != "")){
        qr += ' endDate >= \'';
        qr += data.fromD;
        qr += '\' and startDate <= \'';
        qr += data.toD;
        qr += '\' ';
    }
                    
    if(data.search != "") {
        qr += ' and (eventName like \'\%';
        qr += data.search;
        qr += '\%\' or overview like \'\%'
        qr += data.search;
        qr += '\%\' or addr1 like \'\%'
        qr += data.search;
        qr += '\%\' or addr2 like \'\%'
        qr += data.search;
        qr += '\%\' or eventplace like \'\%'
        qr += data.search;
        qr += '\%\' or price like \'\%'
        qr += data.search;
        qr += '\%\') ';
    }

    if(data.free == 1){
        qr += ' and price like \'\%무료\%\' ';
    }
                    
                        if((data.k1 == 1)||(data.k2== 1)||(data.k3== 1)||(data.k4== 1)||(data.k5== 1)||(data.k6== 1)||(data.k7== 1)||(data.k8== 1)||(data.k9== 1)||(data.k10== 1)||(data.k11== 1)||(data.k12== 1)||(data.k13== 1)||(data.k14== 1)||(data.k15== 1)){
                            qr += ' and kind in (';

                            if(data.k1 == 1){
                                kind += ' \'A02070100\'';
                            }

                            if(data.k2 == 1){
                                if(!kind.length)kind += ' \'A02070200\'';
                                else kind += ', \'A02070200\'';
                            }

                            if(data.k3 == 1){
                                if(!kind.length)kind += ' \'A02080100\'';
                                else kind += ', \'A02080100\'';
                            }

                            if(data.k4 == 1){
                                if(!kind.length)kind += ' \'A02080200\'';
                                else kind += ', \'A02080200\'';
                            }

                            if(data.k5 == 1){
                                if(!kind.length)kind += ' \'A02080300\'';
                                else kind += ', \'A02080300\'';
                            }

                            if(data.k6 == 1){
                                if(!kind.length)kind += ' \'A02080400\'';
                                else kind += ', \'A02080400\'';
                            }

                            if(data.k7 == 1){
                                if(!kind.length)kind += ' \'A02080500\'';
                                else kind += ', \'A02080500\'';
                            }

                            if(data.k8 == 1){
                                if(!kind.length)kind += ' \'A02080600\'';
                                else kind += ', \'A02080600\'';
                            }

                            if(data.k9 == 1){
                                if(!kind.length)kind += ' \'A02080700\'';
                                else kind += ', \'A02080700\'';
                            }

                            if(data.k10 == 1){
                                if(!kind.length)kind += ' \'A02080800\'';
                                else kind += ', \'A02080800\'';
                            }

                            if(data.k11 == 1){
                                if(!kind.length)kind += ' \'A02080900\'';
                                else kind += ', \'A02080900\'';
                            }

                            if(data.k12 == 1){
                                if(!kind.length)kind += ' \'A02081000\'';
                                else kind += ', \'A02081000\'';
                            }

                            if(data.k13 == 1){
                                if(!kind.length)kind += ' \'A02081100\'';
                                else kind += ', \'A02081100\'';
                            }

                            if(data.k14 == 1){
                                if(!kind.length)kind += ' \'A02081200\'';
                                else kind += ', \'A02081200\'';
                            }

                            if(data.k15 == 1){
                                if(!kind.length)kind += ' \'A02081300\'';
                                else kind += ', \'A02081300\'';
                            }

                            qr += kind;
                            qr += ') ';
                        }
                    
                    
                        qr += ' ORDER BY ';
                    
                    if(data.align == 'start'){
                        qr += ' startDate ASC;';
                    }
                    else if(data.align == 'end'){
                        qr += ' endDate ASC;';
                    }
                    else {
                        qr+= ' savedNum DESC;';
                    }

                    db.query(qr, (err, results) => {             
                        if(err) {
                            result(500, {
                                code : 500,
                                isSuccess : false,
                                err}, null);
                        } 
                        else if(!results.length){
                            result(203, null, {
                                code : 200,
                                isSuccess : true,
                                isExist : false,
                                });
                        }
                        else {
                            result(200, null, {
                                code : 200,
                                isSuccess : true,
                                isExist : true,
                                results});
                        }
                    })  
}


export const getHotSearchResults = (result) => {
    db.query("Select word, count(word) as count from SearchTBL Group By word ORDER BY count(*) DESC LIMIT 10;", (err, results) => {             
        if(err) {
            result(500, {
                code : 500,
                isSuccess : false,
                err}, null);
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results});
        }
    });   
}


export const updateSearchCount = (data, result) => {
    if(!data.search){
        result(200, null,
            {code : 204,
            isSuccess : true,
            msg : "검색어가 입력되지 않았습니다."
        });
    }
    else{           
            db.query("insert into SearchTBL (word) values (?);", [data.search],(err,results) => {
                if(err) {
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        err}, null);
                }
                else{
                    result(200, null, {
                        code : 200,
                        msg : "검색어 테이블에 검색어가 입력되었습니다.",
                        isSuccess : true});
                }
            })
 
} 
}