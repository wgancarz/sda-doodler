package hospital.model;

public enum Disease {
    COLD(2),
    DIARRHEA(3),
    INFLUENZA(1),
    SOMETHING_SERIOUS(4);

    private Integer contagiousness;

    Disease(Integer contagiousness) {
        this.contagiousness = contagiousness;
    }

    public Integer getContagiousness() {
        return contagiousness;
    }

    public String toString(){
        return this.name() + " " + "(Contagiousness: " + this.getContagiousness() + ")";
    }
}
