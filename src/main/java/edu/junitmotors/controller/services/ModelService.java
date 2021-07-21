package edu.junitmotors.controller.services;

import edu.junitmotors.model.CarModel;
import edu.junitmotors.model.KnownDefect;

import java.util.Collection;
import java.util.List;

public interface ModelService {

    List<KnownDefect> findDefects(final Collection<CarModel> models);
}
