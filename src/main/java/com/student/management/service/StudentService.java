package com.student.management.service;


import com.student.management.model.Student;
import com.student.management.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class StudentService {

    @Autowired
    private  final StudentRepository studentRepository;


    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){
        Optional<Student> existing = studentRepository.findByEmail(student.getEmail());
        if(existing.isPresent()){
            throw new RuntimeException("Email id already exists try another one: " + student.getEmail());
        }
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student updatedStudent){
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setCourse(updatedStudent.getCourse());
                    return studentRepository.save(student);
                })
                .orElseThrow(()-> new RuntimeException("Student not found with id" + id));
    }

    public List<Student> getAllStudents(){
        return  studentRepository.findAll();
    }

    public Optional<Student> getStudentById(Long id){
        return  studentRepository.findById(id);
    }

    public void deleteStudent(Long id){
        studentRepository.deleteById(id);
    }

}
