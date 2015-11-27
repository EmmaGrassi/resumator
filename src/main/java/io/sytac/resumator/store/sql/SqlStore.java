package io.sytac.resumator.store.sql;

import io.sytac.resumator.ConfigurationException;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.store.EventStore;
import io.sytac.resumator.store.StoreException;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * Stores and retrieves events from a SQL database
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class SqlStore implements EventStore {

    private final static String CONF_DB_DRIVER = "resumator.db.driver";
    private final static String CONF_DB_URL    = "resumator.db.url";
    private final static String CONF_DB_USER   = "resumator.db.user";
    private final static String CONF_DB_PASS   = "resumator.db.password";

    private final SqlSessionFactory sqlSessionFactory;
    private final DataSource dataSource;

    public SqlStore(final io.sytac.resumator.Configuration properties){
        dataSource = createDataSource(properties);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("resumator", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    }

    private PooledDataSource createDataSource(final io.sytac.resumator.Configuration properties) {
        final String driver = getDBProperty(properties, CONF_DB_DRIVER);
        final String url = getDBProperty(properties, CONF_DB_URL);
        final String user = getDBProperty(properties, CONF_DB_USER);
        final String password = getDBProperty(properties, CONF_DB_PASS);

        return new PooledDataSource(driver, url, user, password);
    }

    private String getDBProperty(final io.sytac.resumator.Configuration properties, final String property) {
        return properties.getProperty(property).orElseThrow(() -> new ConfigurationException(property));
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void put(Event event) throws StoreException {

    }
}
