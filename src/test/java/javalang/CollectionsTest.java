package javalang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsTest {

    @Test
    void listTest() {
        final List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        Collections.addAll(numbers, 2, 3);
        numbers.addAll(List.of(4, 5));
        numbers.forEach(nr -> {
            System.out.printf("%d, ", nr);
        });
        System.out.println();

        try {
            final List<Integer> immutableNumbers = Collections.unmodifiableList(numbers);
            immutableNumbers.add(6);
        } catch (Exception e) {
            System.out.println("Change Collections.unmodifiableList: " + e.toString());
        }

        try {
            final List<Integer> immutableNumbers2 = List.of(1, 2, 3);
            immutableNumbers2.add(4);
        } catch (Exception e) {
            System.out.println("Change List.of(): " + e.toString());
        }

    }
}
