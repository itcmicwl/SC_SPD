package gyqx.txp.utils.sql;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.AbstractSqlParameterSource;
import org.springframework.util.Assert;

/**
 * {@link SqlParameterSource} implementation that holds a given Map of parameters.
 *
 * <p>This class is intended for passing in a simple Map of parameter values
 * to the methods of the {@link NamedParameterJdbcTemplate} class.
 *
 * <p>The {@code addValue} methods on this class will make adding several values
 * easier. The methods return a reference to the {@link MapSqlParameterSource}
 * itself, so you can chain several method calls together within a single statement.
 *
 * @author Thomas Risberg
 * @author Juergen Hoeller
 * @since 2.0
 * @see #addValue(String, Object)
 * @see #addValue(String, Object, int)
 * @see #registerSqlType
 * @see NamedParameterJdbcTemplate
 */
public class TxpMapSqlParameterSource extends TxpAbstractSqlParameterSource {

	private final Map<String, Object> values = new LinkedHashMap<String, Object>();


	/**
	 * Create an empty MapSqlParameterSource,
	 * with values to be added via {@code addValue}.
	 * @see #addValue(String, Object)
	 */
//	public TxpMapSqlParameterSource() {
//		this(false);
//	}
	boolean omitLackField ;
	public TxpMapSqlParameterSource(boolean omitLackField) 
	{
		this.omitLackField = omitLackField;
	}

	/**
	 * Create a new MapSqlParameterSource, with one value
	 * comprised of the supplied arguments.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @see #addValue(String, Object)
	 */
	public TxpMapSqlParameterSource(String paramName, Object value,boolean omitLackField) {
		this.omitLackField = omitLackField;
		addValue(paramName, value);
	}

	/**
	 * Create a new MapSqlParameterSource based on a Map.
	 * @param values a Map holding existing parameter values (can be {@code null})
	 */
	public TxpMapSqlParameterSource(Map<String, ?> values,boolean omitLackField) {
		this.omitLackField = omitLackField;
		addValues(values);
	}


	/**
	 * Add a parameter to this parameter source.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public TxpMapSqlParameterSource addValue(String paramName, Object value) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.values.put(paramName.toLowerCase(), value);
		if (value instanceof SqlParameterValue) {
			registerSqlType(paramName.toLowerCase(), ((SqlParameterValue) value).getSqlType());
		}
		return this;
	}

	/**
	 * Add a parameter to this parameter source.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @param sqlType the SQL type of the parameter
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public TxpMapSqlParameterSource addValue(String paramName, Object value, int sqlType) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.values.put(paramName.toLowerCase(), value);
		registerSqlType(paramName.toLowerCase(), sqlType);
		return this;
	}

	/**
	 * Add a parameter to this parameter source.
	 * @param paramName the name of the parameter
	 * @param value the value of the parameter
	 * @param sqlType the SQL type of the parameter
	 * @param typeName the type name of the parameter
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public TxpMapSqlParameterSource addValue(String paramName, Object value, int sqlType, String typeName) {
		Assert.notNull(paramName, "Parameter name must not be null");
		this.values.put(paramName.toLowerCase(), value);
		registerSqlType(paramName.toLowerCase(), sqlType);
		registerTypeName(paramName.toLowerCase(), typeName);
		return this;
	}

	/**
	 * Add a Map of parameters to this parameter source.
	 * @param values a Map holding existing parameter values (can be {@code null})
	 * @return a reference to this parameter source,
	 * so it's possible to chain several calls together
	 */
	public TxpMapSqlParameterSource addValues(Map<String, ?> values) {
		if (values != null) {
			for (Map.Entry<String, ?> entry : values.entrySet()) {
				this.values.put(entry.getKey().toLowerCase(), entry.getValue());
				if (entry.getValue() instanceof SqlParameterValue) {
					SqlParameterValue value = (SqlParameterValue) entry.getValue();
					registerSqlType(entry.getKey().toLowerCase(), value.getSqlType());
				}
			}
		}
		return this;
	}

	/**
	 * Expose the current parameter values as read-only Map.
	 */
	public Map<String, Object> getValues() {
		return Collections.unmodifiableMap(this.values);
	}


	@Override
	public boolean hasValue(String paramName) {
		return this.values.containsKey(paramName.toLowerCase());
	}

	@Override
	public Object getValue(String paramName) {
		if (!hasValue(paramName)) {
			if(omitLackField)
				return null;
			throw new IllegalArgumentException("No value registered for key '" + paramName + "'");
		}
		return this.values.get(paramName.toLowerCase());
	}

}
