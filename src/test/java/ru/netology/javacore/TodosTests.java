package ru.netology.javacore;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TodosTests {

    Todos todos;

    @BeforeEach
    public void beforeEachMethod() {
        todos = new Todos ();
    }

    @ParameterizedTest
    @ValueSource(strings = {"Работа", "Учеба"})
    public void addTaskTest(String g) {
        // given: arrange

        // when: act
        todos.addTask (g);

        // then: assert
        Assertions.assertTrue (todos.getAllTasks ().contains (g));
    }

    @ParameterizedTest
    @ValueSource(strings = {"Работа", "Учеба"})
    public void removeTaskTest(String g) {
        // given: arrange
        todos.addTask (g);

        // when: act
        todos.removeTask (g);

        // then: assert
        Assertions.assertFalse (todos.getAllTasks ().contains (g));
    }

    @Test
    public void getAllTasksTest() {
        // given: arrange
        todos.addTask ("Учеба");
        todos.addTask ("Работа");
        String expected = "Работа Учеба";
        // when: act
        String result = todos.getAllTasks ();

        // then: assert
        Assertions.assertEquals (result, expected);
    }


}

