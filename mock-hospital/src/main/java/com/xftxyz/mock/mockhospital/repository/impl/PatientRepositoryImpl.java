package com.xftxyz.mock.mockhospital.repository.impl;

import com.xftxyz.mock.mockhospital.domain.Patient;
import com.xftxyz.mock.mockhospital.repository.PatientRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class PatientRepositoryImpl implements PatientRepository {

    private List<Patient> patients = new ArrayList<>();

    @Override
    public void save(Patient patient) {
        patients.add(patient);
    }

    @Override
    public void delete(Long id) {
        patients.removeIf(patient -> patient.getId().equals(id));
    }

    @Override
    public void update(Patient patient) {
        delete(patient.getId());
        save(patient);
    }

    @Override
    public List<Patient> query(Predicate<Patient> filter) {
        return patients.stream().filter(filter).collect(Collectors.toList());
    }
}
