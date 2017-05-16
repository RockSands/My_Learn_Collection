package sql.jsqlparser;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;

public class SqlParser {

	/**
	 * @description 需要解析的tql
	 */
	private String sql;

	/**
	 * @description 搜寻器
	 */
	private SQLColumnAndTableFinder finder = new SQLColumnAndTableFinder();

	/**
	 * @description 解析的Select对象
	 */
	private Select selectStatement;

	private ArrayList<Column> columns;

	private ArrayList<Table> tables;

	/**
	 * @name 加载SQL
	 */
	public void attachTQL(String sql) throws JSQLParserException {
		this.sql = sql;
		initFinder();
		initLocal();
		dealSelectColumnAlias();
	}

	/**
	 * @name 初始化Finder
	 * @description 初始化Finder
	 * @time 创建时间:2017年5月11日下午3:24:09
	 * @throws JSQLParserException
	 * @author chenkw
	 * @history 修订历史（历次修订内容、修订人、修订时间等）
	 */
	private void initFinder() throws JSQLParserException {
		finder.init();
		final CCJSqlParserManager ccjSqlParserManager = new CCJSqlParserManager();
		final StringReader sqlReader = new StringReader(sql);
		selectStatement = (Select) ccjSqlParserManager.parse(sqlReader);
		finder.attach(selectStatement);
	}

	/**
	 * 初始化本地参数
	 */
	private void initLocal() {
		columns = new ArrayList<Column>();
		tables = new ArrayList<Table>();
		final Set<String> set = new HashSet<String>();
		for (Column column : finder.getColumns()) {
			if (!set.contains(column.getColumnName())) {
				columns.add(column);
				set.add(column.getColumnName());
			}
		}
		for (Column column : finder.getColumns()) {
			if (!set.contains(column.getColumnName())) {
				columns.add(column);
				set.add(column.getColumnName());
			}
		}
		set.clear();
		for (Table table : finder.getTables()) {
			if (!set.contains(table.getName())) {
				tables.add(table);
				set.add(table.getName());
			}
		}
		set.clear();
	}

	/**
	 * 处理selectColumn的别名问题
	 */
	private void dealSelectColumnAlias() {
		Expression expression = null;
		for (SelectExpressionItem item : finder.getSelectExpressionItems()) {
			expression = item.getExpression();
			if (item.getAlias() == null || "".equals(item.getAlias().getName())) {
				// 别名使用''括起来,避免CONCAT(col1, col2)这种带有空格
				Alias alias = null;
				if (Column.class.isInstance(expression)) {
					alias = new Alias("'" + ((Column) expression).getColumnName() + "'", true);
				} else {
					alias = new Alias("'" + expression.toString() + "'", true);
				}
				item.setAlias(alias);
			}
		}
		for (Table table : finder.getTables()) {
			if (table.getAlias() == null) {
				table.setAlias(new Alias("'" + table.getName() + "'", true));
			}
		}
	}

	/**
	 * 创建时间:2017年5月11日下午3:31:32 get方法
	 * 
	 * @return the tqlColumns
	 */
	public List<Column> getColumns() {
		return columns;
	}

	/**
	 * 创建时间:2017年5月11日下午3:31:32 get方法
	 * 
	 * @return the tqlTables
	 */
	public List<Table> getTables() {
		return tables;
	}

	public String getSql() {
		return sql;
	}
}
