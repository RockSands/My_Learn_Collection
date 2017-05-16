package sql.jsqlparser;

import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class Main {

	public static void main(String[] args) throws JSQLParserException {
		List<String> sqls = new ArrayList<String>();
		// 简单样例
		sqls.add("select count(1) as cnt, sum(Table1.COL1) as setj from Table1 limit 1000");
		// 别名
		sqls.add(
				"select count(1) as cnt, sum(Table1.COL1) as setj,COL2 a,COL3,CONCAT(COL2, COL3),CONCAT(COL2, COL3) AS TT from Table1");
		// 表名原有别名
		sqls.add("select count(1) as cnt, sum(MCTB.COL1) as setj from Table1 MCTB");
		// join
		sqls.add(
				"select count(1) as cnt, sum(MCTB.COL1) as setj from Table1 MCTB LEFT JOIN Table2 ON MCTB.COL2 = Table2.COL3");
		// 查询的表名与之前的表名重复
		sqls.add("select count(1) as cnt, sum(Table1.COL1) as setj from Table1,T1");
		for (String sql : sqls) {
			SqlParser tqlParser = new SqlParser();
			tqlParser.attachTQL(sql);
			System.out.println("==>" + tqlParser.getSql());
			System.out.print("=Column=>");
			for (Column column : tqlParser.getColumns()) {
				System.out.print(column.getColumnName() + ",");
			}
			System.out.println();
			System.out.print("=Tables=>");
			for (Table table : tqlParser.getTables()) {
				System.out.print(table.getName() + ",");
			}
		}
	}
}
