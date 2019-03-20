package hospital.model;

public enum Disease {
    INFLUENZA(1),
    COLD(2),
    DIARRHEA(3),
    SOMETHING_SERIOUS(4);

    private Integer contagiousness;

    Disease(Integer contagiousness) {
        this.contagiousness = contagiousness;
    }

    public Integer getContagiousness() {
        return contagiousness;
    }
}
