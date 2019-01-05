package com.cxf.sell.dataobject.base;

import com.cxf.sell.utils.RegexUtils;

import java.util.*;


public class BasicExample <T extends IBaseDBPO>{
    
	protected  Object tName;

	protected  Object join;

	protected  Object col="*";

	protected  Class clazz;

	protected  Object pkName;
	
	protected  Object pkValue;
    	
	protected String orderByClause;

	protected boolean distinct;

	protected List<Criteria> oredCriteria;
	
	protected Map<String,Object> tMap=new HashMap<>();

	public BasicExample(String join, String... tN) throws IllegalAccessException, InstantiationException {
		String n="";
		for (String t: tN) {
			n+=","+t;
		}
		this.tName=n.substring(1);
		this.join=join;
		oredCriteria = new ArrayList<Criteria>();
	}public BasicExample() {
		oredCriteria = new ArrayList<Criteria>();
	}

	public BasicExample(Class<T> clazz) throws InstantiationException, IllegalAccessException {
		T t=clazz.newInstance();
		this.clazz=clazz;
		this.tName=t._getTableName();
		this.pkName=t._getPKColumnName();
		this.pkValue=t._getPKValue();
		oredCriteria = new ArrayList<Criteria>();
	}

	public Object getJoin() {
		return join;
	}

	public void setJoin(String join) {
		this.join = join;
	}

	public Object getCol() {
		return col;
	}

	public void setCol(String col) {
		this.col = col;
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class<T> clazz) throws IllegalAccessException, InstantiationException {
		T t=clazz.newInstance();
		this.clazz=clazz;
		this.tName=t._getTableName();
		this.pkName=t._getPKColumnName();
		this.pkValue=t._getPKValue();
	}

	public Object getTName() {
		return tName;
	}
	public Object getTMap() {
		return tMap;
	}

	public void setTName(String tableName) {
		tMap.put("tName", tableName);
		this.tName = tableName;
	}


	public Object getPkName() {
		return pkName;
	}



	public void setPkName(String pkName) {
		tMap.put("pkName", pkName);
		this.pkName = pkName;
	}



	public Object getPkValue() {
		return pkValue;
	}



	public void setPkValue(Object pkValue) {
		tMap.put("pkValue", pkName);
		this.pkValue = pkValue;
	}



	public void setOrderByClause(String orderByClause) {
		orderByClause = RegexUtils.humpToLine(orderByClause);
		this.orderByClause = orderByClause;
	}

	public String getOrderByClause() {
		return orderByClause;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void or(Criteria criteria) {
		oredCriteria.add(criteria);
	}

	public Criteria or() {
		Criteria criteria = createCriteriaInternal();
		oredCriteria.add(criteria);
		return criteria;
	}

	public Criteria createCriteria() {
		Criteria criteria = createCriteriaInternal();
		if (oredCriteria.size() == 0) {
			oredCriteria.add(criteria);
		}
		return criteria;
	}

	protected Criteria createCriteriaInternal() {
		Criteria criteria = new Criteria();
		return criteria;
	}

	public void clear() {
		oredCriteria.clear();
		orderByClause = null;
		distinct = false;
	}

	protected abstract static class GeneratedCriteria {
		

		public static final String IS_NUll = "null";

		public static final String NOT_NUll = "notNull";

		public static final String EQ = "eq";

		public static final String NOT_EQ = "notEq";

		public static final String LIKE = "like";

		public static final String NOT_LIKE = "notLike";

		public static final String IN = "in";

		public static final String NOT_IN = "notIn";

		public static final String BETWEEN = "between";

		public static final String NOT_BETWEEN = "notBetween";

		public static final String LESS_THAN = "lessThan";

		public static final String LESS_THAN_OR_EQ = "lessThanOrEq";

		public static final String GREAT_THAN = "GreaterThan";

		public static final String GREAT_THAN_OR_EQ = "GreaterThanOrEq";
		
		protected List<Criterion> criteria;

		protected GeneratedCriteria() {
			super();
			criteria = new ArrayList<Criterion>();
		}

		public boolean isValid() {
			return criteria.size() > 0;
		}

		public List<Criterion> getAllCriteria() {
			return criteria;
		}

		public List<Criterion> getCriteria() {
			return criteria;
		}

		protected void addCriterion(String condition) {
			if (condition == null) {
				throw new RuntimeException("Value for condition cannot be null");
			}
			criteria.add(new Criterion(condition));
		}

		protected void addCriterion(String condition, Object value, String property) {
			if (value == null) {
				throw new RuntimeException("Value for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value));
		}

		protected void addCriterion(String condition, Object value1, Object value2, String property) {
			if (value1 == null || value2 == null) {
				throw new RuntimeException("Between values for " + property + " cannot be null");
			}
			criteria.add(new Criterion(condition, value1, value2));
		}

		public Criteria andJoin(String var) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var);
			return (Criteria) this;
		}

		public Criteria andVarIsNull(String var) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " is null");
			return (Criteria) this;
		}

