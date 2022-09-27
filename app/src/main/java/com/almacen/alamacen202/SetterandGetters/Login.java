package com.almacen.alamacen202.SetterandGetters;

public class Login {
    private String users;
    private String name;
    private String lastname;
    private String type;
    private String branch;
    private String codeBranch;
    private String email;
    private String code;

    public Login() {
    }

    public Login(String users, String name, String lastname, String type, String branch, String codeBranch, String email, String code) {
        this.users = users;
        this.name = name;
        this.lastname = lastname;
        this.type = type;
        this.branch = branch;
        this.codeBranch = codeBranch;
        this.email = email;
        this.code = code;
    }


    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getCodeBranch() {
        return codeBranch;
    }

    public void setCodeBranch(String codeBranch) {
        this.codeBranch = codeBranch;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}