/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Renan
 */
public class Member implements Serializable{
    
    private static int generatedId = 1;
    
    private int id;
    private String name;
    private String email;
    private String localization;
    private List<Skills> skills;
    private List<Language> languages;
    
    public Member(String name, String email, String localization, List<Skills> skills, List<Language> languages){
        this.id = generatedId;
        this.name = name;
        this.email = email;
        this.localization = localization;
        this.skills = skills;
        this.languages = languages;
        
        generatedId++;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the localization
     */
    public String getLocalization() {
        return localization;
    }

    /**
     * @param localization the localization to set
     */
    public void setLocalization(String localization) {
        this.localization = localization;
    }

    /**
     * @return the skills
     */
    public List<Skills> getSkills() {
        return skills;
    }

    /**
     * @param skills the skills to set
     */
    public void setSkills(List<Skills> skills) {
        this.skills = skills;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        } else {
            if(getClass() != obj.getClass()){
                return false;
            } else {
                Member other = (Member) obj;
                if(this.name.equals(other.name) && this.localization.equals(other.localization) && this.email.equals(other.email)){
                    return true;
                } else {
                    return false;
                }
            }
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + this.getId();
        hash = 59 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 59 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 59 * hash + (this.localization != null ? this.localization.hashCode() : 0);
        return hash;
    }

    /**
     * @return the languages
     */
    public List<Language> getLanguages() {
        return languages;
    }

    /**
     * @param languages the languages to set
     */
    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    
    
    
}
