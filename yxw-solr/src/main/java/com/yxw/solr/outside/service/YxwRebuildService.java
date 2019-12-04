package com.yxw.solr.outside.service;

import com.yxw.solr.vo.rebuild.RebuildRequest;
import com.yxw.solr.vo.rebuild.RebuildResponse;

public interface YxwRebuildService {
	public RebuildResponse rebuild(RebuildRequest request);
}
