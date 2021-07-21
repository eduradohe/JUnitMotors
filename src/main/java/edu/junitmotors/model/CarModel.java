package edu.junitmotors.model;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CarModel {

    private static final Integer INITIAL_VERSION = 1;

    private String brand;
    private String name;
    private Integer version;

    public CarModel() {
        this.version = INITIAL_VERSION;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    /**
     * Increments version defaulting by 1 at a time
     */
    public void incrementVersion() {
        this.incrementVersionBy(1);
    }

    /**
     * Increments version accordingly to parameter passed
     * @param by Number of incremental interaction
     */
    public void incrementVersionBy(final Integer by) {
        this.version += by;
    }

    public String getDataUXFormatted() {
        try {
            return this.prepareDataUXFormatted();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Model{" +
                "brand='" + brand + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                '}';
    }

    /**
     * Gets user friendly data for this Model in the following format:
     * Brand: <code>brand</code>
     * Name: <code>name</code>
     * Version: <code>version</code>
     *
     * @return The user friendly data for this Model
     */
    private String prepareDataUXFormatted() throws IllegalAccessException {

        final StringBuilder sb = new StringBuilder();

        final List<Field> fields = Arrays.stream(this.getClass().getDeclaredFields())
                .filter(f -> !(Modifier.isFinal(f.getModifiers()) && Modifier.isStatic(f.getModifiers())))
                .collect(Collectors.toList());

        for ( final Field field : fields ) {

            final String fieldName = field.getName();
            final String fieldValue = field.get(this) == null ? "" : field.get(this).toString();

            sb.append(fieldName);
            sb.append(": ");
            sb.append(fieldValue);
            sb.append("\n");
        }

        return sb.toString();
    }
}
