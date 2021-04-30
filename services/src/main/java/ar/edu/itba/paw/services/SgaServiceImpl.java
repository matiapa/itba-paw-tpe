package ar.edu.itba.paw.services;

import java.io.IOException;
import java.net.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.itba.paw.models.Career;
import ar.edu.itba.paw.models.User;

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
    public User fetchFromEmail(String email) {
        try {
            SgaBasicData basicData = mapper.readValue(new URL(EMAIL_ENDPOINT + email), SgaBasicData.class);
            SgaExtendedData extendedData = mapper.readValue(new URL(DNI_ENDPOINT + basicData.dni), SgaExtendedData.class);
            Career career = careerService.findByCode(extendedData.careerCode).get();
            return new User(extendedData.code, basicData.firstName, basicData.lastName, email, null, career.getCode());
        } catch (IOException e) {
            e.printStackTrace();
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
