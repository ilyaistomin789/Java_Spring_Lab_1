package by.belstu.istomin.springbooks.forms;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeForm {
    private String name;
    private String profession;
    private String editField;
}
