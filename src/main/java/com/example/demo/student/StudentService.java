package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

private final StudentRepository studentRepository;

@Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @ModelAttribute("getStudents")
    public List<Student> getStudents(){
    return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
    Optional<Student> studentOptional =studentRepository.findStudentByEmail(student.getEmail());
if(studentOptional.isPresent()){
    throw new IllegalStateException("email taken");
}
studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
   boolean exist = studentRepository.existsById(studentId);

   if(!exist){
       throw new IllegalStateException("student does not exist");

   }
        studentRepository.deleteById(studentId);
    }

    public void updateStudent(Long studentId, String name, String email) {
   Student student = studentRepository.findById(studentId).orElseThrow(()->new IllegalStateException("Student does not exist"));

   if(name!= null && name.length()>0 && !Objects.equals(student.getName(),name))
       student.setName(name);

        if(email!= null && email.length()>0 && !Objects.equals(student.getEmail(),email)) {
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);

            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");

            }
            student.setEmail(email);
        }

    }



}
