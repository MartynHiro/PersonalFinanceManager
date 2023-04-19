import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

class SumCalculatorTest {
    private Storage storage;
    private SumCalculator calculator = new SumCalculator();

    @BeforeEach
    public void init() {
        storage = Mockito.mock(Storage.class);

    }

    @ParameterizedTest
    @ValueSource(strings = {"еда", "одежда", "быт", "финансы", "другое"})
    void testFormingJsonForAnswerWithDiffCategories(String category) {

        Mockito.when(storage.getMaxCategory()).thenReturn(category);
        Mockito.when(storage.getMaxSum()).thenReturn(1666);

        Assertions.assertEquals("{\"maxCategory\":{\"sum\":1666,\"category\":\"" + category + "\"}}",
                calculator.formingJsonForAnswer(storage));
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 400, 3293384, 5958473, 0})
    void testFormingJsonForAnswerWithDiffNumbers(int sum) {
        Mockito.when(storage.getMaxCategory()).thenReturn("тест");
        Mockito.when(storage.getMaxSum()).thenReturn(sum);

        Assertions.assertEquals("{\"maxCategory\":{\"sum\":" + sum + ",\"category\":\"тест\"}}",
                calculator.formingJsonForAnswer(storage));
    }
}