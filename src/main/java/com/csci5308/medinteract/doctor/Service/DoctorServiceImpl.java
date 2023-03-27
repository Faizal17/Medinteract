package com.csci5308.medinteract.doctor.Service;

import com.csci5308.medinteract.city.model.CityModel;
import com.csci5308.medinteract.doctor.Model.DoctorModel;
import com.csci5308.medinteract.doctor.Repository.DoctorRepository;
import com.csci5308.medinteract.province.model.ProvinceModel;
import com.csci5308.medinteract.utilities.PasswordEncodeDecode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Autowired
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
        return doctorRepository.findById(id);
    }

    @Override
    public Map<String, Object> checkIfEmailExists(String email) {
        Map<String, Object> res = new HashMap<>();
        Optional<DoctorModel> newDoctor = doctorRepository.findByDoctorEmail(email);
        boolean result = newDoctor.isPresent() && (newDoctor.get().isActive() || newDoctor.get().isBlocked());
        res.put("result", result);
        if (newDoctor.isPresent()) {
            res.put("id", newDoctor.get().getId());
            res.put("data", newDoctor.get());
        }
        return res;
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
        if (doctor.isPresent() && doctor.get().getDoctorPassword().equals(encodedPassword) && doctor.get().isActive()
                && !doctor.get().isBlocked()) {
            // valid doctor
            System.out.println(doctor.get().getDoctorEmail());
            System.out.println(doctor.get().getDoctorPassword());
            return true;
        }
        return false;
    }

    public String encodePassword(String password) throws Exception {
        System.out.println("password to encode : " + password);
        return PasswordEncodeDecode.encrypt(password);
    }

    public List<DoctorModel> fetchDoctorsOnCity(DoctorModel doctorModel) {
        Long cityId = doctorModel.getDoctorAddressCity();
        return doctorRepository.findByDoctorAddressCity(cityId);
    }

    public List<DoctorModel> fetchDoctorsOnProvince(DoctorModel doctorModel) {
        Long provinceId = doctorModel.getDoctorAddressProvince();
        return doctorRepository.findByDoctorAddressProvince(provinceId);
    }

    public List<DoctorModel> fetchDoctorsOnName(DoctorModel doctorModel) {
        String name = doctorModel.getDoctorName();
        return doctorRepository.findByDoctorNameContaining(name);
    }

    public List<DoctorModel> fetchDoctorsOnQualification(DoctorModel doctorModel) {
        String qualification = doctorModel.getDoctorQualification();
        return doctorRepository.findByDoctorQualificationContaining(qualification);
    }

    @Override
    public List<DoctorModel> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<DoctorModel> getDoctorById(Long id) {
        return doctorRepository.findById(id);
    }

    @Override
    public void deleteDoctorById(Long id) {
        doctorRepository.deleteById(id);
    }

    @Transactional
    public void updateDoctorById(
            Long id,
            String new_doctorName,
            String new_doctorAddressPostalCode,
            String new_doctorAddressStreet,
            String new_doctorMobileNumber) {

        DoctorModel doctorModel = doctorRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                "Doctor with id " + id + " does not exist"));

        // Update name:
        if (new_doctorName != null && new_doctorName.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorName(), new_doctorName)) {
            doctorModel.setDoctorName(new_doctorName);
        }

        // Update AddressPostalCode:
        if (new_doctorAddressPostalCode != null && new_doctorAddressPostalCode.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorAddressPostalCode(), new_doctorAddressPostalCode)) {
            doctorModel.setDoctorAddressPostalCode(new_doctorAddressPostalCode);
        }

        // Update AddressStreet:
        if (new_doctorAddressStreet != null && new_doctorAddressStreet.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorAddressStreet(), new_doctorAddressStreet)) {
            doctorModel.setDoctorAddressStreet(new_doctorAddressStreet);
        }

        // Update MobileNumber:
        if (new_doctorMobileNumber != null && new_doctorMobileNumber.length() > 0 &&
                !Objects.equals(doctorModel.getDoctorMobileNumber(), new_doctorMobileNumber)) {
            doctorModel.setDoctorMobileNumber(new_doctorMobileNumber);
        }
    }

    @Override
    public List<DoctorModel> isPending() {
        Optional<List<DoctorModel>> pendingDoctorList;
        pendingDoctorList = doctorRepository.findPendingDoctors();
        return pendingDoctorList.orElse(null);
    }

    @Override
    public List<DoctorModel> isApproved() {
        Optional<List<DoctorModel>> approvedDoctorList;
        approvedDoctorList = doctorRepository.findApprovedDoctors();
        return approvedDoctorList.orElse(null);
    }

    @Override
    public List<DoctorModel> isBlocked() {
        Optional<List<DoctorModel>> blockedDoctorList;
        blockedDoctorList = doctorRepository.findBlockedDoctors();
        return blockedDoctorList.orElse(null);
    }

    @Override
    public void verifyDoctor(String email, boolean isActive, boolean isBlocked) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findByDoctorEmail(email);

        if (doctorOptional.isPresent()) {
            DoctorModel doctor = doctorOptional.get();
            doctor.setActive(isActive);
            doctor.setBlocked(!isBlocked);
            doctorRepository.save(doctor);
        }
    }

    @Override
    public void blockDoctor(String email, boolean isBlocked) {
        Optional<DoctorModel> doctorOptional = doctorRepository.findByDoctorEmail(email);

        if (doctorOptional.isPresent()) {
            DoctorModel doctor = doctorOptional.get();
            doctor.setBlocked(isBlocked);
            doctorRepository.save(doctor);
        }
    }

    public List<DoctorModel> getDoctorByDetails(DoctorModel doctorModel) {
        String name = doctorModel.getDoctorName();
        Long province = doctorModel.getDoctorAddressProvince();
        Long city = doctorModel.getDoctorAddressCity();
        String qualification = doctorModel.getDoctorQualification();
        return doctorRepository.findByDoctorOnDetails(name, province, city, qualification);
    }

    public List<Map<String, Object>> findDoctorOnDetailsWithCity(DoctorModel doctorModel) {
        String name = doctorModel.getDoctorName();
        Long province = doctorModel.getDoctorAddressProvince();
        Long city = doctorModel.getDoctorAddressCity();
        String qualification = doctorModel.getDoctorQualification();
        List<Object> doctorModelList = doctorRepository.findDoctorOnDetailsWithCity(name, province, city,
                qualification);
        List<Map<String, Object>> doctorDetailsList = new ArrayList<>();

        for (int i = 0; i < doctorModelList.size(); i++) {
            DoctorModel doctorModel1 = (DoctorModel) (((Object[]) doctorModelList.get(i))[0]);
            ProvinceModel provinceModel = (ProvinceModel) (((Object[]) doctorModelList.get(i))[1]);
            CityModel cityModel = (CityModel) (((Object[]) doctorModelList.get(i))[2]);
            Map<String, Object> data = new HashMap<>();

            data.put("id", doctorModel1.getId());
            data.put("doctorEmail", doctorModel1.getDoctorEmail());
            data.put("doctorName", doctorModel1.getDoctorName());
            data.put("doctorAddressProvince", provinceModel.getName());
            data.put("doctorAddressCity", cityModel.getCity());
            data.put("doctorType", doctorModel1.getDoctorType());
            data.put("doctorQualification", doctorModel1.getDoctorQualification());

            doctorDetailsList.add(data);
        }

        return doctorDetailsList;
    }

    public List<Map<String, Object>> findDoctorOnDetailsWithCityAndFeedback(DoctorModel doctorModel) {
        String name = doctorModel.getDoctorName();
        Long province = doctorModel.getDoctorAddressProvince();
        Long city = doctorModel.getDoctorAddressCity();
        String qualification = doctorModel.getDoctorQualification();
        List<Object> doctorModelList = doctorRepository.findDoctorOnDetailsWithCityAndFeedback(name, province, city,
                qualification);
        List<Map<String, Object>> doctorDetailsList = new ArrayList<>();

        for (int i = 0; i < doctorModelList.size(); i++) {
            DoctorModel doctorModel1 = (DoctorModel) (((Object[]) doctorModelList.get(i))[0]);
            ProvinceModel provinceModel = (ProvinceModel) (((Object[]) doctorModelList.get(i))[1]);
            CityModel cityModel = (CityModel) (((Object[]) doctorModelList.get(i))[2]);
            Long feedbackCount = (Long) (((Object[]) doctorModelList.get(i))[3]);
            Long feedbackAvg = (Long) (((Object[]) doctorModelList.get(i))[4]);
            Map<String, Object> data = new HashMap<>();

            data.put("id", doctorModel1.getId());
            data.put("doctorEmail", doctorModel1.getDoctorEmail());
            data.put("doctorName", doctorModel1.getDoctorName());
            data.put("doctorAddressProvince", provinceModel.getName());
            data.put("doctorAddressCity", cityModel.getCity());
            data.put("doctorType", doctorModel1.getDoctorType());
            data.put("doctorQualification", doctorModel1.getDoctorQualification());
            data.put("feebackCount", feedbackCount);
            data.put("feedbackAverage", feedbackAvg);

            doctorDetailsList.add(data);
        }

        return doctorDetailsList;
    }
}
