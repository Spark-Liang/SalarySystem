package com.lzh.salarysystem.ittest.common.util;

import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

public class XlsDataSetLoader extends AbstractDataSetLoader{

	@Override
	protected IDataSet createDataSet(Resource resource) throws Exception {
		return new XlsDataFileLoader().loadDataSet(resource.getURL());
	}

}
