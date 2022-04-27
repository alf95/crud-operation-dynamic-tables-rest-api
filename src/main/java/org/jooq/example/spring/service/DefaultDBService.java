package org.jooq.example.spring.service;

import org.jooq.*;
import org.jooq.Record;
import org.jooq.example.spring.dto.EntitySave;
import org.jooq.example.spring.dto.FilterQuery;
import org.jooq.example.spring.dto.InfoTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;

/**
 * @author Lukas Eder
 */
@Service
public class DefaultDBService implements DBOperation {

	private static final Logger logger = LoggerFactory.getLogger(DefaultDBService.class);

	@Autowired DSLContext dsl;

	@Value("${db.name}")
	private String dbName;

	@Override
	public List<Map> select(InfoTable it) {
		List<Field> fields = buildFields(it.getPopulate());
		List<Condition> conditions = buildFilters(it.getFilters());
		Select select = dsl.select(fields).from(table(it.getEntity())).where(conditions);
		logger.info("query select:");
		logger.info(select.getSQL());
		return select.fetch().intoMaps();
	}

	@Override
	@Transactional
	public Map save(EntitySave entitySave) {
		Map res = null;
		//Object[] objects = dsl.meta(dbName).getTables(entitySave.getEntity()).get(0).getPrimaryKey();
		Map<String, Object> fields = entitySave.getFields();
		final String tableName = entitySave.getEntity();
		final List columnsPrimaryKey = getColumnsPrimaryKey(tableName);

		if(rowExists(fields, columnsPrimaryKey)){
			//update
			Map<String, Object> updateFields = new HashMap<>();
			List<Condition> whereFields = new ArrayList<>();
			fields.forEach((k, v) -> {
				if(columnsPrimaryKey.contains(k)){
					whereFields.add(field(k).eq(v));
				}else{
					updateFields.put(k, v);
				}
			});
			UpdateReturningStep<Record> update = dsl.update(table(tableName)).set(updateFields).where(whereFields);
			logger.info("UPDATE");
			logger.info(update.getSQL());
			res = update.returning().fetchOne().intoMap();
		}else{
			//insert
			InsertSetMoreStep insert = dsl.insertInto(table(tableName)).set(fields);
			logger.info("INSERT");
			logger.info(insert.getSQL());
			insert.execute();
			final BigInteger id = dsl.lastID();
			res = dsl.select().from(tableName).where(field((String) columnsPrimaryKey.get(0)).eq(id)).fetchOneMap();
			//res = insertSetStep.fetchOne().intoMap();
		}
		return res;
	}

	private boolean rowExists(Map fields, List cols) {
		return fields.keySet().containsAll(cols);
	}

	@Override
	public List<String> showTablesDB() {
		List tablesName = new ArrayList();
		dsl.meta().getSchemas(dbName).get(0).getTables().forEach(table -> {
			tablesName.add(table.getName());
		});
		return tablesName;
	}


	private List<Field> buildFields(List<String> columns){
		List<Field> fields = new ArrayList<>();
		columns.forEach(col -> {
			fields.add(field(col));
		});
		return fields;
	}

	private List<Condition> buildFilters(List<FilterQuery> filters){
		List<Condition> conditions = new ArrayList<>();
		filters.forEach(filter -> {
			Condition c;
			String op = filter.getOp();
			switch ((op != null) ? op : ""){
				case "eq":
					c = field(filter.getName()).eq(filter.getValue());
					break;
				case "lt":
					c = field(filter.getName()).lessThan(filter.getValue());
					break;
				case "gt":
					c = field(filter.getName()).greaterThan(filter.getValue());
					break;
				default:
					c = field(filter.getName()).eq(filter.getValue());
			}

			conditions.add(c);
		});
		return conditions;
	}



	private List getColumnsPrimaryKey(String tableName){
		Table<?> table = dsl.meta().getSchemas(dbName).get(0).getTable(tableName);
		List<? extends TableField<?, ?>> fieldsArray = table.getPrimaryKey().getFields();
		List columnsPK = new ArrayList();
		fieldsArray.forEach(tableField -> {
			columnsPK.add(tableField.getName());
		});
		return columnsPK;
	}

}
