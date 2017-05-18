package sql.jsqlparser.create;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.create.table.ColDataType;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class Main {

	public static void main(String[] args) {
		
		CreateTable createTable = new CreateTable();
		// 表
		createTable.setIfNotExists(true);
		createTable.setTable(new Table());
		createTable.getTable().setName("Table1");
		createTable.setTableOptionsStrings(Arrays.asList("COMMENT", "=", "'我的表'"));
		// 列
		List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
		for (int i = 0; i < 3; i++) {
			ColumnDefinition columnDefinition = new ColumnDefinition();
			columnDefinition.setColumnName("COL" + i);
			ColDataType colDataType = new ColDataType();
			colDataType.setDataType("varchar");
			colDataType.setArgumentsStringList(Arrays.asList("255"));
			columnDefinition.setColDataType(colDataType);
			columnDefinition.setColumnSpecStrings(Arrays.asList("NOT", "NULL", "DEFAULT 0", "COMMENT '注释" + i + "'"));
			columnDefinitions.add(columnDefinition);
		}
		createTable.setColumnDefinitions(columnDefinitions);
		System.out.println(createTable.toString());
	}

}
