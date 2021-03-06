package pl.ormlite.example.Utils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import pl.ormlite.example.Model.Author;
import pl.ormlite.example.Model.Book;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by ZacznijProgramowac.
 * https://www.youtube.com/zacznijprogramowac
 * http://zacznijprogramowac.net/
 * https://www.facebook.com/zacznijprogramowac
 */
public class DbManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbManager.class);

    private static final String JDBC_DRIVER_SQLITE = "jdbc:sqlite:database";

    private static ConnectionSource connectionSource;

    public static void initDatabase(){
        createConnectionSource();
        dropTable();
        createTable();
        closeConnectionSource();
    }

    private static void createConnectionSource(){
        try {
            connectionSource = new JdbcConnectionSource(JDBC_DRIVER_SQLITE);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public static ConnectionSource getConnectionSource(){
        if(connectionSource == null){
            createConnectionSource();
        }
        return connectionSource;
    }

    public static void closeConnectionSource(){
        if(connectionSource!=null){
            try {
                connectionSource.close();
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
            }
        }
    }

    private static void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, Author.class);
            TableUtils.createTableIfNotExists(connectionSource, Book.class);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private  static  void  dropTable(){
        try {
            TableUtils.dropTable(connectionSource, Author.class, true);
            TableUtils.dropTable(connectionSource, Book.class, true);
        } catch (SQLException e) {
            LOGGER.warn(e.getMessage());
        }
    }
}
