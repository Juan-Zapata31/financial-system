package app.infrastructure;

import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.domain.models.EnterpriseClient;
import app.domain.models.NaturalClient;
import app.domain.models.User;
import app.domain.models.enums.Roles;
import app.domain.models.enums.UserState;
import app.domain.services.CreateClient;
import app.domain.services.CreateUser;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final CreateUser createUser;
    private final CreateClient createClient;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
            CreateUser createUser,
            CreateClient createClient,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.createUser = createUser;
        this.createClient = createClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // Solo omite el seeder si ya existe el usuario analyst01 (usuario clave del
        // sistema)
        if (userRepository.existsByUsername("analyst01")) {
            return;
        }

        List<User> users = seedUsers();
        seedClients(users);
    }

    // -------------------------------------------------------------------------
    // Usuarios del sistema (empleados del banco + clientes)
    // -------------------------------------------------------------------------

    private List<User> seedUsers() {
        String pass = passwordEncoder.encode("admin123");

        // Cajero / Teller
        User teller = buildUser("teller01", pass, Roles.Teller, null);

        // Analista Interno
        User analyst = buildUser("analyst01", pass, Roles.InternalAnalyst, null);

        // Asesor Comercial
        User commercial = buildUser("commercial01", pass, Roles.CommercialEmployee, null);

        // Empleado de empresa (vinculado a NIT de empresa ejemplo)
        User companyEmployee = buildUser("companyemp01", pass, Roles.CompanyEmployee, "900111222-1");

        // Supervisor de empresa
        User companySupervisor = buildUser("companysup01", pass, Roles.CompanySupervisor, "900111222-1");

        // Usuario cliente empresa (representante / admin empresa)
        User companyClient = buildUser("companyclient01", pass, Roles.CompanyClient, "900111222-1");

        // Usuarios clientes individuales (se vinculan a clientes naturales abajo)
        User individualClient1 = buildUser("client01", pass, Roles.IndividualClient, null);
        User individualClient2 = buildUser("client02", pass, Roles.IndividualClient, null);
        User individualClient3 = buildUser("client03", pass, Roles.IndividualClient, null);

        // Empleados adicionales de la empresa (misma empresa 900111222-1)
        User companyEmployee2 = buildUser("companyemp02", pass, Roles.CompanyEmployee, "900111222-1");
        User companyEmployee3 = buildUser("companyemp03", pass, Roles.CompanyEmployee, "900111222-1");

        List<User> all = List.of(
                teller, analyst, commercial,
                companyEmployee, companySupervisor, companyClient,
                companyEmployee2, companyEmployee3,
                individualClient1, individualClient2, individualClient3);

        System.out.println("=== DataSeeder: Usuarios ===");
        for (User u : all) {
            try {
                createUser.createUser(u);
                System.out.printf("  [OK]   %-20s -> role: %s%n", u.getUsername(), u.getRoles());
            } catch (Exception e) {
                System.out.printf("  [SKIP] %-20s -> ya existe (%s)%n", u.getUsername(), e.getMessage());
            }
        }
        System.out.println("  Contraseña de todos: admin123");
        System.out.println("====================================");

        return all;
    }

    private User buildUser(String username, String password, Roles role, String companyNit) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setRoles(role);
        u.setUserState(UserState.ACTIVE);
        u.setCompanyNit(companyNit);
        return u;
    }

    // -------------------------------------------------------------------------
    // Clientes (naturales + empresa)
    // -------------------------------------------------------------------------

    private void seedClients(List<User> users) {
        // Los 3 últimos usuarios son clientes individuales
        User userClient1 = users.get(8); // client01
        User userClient2 = users.get(9); // client02
        User userClient3 = users.get(10); // client03

        // El usuario companyClient es el admin de la empresa
        User userCompanyClient = users.get(5); // companyclient01

        // Empleados adicionales de la empresa
        User userCompanyEmployee2 = users.get(6); // companyemp02
        User userCompanyEmployee3 = users.get(7); // companyemp03

        // Long analystId de quien hace el registro (commercial)
        Long commercialId = users.get(2).getUserId();
        String commercialUsername = users.get(2).getUsername();

        // ── Clientes naturales ────────────────────────────────────────────────

        NaturalClient n1 = buildNaturalClient(
                "client01", "Carlos Gómez", "1100000001",
                "cgomez@mail.com", "3101111001", "Carrera 10 # 20-30",
                LocalDate.of(1990, 5, 15), userClient1);

        NaturalClient n2 = buildNaturalClient(
                "client02", "María Rodríguez", "1100000002",
                "mrodriguez@mail.com", "3101111002", "Calle 45 # 12-10",
                LocalDate.of(1985, 8, 22), userClient2);

        NaturalClient n3 = buildNaturalClient(
                "client03", "Luis Martínez", "1100000003",
                "lmartinez@mail.com", "3101111003", "Avenida 30 # 5-60",
                LocalDate.of(1978, 11, 3), userClient3);

        // ── Clientes adicionales de la empresa (empleados operativos) ───────────

        NaturalClient empClient2 = buildNaturalClient(
                "companyemp02", "Andrés Vargas", "1100000004",
                "avargas@tecnologiaxyz.com", "3101111004", "Calle 72 # 15-30",
                LocalDate.of(1992, 3, 18), userCompanyEmployee2);

        NaturalClient empClient3 = buildNaturalClient(
                "companyemp03", "Valentina Ospina", "1100000005",
                "vospina@tecnologiaxyz.com", "3101111005", "Carrera 15 # 88-20",
                LocalDate.of(1995, 9, 7), userCompanyEmployee3);

        // ── Cliente empresa ───────────────────────────────────────────────────

        EnterpriseClient empresa = buildEnterpriseClient(
                "client_empresa01", "Tecnología XYZ S.A.S.", "900111222-1",
                "contacto@tecnologiaxyz.com", "6012345678", "Calle 80 # 10-20 Bogotá",
                "1100000001", // representante legal = documento de Carlos Gómez
                "Tecnología XYZ S.A.S.", userCompanyClient);

        // Guardar con skip de auditoría usando Long=null (seeder crea como sistema)
        System.out.println("=== DataSeeder: Clientes ===");

        seedNaturalClient(n1, commercialId, commercialUsername);
        seedNaturalClient(n2, commercialId, commercialUsername);
        seedNaturalClient(n3, commercialId, commercialUsername);
        seedNaturalClient(empClient2, commercialId, commercialUsername);
        seedNaturalClient(empClient3, commercialId, commercialUsername);

        try {
            createClient.createEnterpriseClient(empresa, commercialId, commercialUsername);
            System.out.println("  [OK]   Tecnología XYZ S.A.S. (NIT: 900111222-1)");
        } catch (Exception e) {
            System.out.printf("  [SKIP] Tecnología XYZ S.A.S. -> ya existe (%s)%n", e.getMessage());
        }

        System.out.println("====================================");
    }

    private void seedNaturalClient(NaturalClient client, Long userId, String username) {
        try {
            createClient.createNaturalClient(client, userId, username);
            System.out.printf("  [OK]   %s (doc: %s)%n", client.getFullName(), client.getDocumentNumber());
        } catch (Exception e) {
            System.out.printf("  [SKIP] %s -> ya existe (%s)%n", client.getFullName(), e.getMessage());
        }
    }

    private NaturalClient buildNaturalClient(String username, String fullName,
            String documentNumber, String email,
            String phone, String address,
            LocalDate birthDate, User linkedUser) {
        NaturalClient c = new NaturalClient();
        c.setFullName(fullName);
        c.setDocumentNumber(documentNumber);
        c.setEmail(email);
        c.setPhone(phone);
        c.setAddress(address);
        c.setBirthDate(birthDate);
        c.setUser(linkedUser);
        return c;
    }

    private EnterpriseClient buildEnterpriseClient(String username, String fullName,
            String nit, String email,
            String phone, String address,
            String legalRepresentative,
            String companyName, User linkedUser) {
        EnterpriseClient c = new EnterpriseClient();
        c.setFullName(fullName);
        c.setDocumentNumber(nit);
        c.setNit(nit);
        c.setEmail(email);
        c.setPhone(phone);
        c.setAddress(address);
        c.setLegalRepresentative(legalRepresentative);
        c.setCompanyName(companyName);
        c.setUser(linkedUser);
        return c;
    }
}

