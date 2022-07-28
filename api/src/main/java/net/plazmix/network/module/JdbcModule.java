package net.plazmix.network.module;

import javax.sql.DataSource;

public interface JdbcModule extends NetworkModule {

    DataSource getDataSource();
}
