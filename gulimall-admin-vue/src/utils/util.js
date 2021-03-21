/**
 * 删除对象中 id 相同的数据 只保存最后进来的数据
 * @param {Array} arr   必须有id属性 
 */
export function RemoveCommonObjectById(arr) {
  let index = {};
  arr.forEach((e,i)=>{
   if( index[ e.id ] != undefined ){
      arr[ index[e.id] ] = "" ;
   } 
   index [e.id] = i ;
  })
 return arr.filter((e,i)=>{
    if(e) return e ;
  })
}
//[{'id':'1' , "b":'c'},{'id':'3' , "b":'c'},{'id':'2' , "b":'c'},{'id':'3' , "b":'ddd'},{'id':'1' , "b":'vvvvvv'}]

// var obj = {};
// arr = arr.reduce(function(item, next) {
// obj[next.key] ? '' : obj[next.key] = true && item.push(next);
// return item;
// }, []);