import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.HttpURLConnection;

public class Main {
    public static void main(String[] args) {
        System.out.print(api("What is 1 + 1?"));
    }

    public static String api(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String api_key = "";
        String model = "gpt-3.5-turbo";

        try {
            // Create the connection to the API
            URL object = new URL(url);
            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + api_key);
            con.setRequestProperty("Content-Type", "application/json");

            // Correct the JSON payload, fixing any errors in the structure
            String body = "{\"model\":\"" + model + "\", \"messages\": [{\"role\":\"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Read the response from the API
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Extract the response content
            return (response.toString().split("\"content\":\"")[1].split("\""))[0];

        } catch (IOException ee) {
            System.out.println("Error: " + ee.getMessage());
            return "Request failed";
        }
    }
}
