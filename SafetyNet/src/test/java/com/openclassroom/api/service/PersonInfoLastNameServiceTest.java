package com.openclassroom.api.service;

import com.openclassroom.api.model.MedicalRecord;
import com.openclassroom.api.model.Person;
import com.openclassroom.api.repository.MyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonInfoLastNameServiceTest {

    @Autowired
    private MyRepository myRepository;

    @Autowired
    private PersonInfoLastNameService personInfoLastNameService;

    @BeforeEach
    void setUp() {

        myRepository.Init();
    }

    @Test
    void testGetPersonInfoByLastName_Found() {
        List<Map<String, Object>> result = personInfoLastNameService.getPersonInfoByLastName("Boyd");

        assertEquals(6, result.size());
        assertEquals("John", result.get(0).get("firstName"));
        assertEquals("Jacob", result.get(1).get("firstName"));
    }

    @Test
    void testGetPersonInfoByLastName_NotFound() {
        List<Map<String, Object>> result = personInfoLastNameService.getPersonInfoByLastName("NonExistent");
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetPersonInfoByLastName_NoMedicalRecord() {
        myRepository.getMedicalRecords().clear();

        List<Map<String, Object>> result = personInfoLastNameService.getPersonInfoByLastName("Boyd");
        assertTrue(result.isEmpty());
    }
}
