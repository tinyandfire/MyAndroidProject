package com.cong.android.imageloader.core.type;

/**
 * author : Cong
 * date   : 2020-10-09
 */
public enum DiskCacheType {
    /**
     * 默认缓存
     */
    DEFAULT_DISK_CACHE,
    /**
     * 不缓存任何内容
     */
    NONE_DISK_CACHE,
    /**
     * 只缓存原始图片
     */
    META_DATE_DISK_CACHE,
    /**
     * 只缓存转换过后的图片
     */
    RESOURCE_DISK_CACHE,
    /**
     * 既缓存原始图片，也缓存转换过后的图片
     */
    ALL_DISK_CACHE,
}