package az.apexcoach.api.simulation.internal.generic;

import org.springframework.stereotype.Component;
import shared.error.TelemetryParseException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class GenericCsvParser {

    public List<Map<String, String>> parse(byte[] content) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new ByteArrayInputStream(content), StandardCharsets.UTF_8)
        )) {
            String headerLine = reader.readLine();

            if (headerLine == null || headerLine.isBlank()) {
                throw new TelemetryParseException("CSV file is empty");
            }

            String[] headers = headerLine.split(",");
            List<Map<String, String>> rows = new ArrayList<>();

            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length && i < values.length; i++) {
                    row.put(headers[i].trim(), values[i].trim());
                }

                rows.add(row);
            }

            return rows;
        } catch (Exception e) {
            throw new TelemetryParseException("Unable to parse CSV telemetry", e);
        }
    }
}
