package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.Entreprise;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

@DataJpaTest
public class EmployeRepositoryTest {

    @Autowired
    private EmployeRepository employeRepository;

    @BeforeEach
    @AfterEach
    public void setup(){
        employeRepository.deleteAll();
    }

    @Test
    public void testFindLastMatriculeEmpty(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertNull(lastMatricule);
    }

    @Test
    public void testFindLastMatriculeSingle(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("12345", lastMatricule);
    }

    @Test
    public void testFindLastMatriculeMultiple(){
        //Given
        employeRepository.save(new Employe("Doe", "John", "T12345", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jane", "M40325", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Doe", "Jim", "C06432", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertEquals("40325", lastMatricule);
    }
    @Test
    public void IntegrationAvgPerformanceWhereMatriculeStartsWith() {
        //Given
        employeRepository.save(new Employe("Rizal", "Jose", "C002018", LocalDate.now(), Entreprise.SALAIRE_BASE, 2, 1.0));
        employeRepository.save(new Employe("Yourcenar", "Marguerite", "C002019", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 0.5));
        employeRepository.save(new Employe("De Musset", "Alfred", "C002021", LocalDate.now(), Entreprise.SALAIRE_BASE, 4, 1.0));
        employeRepository.save(new Employe("Couplan", "François", "C002022", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 0.5));
        employeRepository.save(new Employe("Loiseau", "Bernard", "C002023", LocalDate.now(), Entreprise.SALAIRE_BASE, 1, 1.0));
        employeRepository.save(new Employe("Vincent", "Odile", "C002024", LocalDate.now(), Entreprise.SALAIRE_BASE, 3, 1.0));


        //When
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        // performance moyenne attendue = (2+1+4+1+1+3)/6 = 2
        Assertions.assertEquals(2, performanceMoyenne.doubleValue());

    }
}