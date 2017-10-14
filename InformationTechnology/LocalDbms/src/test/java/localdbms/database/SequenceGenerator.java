package localdbms.database;

import java.util.Random;
import java.util.stream.Stream;

public class SequenceGenerator {
    public static Stream<String> getStringGenerator(int length) {
        return Stream.generate(() -> {
            Random random = new Random(System.nanoTime());
            StringBuilder name = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                name.append((char)('a' + Math.abs(random.nextInt()%26)));
            }
            return name.toString();
        });
    }
}
