package A;

public class Patient {

    private int id = 0;
    private int medicalCardNumber = 0;
    private String name = "";
    private String surname = "";
    private String patronymic = "";
    private String address = "";
    private String phone = "";
    private String diagnosis = "";

    public Patient() {
    }

    public Patient(int medicalCardNumber, String name, String surname, String patronymic) {
        this.medicalCardNumber = medicalCardNumber;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    private Patient(int id, int medicalCardNumber, String name, String surname,
                    String patronymic, String address, String phone, String diagnosis) {
        this.id = id;
        this.medicalCardNumber = medicalCardNumber;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.address = address;
        this.phone = phone;
        this.diagnosis = diagnosis;
    }

    public static PatientBuilder getPatientBuilder() {
        return new PatientBuilder();
    }

    public static class PatientBuilder {
        private int id = 0;
        private int medicalCardNumber = 0;
        private String name = "";
        private String surname = "";
        private String patronymic = "";
        private String address = "";
        private String phone = "";
        private String diagnosis = "";

        public PatientBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public PatientBuilder setMedicalCardNumber(int medicalCardNumber) {
            this.medicalCardNumber = medicalCardNumber;
            return this;
        }

        public PatientBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public PatientBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public PatientBuilder setPatronymic(String patronymic) {
            this.patronymic = patronymic;
            return this;
        }

        public PatientBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public PatientBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public PatientBuilder setDiagnosis(String diagnosis) {
            this.diagnosis = diagnosis;
            return this;
        }

        public Patient createPatient() {
            return new Patient(id, medicalCardNumber, name, surname, patronymic, address, phone, diagnosis);
        }
    }



    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", medicalCardNumber=" + medicalCardNumber +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicalCardNumber() {
        return medicalCardNumber;
    }

    public void setMedicalCardNumber(int medicalCardNumber) {
        this.medicalCardNumber = medicalCardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
}
