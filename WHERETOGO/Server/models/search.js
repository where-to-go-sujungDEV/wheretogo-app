import db from "../config/dbConnection.js";

export const getSearchResults = (data, result) => {
    var qr = 'select eventID, eventName, cat2, cat3, startDate, endDate, firstimage as pic, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum from eventTBL ';
    qr += ' where ';

    if((!data.fromD)&&(!data.toD)) {
        qr += ' endDate >= now() ';
    }
    else if((!data.fromD)&&(data.toD)){
        qr += ' (endDate >= now() and startDate <= \'';
        qr += data.toD;
        qr += '\') ';
    }
    else if((data.fromD)&&(!data.toD)){
        qr += ' (endDate >= \'';
        qr += data.fromD;
        qr += '\') ';
    }
    else if((data.fromD)&&(data.toD)){
        qr += ' (endDate >= \'';
        qr += data.fromD;
        qr += '\' and startDate <= \'';
        qr += data.toD;
        qr += '\') ';
    }

    if(data.search) {
        qr += ' and (eventName like \'\%';
        qr += data.search;
        qr += '\%\' or content like \'\%'
        qr += data.search;
        qr += '\%\') ';
    }

    if(data.cat2){
        qr += ' and cat2 in (';
        qr += data.cat2;
        qr += ') ';
    }

    if(data.cat3){
        qr += ' and cat3 in (';
        qr += data.cat3;
        qr += ') ';
    }


    if(data.areacode){
        qr += ' and areacode = \'';
        qr += data.areacode;
        qr += '\' ';
    }

    if(data.sigungucode){
        qr += ' and sigungucode = \'';
        qr += data.sigungucode;
        qr += '\' ';
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
        } else {
            result(200, null, {
                code : 200,
                isSuccess : true,
                results});
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