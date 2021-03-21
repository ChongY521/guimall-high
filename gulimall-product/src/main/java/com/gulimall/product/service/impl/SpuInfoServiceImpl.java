package com.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulimall.common.constant.ProductConstant;
import com.gulimall.common.exception.BusinessException;
import com.gulimall.common.exception.CouponErrorCode;
import com.gulimall.common.to.SkuHasStockTo;
import com.gulimall.common.to.SkuReductionTo;
import com.gulimall.common.to.SpuBoundTo;
import com.gulimall.common.to.es.Attrs;
import com.gulimall.common.to.es.SkuEsModel;
import com.gulimall.common.utils.CommonResult;
import com.gulimall.product.convert.AttrConvert;
import com.gulimall.product.convert.SkuConvert;
import com.gulimall.product.convert.SpuConvert;
import com.gulimall.product.dao.SpuInfoDao;
import com.gulimall.product.entity.*;
import com.gulimall.product.feign.CouponFeignService;
import com.gulimall.product.feign.SearchFeignService;
import com.gulimall.product.feign.WareFeignService;
import com.gulimall.product.service.*;
import com.gulimall.product.vo.*;
import com.gulimall.service.utils.PageUtils;
import com.gulimall.service.utils.Query;
import com.gulimall.service.utils.QueryPage;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("spuInfoService")
@Slf4j
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    SpuInfoDescService spuInfoDescService;

    @Autowired
    SpuImagesService spuImagesService;
    @Autowired
    AttrService attrService;
    @Autowired
    SkuInfoService skuInfoService;

    @Autowired
    SkuImagesService skuImageService;
    @Autowired
    ProductAttrValueService productAttrValueService;
    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    CouponFeignService couponFeignService;
    @Autowired
    BrandService brandService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    WareFeignService wareFeignService;
    @Autowired
    SearchFeignService searchFeignService ;


    /**
     * TODO 如果失败  全局事务
     *
     * @param spuSaveVo
     */
    @Transactional
    @Override
    public void saveInfo(SpuSaveVo spuSaveVo) {
        // 1、保存spu 基本信息  spu_info
        SpuInfoEntity spuInfoEntity = SpuConvert.INSTANCE.vo2entity(spuSaveVo);
        baseMapper.insert(spuInfoEntity);
        // 2 保存spu的描述图片 spu_info_desc
        List<String> decript = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        String desc = String.join(",", decript);
        spuInfoDescEntity.setDecript(desc);
        spuInfoDescService.save(spuInfoDescEntity);

//      3、保存sup的图片集 spu_images
        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(), images);
//        4、保存spu的规格参数 pms_product_attr_value
        List<SpuSaveBaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();

        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(baseAttr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();

            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            productAttrValueEntity.setAttrId(baseAttr.getAttrId());

            AttrEntity attrInfo = attrService.getById(baseAttr.getAttrId());


            productAttrValueEntity.setAttrName(attrInfo.getAttrName());

            productAttrValueEntity.setQuickShow(baseAttr.getShowDesc());
            productAttrValueEntity.setAttrValue(baseAttr.getAttrValues());
            return productAttrValueEntity;
        }).collect(Collectors.toList());
        // 不用自动生成的方法 ，  后面可以配置缓存
        productAttrValueService.saveAttrValueInfo(productAttrValueEntities);

        //4、保存spu的规格参数;pms_product_attr_value


        // 5.0 、保存spu 积分信息

        SpuSaveBounds bounds = spuSaveVo.getBounds();

        SpuBoundTo spuBoundTo = new SpuBoundTo();

        spuBoundTo.setSpuId(spuInfoDescEntity.getSpuId());
        spuBoundTo.setBuyBounds(bounds.getBuyBounds());
        spuBoundTo.setGrowBounds(bounds.getGrowBounds());
        CommonResult saveSpuBoundsResult = couponFeignService.saveSpuBounds(spuBoundTo);

        if (saveSpuBoundsResult.getCode() != 0) {
            log.error("保存spu积分信息失败");
            throw new BusinessException(CouponErrorCode.SAVE_SPU_BOUND_FAIL);
        }
        // 5 spu 对应的sku信息
