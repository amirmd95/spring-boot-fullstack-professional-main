package com.example.demo.student;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
     private  StudentRepository studentRepository;



    @Test
    void itShouldSelectExistsByEmail() {
        String email="amir.1997.ansari@gmail.com";

        //given
       Student studentAmir= new Student("amir",email,Gender.MALE);
       studentRepository.save(studentAmir);
        //when
        boolean existsEmail=studentRepository.selectExistsEmail(email);

        //then
        assertThat(existsEmail).isTrue();

    }
}