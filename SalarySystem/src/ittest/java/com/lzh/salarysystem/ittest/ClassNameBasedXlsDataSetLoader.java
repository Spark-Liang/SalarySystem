package com.lzh.salarysystem.ittest;

import java.net.URL;

import org.dbunit.dataset.IDataSet;
import org.dbunit.util.fileloader.XlsDataFileLoader;
import org.springframework.core.io.ClassPathResource;

import com.github.springtestdbunit.dataset.DataSetLoader;

public class ClassNameBasedXlsDataSetLoader implements DataSetLoader{

	@Override
	public IDataSet loadDataSet(Class<?> testClass, String location) throws Exception {
		String packagePath = testClass.getPackage().getName().replaceAll("\\.", "\\\\");
		URL testDataUrl = new ClassPathResource(packagePath + "\\" + location).getURL();
		return new XlsDataFileLoader().loadDataSet(testDataUrl);
	}

}
