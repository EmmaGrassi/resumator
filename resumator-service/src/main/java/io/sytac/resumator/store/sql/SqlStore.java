package io.sytac.resumator.store.sql;

import io.sytac.resumator.ConfigurationException;
import io.sytac.resumator.employee.NewEmployeeCommand;
import io.sytac.resumator.events.EventPublisher;
import io.sytac.resumator.model.Event;
import io.sytac.resumator.store.EventStore;
import io.sytac.resumator.store.IllegalInsertOrderException;
import io.sytac.resumator.store.IllegalStreamOrderException;
import io.sytac.resumator.store.sql.mapper.EventMapper;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.List;

import static io.sytac.resumator.ConfigurationEntries.*;

/**
 * Stores and retrieves events from a SQL database
 *
 * @author Carlo Sciolla
 * @since 0.1
 */
public class SqlStore implements EventStore {

    private final ThreadLocal<SqlSession> session = new ThreadLocal<>();
    private final SqlSessionFactory sessionFactory;
    private final DataSource dataSource;

    @Inject
    public SqlStore(final io.sytac.resumator.Configuration config, final EventPublisher events){
        dataSource = createDataSource(config);
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("resumator", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.getTypeHandlerRegistry().register("io.sytac.resumator.store.sql.mapper");
        configuration.addMapper(EventMapper.class);
        sessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        events.subscribe(this::put, NewEmployeeCommand.EVENT_TYPE);
    }

    private PooledDataSource createDataSource(final io.sytac.resumator.Configuration properties) {
        final String driver = getDBProperty(properties, SQL_DB_DRIVER);
        final String url = getDBProperty(properties, SQL_DB_URL);
        final String user = getDBProperty(properties, SQL_DB_USER);
        final String password = getDBProperty(properties, SQL_DB_PASS);

        return new PooledDataSource(driver, url, user, password);
    }

    private String getDBProperty(final io.sytac.resumator.Configuration properties, final String property) {
        return properties.getProperty(property).orElseThrow(() -> new ConfigurationException(property));
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    @Override
    public void put(Event event) {
        if(session.get() == null) {
            session.set(sessionFactory.openSession());
        }
        EventMapper mapper = session.get().getMapper(EventMapper.class);
        checkInsertOrder(event, mapper);
        checkStreamOrder(event, mapper);
        mapper.put(event);
        session.get().commit();
    }

    private void checkStreamOrder(Event event, EventMapper mapper) {
        if(event.hasStreamOrder()){
            Long streamOrder = event.getStreamOrder();
            Long lastStreamOrder = mapper.getLastStreamOrder(event.getStreamId());
            lastStreamOrder = lastStreamOrder == null ? -1 : lastStreamOrder;
            if(lastStreamOrder >= streamOrder) {
                throw new IllegalStreamOrderException();
            }
        }
    }

    private void checkInsertOrder(Event event, EventMapper mapper) {
        if(event.hasInsertOrder()) {
            Long insertSequence = event.getInsertOrder();
            Long lastInsertSequence = mapper.getLastInsertOrder();
            lastInsertSequence = lastInsertSequence == null ? -1 : lastInsertSequence;
            if(lastInsertSequence >= insertSequence) {
                throw new IllegalInsertOrderException();
            }
        }
    }

    @Override
    public List<Event> getAll() {
        if(session.get() == null) {
            session.set(sessionFactory.openSession());
        }
        EventMapper mapper = session.get().getMapper(EventMapper.class);
        session.get().commit();
        return mapper.getAll();
    }

    @Override
    public void removeAll() {
        if(session.get() == null) {
            session.set(sessionFactory.openSession());
        }
        EventMapper mapper = session.get().getMapper(EventMapper.class);
        mapper.removeAll();
        session.get().commit();
    }
}
