import http from '@/utils/httpRequest.js'
export function policy() {
   return  new Promise((resolve,reject)=>{
        http({
            url: http.adornUrl("/thirdparty/oss/policy"),
            method: "get"
        }).then((data) => {
            console.log(data)
            resolve(data);
        })
    });
}