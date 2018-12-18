package gyqx.txp.utils.sql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import org.apache.camel.RuntimeCamelException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;

public class ResultSetIterator<T> implements Iterator<T>, Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(ResultSetIterator.class);

    private final DataSource dataSource;
    private final Connection connection;
    private final Statement statement;
    private final ResultSet resultSet;
    private final RowMapper<T> rowMapper;
    private final AtomicBoolean closed = new AtomicBoolean();
    private int rowNum;

    public ResultSetIterator(DataSource dataSource,Connection connection, Statement statement, ResultSet resultSet, RowMapper rowMapper) throws SQLException {
        this.dataSource = dataSource;
    	this.connection = connection;
        this.statement = statement;
        this.resultSet = resultSet;
        this.rowMapper = rowMapper;

        loadNext();
    }

    @Override
    public boolean hasNext() {
        return !closed.get();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        try {
            T next = rowMapper.mapRow(resultSet, rowNum++);
            loadNext();
            return next;
        } catch (SQLException e) {
            close();
            throw new RuntimeCamelException("Cannot process result", e);
        }
    }

    private void loadNext() throws SQLException {
        boolean hasNext = resultSet.next();
        if (!hasNext) {
            close();
        }
    }

    @Override
    public void close() {
        if (closed.compareAndSet(false, true)) {
            safeCloseResultSet();
            safeCloseStatement();
            safeCloseConnection();
        }
    }

    private void safeCloseResultSet() {
        try {
            resultSet.close();
        } catch (SQLException e) {
            LOG.warn("Error by closing result set: " + e, e);
        }
    }

    private void safeCloseStatement() {
        try {
            statement.close();
        } catch (SQLException e) {
            LOG.warn("Error by closing statement: " + e, e);
        }
    }

    private void safeCloseConnection() {
        DataSourceUtils.releaseConnection(connection, dataSource);        
    }

}
