import java.sql.*;

public class DataBaseUtils {
    public static int getNumberOfProductsByName(String name) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "SELECT COUNT(*) as COUNT "
                    + "FROM PRODUCT "
                    + "WHERE NAME = \"" + name + "\"";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int result = Integer.parseInt(rs.getString("count"));
            stmt.close();

            return result;
        }
    }
}
