package pl.ormlite.example;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

/**
 * Created by ZacznijProgramowac.
 */
public class Main {

    public static void main(String[] args) throws SQLException, IOException, ParseException {

        String databaseUrl = "jdbc:sqlite:database.db";
        String databaseUrlH2 = "jdbc:h2:./database";

        ConnectionSource connectionSource = new JdbcConnectionSource(databaseUrlH2);

        TableUtils.dropTable(connectionSource, Book.class, true);
        TableUtils.createTableIfNotExists(connectionSource, Book.class);

        //Pierwsza
        Book book = DataCreator.firstBook();

        //Druga
        Book book2 = DataCreator.secondBook();

        //Wiedźmin
        Book book3 = DataCreator.thirdBook();

        Dao<Book, Integer> dao = DaoManager.createDao(connectionSource, Book.class);

        dao.create(book);
        dao.create(book2);
        dao.create(book3);


        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT * FROM books");
        List<String[]> result = rawResults.getResults();
        result.forEach(e->{
            for (String s : e) {
                System.out.println(s);
            }
        });

        GenericRawResults<String[]> where =dao.queryRaw("SELECT * FROM books WHERE title = 'Hobbit'");
        List<String[]> resultsWhere = where.getResults();
        resultsWhere.forEach(e->{
            for (String s : e) {
                System.out.println(s);
            }
        });

        GenericRawResults<String[]> selectMinMax =dao.queryRaw("SELECT MIN(price), MAX(price) FROM books");
        List<String[]> resultsMinMax = selectMinMax.getResults();
        resultsMinMax.forEach(e->{
            for (String s : e) {
                System.out.println(s);
            }
        });

        GenericRawResults<String[]> selectCount =dao.queryRaw("select count(*) from books where borrowed = 1");
        List<String[]> resultsCount = selectCount.getResults();
        resultsCount.forEach(e->{
            for (String s : e) {
                System.out.println(s);
            }
        });

        double maxUnits = dao.queryRawValue("select AVG(price) from books");
        System.out.println(maxUnits);
    }
}
