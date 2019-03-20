package hospital.model;

public class Patient implements Comparable<Patient> {
    String firstName;
    String lastName;
    Integer angerLevel;
    Disease disease;

    private int getPriority() {
        return angerLevel * disease.getContagiousness();
    }

    @Override
    public int compareTo(Patient otherPatient) {
        final String PRIORITY_LASTNAME = "Kowalski";

        if ((this.getLastName().equalsIgnoreCase(PRIORITY_LASTNAME))
                && !(otherPatient.getLastName().equalsIgnoreCase(PRIORITY_LASTNAME))) {
            return -1;
        }

        if ((otherPatient.getLastName().equalsIgnoreCase(PRIORITY_LASTNAME))
                && !(this.getLastName().equalsIgnoreCase(PRIORITY_LASTNAME))) {
            return 1;
        }

        if (this.getPriority() > otherPatient.getPriority()) {
            return -1;
        } else if (this.getPriority() == otherPatient.getPriority()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + ": "
                + "Anger: " + angerLevel + ", "
                + "Disease: " + disease.name() + ", "
                + "Contagiousness: " + disease.getContagiousness();
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
