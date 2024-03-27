package tn.esprit.models;

public class User {

    //attribute
    private int id,num_tel;
    private String email,roles,password,nom,prenom;

    private Byte is_verified;

    //constructor

    public User()
    {

    }
    public User(int id, Byte is_verified,int num_tel ,String email, String roles, String password, String nom, String prenom)
    {
        this.id = id;
        this.is_verified = is_verified;
        this.num_tel = num_tel;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(Byte is_verified,int num_tel ,String email, String roles, String password, String nom, String prenom)
    {

        this.is_verified = is_verified;
        this.num_tel = num_tel;
        this.email = email;
        this.roles = roles;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
    }

    //Getter and setters


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Byte getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(Byte is_verified) {
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

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
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
