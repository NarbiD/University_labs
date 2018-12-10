package A;

public class Car {
    private String model;
    private String num;
    private String color;

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", num='" + num + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
