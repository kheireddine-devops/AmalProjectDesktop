package com.amal.amalproject.models;

import com.amal.amalproject.entities.*;
import com.amal.amalproject.utils.enums.AccountStatus;

import java.util.List;
import java.util.Map;

public interface IUserModel {
    Compte login(String username, String password);
    Compte addCompte(Compte compte);
    Compte getCompteByLogin(String login);
    User addUser(User user);
    User editUser(User userUpdated,int userId);
    List<User> getAllUsers();
    List<Compte> getAllComptes();
    List<Organization> getAllOrganizations();
    List<Medecin> getAllMedecins();
    List<Beneficier> getAllBeneficiers();
    List<Benevole> getAllBenevoles();
    User getUserById(int userId);
    Medecin addMedecin(Medecin medecin);
    Organization addOrganization(Organization organization);
    Beneficier addBeneficier(Beneficier beneficier);
    Benevole addBenevole(Benevole benevole);
    boolean existsCompteByLogin(String login);
    boolean existsUserByEmail(String email);
    boolean existsUserByTelephone(String telephone);
    boolean existsMedecinByCIN(String cin);
    boolean existsMedecinByMatricule(String matricule);
    Organization getOrganizationById(int organizationId);
    Medecin getMedecinById(int medecinId);
    Beneficier getBeneficierById(int beneficierId);
    Benevole getBenevoleById(int benevoleId);
    Compte editCompte(int compteId,Compte updatedCompte);
    Organization editOrganization(int organizationId,Organization updatedOrganization);
    Map<String,Integer> getUserNumbersByRole();
    boolean changePassword(int compteId,String newPassword);
    String editUserProfilePhoto(int userId,String photo);
    String editOrganizationProfilePhoto(int organizationId,String photo);

    int getUserIdByEmail(String email);

    boolean forgotPassword(int compteId, String passwordGenerated);

    boolean changeAccountStatus(int compteId,AccountStatus status);
}
