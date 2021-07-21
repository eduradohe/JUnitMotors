package edu.junitmotors.controller;

import edu.junitmotors.controller.services.ModelService;
import edu.junitmotors.model.Car;
import edu.junitmotors.model.CarModel;
import edu.junitmotors.model.KnownDefect;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CarController {

    private ModelService modelService;

    public CarController(ModelService modelService) {
        this.modelService = modelService;
    }

    public List<KnownDefect> findDefects(final List<Car> cars) {
        final Set<CarModel> models = this.filterModels(cars);
        return modelService.findDefects(models);
    }

    public Set<CarModel> filterModels(final List<Car> cars) {
        return cars.stream().map(c -> c.getModel()).collect(Collectors.toSet());
    }
}
