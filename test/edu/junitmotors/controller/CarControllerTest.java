package edu.junitmotors.controller;

import edu.junitmotors.model.Car;
import edu.junitmotors.model.CarModel;
import edu.junitmotors.model.KnownDefect;
import edu.junitmotors.util.GenericBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarControllerTest {

    private static final CarModel CAR_MODEL_VW_GOLF = GenericBuilder.of(CarModel::new)
            .with(CarModel::setName, "VW")
            .with(CarModel::setBrand, "Golf")
            .build();
    private static final CarModel CAR_MODEL_HD_HB20 = GenericBuilder.of(CarModel::new)
            .with(CarModel::setName, "HD")
            .with(CarModel::setBrand, "HB20")
            .build();

    private static final Car CAR1 = GenericBuilder.of(Car::new)
            .with(Car::setModel, CAR_MODEL_VW_GOLF)
            .with(Car::setYear, 2013)
            .with(Car::setEngineSerialNumber, "12134")
            .build();
    private static final Car CAR2 = GenericBuilder.of(Car::new)
            .with(Car::setModel, CAR_MODEL_HD_HB20)
            .with(Car::setYear, 2016)
            .with(Car::setEngineSerialNumber, "84859")
            .build();
    private static final Car CAR3 = GenericBuilder.of(Car::new)
            .with(Car::setModel, CAR_MODEL_HD_HB20)
            .with(Car::setYear, 2020)
            .with(Car::setEngineSerialNumber, "84859")
            .build();

    private static final KnownDefect KNOWN_DEFECT1 = GenericBuilder.of(KnownDefect::new)
            .with(KnownDefect::setCode, "")
            .with(KnownDefect::setAffectedYears, new HashSet<>(Arrays.asList(2016, 2021)))
            .with(KnownDefect::setModel, CAR_MODEL_HD_HB20)
            .build();

    private static List<Car> CARS_EMPTY = Arrays.asList();
    private static List<Car> CARS_3CARS_2MODELS = Arrays.asList(CAR1, CAR2, CAR3);

    private static List<CarModel> CAR_MODELS_EMPTY = Arrays.asList();

    private static List<KnownDefect> KNOWN_DEFECTS_EMPTY = Arrays.asList();
    private static List<KnownDefect> KNOWN_DEFECTS_1DEFECT = Arrays.asList(KNOWN_DEFECT1);

    private static ModelService modelService;
    private static CarController carController;

    @BeforeAll
    public static void setUpTest() {
        modelService = mock(ModelService.class);
        carController = new CarController(modelService);
    }

    @Test
    public void testFindDefects_withEmptyCarsList() {
        when(modelService.findDefects(this.carController.filterModels(CARS_EMPTY)))
                .thenReturn(KNOWN_DEFECTS_EMPTY);

        final List<KnownDefect> defects = this.carController.findDefects(CARS_EMPTY);

        assertThat(Boolean.TRUE, is(defects.isEmpty()));
    }

    @Test
    public void testFindDefects_withCarsList() {
        when(modelService.findDefects(this.carController.filterModels(CARS_3CARS_2MODELS)))
                .thenReturn(KNOWN_DEFECTS_1DEFECT);

        final List<KnownDefect> defects = this.carController.findDefects(CARS_3CARS_2MODELS);

        assertThat(Boolean.FALSE, is(defects.isEmpty()));
        assertThat(KNOWN_DEFECT1, is(defects.get(0)));
    }
}
