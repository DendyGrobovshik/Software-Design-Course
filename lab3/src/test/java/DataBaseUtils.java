import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUtils {
    public static int getNumberOfProductsByName(String name) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "SELECT COUNT(*) as COUNT "
                    + "FROM PRODUCT "
                    + "WHERE NAME = \"" + name + "\"";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            int result = rs.getInt("count");
            stmt.close();

            return result;
        }
    }

    public static String getAllProductsFormattedForHTML() throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "SELECT NAME, PRICE "
                    + "FROM PRODUCT "
                    + "WHERE TRUE";
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>\n");

            while (rs.next()) {
                String  name = rs.getString("name");
                int price  = rs.getInt("price");
                sb.append(name + "\t" + price + "</br>\n");
            }
            sb.append("</body></html>\n");

            stmt.close();

            return sb.toString();
        }
    }
}
