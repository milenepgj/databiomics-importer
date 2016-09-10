package app.dto;

/**
 * Created by milene.guimaraes on 29/08/16.
 */
import java.sql.Date;

public class Employee {

    private Long id;

    private String firstname;

    private String lastname;

    private Date birthDate;

    private String cellphone;

    public Employee() {

    }

    public Employee(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.cellphone = phone;

    }

    // Getter and Setter methods


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }
}