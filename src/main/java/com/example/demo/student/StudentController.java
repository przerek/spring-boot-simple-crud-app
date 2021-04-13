package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ResponseBody
    @GetMapping("/accountsjson")
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping("/index")
    public String accounts(Model model) {
        List<Student> listStudent = studentService.getStudents();
        model.addAttribute("studentList", listStudent);
        return "index";
    }

    @GetMapping("/dodaj")
    public String form(Model model) {
    model.addAttribute("student", new Student());
    return "formularz";
}
    @PostMapping("/index")
    public String formAction(@ModelAttribute Student student, Model model) {
        studentService.addNewStudent(student);
        List<Student> listStudent = studentService.getStudents();
        model.addAttribute("clientsList", listStudent);
        return "redirect:/index";
    }

    @GetMapping("/delete/{studentId}")
    public String deleteStudent(@PathVariable Long studentId,Model model) {
        studentService.deleteStudent(studentId);
        return "redirect:/index";
    }

    @PutMapping(path = "{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String email) {
        studentService.updateStudent(studentId, name, email);
    }

}