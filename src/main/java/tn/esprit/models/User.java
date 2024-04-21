package tn.esprit.models;

public class User {

    //attribute
    private int id,num_tel;
    private String[] roles;
    private String email,password,nom,prenom;

    private int is_verified;

    //constructor

    public User()
    {

    }
    public User(String email, String[] roles, String password, int is_verified, String nom, String prenom,int num_tel)
    {

        this.email = email;
        this.roles = roles;
        this.password = password;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
    }

    public User(int id, String email, String[] roles, String password, int is_verified, String nom, String prenom,int num_tel)
    {
        this.id = id;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.is_verified = is_verified;
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;




    }

    //Getter and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(int is_verified) {
        this.is_verified = is_verified;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    //Display


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", is_verified=" + is_verified +
                ", num_tel=" + num_tel +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", password='" + password + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }
}
