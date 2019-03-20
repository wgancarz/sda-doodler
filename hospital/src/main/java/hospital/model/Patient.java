package hospital.model;

public class Patient implements Comparable<Patient> {
    String firstName;
    String lastName;
    Integer angerLevel;
    Disease disease;

    private int getPriority() {
        if (this.lastName.equalsIgnoreCase("Kowalski")) {
            return Integer.MIN_VALUE;
        } else {
            return angerLevel * disease.getContagiousness();
        }
    }

    @Override
    public int compareTo(Patient otherPatient) {
        return Integer.compare(this.getPriority(), otherPatient.getPriority());
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName + '\n'
                + "Anger level: " + angerLevel + '\n'
                + "Disease name: " + disease.name() + '\n'
                + "Disease contagiousness: " + disease.getContagiousness();
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
