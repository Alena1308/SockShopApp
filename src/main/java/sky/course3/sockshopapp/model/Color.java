package sky.course3.sockshopapp.model;

public enum Color {
    WHITE("Белый"), BLACK("Черный"), YELLOW("Желтый"),
    GREEN("Зеленый"), BLUE("Синий"), GRAY("Серый"),
    RED("Красный"), BROWN("Коричневый"), PINK("Розовый"),
    ORANGE("Оранжевый"), PURPLE("Фиолетовый"), MULTICOLOUR("Цветной");
    private final String namesColor;

    Color(String namesColor) {
        this.namesColor = namesColor;
    }

    public String getNamesColor() {
        return namesColor;
    }
}
