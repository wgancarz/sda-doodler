package com.sda.air;

public class Airplane {
    private final static int LIMIT = 1000000;
    private String name;
    private int height;

    public Airplane(String name, int height) {
        if (height >= 0 && height <= LIMIT) {
            this.height = height;
        } else if (height < 0) {
            this.height = 0;
        } else {
            this.height = LIMIT;
        }
        this.name = name;
    }

    public void ascent(int value) {
        this.height = value + this.height <= LIMIT ? this.height + value : LIMIT;
    }

    public void descent(int value) {
        this.height = value <= this.height ? this.height - value : 0;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }
}
