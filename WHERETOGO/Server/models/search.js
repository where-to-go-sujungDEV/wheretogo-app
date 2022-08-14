import db from "../config/dbConnection.js";

export const getSearchResults = (data, result) => {
    var qr = 'select eventID, eventName, genre, kind, theme, startDate, endDate, pic, w1+w2+w3+w4+w6+m1+m2+m3+m4+m6 as savedNum from eventTBL ';
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

    if(data.genre){
        qr += ' and genre in (';
        qr += data.genre;
        qr += ') ';
    }

    if(data.kind){
        qr += ' and kind in (';
        qr += data.kind;
        qr += ') ';
    }

    if(data.theme){
        qr += ' and theme in (';
        qr += data.theme;
        qr += ') ';
    }

    if(data.dou){
        qr += ' and dou = \'';
        qr += data.dou;
        qr += '\' ';
    }

    if(data.si){
        qr += ' and si = \'';
        qr += data.si;
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
            console.log(err);
            result({
                code : 500,
                isSuccess : false,
                err}, null);
        } else {
            result(null, {
                code : 200,
                isSuccess : true,
                results});
        }
    }); 
}