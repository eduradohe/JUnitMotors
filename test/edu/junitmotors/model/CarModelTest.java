package edu.junitmotors.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarModelTest {

    private static final String CAR_MODEL_BRAND_VW = "VW";
    private static final String CAR_MODEL_NAME_GOLF = "Golf";

    private static CarModel defaultCarModel;

    private CarModel carModel;

    @BeforeAll
    public static void setUpClass () {
        defaultCarModel = buildDefaultCarModel();
    }

    @BeforeEach
    public void setUp() {
        this.carModel = defaultCarModel;
    }

    private Pattern getUXFormatPattern() {

        final StringBuilder sb = new StringBuilder();

        sb.append("brand: ");
        sb.append("(?!null)\\w*[\\r\\n]");
        sb.append("name: ");
        sb.append("(?!null)\\w*[\\r\\n]");
        sb.append("version: ");
        sb.append("(?!null)\\w*[\\r\\n]");

        return Pattern.compile(sb.toString());
    }

    private void testUxMatching( final CarModel carModel ) {
        final String uxFormattedCarModel = carModel.getDataUXFormatted();
        assertThat(Boolean.TRUE, is(getUXFormatPattern().matcher(uxFormattedCarModel).matches()));
    }

    @Test
    public void getDataUXFormatted_withNoIssues() {
        this.testUxMatching(this.carModel);
    }

    @Test
    public void getDataUXFormatted_withNullBrand() {
        this.carModel.setBrand(null);
        this.testUxMatching(this.carModel);
    }

    @Test
    public void getDataUXFormatted_withNullName() {
        this.carModel.setName(null);
        this.testUxMatching(this.carModel);
    }

    @Test
    public void incrementVersion_withNoIssues() {

        final Integer previousVersion = this.carModel.getVersion();
        this.carModel.incrementVersion();
        final Integer currentVersion = this.carModel.getVersion();

        assertThat(currentVersion, is(previousVersion + 1));
    }

    private static CarModel buildDefaultCarModel() {

        final CarModel carModel = new CarModel();

        carModel.setBrand(CAR_MODEL_BRAND_VW);
        carModel.setName(CAR_MODEL_NAME_GOLF);

        return carModel;
    }
}
