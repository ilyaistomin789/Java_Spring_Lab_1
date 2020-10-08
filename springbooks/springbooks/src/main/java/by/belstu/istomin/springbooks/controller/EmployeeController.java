package by.belstu.istomin.springbooks.controller;
import by.belstu.istomin.springbooks.forms.EmployeeForm;
import by.belstu.istomin.springbooks.model.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Controller
public class EmployeeController {
    private static List<Employee> employees = new ArrayList<Employee>();
    static {
        employees.add(new Employee("Full Stack Development with JHipster", "Deepu K Sasidharan, Sendil Kumar N"));
        employees.add(new Employee("Pro Spring Security", "Carlo Scarioni, Massimo Nardone"));
    }
    //
    // Вводится (inject) из application.properties.
    @Value("${welcome.message}")
    private String message;
    @Value("${error.message}")
    private String errorMessage;
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView index(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        model.addAttribute("message", message);
        log.info("/index was called");
        return modelAndView;
    }
    @RequestMapping(value = {"/allEmployees"}, method = RequestMethod.GET)
    public ModelAndView personList(Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employeeList");
        model.addAttribute("employeesL", employees);
        return modelAndView;
    }

    @RequestMapping(value = {"/addEmployee"}, method = RequestMethod.GET)
    public ModelAndView showAddPersonPage(Model model) {
        ModelAndView modelAndView = new ModelAndView("addEmployee");
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);
        return modelAndView;
    }
    // @PostMapping("/addbook")
//GetMapping("/")
    @RequestMapping(value = {"/addEmployee"}, method = RequestMethod.POST)
    public ModelAndView savePerson(Model model, @ModelAttribute("employeeForm") EmployeeForm employeeForm) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("employeeList");
        String title = employeeForm.getName();
        String author = employeeForm.getProfession();
        if (title != null && title.length() > 0 //
                && author != null && author.length() > 0) {
            Employee newEmployee = new Employee(title, author);
            employees.add(newEmployee);
            model.addAttribute("employeesL", employees);
            return modelAndView;
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("addEmployee");
        return modelAndView;
    }
    @RequestMapping(value = {"/removeEmployee"}, method = RequestMethod.GET)
    public ModelAndView showRemovePersonPage(Model model){
        ModelAndView modelAndView = new ModelAndView("removeEmployee");
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);
        return modelAndView;
    }
    @RequestMapping(value = {"/removeEmployee"}, method = RequestMethod.POST)
    public ModelAndView removePerson(Model model, @ModelAttribute("employeeForm") EmployeeForm employeeForm){
        ModelAndView modelAndView = new ModelAndView("employeeList");
        String title = employeeForm.getName();
        if (title != null && title.length() > 0){
            for (Employee bf : employees){
                if (bf.getName().equals(title)){
                    employees.remove(bf);
                    model.addAttribute("employeesL", employees);
                    return modelAndView;
                }
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        modelAndView.setViewName("index");
        return modelAndView;
    }
    @RequestMapping(value = {"/editEmployee"}, method = RequestMethod.GET)
    public ModelAndView showEditPersonPage(Model model){
        ModelAndView modelAndView = new ModelAndView("editEmployee");
        Employee oldEmployee = new Employee();
        EmployeeForm employeeForm = new EmployeeForm();
        model.addAttribute("employeeForm", employeeForm);
        model.addAttribute("oldEmployee", oldEmployee);
        return modelAndView;
    }
    @RequestMapping(value = {"/editEmployee"}, method = RequestMethod.POST)
    public ModelAndView editPerson(Model model, @ModelAttribute("employeeForm") EmployeeForm employeeForm){
        ModelAndView modelAndView = new ModelAndView("employeeList");
        String oldname = employeeForm.getEditField();
        String newName = employeeForm.getName();
        String newProfession = employeeForm.getProfession();
        if (oldname != null && oldname.length() > 0 && newName != null && newName.length() > 0 && newProfession != null && newProfession.length() > 0){
            for (Employee b : employees){
                if (b.getName().equals(oldname)){
                    b.setName(newName);
                    b.setProfession(newProfession);
                    model.addAttribute("employeesL", employees);
                    return modelAndView;
                }
            }
        }
        model.addAttribute("errorMessage", errorMessage);
        return modelAndView;
    }

}