//        5.1  基本信息 sku_info
        List<SpuSaveSku> skus = spuSaveVo.getSkus();
        if (skus != null && skus.size() > 0) {
            skus.forEach(sku -> {
                String defaultImg = "";
                List<SpuSaveImage> imageList = sku.getImages();
                for (SpuSaveImage img : imageList) {
                    if (img.getDefaultImg() == 1) {
                        defaultImg = img.getImgUrl();
                        break;
                    }
                }
                //    private String skuName;
                //    private BigDecimal price;
                //    private String skuTitle;
                //    private String skuSubtitle;
                SkuInfoEntity skuInfoEntity = SkuConvert.INSTANCE.vo2entity(sku);
                // 复制不一样的地方
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                ;
                skuInfoEntity.setCategoryId(spuInfoEntity.getCategoryId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);

                // TODO 如果是采用分布式主键生成 则是一个批量保存
                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();

//        5.2  图片信息 sku_info
                // 没有图片路径的无需保存
                List<SkuImagesEntity> imagesEntities = imageList.stream().filter(img ->
                        !StringUtils.isEmpty(img.getImgUrl())
                ).map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).collect(Collectors.toList());
                skuImageService.saveBatch(imagesEntities);


//        5.3 sku的销售属性 sku_sale_attr_value
                List<SpuSaveAttr> attr = sku.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(item -> {
                    SkuSaleAttrValueEntity attrValueEntity = SkuConvert.INSTANCE.vo2entity(item);
                    attrValueEntity.setSkuId(skuId);
                    return attrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

//        5.4 优惠信息
//              会员价、满减、打折 、积分
                SkuReductionTo skuReductionTo = new SkuReductionTo();
                skuReductionTo = SkuConvert.INSTANCE.vo2to(sku);

                skuReductionTo.setSkuId(skuId);

                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrice().compareTo(new BigDecimal("0")) == 1) {
                    CommonResult skuReductionResult = couponFeignService.saveSkuReduction(skuReductionTo);

                    if (skuReductionResult.getCode() != 0) {
                        log.error("保存sku优惠信息失败");
                        throw new BusinessException(CouponErrorCode.SAVE_KEU_RELATION_FAIL);
                    }
                }
            });
        }
    }

    @Override
    public PageUtils queryPageOnCondition(SpuPageVo params) {
        LambdaQueryWrapper<SpuInfoEntity> wrapper = new LambdaQueryWrapper<>();

        if (params.getStatus() != null) {
            wrapper.eq(SpuInfoEntity::getPublishStatus, params.getStatus());
        }
        if (!StringUtils.isEmpty(params.getKey())) {
            wrapper.and(w -> {
                w.eq(SpuInfoEntity::getId, params.getKey())
                        .or()
                        .like(SpuInfoEntity::getSpuName, params.getKey());
            });
        }

        if (params.getBrandId() != null && params.getBrandId() > 0) {
            wrapper.eq(SpuInfoEntity::getBrandId, params.getBrandId());
        }
        if (params.getCategoryId() != null && params.getCategoryId() > 0) {
            wrapper.eq(SpuInfoEntity::getCategoryId, params.getCategoryId());
        }
        IPage<SpuInfoEntity> spuInfoEntityIPage = baseMapper.selectPage(new QueryPage<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(spuInfoEntityIPage);
    }

    @Transactional
    @Override
    public void up(Long spuId) {
        // 组装数据 并发送给es
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

        // 3、
        List<ProductAttrValueEntity> attrValueEntities = productAttrValueService.baseAttrListForSpuId(spuId);

        List<Long> attrIds = attrValueEntities.stream().map(ProductAttrValueEntity::getAttrId).collect(Collectors.toList());
        /**
         * 可以用来检索的 id
         */
        List<Long> searchAttrIds = attrService.getSearchAttrIds(attrIds);

        // 遍历 得到可以存放的数据
        HashSet<Long> set = new HashSet<>(searchAttrIds);
        List<Attrs> searchAttrList = attrValueEntities.stream().filter(attrValue -> {
            return set.contains(attrValue.getAttrId());
        }).map(e -> {
            Attrs attrs = AttrConvert.INSTANCE.entity2model(e);
            return attrs;
        }).collect(Collectors.toList());
        Map<Long, Boolean> hasStock = null;
        try {
            List<Long> skuIds = skuInfoEntities.stream().map(SkuInfoEntity::getSkuId).collect(Collectors.toList());
            List<SkuHasStockTo> stockTos = wareFeignService.hasStock(skuIds).getData();
            hasStock = stockTos.stream().collect(Collectors.toMap(SkuHasStockTo::getSkuId, SkuHasStockTo::getHasStock));
        } catch (Exception e) {
            log.error("远程库存服务异常:  {}", e);
        }
        // 参数对拷
        Map<Long, Boolean> finalHasStock = hasStock;
        List<SkuEsModel> skuEsModelList = skuInfoEntities.stream().map(skuInfoEntity -> {
            SkuEsModel skuEsModel = SkuConvert.INSTANCE.entity2model(skuInfoEntity);
            // 1、价格 图片 ， hasStock  在 convert 中配置
            // TODO 库存系统是否有库存
            skuEsModel.setHotScore(0L);

            if (finalHasStock != null) {
                skuEsModel.setHasStock(finalHasStock.get(skuInfoEntity.getSkuId()));
            } else {
                skuEsModel.setHasStock(true);
            }

            // 2、查询品牌 及分类

            BrandEntity brandEntity = brandService.getById(skuInfoEntity.getBrandId());
            skuEsModel.setBrandName(brandEntity.getName());
            skuEsModel.setBrandImg(brandEntity.getLogo());
            skuEsModel.setBrandId(brandEntity.getBrandId() );

            CategoryEntity categoryEntity = categoryService.getById(skuInfoEntity.getCategoryId());
            skuEsModel.setCategoryName(categoryEntity.getName());

            // 3、规格属性  sku中  相同属性
            skuEsModel.setAttrs(searchAttrList);
            return skuEsModel;
        }).collect(Collectors.toList());

//        4 、 es 保存
        CommonResult commonResult = searchFeignService.productStatusUp(skuEsModelList);
        Integer code = commonResult.getCode();
        if (code == 0 ){
            log.info("商品es保存成功");

            // 更新 数据库信息
            SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
            spuInfoEntity.setId(spuId);
            spuInfoEntity.setPublishStatus(ProductConstant.SPU_STATUS_UP);
            baseMapper.updateById(spuInfoEntity);
        }else{
            log.error("商品es保存失败");
        }
// TODO 重复调用  接口幂等性

        /**
         * feign 请求流程
         *
         * 1、构造请求数据 ， 将对象转为json
         * 2、发送请求
         *  SynchronousMethodHandler/executeAndDecode
         * 3、有重试
         * Retryer
         *
         */


    }
}