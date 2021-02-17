package kz.edu.controller;

import kz.edu.dao.BookDAO;
import kz.edu.dao.StudentDAO;
import kz.edu.dao.UserDAO;
import kz.edu.model.Book;
import kz.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class Controller3 {
    private final StudentDAO studentDAO;
    PasswordEncoder passwordEncoder;
    @Autowired
    public Controller3(StudentDAO studentDAO)
    {
        this.studentDAO = studentDAO;
    }

    @Autowired
    public void PasswordEncoder(PasswordEncoder passwordEncoder)
    { this.passwordEncoder = passwordEncoder;}

    @GetMapping()
    public String helloPage(Model model)
    {
        model.addAttribute("studentsList", studentDAO.getStudentList());
        return "page-11";
    }
    @GetMapping("/{id}")
    public String student(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("student", studentDAO.getStudent(id));
        return "page-12";
    }
    @GetMapping("/page-13")
    public String addStudentGet(Model model)
    {
        model.addAttribute("student", new User());
        return "page-13";
    }
    @PostMapping()
    public String addStudentPost(@ModelAttribute("student") User student)
    {
        if (studentDAO.getStudent(student.getId()) != null)
        {
            return "page-13";
        }
        else
        {
            student.setPassword(passwordEncoder.encode(student.getPassword()));
            studentDAO.addStudent(student);
            return "redirect:/students/"+student.getId();
        }
    }
    @GetMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("student", studentDAO.getStudent(id));
        return "page-14";
    }
    @PatchMapping("/{id}")
    public String updateStudentPatch(@ModelAttribute("student") User student, @PathVariable("id") int id)
    {
        if (student.getPassword().length() <= 15)
            student.setPassword(passwordEncoder.encode(student.getPassword()));
        studentDAO.updateStudent(student);
        return "redirect:/students/"+student.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("student", studentDAO.getStudent(id));
        return "page-15";
    }
    @DeleteMapping("/{id}")
    public String deleteStudentPatch(@PathVariable("id") int id)
    {
        studentDAO.deleteStudent(id);
        return "redirect:/students";
    }
}
