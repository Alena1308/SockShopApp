package sky.course3.sockshopapp.model;

public enum Size {
    XS(new double[]{36,36.5,37,37.5}), S(new double[]{38,38.5,39,39.5}),
    M(new double[]{40,40.5,41,41.5}), L(new double[]{42,42.5,43,43.5}),
    XL(new double[]{44,44.5,45,45.5,46});
    private final double [] russianSize;

    Size(double[] russianSize) {
        this.russianSize = russianSize;
    }

    public double[] getRussianSize() {
        return russianSize;
    }
}
