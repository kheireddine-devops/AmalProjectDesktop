package com.amal.amalproject.models;

import com.amal.amalproject.entities.*;
import com.amal.amalproject.utils.DBConnection;
import com.amal.amalproject.utils.enums.AccountStatus;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserModel implements IUserModel {
	Connection connection = DBConnection.getConnection();

	@Override
	public Compte login(String username, String password) {
		Compte compte = null;
		try {
			System.out.println("SELECT * FROM `compte` WHERE `login` = ? AND `password` = ?;");
			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte,C.username,C.password,C.role,C.status,C.phone,C.email,C.photo FROM `compte` C WHERE C.username = ? AND ((C.password = ?) OR (C.temp_reset_password = ?)) AND C.status <> 'STATUS_BANNED' AND C.status <> 'STATUS_DEACTIVATE';");
			ps.setString(1,username);
			ps.setString(2,DigestUtils.sha256Hex(password));
			ps.setString(3,DigestUtils.sha256Hex(password));

			System.out.println(DigestUtils.sha256Hex("ShTfdNXxAi"));

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				compte = new Compte();

				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setPassword(resultSet.getString("password"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhone(resultSet.getString("phone"));
				compte.setPhoto(resultSet.getString("photo"));

				System.out.println("SUCCESS-GET-COMPTE-BY-LOGIN");

			} else {
				System.out.println("ERROR-GET-COMPTE-BY-LOGIN");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return compte;
	}

	@Override
	public Compte addCompte(Compte compte) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `compte` (`id_compte`, `username`, `password`, `role`, `status`,`temp_validate_mail`,`temp_validate_phone`,`email`,`phone`,`photo`) VALUES (NULL, ?, ?, ?, ?,?,?,?,?,?);");
			ps.setString(1,compte.getUsername());
			ps.setString(2, DigestUtils.sha256Hex(compte.getPassword()));
			ps.setString(3,compte.getRole());
			ps.setString(4,compte.getStatus());
			ps.setString(5,compte.getTempValidateMail());
			ps.setString(6,compte.getTempValidateMail());
			ps.setString(7,compte.getEmail());
			ps.setString(8,compte.getPhone());
			ps.setString(9,compte.getPhoto());


			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-ADD-COMPTE");
			} else {
				System.out.println("ERROR-ADD-COMPTE");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return compte;
	}

	@Override
	public Compte getCompteByLogin(String login) {
		Compte compte = null;
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `compte` WHERE `username` = ?");
			ps.setString(1,login);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				compte = new Compte();

				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setPassword(resultSet.getString("password"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setPhone(resultSet.getString("phone"));
				compte.setEmail(resultSet.getString("email"));

				System.out.println("SUCCESS-GET-COMPTE-BY-LOGIN");

			} else {
				System.out.println("ERROR-GET-COMPTE-BY-LOGIN");
				System.out.println(resultSet.next());
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return compte;
	}

	@Override
	public User addUser(User user) {

		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO `user` (`id_user`, `nom`, `prenom`, `date_naissance`, `sexe`,`adresse`) " +
					"VALUES (?, ?, ?, ?, ?, ?);");

			ps.setInt(1,user.getUserId());
			ps.setString(2,user.getNom());
			ps.setString(3,user.getPrenom());
			ps.setDate(4,user.getDateNaissance() != null ? Date.valueOf(user.getDateNaissance()) : null);
			ps.setString(5,user.getSexe());
			ps.setString(6,"{\"street\":\""+user.getAdresse()+"\",\"city\":\"\",\"zipcode\":\"\"}");


			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-ADD-USER");
			} else {
				System.out.println("ERROR-ADD-USER");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return user;
	}

	@Override
	public User editUser(User userUpdated, int userId) {
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM user;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				User user = new User();
				user.setUserId(resultSet.getInt("id_user"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setSexe(resultSet.getString("sexe"));
				users.add(user);
			}

			ps.close();
		}
		catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return users;
	}

	@Override
	public List<Compte> getAllComptes() {
		List<Compte> comptes = new ArrayList<>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT * FROM `compte`");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setPhone(resultSet.getString("phone"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhoto(resultSet.getString("photo"));

				comptes.add(compte);
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return comptes;
	}

	@Override
	public List<Organization> getAllOrganizations() {
		List<Organization> organizations = new ArrayList<>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte,C.username,C.role,C.status,C.email,C.phone,C.email,O.matricule_fiscale,O.nom,O.forme_juridique,O.adresse FROM compte C INNER JOIN organisation O ON C.id_compte = O.id_compte;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhone(resultSet.getString("phone"));
				compte.setPhoto(resultSet.getString("photo"));

				Organization organization = new Organization();
				organization.setUserId(resultSet.getInt("id_compte"));
				organization.setNom(resultSet.getString("nom"));
				organization.setFormJuridique(resultSet.getString("forme_juridique"));
				organization.setAdresse(resultSet.getString("adresse"));
				organization.setMatriculeFiscale(resultSet.getString("matricule_fiscale"));
				organization.setCompte(compte);

				organizations.add(organization);
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return organizations;
	}

	@Override
	public List<Medecin> getAllMedecins() {
		List<Medecin> medecins = new ArrayList<>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte, C.username, C.role, C.status, C.email,C.phone,C.photo" +
					"U.nom, U.prenom, U.sexe_user, U.adresse, U.date_naissance" +
					" M.cin, M.specialite, M.matricule, M.Assurance FROM compte C INNER JOIN USER U ON C.id_compte = U.id_user INNER JOIN medecin M ON C.id_compte = M.id_user;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setPhone(resultSet.getString("phone"));

				Medecin medecin = new Medecin();
				medecin.setUserId(resultSet.getInt("id_compte"));
				medecin.setNom(resultSet.getString("nom"));
				medecin.setPrenom(resultSet.getString("prenom"));
				medecin.setAdresse(resultSet.getString("adresse"));
				medecin.setSexe(resultSet.getString("sexe"));
				medecin.setDateNaissance(resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance_user").toLocalDate() : null);


				medecin.setCin(resultSet.getString("cin"));
				medecin.setSpecialite(resultSet.getString("specialite"));
				medecin.setMatricule(resultSet.getString("matricule"));

				medecin.setCompte(compte);

				medecins.add(medecin);
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return medecins;
	}

	@Override
	public List<Beneficier> getAllBeneficiers() {
		List<Beneficier> beneficiers = new ArrayList<>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte, C.login, C.role, C.status,C.phone,C.email,C.photo, U.nom, U.prenom, U.sexe, U.adresse, U.date_naissance, B.carte_handicap, B.date_expiration FROM compte C INNER JOIN USER U ON C.id_compte = U.id_user INNER JOIN beneficier B ON C.id_compte = B.id_user;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhone(resultSet.getString("phonr"));

				Beneficier beneficier = new Beneficier();
				beneficier.setUserId(resultSet.getInt("id_compte"));
				beneficier.setNom(resultSet.getString("nom"));
				beneficier.setPrenom(resultSet.getString("prenom"));
				beneficier.setAdresse(resultSet.getString("adresse"));

				beneficier.setSexe(resultSet.getString("sexe"));
				beneficier.setDateNaissance(resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance").toLocalDate() : null);


				beneficier.setCarteHandicapNumber(resultSet.getString("carte_handicap"));
				beneficier.setDateExpiration(resultSet.getDate("date_expiration") != null ? resultSet.getDate("date_expiration").toLocalDate() : null);

				beneficier.setCompte(compte);

				beneficiers.add(beneficier);
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return beneficiers;
	}

	@Override
	public List<Benevole> getAllBenevoles() {
		List<Benevole> benevoles = new ArrayList<>();
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte, C.login, C.role, C.status, U.nom_user, U.prenom_user, U.email_user, U.telephone_user, U.sexe_user, U.adresse_user, U.date_naissance_user, U.photo_user, B.profession FROM compte C INNER JOIN USER U ON C.id_compte = U.id_user INNER JOIN benevole B ON C.id_compte = B.id_user;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setPhone(resultSet.getString("phone"));

				Benevole benevole = new Benevole();
				benevole.setUserId(resultSet.getInt("id_compte"));
				benevole.setNom(resultSet.getString("nom"));
				benevole.setPrenom(resultSet.getString("prenom"));
				benevole.setAdresse(resultSet.getString("adresse"));
				benevole.setSexe(resultSet.getString("sexe"));
				benevole.setDateNaissance(resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance").toLocalDate() : null);


				benevole.setProfession(resultSet.getString("profession"));

				benevole.setCompte(compte);

				benevoles.add(benevole);
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return benevoles;
	}

	@Override
	public User getUserById(int userId) {
		User user = null;
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT id_user,nom,prenom,date_naissance,sexe,adresse FROM `user` WHERE `id_user` = ?");
			ps.setInt(1,userId);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				user = new User();

				user.setUserId(resultSet.getInt("id_user"));
				user.setNom(resultSet.getString("nom"));
				user.setPrenom(resultSet.getString("prenom"));
				user.setDateNaissance(resultSet.getDate("date_naissance").toLocalDate());
				user.setSexe(resultSet.getString("sexe"));
				user.setAdresse(resultSet.getString("adresse")); //TODO ADDRESS

				System.out.println("SUCCESS-GET-USER-BY-ID");

			} else {
				System.out.println("ERROR-GET-USER-BY-ID");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return user;
	}

	@Override
	public Medecin addMedecin(Medecin medecin) {
		try {

			Compte compte = this.addCompte(medecin.getCompte());
			Compte savedCompte = this.getCompteByLogin(compte.getUsername());
			System.out.println(savedCompte);
			medecin.setUserId(savedCompte.getCompteId());
			User user = this.addUser(medecin);
			User savedUser = this.getUserById(user.getUserId());
			System.out.println(savedUser);
			medecin.setUserId(savedUser.getUserId());

			PreparedStatement ps = connection.prepareStatement("INSERT INTO `medecin` (`id_user`,`matricule`, `specialite`, `cin`, `Assurance`) VALUES (?, ?, ?, ?, ?);");

			ps.setInt(1,medecin.getUserId());
			ps.setString(2,medecin.getMatricule());
			ps.setString(3,medecin.getSpecialite());
			ps.setString(4,medecin.getCin());
			ps.setString(5,medecin.getAssurance());

			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-ADD-DOCTOR");
			} else {
				System.out.println("ERROR-ADD-DOCTOR");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return medecin;
	}

	@Override
	public Organization addOrganization(Organization organization) {
		try {

			Compte compte = this.addCompte(organization.getCompte());
			Compte savedCompte = this.getCompteByLogin(compte.getUsername());
			System.out.println(savedCompte);

			organization.setUserId(savedCompte.getCompteId());

			PreparedStatement ps = connection.prepareStatement("INSERT INTO `organisation` (`matricule_fiscale`, `nom`, `forme_juridique`, `adresse`, `id_compte`) VALUES (?, ?, ?, ?, ?);");

			ps.setString(1,organization.getMatriculeFiscale());
			ps.setString(2,organization.getNom());
			ps.setString(3,organization.getFormJuridique());
			ps.setString(4,"{\"street\":\""+organization.getAdresse()+"\",\"city\":\"\",\"zipcode\":\"\"}");
			ps.setInt(5,organization.getUserId());

			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-ADD-ORGANIZATION");
			} else {
				System.out.println("ERROR-ADD-ORGANIZATION");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return organization;
	}

	@Override
	public Beneficier addBeneficier(Beneficier beneficier) {
		try {

			Compte compte = this.addCompte(beneficier.getCompte());
			Compte savedCompte = this.getCompteByLogin(compte.getUsername());
			System.out.println(savedCompte);
			System.out.println("*".repeat(50));
			beneficier.setUserId(savedCompte.getCompteId());
			System.out.println("*".repeat(50));
			User user = this.addUser(beneficier);
			System.out.println("*".repeat(50));
			User savedUser = this.getUserById(user.getUserId());
			System.out.println(savedUser);
			beneficier.setUserId(savedUser.getUserId());

			PreparedStatement ps = connection.prepareStatement("INSERT INTO `beneficier` (`carte_handicap`, `date_expiration`, `id_user`) VALUES (?, ?, ?);");

			ps.setString(1,beneficier.getCarteHandicapNumber());
			ps.setDate(2,beneficier.getDateExpiration() != null ? Date.valueOf(beneficier.getDateExpiration()) : null);
			ps.setInt(3,beneficier.getUserId());

			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-ADD-BENEFICIER");
			} else {
				System.out.println("ERROR-ADD-BENEFICIER");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return beneficier;
	}

	@Override
	public Benevole addBenevole(Benevole benevole) {
		try {

			Compte compte = this.addCompte(benevole.getCompte());
			Compte savedCompte = this.getCompteByLogin(compte.getUsername());
			System.out.println(savedCompte);
			benevole.setUserId(savedCompte.getCompteId());
			User user = this.addUser(benevole);
			User savedUser = this.getUserById(user.getUserId());
			System.out.println(savedUser);
			benevole.setUserId(savedUser.getUserId());

			PreparedStatement ps = connection.prepareStatement("INSERT INTO `benevole` (`profession`, `id_user`) VALUES (?, ?);");

			ps.setString(1,benevole.getProfession());
			ps.setInt(2,benevole.getUserId());

			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-ADD-BENEVOLE");
			} else {
				System.out.println("ERROR-ADD-BENEVOLE");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return benevole;
	}

	@Override
	public boolean existsCompteByLogin(String login) {
		int nb = 0;

		try {

			PreparedStatement ps = connection.prepareStatement("SELECT count(*) AS NB_COMPTES FROM `compte` WHERE `username`=?;");
			ps.setString(1,login);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_COMPTES");
				System.out.println("NB_COMPTES : "+ nb);

				System.out.println("SUCCESS-GET-COMPTE-BY-LOGIN");

			} else {
				System.out.println("ERROR-GET-COMPTE-BY-LOGIN");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0 ? true : false;
	}

	@Override
	public boolean existsUserByEmail(String email) {
		int nb = 0;

		try {
			// "SELECT SUM(RESULAT.NB_USERS) AS NB_USERS FROM (SELECT count(*) AS NB_USERS FROM `organisation` WHERE `email`=? UNION ALL SELECT count(*) AS NB_USERS FROM `user` WHERE `email_user`=?) AS RESULAT;
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(C.id_compte) AS NB_USERS FROM `compte` AS C WHERE `email`=?");
			ps.setString(1,email);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_USERS");
				System.out.println("NB_USERS : "+ nb);

				System.out.println("SUCCESS-GET-USER-BY-EMAIL");

			} else {
				System.out.println("ERROR-GET-USER-BY-EMAIL");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0 ? true : false;
	}

	@Override
	public boolean existsUserByTelephone(String telephone) {
		int nb = 0;

		try {
			// "SELECT SUM(RESULAT.NB_USERS) AS NB_USERS FROM (SELECT count(*) AS NB_USERS FROM `organisation` WHERE `num_tel`=? UNION ALL SELECT count(*) AS NB_USERS FROM `user` WHERE `telephone_user`=?) AS RESULAT;"
			PreparedStatement ps = connection.prepareStatement("SELECT COUNT(C.id_compte) AS NB_USERS FROM `compte` AS C WHERE `phone`=?");
			ps.setString(1,telephone);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_USERS");
				System.out.println("NB_USERS : "+ nb);

				System.out.println("SUCCESS-GET-USER-BY-TELEPHONE");

			} else {
				System.out.println("ERROR-GET-USER-BY-TELEPHONEL");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0 ? true : false;
	}

	@Override
	public boolean existsMedecinByCIN(String cin) {
		int nb = 0;

		try {

			PreparedStatement ps = connection.prepareStatement("SELECT count(*) AS NB_DOCTORS FROM `medecin` WHERE `cin`=?;");
			ps.setString(1,cin);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_DOCTORS");
				System.out.println("NB_DOCTORS : "+ nb);

				System.out.println("SUCCESS-GET-MEDECIN-BY-CIN");

			} else {
				System.out.println("ERROR-GET-MEDECIN-BY-CIN");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0 ? true : false;
	}

	@Override
	public boolean existsMedecinByMatricule(String matricule) {
		int nb = 0;

		try {

			PreparedStatement ps = connection.prepareStatement("SELECT count(*) AS NB_DOCTORS FROM `medecin` WHERE `matricule`=?;");
			ps.setString(1,matricule);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_DOCTORS");
				System.out.println("NB_DOCTORS : "+ nb);

				System.out.println("SUCCESS-GET-MEDECIN-BY-MATRICULE");

			} else {
				System.out.println("ERROR-GET-MEDECIN-BY-MATRICULE");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0 ? true : false;
	}

	@Override
	public Organization getOrganizationById(int organizationId) {
		Organization organization = null;
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte,C.username,C.role,C.status,C.photo,C.phone,C.email,O.matricule_fiscale,O.nom,O.forme_juridique,O.adresse FROM compte C INNER JOIN organisation O ON C.id_compte = O.id_compte WHERE O.id_compte = ?;");
			ps.setInt(1,organizationId);
			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhone(resultSet.getString("phone"));
				compte.setPhoto(resultSet.getString("photo"));

				organization = new Organization();
				organization.setUserId(resultSet.getInt("id_compte"));
				organization.setNom(resultSet.getString("nom_organisation"));
				organization.setFormJuridique(resultSet.getString("forme_juridique"));
				organization.setAdresse(resultSet.getString("adresse"));
				organization.setMatriculeFiscale(resultSet.getString("matricule_fiscale"));
				organization.setCompte(compte);

				System.out.println("SUCCESS-GET-ORGANIZATION-BY-ID");

			} else {
				System.out.println("ERROR-GET-ORGANIZATION-BY-ID");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return organization;
	}

	@Override
	public Medecin getMedecinById(int medecinId) {
		Medecin medecin = null;
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte, C.login, C.role, C.status,U.nom_user, U.prenom_user, U.email_user, U.telephone_user, U.sexe_user, U.adresse_user, U.date_naissance_user, U.photo_user,M.cin, M.specialite, M.matricule, M.Assurance, M.id_ordre FROM compte C INNER JOIN USER U ON C.id_compte = U.id_user INNER JOIN medecin M ON C.id_compte = M.id_user WHERE M.id_user = ?;");
			ps.setInt(1, medecinId);
			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhone(resultSet.getString("phone"));

				medecin = new Medecin();
				medecin.setUserId(resultSet.getInt("id_compte"));
				medecin.setNom(resultSet.getString("nom"));
				medecin.setPrenom(resultSet.getString("prenom"));
				medecin.setAdresse(resultSet.getString("adresse"));
				medecin.setSexe(resultSet.getString("sexe"));
				medecin.setDateNaissance(resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance").toLocalDate() : null);


				medecin.setCin(resultSet.getString("cin"));
				medecin.setSpecialite(resultSet.getString("specialite"));
				medecin.setMatricule(resultSet.getString("matricule"));

				medecin.setCompte(compte);

				System.out.println("SUCCESS-GET-MEDECIN-BY-ID");
			} else{
				System.out.println("ERROR-GET-MEDECIN-BY-ID");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return medecin;
	}

	@Override
	public Beneficier getBeneficierById(int beneficierId) {
		Beneficier beneficier = null;
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte, C.username, C.role, C.status, U.nom_user, U.prenom_user, U.email_user, U.telephone_user, U.sexe_user, U.adresse_user, U.date_naissance_user, U.photo_user, B.carte_handicap, B.date_expiration FROM compte C INNER JOIN USER U ON C.id_compte = U.id_user INNER JOIN beneficier B ON C.id_compte = B.id_user WHERE B.id_user = ?;");
			ps.setInt(1,beneficierId);
			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhone(resultSet.getString("phone"));

				beneficier = new Beneficier();
				beneficier.setUserId(resultSet.getInt("id_compte"));
				beneficier.setNom(resultSet.getString("nom"));
				beneficier.setPrenom(resultSet.getString("prenom"));
				beneficier.setAdresse(resultSet.getString("adresse"));
				beneficier.setSexe(resultSet.getString("sexe"));
				beneficier.setDateNaissance(resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance").toLocalDate() : null);


				beneficier.setCarteHandicapNumber(resultSet.getString("carte_handicap"));
				beneficier.setDateExpiration(resultSet.getDate("date_expiration") != null ? resultSet.getDate("date_expiration").toLocalDate() : null);

				beneficier.setCompte(compte);
				System.out.println("SUCCESS-GET-BENEFICIER-BY-ID");
			} else {
				System.out.println("ERROR-GET-BENEFICIER-BY-ID");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return beneficier;
	}

	@Override
	public Benevole getBenevoleById(int benevoleId) {
		Benevole benevole = null;
		try {

			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte, C.username, C.role, C.status, U.nom_user, U.prenom_user, U.email_user, U.telephone_user, U.sexe_user, U.adresse_user, U.date_naissance_user, U.photo_user, B.profession FROM compte C INNER JOIN USER U ON C.id_compte = U.id_user INNER JOIN benevole B ON C.id_compte = B.id_user WHERE B.id_user = ?;");
			ps.setInt(1,benevoleId);
			ResultSet resultSet = ps.executeQuery();

			if(resultSet.next()) {
				Compte compte = new Compte();
				compte.setCompteId(resultSet.getInt("id_compte"));
				compte.setUsername(resultSet.getString("username"));
				compte.setRole(resultSet.getString("role"));
				compte.setStatus(resultSet.getString("status"));
				compte.setEmail(resultSet.getString("email"));
				compte.setPhoto(resultSet.getString("photo"));
				compte.setPhone(resultSet.getString("phone"));

				benevole = new Benevole();
				benevole.setUserId(resultSet.getInt("id_compte"));
				benevole.setNom(resultSet.getString("nom"));
				benevole.setPrenom(resultSet.getString("prenom"));
				benevole.setAdresse(resultSet.getString("adresse"));
				benevole.setSexe(resultSet.getString("sexe"));
				benevole.setDateNaissance(resultSet.getDate("date_naissance") != null ? resultSet.getDate("date_naissance").toLocalDate() : null);


				benevole.setProfession(resultSet.getString("profession"));

				benevole.setCompte(compte);
				System.out.println("SUCCESS-GET-BENEVOLE-BY-ID");
			} else {
				System.out.println("ERROR-GET-BENEVOLE-BY-ID");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return benevole;
	}

	@Override
	public Compte editCompte(int compteId, Compte updatedCompte) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `compte` SET `login` = ?, `role`=?, `status`=? WHERE `id_compte` = ?;");
			ps.setString(1,updatedCompte.getUsername());
//            ps.setString(2, DigestUtils.sha256Hex(updatedCompte.getPassword()));
			ps.setString(2,updatedCompte.getRole());
			ps.setString(3,updatedCompte.getStatus());
			ps.setInt(4,compteId);

			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-EDIT-COMPTE");
			} else {
				System.out.println("ERROR-EDIT-COMPTE");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return updatedCompte;
	}

	@Override
	public Organization editOrganization(int organizationId, Organization updatedOrganization) {
		try {

			Compte updatedCompte = this.editCompte(organizationId,updatedOrganization.getCompte());

			PreparedStatement ps = connection.prepareStatement("UPDATE organisation SET `matricule_fiscale` = ?, `nom` = ?, `forme_juridique` = ?, `adresse` = ? WHERE id_compte = ?;");

			ps.setString(1,updatedOrganization.getMatriculeFiscale());
			ps.setString(2,updatedOrganization.getNom());
			ps.setString(3,updatedOrganization.getFormJuridique());
			ps.setString(4,updatedOrganization.getAdresse());
			ps.setInt(5,organizationId);

			int n = ps.executeUpdate();

			if(n == 1) {
				System.out.println("SUCCESS-EDIT-ORGANIZATION");
			} else {
				System.out.println("ERROR-EDIT-ORGANIZATION");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return updatedOrganization;
	}

	@Override
	public Map<String, Integer> getUserNumbersByRole() {
		Map<String,Integer> result = new HashMap<>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT `role` AS 'ROLE',Count(`id_compte`) AS 'NB_USERS' FROM `compte` GROUP BY `role`;");
			ResultSet resultSet = ps.executeQuery();

			while (resultSet.next()) {
				result.put(resultSet.getString("ROLE"),resultSet.getInt("NB_USERS"));
			}

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return result;
	}
	@Override
	public boolean changePassword(int compteId, String newPassword) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `compte` SET `password` = ? WHERE `id_compte` = ?");
			ps.setString(1,DigestUtils.sha256Hex(newPassword));
			ps.setInt(2,compteId);
			ps.executeUpdate();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public String editAccountPhoto(int userId, String photo) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `compte` SET `photo` = ? WHERE `id_compte` = ?");
			ps.setString(1,photo);
			ps.setInt(2,userId);
			int resultat = ps.executeUpdate();

			if(resultat == 1) {
				return photo;
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return null;
	}


	@Override
	public int getUserIdByEmail(String email) {
		try {
			// "SELECT C.id_compte AS 'ID' FROM compte C LEFT JOIN user U ON U.id_user = C.id_compte LEFT JOIN organisation O ON O.id_compte = C.id_compte WHERE (U.email = ?) OR (O.email = ?);"
			PreparedStatement ps = connection.prepareStatement("SELECT C.id_compte AS 'ID' FROM compte AS C WHERE C.email = ?");
			ps.setString(1,email);
			ResultSet resultat = ps.executeQuery();
			if(resultat.next()) {
				return resultat.getInt("ID");
			}

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return -1;
	}

	@Override
	public boolean forgotPassword(int compteId, String passwordGenerated) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `compte` C SET C.temp_reset_password = ? WHERE C.id_compte = ?");
			ps.setString(1,DigestUtils.sha256Hex(passwordGenerated));
			ps.setInt(2,compteId);
			int resultat = ps.executeUpdate();

			if(resultat == 1) {
				return true;
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return false;
	}

	@Override
	public boolean changeAccountStatus(int compteId,AccountStatus status) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE `compte` C SET C.status = ? WHERE C.id_compte = ?");
			ps.setString(1,status.toString());
			ps.setInt(2,compteId);
			int resultat = ps.executeUpdate();

			if(resultat == 1) {
				return true;
			}
		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}
		return false;
	}

	@Override
	public boolean existsBeneficierByCarteHandicapNumber(String carteHandicapNumber) {
		int nb = 0;

		try {

			PreparedStatement ps = connection.prepareStatement("SELECT count(B.carte_handicap) AS NB_BENEFICIERS FROM `beneficier` AS B WHERE `carte_handicap`=?;");
			ps.setString(1,carteHandicapNumber);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_BENEFICIERS");
				System.out.println("NB_BENEFICIERS : "+ nb);

				System.out.println("SUCCESS-GET-BENEFICIER-BY-CARTE");

			} else {
				System.out.println("ERROR-GET-BENEFICIER-BY-CARTE");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0;
	}

	@Override
	public boolean existsOrganizationByMatricule(String matricule) {
		int nb = 0;

		try {

			PreparedStatement ps = connection.prepareStatement("SELECT count(O.matricule_fiscale) AS NB_ORGANIZATIONS FROM `organisation` AS O WHERE `matricule_fiscale`=?;");
			ps.setString(1,matricule);

			ResultSet resultSet = ps.executeQuery();

			if (resultSet.next()) {
				nb = resultSet.getInt("NB_ORGANIZATIONS");
				System.out.println("NB_ORGANIZATIONS : "+ nb);

				System.out.println("SUCCESS-GET-ORGANIZATION-BY-MATRICULE");

			} else {
				System.out.println("ERROR-GET-ORGANIZATION-BY-MATRICULE");
			}

			ps.close();

		} catch (SQLException exception) {
			System.out.println(exception.getMessage());
		}

		return nb != 0;
	}
}