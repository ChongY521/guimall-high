package com.gulimall.search.service;

import com.gulimall.search.vo.SearchParam;
import com.gulimall.search.vo.SearchResult;

/**
 * @author aqiang9  2020-08-19
 */
public interface MallSearchService {
    SearchResult search(SearchParam searchParam);
}
