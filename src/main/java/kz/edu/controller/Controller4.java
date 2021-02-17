package kz.edu.controller;

import kz.edu.dao.BookDAO;
import kz.edu.dao.BorrowDAO;
import kz.edu.dao.StudentDAO;
import kz.edu.dao.UserDAO;
import kz.edu.model.Borrow;
import kz.edu.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/borrows")
public class Controller4 {
    private final BorrowDAO borrowDAO;
    private final UserDAO userDAO;
    private final BookDAO bookDAO;

    @Autowired
    public Controller4(BorrowDAO borrowDAO, UserDAO userDAO, BookDAO bookDAO) {
        this.borrowDAO = borrowDAO;
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String helloPage(Model model)
    {
        model.addAttribute("borrowsList", borrowDAO.getBorrowList());
        return "page-6";
    }
    @GetMapping("/{id}")
    public String borrow(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("borrow", borrowDAO.getBorrow(id));
        return "page-7";
    }
    @GetMapping("/page-8")
    public String addBorrowGet(Model model)
    {
        model.addAttribute("borrow", new Borrow());
        return "page-8";
    }
    @PostMapping()
    public String addBorrowPost(@ModelAttribute("borrow") Borrow borrow)
    {
        borrowDAO.addBorrow(borrow);
        return "redirect:/borrows/"+borrow.getId();
    }
    @GetMapping("/edit/{id}")
    public String updateBorrow(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("borrow", borrowDAO.getBorrow(id));
        return "page-9";
    }
    @PatchMapping("/{id}")
    public String updateBorrowPatch(@ModelAttribute("borrow") Borrow borrow, @PathVariable("id") int id)
    {
        borrowDAO.updateBorrow(borrow);
        return "redirect:/borrows/"+borrow.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteBorrow(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("borrow", borrowDAO.getBorrow(id));
        return "page-10";
    }
    @DeleteMapping("/{id}")
    public String deleteBorrowPatch(@PathVariable("id") int id)
    {
        borrowDAO.deleteBorrow(id);
        return "redirect:/borrows";
    }
}
