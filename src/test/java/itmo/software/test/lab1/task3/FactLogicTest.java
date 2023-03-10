package itmo.software.test.lab1.task3;

import itmo.software.test.lab1.task3.fact.TruthState;
import itmo.software.test.lab1.task3.invention.Invention;
import itmo.software.test.lab1.task3.invention.InventionType;
import itmo.software.test.lab1.task3.planet.Earth;
import itmo.software.test.lab1.task3.resident.Dolphin;
import itmo.software.test.lab1.task3.resident.Human;
import itmo.software.test.lab1.task3.resident.Population;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FactLogicTest {
    private final String HUMAN_FACTS_SUCH = "Факты людей таковы!";
    private final String HUMAN_FACTS_NOT_SUCH = "Факты людей не таковы!";
    private final String DOLPHIN_FACTS_SUCH = "Факты дельфинов таковы!";
    private final String DOLPHIN_FACTS_NOT_SUCH = "Факты дельфинов не таковы!";
    private final Double DEFAULT_VALUE = 10d;

    @Test
    @DisplayName("Проверка таковости фактов людей, при населении только ими планеты")
    @Description(""" 
            Predictions:
            Создать изобретения людей
            Создать людей, прикрепив к ним изобретения
            Создать Землю, населив её людьми
            """)
    void onlyHumanPopulation_FactTrue_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        Earth earth = new Earth(List.of(human));
        assertAll(
                () -> assertEquals(TruthState.SUCH, earth.getFact(human).getTruth().getTruthState(), HUMAN_FACTS_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка таковости фактов дельфинов, при населении только ими планеты")
    @Description(""" 
            Predictions:
            Создать изобретения дельфинов
            Создать дельфинов, прикрепив к ним изобретения
            Создать Землю, населив её дельфинами
            """)
    void onlyDolphinPopulation_FactTrue_test() {
        List<Invention> dolphinInventions = createDolphinInventions(DEFAULT_VALUE);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(dolphin));
        assertAll(
                () -> assertEquals(TruthState.SUCH, earth.getFact(dolphin).getTruth().getTruthState(), DOLPHIN_FACTS_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка не таковости фактов людей, при не населении ими планеты")
    @Description(""" 
            Predictions:
            Создать изобретения людей
            Создать Людей, прикрепив к ним изобретения
            Создать Землю, не населив её никем
            """)
    void noInPopulation_FactFalse_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        Earth earth = new Earth(List.of());
        assertAll(
                () -> assertEquals(TruthState.NOT_SUCH, earth.getFact(human).getTruth().getTruthState(), HUMAN_FACTS_NOT_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка таковости фактов людей, при внесении ими большего вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения людей, ценнее, чем у дельфинов
            Создать изобретения дельфинов
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void humanPopulation_FactTrue_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = createDolphinInventions(9);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.SUCH, earth.getFact(human).getTruth().getTruthState(), HUMAN_FACTS_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка не таковости фактов людей, при внесении ими меньшего вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения людей
            Создать изобретения дельфинов, ценнее, нежели у людей
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void humanPopulation_FactFalse_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = createDolphinInventions(16);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.NOT_SUCH, earth.getFact(human).getTruth().getTruthState(), HUMAN_FACTS_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка не таковости фактов дельфинов, при внесении ими меньшего вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения людей, ценнее, нежели у дельфинов
            Создать изобретения дельфинов
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void dolphinPopulation_FactFalse_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = createDolphinInventions(9);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.NOT_SUCH, earth.getFact(dolphin).getTruth().getTruthState(), DOLPHIN_FACTS_NOT_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка таковости фактов людей, при внесении ими равного с дельфинами вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения людей, по ценности равными вкладу дельфинов
            Создать изобретения дельфинов, по ценности равными вкладу людей
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void humanPopulation_FactTrue_equalsInventions_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = createDolphinInventions(15);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.SUCH, earth.getFact(human).getTruth().getTruthState(), HUMAN_FACTS_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка таковости фактов дельфинов, при внесении ими равного с дельфинами вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения людей, по ценности равными вкладу дельфинов
            Создать изобретения дельфинов, по ценности равными вкладу людей
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void dolphinPopulation_FactTrue_equalsInventions_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = createDolphinInventions(15);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.SUCH, earth.getFact(dolphin).getTruth().getTruthState(), DOLPHIN_FACTS_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка не таковости фактов людей, при не внесении ими вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения дельфинов
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void humanPopulation_FactFalse_withoutInventions_test() {
        List<Invention> humanInventions = List.of();
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = createDolphinInventions(15);
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.NOT_SUCH, earth.getFact(human).getTruth().getTruthState(), HUMAN_FACTS_NOT_SUCH)
        );
    }

    @Test
    @DisplayName("Проверка не таковости фактов дельфинов, при не внесении ими вклада в изобретения")
    @Description(""" 
            Predictions:
            Создать изобретения людей
            Создать людей
            Создать дельфинов
            Прикрепить соотвественно изобретения
            Создать Землю, заселив её дельфинами и людьми
            """)
    void dolphinPopulation_FactFalse_withoutInventions_test() {
        List<Invention> humanInventions = createHumanInventions(DEFAULT_VALUE);
        Population human = new Human(humanInventions);
        List<Invention> dolphinInventions = List.of();
        Population dolphin = new Dolphin(dolphinInventions);
        Earth earth = new Earth(List.of(human, dolphin));
        assertAll(
                () -> assertEquals(TruthState.NOT_SUCH, earth.getFact(dolphin).getTruth().getTruthState(), DOLPHIN_FACTS_NOT_SUCH)
        );
    }


    private List<Invention> createHumanInventions(double weight) {
        return List.of(
                new Invention(InventionType.WHEEL, weight),
                new Invention(InventionType.WAR, weight),
                new Invention(InventionType.NEW_YORK, weight)
        );
    }

    private List<Invention> createDolphinInventions(double weight) {
        return List.of(
                new Invention(InventionType.HAVE_FUN, weight),
                new Invention(InventionType.SPLASHING_AROUND, weight)
        );
    }
}
