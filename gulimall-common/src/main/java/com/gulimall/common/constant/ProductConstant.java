package com.gulimall.common.constant;

/**
 * @author aqiang9  2020-07-31
 */
public interface ProductConstant {

//    public enum AttrEnum{
//        SALE_ATTR_TYPE(0,"销售属性"),
//        BASE_ATTR_TYPE(1,"基本属性")
//        ;
//        private final  int code ;
//        private final String msg ;
//
//        AttrEnum(int code, String msg) {
//            this.code = code;
//            this.msg = msg;
//        }
//
//        public int getCode() {
//            return code;
//        }
//
//        public String getMsg() {
//            return msg;
//        }
//    }


    /**
     * 销售属性
     */
   int SALE_ATTR_TYPE = 0 ;
    /**
     * 基本属性
     */
    int BASE_ATTR_TYPE = 1 ;
    /**
     * 销售属性 & 基本属性
     */
    int All_ATTR_TYPE = 2 ;

    /**
     * 商品状态 - 新建
     */
    int SPU_STATUS_NEW = 0 ;

    /**
     * 商品状态 - 上架
     */
    int SPU_STATUS_UP = 1 ;
    /**
     * 商品状态 - 下架
     */
    int SPU_STATUS_DOWN = 2 ;

}
