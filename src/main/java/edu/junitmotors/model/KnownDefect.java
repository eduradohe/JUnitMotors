package edu.junitmotors.model;

import java.util.Set;

public class KnownDefect {

    private CarModel model;
    private Set<Integer> affectedYears;
    private String code;

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public Set<Integer> getAffectedYears() {
        return affectedYears;
    }

    public void setAffectedYears(Set<Integer> affectedYears) {
        this.affectedYears = affectedYears;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
