package sql.jsqlparser.create;

import java.io.StringReader;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class DeMain {

	public static void main(String[] args) throws JSQLParserException {
		String create = "CREATE TABLE `MyTable1` (" + "`col1`  varchar(255) NOT NULL COMMENT '注释1' ,"
				+ "`col2`  varchar(255) NOT NULL COMMENT '注释2' ,"
				+ "`col3`  varchar(255) NOT NULL DEFAULT 0 COMMENT '注释3',PRIMARY KEY (`col1`,`col2`) " + ")" + "COMMENT='我的表'"
				+ ";";
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Statement statement = parserManager.parse(new StringReader(create));
		CreateTable createTable = (CreateTable) statement;
		for (ColumnDefinition index : createTable.getColumnDefinitions()) {
			System.out.println("=ColumnDefinition=>" + index);
			System.out.println("=ColumnDefinition.ColumnName=>" + index.getColumnName());
			// System.out.println("=ColumnDefinition.ColDataType=>" +
			// index.getColDataType());
			// System.out.println("=ColumnDefinition.ColDataType.DataType=>" +
			// index.getColDataType().getDataType());
			// System.out.println("=ColumnDefinition.ColDataType.ArgumentsStringList=>"
			// + index.getColDataType().getArgumentsStringList());
			// System.out.println("=ColumnDefinition.ColDataType.ArrayData=>" +
			// index.getColDataType().getArrayData());
			// System.out.println("=ColumnDefinition.ColumnSpecStrings=>" +
			// index.getColumnSpecStrings());
		}
		System.out.println();
		if (createTable.getCreateOptionsStrings() != null) {
			for (String index : createTable.getCreateOptionsStrings()) {
				System.out.println("=CreateOptionsString=>" + index);
			}
		}
		if (createTable.getTableOptionsStrings() != null) {
			for (Object index : createTable.getTableOptionsStrings()) {
				System.out.println("=TableOptionsStrings=>" + index);
			}
		}
	}
}
