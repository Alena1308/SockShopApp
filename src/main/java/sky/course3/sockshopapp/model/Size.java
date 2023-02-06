package sky.course3.sockshopapp.model;

public enum Size {
    XS("34-36"), S("37-39"),
    M("40-42"), L("43-45"),
    XL("46-48");
    private final String russianSize;

    Size(String russianSize) {
        this.russianSize = russianSize;
    }

    public String getRussianSize() {
        return russianSize;
    }
}
