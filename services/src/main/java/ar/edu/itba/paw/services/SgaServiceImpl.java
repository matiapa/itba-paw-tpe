package ar.edu.itba.paw.services;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.UserData;

@Service
public class SgaServiceImpl implements SgaService {

    private ObjectMapper mapper;
    private static final String EMAIL_ENDPOINT = "https://itbagw.itba.edu.ar/api/v1/people/1wXxftFa4NTfsmOstgnQHDq55m7jZL1jq7r7gWlprbHg?email=";
    private static final String DNI_ENDPOINT = "https://itbagw.itba.edu.ar/api/v1/students/1wXxftFa4NTfsmOstgnQHDq55m7jZL1jq7r7gWlprbHg/";

    @Autowired
    private CareerService careerService;

    public SgaServiceImpl() {
        this.mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    @Override
    public UserData fetchFromEmail(String email) {
        try {
            SgaBasicData basicData = mapper.readValue(new URL(EMAIL_ENDPOINT + email), SgaBasicData.class);
            SgaExtendedData extendedData = mapper.readValue(new URL(DNI_ENDPOINT + basicData.dni), SgaExtendedData.class);
            Optional<Career> career = careerService.findByCode(extendedData.careerCode);
            if(!career.isPresent())
                return null;
            return new SgaUser(extendedData.code, basicData.firstName, basicData.lastName, email, career.get());
        } catch (IOException e) {
            return null;
        }
    }

    private static class SgaUser implements UserData {
        private final int id;
        private final String name;
        private final String surname;
        private final String email;
        private final Career career;

        public SgaUser(int id, String name, String surname, String email, Career career) {
            this.id = id;
            this.name = name;
            this.surname = surname;
            this.email = email;
            this.career = career;
        }

        @Override
        public int getId() {
            return id;
        }

        @Override
        public String getFullName(){ return name + " " + surname; }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSurname() {
            return surname;
        }

        @Override
        public String getEmail() {
            return email;
        }

        @Override
        public Career getCareer() {
            return career;
        }

        @Override
        public byte[] getPicture() {
            return null;
        }

        @Override
        public Date getSignupDate() {
            return null;
        }
    }

    private static class SgaBasicData {
        @JsonProperty(required = true) private String dni;
        @JsonProperty(required = true) private String firstName;
        @JsonProperty(required = true) private String lastName;
    }

    private static class SgaExtendedData {
        @JsonProperty(required = true) private int code;
        @JsonProperty(required = true) private String careerCode;
        @JsonProperty private String plan;
    }
}
