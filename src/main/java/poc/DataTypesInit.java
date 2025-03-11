package poc;

import java.util.*;

import static java.util.stream.Collectors.joining;

public class DataTypesInit {
    public static void main(String[] args) {
        // array
        int[] numbersArray = {1, 2, 3};
        Arrays.stream(numbersArray).forEach(System.out::println);

        // map
        Map<Integer, String> numbersMap = Map.of(
                1, "one",
                2, "two",
                3, "three"
        );
        numbersMap.forEach((k, v) -> System.out.printf("[%d, %s]%n", k, v));
        numbersMap.entrySet().forEach(entry -> System.out.printf(
                "[%d, %s]%n",
                entry.getKey(),
                entry.getValue())
        );

        // set
        Set<Integer> numbersSet = new HashSet<>(List.of(1, 2, 3, 1, 2, 3));
        numbersSet.add(1);
        numbersSet.forEach(System.out::println);

        // list
        List<Integer> numbersList = List.of(1, 2, 3);
        String numbersListString = numbersList.stream()
                .map(n -> "" + n)
                .collect(joining(", "));

        System.out.println(numbersListString);
    }
}