		public Criteria andVarIsNotNull(String var) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " is not null");
			return (Criteria) this;
		}

		public Criteria andVarEqualTo(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " = ", value, var);
			return (Criteria) this;
		}

		public Criteria andVarNotEqualTo(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " <>", value, var);
			return (Criteria) this;
		}

		public Criteria andVarGreaterThan(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " >", value, var);
			return (Criteria) this;
		}

		public Criteria andVarGreaterThanOrEqualTo(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " >=", value, var);
			return (Criteria) this;
		}

		public Criteria andVarLessThan(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " <", value, var);
			return (Criteria) this;
		}

		public Criteria andVarLessThanOrEqualTo(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " <=", value, var);
			return (Criteria) this;
		}

		public Criteria andVarLike(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " like", value, var);
			return (Criteria) this;
		}

		public Criteria andVarNotLike(String var, String value) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " not like", value, var);
			return (Criteria) this;
		}

		public Criteria andVarIn(String var,List<String> values) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " in", values, var);
			return (Criteria) this;
		}

		// public Criteria andVarNotIn(String var,List<String> values) {
		// 	var = RegexUtils.humpToLine(var);
		// 	addCriterion(var + " not in", values, var);
		// 	return (Criteria) this;
		// }

		public Criteria andVarBetween(String var,String value1, String value2) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " between", value1, value2, var);
			return (Criteria) this;
		}

		public Criteria andVarNotBetween(String var,String value1, String value2) {
			var = RegexUtils.humpToLine(var);
			addCriterion(var + " not between", value1, value2, var);
			return (Criteria) this;
		}

		public Criteria andMap(Map<String, Map<String, Object>> paramMap) {

			Iterator<Map.Entry<String, Map<String, Object>>> entries = paramMap.entrySet().iterator();

			while (entries.hasNext()) {

				Map.Entry<String, Map<String, Object>> entry = entries.next();
				String type = entry.getKey();

				Iterator<Map.Entry<String, Object>> childEntries = entry.getValue().entrySet().iterator();
				while (childEntries.hasNext()) {

					Map.Entry<String, Object> childEntry = childEntries.next();
					String var = childEntry.getKey();
					var = RegexUtils.humpToLine(var);
					Object obj = childEntry.getValue();
					switch (type) {
					case IS_NUll:
						addCriterion(var + " is null");
						break;
					case NOT_NUll:
						addCriterion(var + " is not null");
						break;
					case EQ :
						addCriterion(var + " = ", obj.toString(), var);
						break;
					case NOT_EQ:
						addCriterion(var + " <> ", obj.toString(), var);
						break;
					case LIKE:
						addCriterion(var + " like ", obj.toString(), var);
						break;
					case NOT_LIKE:
						addCriterion(var + " not like ", obj.toString(), var);
						break;
					case IN:
						addCriterion(var + " in", (List<String>)obj, var);
						break;
					case NOT_IN:
						addCriterion(var + " not in", (List<String>)obj, var);
						break;
					case BETWEEN:
						String[] arr=obj.toString().split(",");
						addCriterion(var + " between", arr[0], arr[1], var);
						break;
					case NOT_BETWEEN:
						String[] arr1=obj.toString().split(",");
						addCriterion(var + " not between", arr1[0], arr1[1], var);
						break;
					case LESS_THAN:
						addCriterion(var + " <", obj.toString(), var);
						break;
					case LESS_THAN_OR_EQ:
						addCriterion(var + " <=", obj.toString(), var);
						break;
					case GREAT_THAN:
						addCriterion(var + " >", obj.toString(), var);
						break;
					case GREAT_THAN_OR_EQ:
						addCriterion(var + " >=", obj.toString(), var);
						break;
					default:
						break;
					}
				}
			}
			return (Criteria) this;
		}
	}

	public static class Criteria extends GeneratedCriteria {

		protected Criteria() {
			super();
		}
	}

	public static class Criterion {
		private String condition;

		private Object value;

		private Object secondValue;

		private boolean noValue;

		private boolean singleValue;

		private boolean betweenValue;

		private boolean listValue;

		private String typeHandler;

		public String getCondition() {
			return condition;
		}

		public Object getValue() {
			return value;
		}

		public Object getSecondValue() {
			return secondValue;
		}

		public boolean isNoValue() {
			return noValue;
		}

		public boolean isSingleValue() {
			return singleValue;
		}

		public boolean isBetweenValue() {
			return betweenValue;
		}

		public boolean isListValue() {
			return listValue;
		}

		public String getTypeHandler() {
			return typeHandler;
		}

		protected Criterion(String condition) {
			super();
			this.condition = condition;
			this.typeHandler = null;
			this.noValue = true;
		}

		protected Criterion(String condition, Object value, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.typeHandler = typeHandler;
			if (value instanceof List<?>) {
				this.listValue = true;
			} else {
				this.singleValue = true;
			}
		}

		protected Criterion(String condition, Object value) {
			this(condition, value, null);
		}

		protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
			super();
			this.condition = condition;
			this.value = value;
			this.secondValue = secondValue;
			this.typeHandler = typeHandler;
			this.betweenValue = true;
		}

		protected Criterion(String condition, Object value, Object secondValue) {
			this(condition, value, secondValue, null);
		}
	}
}
