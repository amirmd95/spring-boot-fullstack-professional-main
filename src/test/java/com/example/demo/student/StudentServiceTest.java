package com.example.demo.student;

import com.example.demo.student.exception.BadRequestException;
import com.example.demo.student.exception.StudentNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock private StudentRepository studentRepository;
    private StudentService studentService;

    @BeforeEach
    void setUp() {

        studentService=new StudentService(studentRepository);
    }



    @Test
    void canGetAllStudents() {
        //Given
        studentService.getAllStudents();

        //then
        verify(studentRepository).findAll();




    }

    @Test
    void canAddStudent() {
        //Given
        Student amir =
                new Student
                        ("amir",
                        "amir.1997.ansari@gmail.com",
                        Gender.MALE
                         );

        //
        studentService.addStudent(amir);
        ArgumentCaptor<Student> argumentCaptor=ArgumentCaptor.forClass(Student.class);

        verify(studentRepository).save(argumentCaptor.capture());
        Student value = argumentCaptor.getValue();
        assertThat(value).isEqualTo(amir);

    }
    @Test
    void willThrowExceptionWhenEmailAlreadyExists() {
        //Given
        Student amir =
                new Student
                        ("amir",
                                "amir.1997.ansari@gmail.com",
                                Gender.MALE
                        );

        given(studentRepository.selectExistsEmail(anyString()))
                .willReturn(true);

        //
        assertThatThrownBy(()->studentService.addStudent(amir))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Email " + amir.getEmail() + " taken");

        verify(studentRepository,never()).save(amir);


    }

    @Test
    void deleteStudent() {
        long id=1L;
        when(studentRepository.existsById(id)).thenReturn(true);
        studentService.deleteStudent(id);
        verify(studentRepository).deleteById(id);

    }

    @Test
    void ThrowExceptionWhenStudentIdDoesNotExists() {
        long id=1L;
        when(studentRepository.existsById(id)).thenReturn(false);
        assertThatThrownBy(()->studentService.deleteStudent(id))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + id + " does not exists");
        verify(studentRepository,never()).deleteById(id);

    }
}