package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Client2ch {
    private static final URL BOARDS_URL = createURL("https://2ch.hk/api/mobile/v2/boards");

    public static String fetchBoards() throws IOException {
        return fetch(BOARDS_URL);
    }

    public static String fetchThread(final String id) throws IOException {
        final String urlString = String.format("https://2ch.hk/%s/catalog_num.json", id);
        final URL url = createURL(urlString);
        return fetch(url);
    }

    private static String fetch(final URL url) throws IOException {
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            final int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readStream(connection.getInputStream());
            }

            throw new IOException("Server response code: " + responseCode);
        } finally {
            connection.disconnect();
        }
    }

    private static URL createURL(final String urlString) {
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String readStream(final InputStream stream) throws IOException {
        try (final BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream)
        )) {
            final StringBuilder responseContent = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
            }

            return responseContent.toString();
        }
    }
}
