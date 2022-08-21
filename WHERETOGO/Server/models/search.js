import db from "../config/dbConnection.js";

export const getSearchResults = (data, result) => {
    var qr = 'select eventID, eventName, kind, startDate, endDate, firstimage as pic, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum from eventTBL ';
    qr += ' where ';
    var areacode = -1, sigungucode = -1;
    var kind = '';

    db.query("Select aCode from areaCodeTBL where aName = ?;", [data.areaName], (err, areaC) => {             
        if(err) {
            result(500, {
                code : 500,
                isSuccess : false,
                err}, null);
        } else {
            if(areaC.length){
                if(areaC[0].aCode)areacode = areaC[0].aCode;
            } 
            db.query("Select aDCode from areaCodeDetailTBL where aDName = ?;", [data.dAreaName], (err, dAreaC) => {             
                if(err) {
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        err}, null);
                } 
                else {
                    if(dAreaC.length){
                        if(dAreaC[0].aDCode)sigungucode = dAreaC[0].aDCode; 
                    } 

                    if(areacode != -1){
                        qr += ' areacode = ';
                        qr += areacode;
                    }
                    

                    if(sigungucode != -1 ){
                        qr += ' and sigungucode = ';
                        qr += sigungucode;
                    }

                    if((sigungucode != -1)||(areacode != -1)){
                        qr += ' and ';
                    }

                    if((!data.fromD)&&(!data.toD)) {
                        qr += ' endDate >= now() ';
                    }
                    else if((!data.fromD)&&(data.toD)){
                        qr += ' endDate >= now() and startDate <= \'';
                        qr += data.toD;
                        qr += '\' ';
                    }
                    else if((data.fromD)&&(!data.toD)){
                        qr += ' endDate >= \'';
                        qr += data.fromD;
                        qr += '\' ';
                    }
                    else if((data.fromD)&&(data.toD)){
                        qr += ' endDate >= \'';
                        qr += data.fromD;
                        qr += '\' and startDate <= \'';
                       qr += data.toD;
                        qr += '\' ';
                    }
                    
                    if(data.search) {
                        qr += ' and (eventName like \'\%';
                        qr += data.search;
                        qr += '\%\' or program like \'\%'
                        qr += data.search;
                        qr += '\%\' or addr1 like \'\%'
                        qr += data.search;
                        qr += '\%\' or addr2 like \'\%'
                        qr += data.search;
                        qr += '\%\') ';
                    }
                    
                        if((data.k1)||(data.k2)||(data.k3)||(data.k4)||(data.k5)||(data.k6)||(data.k7)||(data.k8)||(data.k9)||(data.k10)||(data.k11)||(data.k12)||(data.k13)||(data.k14)||(data.k15)){
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
                    //console.log(qr); 

                    db.query(qr, (err, results) => {             
                        if(err) {
                            result(500, {
                                code : 500,
                                isSuccess : false,
                                err}, null);
                        } 
                        else if(!results.length){
                            result(200, null, {
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
                }); 
        }
    }); 
}


export const getHotSearchResults = (result) => {
    db.query("Select searchID, word from searchTBL ORDER BY count DESC LIMIT 10;", (err, results) => {             
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
    db.query("Select * from searchTBL where word = ?;", [data.search], (err, cnt) => {             
        if(err) {
            result(500, {
                code : 500,
                isSuccess : false,
                err}, null);
        } else if(!cnt.length){
            db.query("insert into searchTBL (word) values (?);", [data.search],(err,results) => {
                if(err) {
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        err}, null);
                }
                else{
                    result(200, null, {
                        code : 200,
                        msg : "새로운 검색어입니다. 검색어 테이블에 검색어가 입력되었습니다.",
                        isSuccess : true});
                }
            })
        }
        else {
            db.query("update searchTBL set count = count + 1 where word = ?;", [data.search],(err,results) => {
                if(err) {
                    result(500, {
                        code : 500,
                        isSuccess : false,
                        err}, null);
                }
                else{
                    result(200, null, {
                        code : 200,
                        msg : "기존에 존재하던 검색어입니다. 검색어 테이블의 count를 업데이트 하였습니다.",
                        isSuccess : true,
                        });
                }
            })
        }
    });  
} 
}