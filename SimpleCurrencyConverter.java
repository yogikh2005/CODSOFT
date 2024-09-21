import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SimpleCurrencyConverter {

    private static final String API_KEY = "4dc99106f9002e47f6377a80"; // Replace with your API key
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input base currency
        System.out.print("Enter base currency (e.g., USD): ");
        String baseCurrency = scanner.nextLine().toUpperCase();

        // Input target currency
        System.out.print("Enter target currency (e.g., EUR): ");
        String targetCurrency = scanner.nextLine().toUpperCase();

        // Input amount to convert
        System.out.print("Enter amount to convert: ");
        double amount = scanner.nextDouble();

        // Fetch and convert currency
        double convertedAmount = convertCurrency(baseCurrency, targetCurrency, amount);
        if (convertedAmount != -1) {
            System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);
        } else {
            System.out.println("Conversion failed. Please check your currency codes.");
        }

        scanner.close();
    }

    private static double convertCurrency(String base, String target, double amount) {
        try {
            URL url = new URL(API_URL + base);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            String jsonData = jsonResponse.toString();
            int startIndex = jsonData.indexOf(target) + 5; // Position after "target" key
            int endIndex = jsonData.indexOf(',', startIndex);
            if (endIndex == -1) endIndex = jsonData.indexOf('}', startIndex);

            double rate = Double.parseDouble(jsonData.substring(startIndex, endIndex));
            return amount * rate;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
