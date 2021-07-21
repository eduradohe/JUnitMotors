package edu.junitmotors.controller;

import edu.junitmotors.controller.services.ModelService;
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

    // CAR MODELS
    private static final CarModel CAR_MODEL_VW_GOLF = GenericBuilder.of(CarModel::new)
            .with(CarModel::setName, "VW")
            .with(CarModel::setBrand, "Golf")
            .build();
    private static final CarModel CAR_MODEL_HD_HB20 = GenericBuilder.of(CarModel::new)
            .with(CarModel::setName, "HD")
            .with(CarModel::setBrand, "HB20")
            .build();
    private static final CarModel CAR_MODEL_HD_I30 = GenericBuilder.of(CarModel::new)
            .with(CarModel::setName, "HD")
            .with(CarModel::setBrand, "i30")
            .build();

    // CARS
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
    private static final Car CAR4 = GenericBuilder.of(Car::new)
            .with(Car::setModel, CAR_MODEL_HD_I30)
            .with(Car::setYear, 2021)
            .with(Car::setEngineSerialNumber, "435435")
            .build();

    private static List<Car> CARS_EMPTY = Arrays.asList();
    private static List<Car> CARS_3CARS_2MODELS = Arrays.asList(CAR1, CAR2, CAR3);
    private static List<Car> CARS_3CARS_3MODELS = Arrays.asList(CAR1, CAR2, CAR4);

    // KNOWN DEFECTS
    private static final KnownDefect KNOWN_DEFECT1 = GenericBuilder.of(KnownDefect::new)
            .with(KnownDefect::setCode, "ERRHB2016")
            .with(KnownDefect::setAffectedYears, new HashSet<>(Arrays.asList(2016, 2021)))
            .with(KnownDefect::setModel, CAR_MODEL_HD_HB20)
            .build();
    private static final KnownDefect KNOWN_DEFECT2 = GenericBuilder.of(KnownDefect::new)
            .with(KnownDefect::setCode, "ERRI3020")
            .with(KnownDefect::setAffectedYears, new HashSet<>(Arrays.asList(2020, 2021)))
            .with(KnownDefect::setModel, CAR_MODEL_HD_I30)
            .build();

    private static List<KnownDefect> KNOWN_DEFECTS_EMPTY = Arrays.asList();
    private static List<KnownDefect> KNOWN_DEFECTS_1DEFECT = Arrays.asList(KNOWN_DEFECT1);
    private static List<KnownDefect> KNOWN_DEFECTS_2DEFECTS = Arrays.asList(KNOWN_DEFECT1, KNOWN_DEFECT2);

    // mocks and controller
    private static ModelService modelService;
    private static CarController carController;

    @BeforeAll
    public static void setUpTest() {
        modelService = mock(ModelService.class);
        carController = new CarController(modelService);
    }

    @Test
    public void testFilterModels_with3CarsAnd2Models() {
        final Set<CarModel> models = this.carController.filterModels(CARS_3CARS_2MODELS);

        assertThat(2, is(models.size()));
        assertThat(Boolean.TRUE, is(models.contains(CAR_MODEL_VW_GOLF)));
        assertThat(Boolean.TRUE, is(models.contains(CAR_MODEL_HD_HB20)));
        assertThat(Boolean.FALSE, is(models.contains(CAR_MODEL_HD_I30)));
    }

    @Test
    public void testFilterModels_with3CarsAnd3Models() {
        final Set<CarModel> models = this.carController.filterModels(CARS_3CARS_3MODELS);

        assertThat(3, is(models.size()));
        assertThat(Boolean.TRUE, is(models.contains(CAR_MODEL_VW_GOLF)));
        assertThat(Boolean.TRUE, is(models.contains(CAR_MODEL_HD_HB20)));
        assertThat(Boolean.TRUE, is(models.contains(CAR_MODEL_HD_I30)));
    }

    @Test
    public void testFilterModels_withEmptyCarList() {
        final Set<CarModel> models = this.carController.filterModels(CARS_EMPTY);

        assertThat(Boolean.TRUE, is(models.isEmpty()));
    }

    @Test
    public void testFindDefects_withEmptyCarsList() {
        final Set<CarModel> expectedCarModelList = this.carController.filterModels(CARS_EMPTY);
        when(modelService.findDefects(expectedCarModelList)).thenReturn(KNOWN_DEFECTS_EMPTY);

        final List<KnownDefect> defects = this.carController.findDefects(CARS_EMPTY);

        assertThat(Boolean.TRUE, is(defects.isEmpty()));
    }

    @Test
    public void testFindDefects_with3CarsAnd2Models() {
        final Set<CarModel> expectedCarModelList = this.carController.filterModels(CARS_3CARS_2MODELS);
        when(modelService.findDefects(expectedCarModelList)).thenReturn(KNOWN_DEFECTS_1DEFECT);

        final List<KnownDefect> defects = this.carController.findDefects(CARS_3CARS_2MODELS);

        assertThat(Boolean.FALSE, is(defects.isEmpty()));
        assertThat(Boolean.TRUE, is(defects.contains(KNOWN_DEFECT1)));
        assertThat(Boolean.FALSE, is(defects.contains(KNOWN_DEFECT2)));
    }

    @Test
    public void testFindDefects_with3CarsAnd3Models() {
        final Set<CarModel> expectedCarModelList = this.carController.filterModels(CARS_3CARS_3MODELS);
        when(modelService.findDefects(expectedCarModelList)).thenReturn(KNOWN_DEFECTS_2DEFECTS);

        final List<KnownDefect> defects = this.carController.findDefects(CARS_3CARS_3MODELS);

        assertThat(Boolean.FALSE, is(defects.isEmpty()));
        assertThat(Boolean.TRUE, is(defects.contains(KNOWN_DEFECT1)));
        assertThat(Boolean.TRUE, is(defects.contains(KNOWN_DEFECT2)));
    }
}
