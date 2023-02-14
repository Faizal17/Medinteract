package com.csci5308.medinteract.Doctor.Service;

import com.csci5308.medinteract.Doctor.Model.DoctorModel;
import com.csci5308.medinteract.Doctor.Repository.DoctorRepository;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService{

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<DoctorModel> fetchAll() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorModel saveDoctor(DoctorModel doctorModel) {
        return doctorRepository.save(doctorModel);
    }

    @Override
    public Optional<DoctorModel> findById(Long id) {
        return  doctorRepository.findById(id);
    }



    @Override
    public boolean checkIfEmailExists(String email) {
        boolean result ;
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        result = newDoctor.isPresent() && newDoctor.get().isActive();
        return result;
    }

    @Override
    public DoctorModel getDoctorByEmail(String email) {
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        return newDoctor.orElse(null);
    }

    @Override
    public boolean isDoctorValid(String doctorEmail, String doctorPassword) throws Exception {
        Optional<DoctorModel> doctor = doctorRepository.findByDoctorEmail(doctorEmail);

        String encodedPassword = encodePassword(doctorPassword);
        if(doctor.isPresent() && doctor.get().getDoctorPassword().equals(encodedPassword) && doctor.get().isActive())
        {
            //valid doctor
            System.out.println(doctor.get().getDoctorEmail());
            System.out.println(doctor.get().getDoctorPassword());
            return true;
        }
        return false;
    }

    public String encodePassword(String password) throws Exception {
        System.out.println("password to encode : "+password);
        return PasswordEncodeDecode.encrypt(password);
    }

    

}
