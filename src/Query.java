import com.mysql.jdbc.Driver;

import java.lang.management.ManagementFactory;
import java.sql.*;
import java.text.MessageFormat;

/**
 * Query
 *
 * @author shuai
 * @date 2019/3/10
 */
public class Query {
    public static void main(String[] args) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql:///test";
        String user = "root";
        String password = "root";

        Connection connection = DriverManager.getConnection(url, user, password);
        String sql = "SELECT*FROM emp WHERE DATEDIFF(NOW(),time)>2";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            Date time = resultSet.getDate(3);
            System.out.println(MessageFormat.format("id={0},name={1},time={2}",id,name,time));
        }
        preparedStatement.close();
        connection.close();

    }
}
