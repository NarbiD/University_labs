package A;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PatientTest {
    List<Patient> patients = new ArrayList<>();

    @Before
    public void before() {
        patients.add(new Patient());
        patients.add(new Patient(1212, "Иван", "Иванов", "Иванович"));
        patients.get(1).setDiagnosis("ОРЗ");
        patients.add(Patient.getPatientBuilder()
                .setSurname("Пупкин")
                .setName("Василий")
                .setPatronymic("Петрович")
                .setMedicalCardNumber(1213)
                .setPhone("+38 (050) 000-00-00")
                .setDiagnosis("ОРЗ")
                .createPatient()
        );
        patients.add(Patient.getPatientBuilder()
                .setSurname("Петров")
                .setName("Пётр")
                .setPatronymic("Петрович")
                .setMedicalCardNumber(1214)
                .setPhone("+38 (050) 111-00-00")
                .setDiagnosis("ВСД")
                .createPatient()
        );
    }


    @Test
    public  void getPacientsByDiagnosis() {
        List<Patient> patientsWithDiagnosis = new ArrayList<>();
        for (Patient patient : patients) {
            if ("орз".equals(patient.getDiagnosis().toLowerCase())) {
                patientsWithDiagnosis.add(patient);
            }
        }
        System.out.println("\nПациенты с диагнозом ОРЗ:");
        System.out.println(patientsWithDiagnosis);
    }

    @Test
    public void getPatientsWithMedCardNumberBetween(){
    List<Patient> resultPatients = new ArrayList<>();
    int from = 1213, to = 1214;
        patients.forEach(patient -> {
            if (patient.getMedicalCardNumber() >= from && patient.getMedicalCardNumber() <= to) {
                resultPatients.add(patient);
            }
        });
        System.out.println("\nПациенты с номером медицинской карты от 1213 до 1214:");
        System.out.println(resultPatients);

    }


}
