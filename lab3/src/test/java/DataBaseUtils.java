import java.sql.*;

public class DataBaseUtils {
    public static String getMin() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
        String header = "<h1>Product with min price: </h1>\n";

        return executeQueryAndFormatForHTML(sql, header, appendNameAndPrice);
    }

    public static String getMax() throws SQLException {
        String sql = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
        String header = "<h1>Product with max price: </h1>\n";

        return executeQueryAndFormatForHTML(sql, header, appendNameAndPrice);
    }

    public static String getSum() throws SQLException {
        String sql = "SELECT SUM(price) as value FROM PRODUCT";
        String header = "Summary price: \n";

        return executeQueryAndFormatForHTML(sql, header, appendInt);
    }

    public static String getCount() throws SQLException {
        String sql = "SELECT COUNT(*) as value FROM PRODUCT";
        String header = "Number of products: \n";

        return executeQueryAndFormatForHTML(sql, header, appendInt);
    }

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

    public static String executeQueryAndFormatForHTML(String sql, String header, ResultSetHandler rsHandler) throws SQLException {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            StringBuilder sb = new StringBuilder();
            sb.append("<html><body>\n");
            sb.append(header);

            rsHandler.handle(sb, rs);

            sb.append("</body></html>\n");

            stmt.close();

            return sb.toString();
        }
    }

    @FunctionalInterface
    private interface ResultSetHandler {
        void handle(StringBuilder sb, ResultSet rs) throws SQLException;
    }

    private static ResultSetHandler appendNameAndPrice = (StringBuilder sb, ResultSet rs) -> {
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            sb.append(name).append("\t").append(price).append("</br>\n");
        }
    };

    private static ResultSetHandler appendInt = (StringBuilder sb, ResultSet rs) -> {
        while (rs.next()) {
            sb.append(rs.getInt("value")).append('\n');
        }
    };
}
