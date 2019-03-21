package hospital.model;

public class Patient {
    String firstName;
    String lastName;
    Integer angerLevel;
    Disease disease;

    @Override
    public String toString() {
        return getFullName() + ": "
                + "Anger: " + angerLevel + ", "
                + "Disease: " + disease;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAngerLevel() {
        return angerLevel;
    }

    public void setAngerLevel(Integer angerLevel) {
        this.angerLevel = angerLevel;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }
}
