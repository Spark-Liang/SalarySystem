package com.lzh.salarysystem.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DefaultDataSet;
import org.dbunit.dataset.DefaultTable;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.excel.XlsDataSetWriter;
import org.dbunit.dataset.filter.IColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.operation.DatabaseOperation;

import com.github.springtestdbunit.assertion.DatabaseAssertion;
import com.lzh.salarysystem.ittest.ClassNameBasedXlsDataSetLoader;

public class DBUnitEnvironment {
	
	private final File backupFile;
	
	private final IDatabaseConnection conn;
	
	private final Class<?> testClass;
	
	DBUnitEnvironment(IDatabaseConnection conn,File rootOfBackupFile,Class<?> testClass) throws Exception{
		this.conn = conn;
		backupFile = File.createTempFile("dbBackUp", null, rootOfBackupFile);
		this.testClass = testClass;
	}
	

    /**
     * Get DB DataSet
     * 
     * @Title: getDBDataSet
     * @return
     * @throws SQLException
     */
    public IDataSet getDBDataSet() throws SQLException {
        return conn.createDataSet();
    }

    /**
     * Get Query DataSet
     * 
     * @Title: getQueryDataSet
     * @return
     * @throws SQLException
     */
    protected QueryDataSet getQueryDataSet() throws SQLException {
        return new QueryDataSet(conn);
    }

    /**
     * Get DataSet from file base on the testClass and the given filename
     * 
     * @Title: getDataSet from xls file
     * @return
     * @throws Exception 
     */
    protected IDataSet getDataSet(String fileName) throws Exception {
        return new ClassNameBasedXlsDataSetLoader().loadDataSet(testClass, fileName);
    }
    
    /**
     * backup the whole DB
     * 
     * @Title: backupAll
     * @throws Exception
     */
    public void backupAll() throws Exception {
        // create DataSet from database.
        IDataSet ds = conn.createDataSet();

        // write the content of database to temp file
        FlatXmlDataSet.write(ds, new FileWriter(backupFile), "UTF-8");
    }

    /**
     * back specified DB table
     * 
     * @Title: backupCustom
     * @param tableName
     * @throws Exception
     */
    public void backupCustom(String... tableName) throws Exception {
        // back up specific files
        QueryDataSet qds = new QueryDataSet(conn);
        for (String str : tableName) {

            qds.addTable(str);
        }
        FlatXmlDataSet.write(qds, new FileWriter(backupFile), "UTF-8");
        new XlsDataSetWriter().write(qds, new FileOutputStream(backupFile));
    }

    /**
     * rollback database
     * 
     * @Title: rollback
     * @throws Exception
     */
    public void rollback() throws Exception {
        IDataSet ds = new XlsDataSet(backupFile);
        InsertIdentityOperation.CLEAN_INSERT.execute(conn, ds);
    }

    /**
     * destory the dbunit environment and release the resource
     * 
     * @Title: destory
     * @throws Exception
     */
    public void destory() throws Exception {
        try {
        	if(conn != null) {
            	conn.close();
            }
		} finally {
			if(backupFile != null) {
				if(!backupFile.delete()) {
					backupFile.deleteOnExit();
				}
			}
		}
    }
    
    
    /**
     * Clear data of table
     * 
     * @param tableName
     * @throws Exception
     */
    public void clearTable(String tableName) throws Exception {
        DefaultDataSet dataset = new DefaultDataSet();
        dataset.addTable(new DefaultTable(tableName));
        DatabaseOperation.DELETE_ALL.execute(conn, dataset);
    }

    /**
     * do the given operation
     * 
     * @param operation
     * @param dataset
     * @throws Exception
     */
    public void execute(DatabaseOperation operation ,IDataSet dataSet) throws Exception{
    	operation.execute(conn, dataSet);
    }
    
    public void verifyTable(DatabaseAssertion assertionOption, ITable expectedTable, List<IColumnFilter> filtersToIgnoreColumn) throws Exception {
    	ITable actualTable = getDBDataSet().getTable(expectedTable.getTableMetaData().getTableName());
    	assertionOption.assertEquals(expectedTable, actualTable, filtersToIgnoreColumn);
    }
    
    public void verifyDataSet(DatabaseAssertion assertionOption, IDataSet expectedDataSet,List<IColumnFilter> filtersToIgnoreColumn) throws Exception{
    	IDataSet actualDataSet = getDBDataSet();
    	assertionOption.assertEquals(expectedDataSet, actualDataSet, filtersToIgnoreColumn);
    }
}
