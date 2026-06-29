package practices.multiThread;

import java.util.Arrays;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(23, 22, 65, 42, 88, 22, 11, 8, 15, 16, 100, 4, 3);

        NumberProcessor processor = new NumberProcessor(numbers);

        processor.start(10, NumberRules::print);
    }
}