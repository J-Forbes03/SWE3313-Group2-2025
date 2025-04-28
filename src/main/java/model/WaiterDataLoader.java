package model;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class WaiterDataLoader {

    public static List<Waiter> loadWaiters() {
        List<Waiter> waiters = new ArrayList<>();

        try (InputStream is = WaiterDataLoader.class.getResourceAsStream("/waiters")) {
            if (is == null) {
                System.out.println("Error: 'waiters' file not found in resources folder!");
                return waiters;
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        String password = parts[2].trim();
                        waiters.add(new Waiter(id, name, password));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading waiter data: " + e.getMessage());
        }

        return waiters;
    }
}
