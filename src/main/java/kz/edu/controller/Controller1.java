package kz.edu.controller;

import kz.edu.dao.BookDAO;
import kz.edu.model.Book;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class Controller1
{
    private final BookDAO bookDAO;
    @Autowired
    public Controller1(BookDAO bookDAO)
    {
        this.bookDAO = bookDAO;
    }

    @GetMapping()
    public String helloPage(Model model)
    {
        model.addAttribute("booksList", bookDAO.getBookList());
        return "page-1";
    }
    @GetMapping("/{id}")
    public String book(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("book", bookDAO.getBook(id));
        return "page-2";
    }
    @GetMapping("/page-3")
    public String addBookGet(Model model)
    {
        model.addAttribute("book", new Book());
        return "page-3";
    }
    @PostMapping()
    public String addBookPost(@ModelAttribute("book") Book book)
    {
        bookDAO.addBook(book);
        return "redirect:/books/"+book.getId();
    }
    @GetMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") int id, Model model)
    {
        model.addAttribute("book", bookDAO.getBook(id));
        return "page-4";
    }
    @PatchMapping("/{id}")
    public String updateBookPatch(@ModelAttribute("book") Book book, @PathVariable("id") int id)
    {
        bookDAO.updateBook(book);
        return "redirect:/books/"+book.getId();
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("book", bookDAO.getBook(id));
        return "page-5";
    }
    @DeleteMapping("/{id}")
    public String deleteBookPatch(@PathVariable("id") int id)
    {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}