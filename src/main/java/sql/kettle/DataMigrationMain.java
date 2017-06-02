package sql.kettle;

import org.pentaho.di.core.Condition;
import org.pentaho.di.core.KettleEnvironment;
import org.pentaho.di.core.NotePadMeta;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.row.ValueMetaAndData;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransHopMeta;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.steps.delete.DeleteMeta;
import org.pentaho.di.trans.steps.filterrows.FilterRowsMeta;
import org.pentaho.di.trans.steps.mergerows.MergeRowsMeta;
import org.pentaho.di.trans.steps.selectvalues.SelectValuesMeta;
import org.pentaho.di.trans.steps.tableinput.TableInputMeta;
import org.pentaho.di.trans.steps.tableoutput.TableOutputMeta;
import org.pentaho.di.trans.steps.update.UpdateMeta;

public class DataMigrationMain {
	public static void main(String[] args) throws Exception {
		// 初始化环境
		KettleEnvironment.init();
		EnvUtil.environmentInit();
		try {
			// 创建一个转换
			TransMeta transMeta = new TransMeta();
			transMeta.setName("CKW-TransMeta-Test");
			// 源数据源
			DatabaseMeta sourceDataBase = new DatabaseMeta("sourceDataBase", "MySql", "Native", "192.168.80.138",
					"employees", "3306", "root", "123456");
			transMeta.addDatabase(sourceDataBase);
			// 目标数据源
			DatabaseMeta targetDatabase = new DatabaseMeta("targetDatabase", "MySql", "Native", "192.168.80.138",
					"person", "3306", "root", "123456");
			transMeta.addDatabase(targetDatabase);

			/*
			 * 日志输出
			 */
			String startNote = "Start CKW-TransMeta-Test";
			NotePadMeta ni = new NotePadMeta(startNote, 150, 10, -1, -1);
			transMeta.addNote(ni);

			/*
			 * 源表输入
			 */
			TableInputMeta tii = new TableInputMeta();
			tii.setDatabaseMeta(sourceDataBase);
			String selectSQL = "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary, salaries.from_date, salaries.to_date "
					+ "FROM employees, salaries "
					+ "WHERE salaries.emp_no = employees.emp_no ORDER BY employees.emp_no, salaries.from_date;";
			tii.setSQL(selectSQL);
			StepMeta query = new StepMeta("query", tii);
			transMeta.addStep(query);

			/*
			 * 转换名称
			 */
			String[] sourceFields = { "emp_no", "first_name", "last_name", "salary", "from_date", "to_date" };
			String[] targetFields = { "empID", "firstName", "lastName", "salary", "fromDate", "toDate" };
			// int[] targetPrecisions = { 0, 0, 0, 0, 0 };
			// int[] targetLengths = { 64, 64, 64, 64, 64 };
			SelectValuesMeta svi = new SelectValuesMeta();
			// svi.setSelectPrecision(targetPrecisions);
			svi.setSelectName(sourceFields);
			svi.setSelectRename(targetFields);
			StepMeta chose = new StepMeta("chose", svi);
			chose.setDescription("Rename field names");
			transMeta.addStep(chose);
			// 链接
			transMeta.addTransHop(new TransHopMeta(query, chose));

			/*
			 * 目标表输入
			 */
			TableInputMeta targettii = new TableInputMeta();
			targettii.setDatabaseMeta(sourceDataBase);
			tii.setSQL(
					"SELECT empID, firstName, lastName, salary, fromDate, toDate FROM targetSalary ORDER BY empID, fromDate");
			StepMeta targetQuery = new StepMeta("targetQuery", tii);
			transMeta.addStep(targetQuery);

			/*
			 * 合并数据
			 */
			MergeRowsMeta mrm = new MergeRowsMeta();
			mrm.setFlagField("flagfield");
			mrm.setValueFields(new String[] { "firstName", "lastName", "salary", "toDate" });
			mrm.setKeyFields(new String[] { "empID", "fromDate" });
			StepMeta merage = new StepMeta("merage", svi);
			transMeta.addTransHop(new TransHopMeta(chose, merage));
			transMeta.addTransHop(new TransHopMeta(targetQuery, merage));

			/*
			 * noChange判断
			 */
			FilterRowsMeta frm = new FilterRowsMeta();
			frm.setCondition(new Condition("flagfield", Condition.FUNC_EQUAL, null,
					new ValueMetaAndData("identical", "identical")));
			StepMeta nochang = new StepMeta("nochang", frm);
			transMeta.addTransHop(new TransHopMeta(merage, nochang));
			/*
			 * nothing
			 */
			StepMeta nothing = new StepMeta("nothing", null);
			transMeta.addTransHop(new TransHopMeta(nochang, nothing));
			/*
			 * isNew判断
			 */
			frm = new FilterRowsMeta();
			frm.setCondition(
					new Condition("flagfield", Condition.FUNC_EQUAL, null, new ValueMetaAndData("new", "new")));
			StepMeta isNew = new StepMeta("isNew", frm);
			transMeta.addTransHop(new TransHopMeta(nochang, isNew));
			/*
			 * insert
			 */
			TableOutputMeta toi = new TableOutputMeta();
			toi.setDatabaseMeta(targetDatabase);
			toi.setTableName("targetSalary");
			toi.setCommitSize(100);
			toi.setTruncateTable(false);
			toi.setFieldDatabase(targetFields);
			toi.setFieldStream(targetFields);
			StepMeta insert = new StepMeta("insert", toi);
			transMeta.addTransHop(new TransHopMeta(isNew, insert));
			/*
			 * isChange判断
			 */
			frm = new FilterRowsMeta();
			frm.setCondition(new Condition("flagfield", Condition.FUNC_EQUAL, null,
					new ValueMetaAndData("isChange", "changed")));
			StepMeta isChange = new StepMeta("isChange", frm);
			transMeta.addTransHop(new TransHopMeta(isNew, isChange));
			/*
			 * update
			 */
			UpdateMeta um = new UpdateMeta();
			um.setDatabaseMeta(targetDatabase);
			um.setTableName("targetSalary");
			um.setCommitSize(100);
			um.setKeyCondition(new String[] { "=", "=" });
			um.setKeyStream2(new String[] { null, null });
			um.setKeyStream(new String[] { "empID", "fromDate" });
			um.setUseBatchUpdate(true);
			um.setUpdateLookup(targetFields);
			StepMeta update = new StepMeta("update", um);
			transMeta.addTransHop(new TransHopMeta(isChange, update));
			/*
			 * delete
			 */
			DeleteMeta dm = new DeleteMeta();
			dm.setDatabaseMeta(targetDatabase);
			dm.setTableName("targetSalary");
			dm.setCommitSize(100);
			dm.setKeyCondition(new String[] { "=", "=" });
			dm.setKeyLookup(new String[] { "empID", "fromDate" });
			dm.setKeyStream2(new String[] { null, null });
			dm.setKeyStream(new String[] { "empID", "fromDate" });
			StepMeta delete = new StepMeta("delete", dm);
			transMeta.addTransHop(new TransHopMeta(isNew, delete));
			/*
			 * 执行
			 */
			Trans trans = new Trans(transMeta);
			trans.execute(null);
			trans.waitUntilFinished();
			// 转换构建完成
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
