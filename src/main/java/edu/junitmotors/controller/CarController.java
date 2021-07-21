package edu.junitmotors.controller;

import edu.junitmotors.controller.services.ModelService;
import edu.junitmotors.model.Car;
import edu.junitmotors.model.CarModel;
import edu.junitmotors.model.KnownDefect;
import edu.junitmotors.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CarController {

    private ModelService modelService;

    public CarController(ModelService modelService) {
        this.modelService = modelService;
    }

    public List<KnownDefect> findDefects(final List<Car> cars) {
        if ( CollectionUtils.isNullOrEmpty(cars) ) return buildNullOrEmpty(cars, KnownDefect.class);
        final Collection<CarModel> models = this.filterModels(cars);
        return modelService.findDefects(models);
    }

    public Collection<CarModel> filterModels(final List<Car> cars) {
        if ( CollectionUtils.isNullOrEmpty(cars) ) return buildNullOrEmpty(cars, CarModel.class);
        return cars.stream().map(c -> c.getModel()).collect(Collectors.toSet());
    }

    private <T> List<T> buildNullOrEmpty(final List<Car> cars, final Class<T> clazz) {
        if ( cars == null ) return null;
        return Collections.emptyList();
    }
}
