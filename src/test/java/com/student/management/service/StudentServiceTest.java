package com.student.management.service;

import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setEmail("john.doe@example.com");
    }

    @Test
    void createStudent_ShouldReturnStudent_WhenEmailNotDuplicate() {
        // Mock repository to return empty for email check
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.empty());
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student createdStudent = studentService.createStudent(student);

        assertNotNull(createdStudent);
        assertEquals("John Doe", createdStudent.getName());
        assertEquals("john.doe@example.com", createdStudent.getEmail());
    }

    @Test
    void createStudent_ShouldThrowException_WhenEmailDuplicate() {
        // Mock repository to simulate duplicate email
        when(studentRepository.findByEmail(student.getEmail())).thenReturn(Optional.of(student));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.createStudent(student);
        });

        assertEquals("Email id already exists try another one: john.doe@example.com", exception.getMessage());
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent_WhenStudentFound() {
        Student updatedStudent = new Student();
        updatedStudent.setName("Updated Name");
        updatedStudent.setEmail("updated.email@example.com");

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(updatedStudent);

        Student result = studentService.updateStudent(student.getId(), updatedStudent);

        assertNotNull(result);
        assertEquals("Updated Name", result.getName());
        assertEquals("updated.email@example.com", result.getEmail());
    }

    @Test
    void updateStudent_ShouldThrowException_WhenStudentNotFound() {
        Student updatedStudent = new Student();
        updatedStudent.setName("Updated Name");

        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.updateStudent(student.getId(), updatedStudent);
        });

        assertEquals("Student not found with id" + student.getId(), exception.getMessage());
    }

    @Test
    void getAllStudents_ShouldReturnListOfStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(student));

        List<Student> students = studentService.getAllStudents();

        assertNotNull(students);
        assertFalse(students.isEmpty());
        assertEquals(1, students.size());
    }

    @Test
    void getStudentById_ShouldReturnStudent_WhenStudentFound() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        Optional<Student> foundStudent = studentService.getStudentById(student.getId());

        assertTrue(foundStudent.isPresent());
        assertEquals(student.getId(), foundStudent.get().getId());
    }

    @Test
    void getStudentById_ShouldReturnEmpty_WhenStudentNotFound() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());

        Optional<Student> foundStudent = studentService.getStudentById(student.getId());

        assertFalse(foundStudent.isPresent());
    }

    @Test
    void deleteStudent_ShouldDeleteStudent_WhenStudentExists() {
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        studentService.deleteStudent(student.getId());

        // Verify delete method is called
        verify(studentRepository, times(1)).deleteById(student.getId());
    }
//
//    @Test
//    void deleteStudent_ShouldThrowException_WhenStudentNotFound() {
//        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());
//
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            studentService.deleteStudent(student.getId());
//        });
//
//        assertEquals("Student not found with id" + student.getId(), exception.getMessage());
//    }
}
