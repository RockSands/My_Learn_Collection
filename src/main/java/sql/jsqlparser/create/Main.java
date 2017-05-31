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
		// 服务
		CreateTable createTable = new CreateTable();
		// 表
		createTable.setIfNotExists(true);
		createTable.setTable(new Table());
		createTable.getTable().setName("Table1");//变量
		createTable.setTableOptionsStrings(Arrays.asList("COMMENT", "=", "'我的表'"));//表注释变量
		// 列
		List<ColumnDefinition> columnDefinitions = new ArrayList<ColumnDefinition>();
		for (int i = 0; i < 3; i++) {
			ColumnDefinition columnDefinition = new ColumnDefinition();
			columnDefinition.setColumnName("COL" + i);//字段名
			ColDataType colDataType = new ColDataType();
			colDataType.setDataType("varchar");//类型 + 长度
			colDataType.setArgumentsStringList(Arrays.asList("255"));//
			columnDefinition.setColDataType(colDataType);
			columnDefinition.setColumnSpecStrings(Arrays.asList("NOT", "NULL", "DEFAULT 0", "COMMENT '注释" + i + "'"));// NOT NULL Default,注释
			//某些列是
			columnDefinitions.add(columnDefinition);
		}
		createTable.setColumnDefinitions(columnDefinitions);
		System.out.println(createTable.toString());
	}

}
