package utils;

import academic.*;
import core.UniversityKernel;
import infrastructure.NewsEntry;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import users.*;
import research.*;
import exceptions.*;

public class DatabaseSeeder {
    public static void seed() {
        // 1. Administrators
        populateAdministrators();

        // 2. Managers and Academic Officers
        populateManagers();

        // 3. School of Information Technology and Engineering (SITE)
        populateSITE();

        // 4. Business School (BS)
        populateBS();

        // 5. International School of Economics (ISE)
        populateISE();

        // 6. Faculty of Energy and Oil & Gas (FEOG)
        populateFEOG();

        // 7. Global Research Projects
        populateResearchProjects();

        // 8. General News Announcements
        populateNews();
    }

    private static void addStandardLessons(Course course) {
        course.addLesson(new Lesson(LessonType.LECTURE, course.getName() + " (Lection)"));
        course.addLesson(new Lesson(LessonType.PRACTICE, course.getName() + " (Practice)"));
    }

    private static void populateAdministrators() {
        Admin rootAdmin = new Admin(
                "ADM-001",
                "admin",
                "admin",
                "System",
                "Administrator",
                "admin@kbtu.kz"
        );
        UniversityKernel.getInstance().getUsers().add(rootAdmin);

        Admin assistantAdmin = new Admin(
                "ADM-002",
                "assistant_admin",
                "securepass1",
                "Kairat",
                "Nurtas",
                "k.nurtas@kbtu.kz"
        );
        UniversityKernel.getInstance().getUsers().add(assistantAdmin);
    }

    private static void populateManagers() {
        Manager chiefRegistrar = UserFactory.createManager(
                "MNG-001",
                "manager",
                "123",
                "Assel",
                "Berikova",
                "a_berikova@kbtu.kz",
                650000,
                "OR",
                ManagerType.OR
        );
        UniversityKernel.getInstance().getUsers().add(chiefRegistrar);

        Manager siteManager = UserFactory.createManager(
                "MNG-002",
                "site_manager",
                "site123",
                "Aigerim",
                "Suleimenova",
                "a.suleimenova@kbtu.kz",
                550000,
                "SITE",
                ManagerType.DEPARTMENT
        );
        UniversityKernel.getInstance().getUsers().add(siteManager);

        Manager bsManager = UserFactory.createManager(
                "MNG-003",
                "bs_manager",
                "bs123",
                "Nurlan",
                "Amangeldiyev",
                "n.amangeldiyev@kbtu.kz",
                540000,
                "BS",
                ManagerType.DEPARTMENT
        );
        UniversityKernel.getInstance().getUsers().add(bsManager);

        Manager iseManager = UserFactory.createManager(
                "MNG-004",
                "ise_manager",
                "ise123",
                "Damir",
                "Zhumabayev",
                "d.zhumabayev@kbtu.kz",
                540000,
                "ISE",
                ManagerType.DEPARTMENT
        );
        UniversityKernel.getInstance().getUsers().add(iseManager);
    }

    private static void populateSITE() {
        System.out.println("Seeding School of Information Technology and Engineering (SITE)...");

        Teacher profShamoi = UserFactory.createTeacher(
                "TCH-101",
                "p_shamoi",
                "shamoiSITE",
                "Pakita",
                "Shamoi",
                "p.shamoi@kbtu.kz",
                800000,
                "SITE",
                TeacherTitle.PROFESSOR
        );
        UniversityKernel.getInstance().getUsers().add(profShamoi);

        Teacher profKrasnoslobodtsev = UserFactory.createTeacher(
                "TCH-102",
                "w_krasnoslobodtsev",
                "wladSITE",
                "Wladimir",
                "Krasnoslobodtsev",
                "w.krasnoslobodtsev@kbtu.kz",
                750000,
                "SITE",
                TeacherTitle.PROFESSOR
        );
        UniversityKernel.getInstance().getUsers().add(profKrasnoslobodtsev);

        Teacher profUmarov = UserFactory.createTeacher(
                "TCH-103",
                "t_umarov",
                "timurSITE",
                "Timur",
                "Umarov",
                "t.umarov@kbtu.kz",
                780000,
                "SITE",
                TeacherTitle.PROFESSOR
        );
        UniversityKernel.getInstance().getUsers().add(profUmarov);

        Teacher tutorKuralbayev = UserFactory.createTeacher(
                "TCH-104",
                "d_kuralbayev",
                "danialSITE",
                "Danial",
                "Kuralbayev",
                "d.kuralbayev@kbtu.kz",
                350000,
                "SITE",
                TeacherTitle.TUTOR
        );
        UniversityKernel.getInstance().getUsers().add(tutorKuralbayev);

        Teacher seniorLecturerAmanbek = UserFactory.createTeacher(
                "TCH-105",
                "y_amanbek",
                "yerlanSITE",
                "Yerlan",
                "Amanbek",
                "y.amanbek@kbtu.kz",
                450000,
                "SITE",
                TeacherTitle.SENIOR_LECTURER
        );
        UniversityKernel.getInstance().getUsers().add(seniorLecturerAmanbek);

        seniorLecturerAmanbek.setResearchComponent(new ResearchDecorator(seniorLecturerAmanbek));

        // Courses
        Course pp1 = new Course("CSCI1103", "Programming Principles 1", 4, CourseStatus.MAJOR, 1);
        addStandardLessons(pp1);
        pp1.addInstructor(profShamoi);
        pp1.addInstructor(tutorKuralbayev);
        pp1.setSyllabus("Basics of structural programming in C++, variables, branches, iterations, basic arrays, functions.");
        UniversityKernel.getInstance().getCourses().add(pp1);

        Course calc1 = new Course("MAT1245", "Calculus 1", 3, CourseStatus.MAJOR, 1);
        addStandardLessons(calc1);
        calc1.setSyllabus("Limits, continuity, differentiation, application of derivatives, and introduction to integration.");
        UniversityKernel.getInstance().getCourses().add(calc1);

        Course linalg = new Course("MATH1203", "Linear Algebra for Engineers", 3, CourseStatus.MAJOR, 1);
        addStandardLessons(linalg);
        linalg.setSyllabus("Vector spaces, linear maps, matrices, systems of equations, eigenvalues, and eigenvectors.");
        UniversityKernel.getInstance().getCourses().add(linalg);

        Course ict = new Course("INFT1101", "Information and Communication Technologies", 3, CourseStatus.MAJOR, 1);
        addStandardLessons(ict);
        ict.setSyllabus("Modern computing platforms, internet architecture, spreadsheets, databases, security, and cloud systems.");
        UniversityKernel.getInstance().getCourses().add(ict);

        Course pp2 = new Course("CSCI1204", "Programming Principles 2", 4, CourseStatus.MAJOR, 1);
        addStandardLessons(pp2);
        pp2.addInstructor(tutorKuralbayev);
        pp2.setSyllabus("Pointers, manual memory allocation, complex data types, recursive procedures, and structures.");
        UniversityKernel.getInstance().getCourses().add(pp2);

        Course discrete = new Course("CSCI1102", "Discrete Structures", 3, CourseStatus.MAJOR, 1);
        addStandardLessons(discrete);
        discrete.addInstructor(profKrasnoslobodtsev);
        discrete.setSyllabus("Propositional logic, sets, relations, functions, graph theory, combinatorics, and induction.");
        UniversityKernel.getInstance().getCourses().add(discrete);

        Course databases = new Course("CSCI2104", "Databases", 3, CourseStatus.MAJOR, 2);
        addStandardLessons(databases);
        databases.addInstructor(seniorLecturerAmanbek);
        databases.setSyllabus("Database schemas, ER modeling, relational algebra, SQL DDL/DML, normalization up to BCNF.");
        UniversityKernel.getInstance().getCourses().add(databases);

        Course ads = new Course("CSCI2105", "Algorithms and Data Structures", 3, CourseStatus.MAJOR, 2);
        addStandardLessons(ads);
        ads.addInstructor(profKrasnoslobodtsev);
        ads.addInstructor(tutorKuralbayev);
        ads.setSyllabus("Analysis of algorithms, recursion, lists, trees, graphs, sorting and searching techniques.");
        UniversityKernel.getInstance().getCourses().add(ads);

        Course compArch = new Course("CSCI3115", "Computer Architecture", 3, CourseStatus.MAJOR, 2);
        addStandardLessons(compArch);
        compArch.addInstructor(profKrasnoslobodtsev);
        compArch.setSyllabus("Processor design, MIPS assembly language, pipelining, memory hierarchy, cache optimization.");
        UniversityKernel.getInstance().getCourses().add(compArch);

        Course webDev = new Course("INFT2205", "Web Development", 4, CourseStatus.MAJOR, 2);
        addStandardLessons(webDev);
        webDev.addInstructor(tutorKuralbayev);
        webDev.setSyllabus("Frontend development, DOM manipulation, asynchronous javascript, backend frameworks, and RESTful APIs.");
        UniversityKernel.getInstance().getCourses().add(webDev);

        Course networks = new Course("INFT2102", "IT Infrastructure and Computer Networks", 4, CourseStatus.MAJOR, 2);
        addStandardLessons(networks);
        networks.addInstructor(profUmarov);
        networks.setSyllabus("Network layers, routing protocols, subnets, transport layer flow control, IP addressing, DNS.");
        UniversityKernel.getInstance().getCourses().add(networks);

        Course oop = new Course("CSCI2106", "Object-Oriented Programming and Design", 3, CourseStatus.MAJOR, 2);
        addStandardLessons(oop);
        oop.addInstructor(profShamoi);
        oop.setSyllabus("Introduction to Java, inheritance, polymorphism, encapsulation, SOLID principles, design patterns.");
        UniversityKernel.getInstance().getCourses().add(oop);

        Course softEng = new Course("CSCI2208", "Software Engineering", 3, CourseStatus.MAJOR, 3);
        addStandardLessons(softEng);
        softEng.addInstructor(seniorLecturerAmanbek);
        softEng.setSyllabus("Agile methodologies, software architectures, design patterns, testing (JUnit), CI/CD pipelines.");
        UniversityKernel.getInstance().getCourses().add(softEng);

        Course cyberSec = new Course("INFT3105", "Cyber Security Fundamentals", 3, CourseStatus.MAJOR, 3);
        addStandardLessons(cyberSec);
        cyberSec.addInstructor(profUmarov);
        cyberSec.setSyllabus("Symmetric/asymmetric encryption, hash functions, network security, firewalls, threat models.");
        UniversityKernel.getInstance().getCourses().add(cyberSec);

        seedIEEEPapersForSITE(profShamoi, profKrasnoslobodtsev, profUmarov);

        // Simple Guy
        Student testStudent = UserFactory.createStudent(
                "24B031692", 
                "student", 
                "student", 
                "Paren", 
                "Prostoi", 
                "pivo@kbtu.kz", 
                DegreeType.BACHELOR, 
                2
        );
        UniversityKernel.getInstance().getUsers().add(testStudent);

        try {
            testStudent.registerForCourse(pp1);       testStudent.addCourseSchedule(pp1);
            testStudent.registerForCourse(calc1);     testStudent.addCourseSchedule(calc1);
            testStudent.registerForCourse(linalg);    testStudent.addCourseSchedule(linalg);
            testStudent.registerForCourse(ict);       testStudent.addCourseSchedule(ict);
            testStudent.registerForCourse(pp2);       testStudent.addCourseSchedule(pp2);
            testStudent.registerForCourse(discrete);  testStudent.addCourseSchedule(discrete);
            testStudent.registerForCourse(databases); testStudent.addCourseSchedule(databases);
            testStudent.registerForCourse(ads);       testStudent.addCourseSchedule(ads);
            testStudent.registerForCourse(oop);       testStudent.addCourseSchedule(oop);
            System.out.println("Paren Prostoi successfully registered in 9 SITE courses (total 29 credits) and schedule generated.");
        } catch (CreditLimitExceededException e) {
            System.err.println("Failed to register courses for Paren Prostoi: " + e.getMessage());
        }

        Student studentYernar = UserFactory.createStudent("21B031601", "y_kairat", "123", "Yernar", "Kairatov", "y.kairatov@kbtu.kz", DegreeType.BACHELOR, 4);
        UniversityKernel.getInstance().getUsers().add(studentYernar);

        Student studentAssel = UserFactory.createStudent("22B031405", "a_sabyr", "123", "Assel", "Sabyrova", "a.sabyrova@kbtu.kz", DegreeType.BACHELOR, 3);
        UniversityKernel.getInstance().getUsers().add(studentAssel);

        Student phdBauyrzhan = UserFactory.createStudent("23D010204", "b_amangeldy", "123", "Bauyrzhan", "Amangeldiyev", "b.amangeldiyev@kbtu.kz", DegreeType.PHD, 2);
        phdBauyrzhan.setResearchComponent(new ResearchDecorator(phdBauyrzhan));
        UniversityKernel.getInstance().getUsers().add(phdBauyrzhan);

        try {
            studentYernar.setResearchSupervisor(profShamoi);
            System.out.println("Assigned supervisor Pakita Shamoi to 4th year student Yernar Kairatov.");
        } catch (IndexTooLowException e) {
            System.err.println("Failed to assign supervisor: " + e.getMessage());
        }
    }

    private static void populateBS() {
        System.out.println("Seeding Business School (BS)...");

        Teacher profKalidoldayeva = UserFactory.createTeacher(
                "TCH-201",
                "a_kalidoldayeva",
                "asemgulBS",
                "Asemgul",
                "Kalidoldayeva",
                "a.kalidoldayeva@kbtu.kz",
                700000,
                "BS",
                TeacherTitle.PROFESSOR
        );
        UniversityKernel.getInstance().getUsers().add(profKalidoldayeva);

        Teacher lecturerSadykov = UserFactory.createTeacher(
                "TCH-202",
                "n_sadykov",
                "nurlanBS",
                "Nurlan",
                "Sadykov",
                "n.sadykov@kbtu.kz",
                420000,
                "BS",
                TeacherTitle.SENIOR_LECTURER
        );
        UniversityKernel.getInstance().getUsers().add(lecturerSadykov);

        Course finance = new Course("FIN1101", "Introduction to Finance", 3, CourseStatus.MAJOR, 1);
        addStandardLessons(finance);
        finance.addInstructor(profKalidoldayeva);
        finance.setSyllabus("Understanding modern finance markets, stock valuation, and principles of financial decision making.");
        UniversityKernel.getInstance().getCourses().add(finance);

        Course strategy = new Course("MGMT4102", "Strategic Management", 4, CourseStatus.MAJOR, 4);
        addStandardLessons(strategy);
        strategy.addInstructor(lecturerSadykov);
        strategy.setSyllabus("SWOT, Porter's Five Forces, formulation and implementation of corporate and business strategy.");
        UniversityKernel.getInstance().getCourses().add(strategy);

        seedIEEEPapersForBS(profKalidoldayeva);

        Student studentDiana = UserFactory.createStudent("21B051203", "d_askarova", "123", "Diana", "Askarova", "d.askarova@kbtu.kz", DegreeType.BACHELOR, 4);
        UniversityKernel.getInstance().getUsers().add(studentDiana);

        try {
            studentDiana.setResearchSupervisor(profKalidoldayeva);
            System.out.println("Assigned supervisor Asemgul Kalidoldayeva to 4th year student Diana Askarova.");
        } catch (IndexTooLowException e) {
            System.err.println("Failed to assign BS supervisor: " + e.getMessage());
        }
    }

    private static void populateISE() {
        System.out.println("Seeding International School of Economics (ISE)...");

        Teacher profIsakov = UserFactory.createTeacher(
                "TCH-301",
                "m_isakov",
                "maratISE",
                "Marat",
                "Isakov",
                "m.isakov@kbtu.kz",
                900000,
                "ISE",
                TeacherTitle.PROFESSOR
        );
        UniversityKernel.getInstance().getUsers().add(profIsakov);

        Teacher lecturerOrazova = UserFactory.createTeacher(
                "TCH-302",
                "s_orazova",
                "sholpanISE",
                "Sholpan",
                "Orazova",
                "s.orazova@kbtu.kz",
                480000,
                "ISE",
                TeacherTitle.SENIOR_LECTURER
        );
        UniversityKernel.getInstance().getUsers().add(lecturerOrazova);

        Course macro = new Course("ECON2101", "Macroeconomics I", 4, CourseStatus.MAJOR, 2);
        addStandardLessons(macro);
        macro.addInstructor(profIsakov);
        macro.setSyllabus("Comprehensive intermediate-level study of economic output, inflation, unemployment, and trade.");
        UniversityKernel.getInstance().getCourses().add(macro);

        Course metrics = new Course("ECON3104", "Econometrics", 4, CourseStatus.MAJOR, 3);
        addStandardLessons(metrics);
        metrics.addInstructor(lecturerOrazova);
        metrics.setSyllabus("Statistical techniques to analyze economic data, hypothesis testing, and multi-collinearity.");
        UniversityKernel.getInstance().getCourses().add(metrics);

        seedIEEEPapersForISE(profIsakov);

        Student studentNursultan = UserFactory.createStudent("21B081109", "n_bolat", "123", "Nursultan", "Bolatov", "n.bolatov@kbtu.kz", DegreeType.BACHELOR, 4);
        UniversityKernel.getInstance().getUsers().add(studentNursultan);

        try {
            studentNursultan.setResearchSupervisor(profIsakov);
            System.out.println("Assigned supervisor Marat Isakov to 4th year student Nursultan Bolatov.");
        } catch (IndexTooLowException e) {
            System.err.println("Failed to assign ISE supervisor: " + e.getMessage());
        }
    }

    private static void populateFEOG() {
        System.out.println("Seeding Faculty of Energy and Oil & Gas (FEOG)...");

        Teacher profKaliyev = UserFactory.createTeacher(
                "TCH-401",
                "k_kaliyev",
                "kanatFEOG",
                "Kanat",
                "Kaliyev",
                "k.kaliyev@kbtu.kz",
                850000,
                "FEOG",
                TeacherTitle.PROFESSOR
        );
        UniversityKernel.getInstance().getUsers().add(profKaliyev);

        Course reservoir = new Course("PETR3101", "Reservoir Engineering", 4, CourseStatus.MAJOR, 3);
        addStandardLessons(reservoir);
        reservoir.addInstructor(profKaliyev);
        reservoir.setSyllabus("Estimation of oil and gas reserves, material balance methods, and multiphase fluid dynamics.");
        UniversityKernel.getInstance().getCourses().add(reservoir);

        seedIEEEPapersForFEOG(profKaliyev);
    }

    private static void populateResearchProjects() {
        System.out.println("Seeding active Research Projects...");

        ResearchProject nlpProject = new ResearchProject("Machine Learning for Local Language NLP");
        ResearchProject blockchainProject = new ResearchProject("Blockchain-based Academic Credentialing");
        ResearchProject smartGridProject = new ResearchProject("Smart Grid Optimization in City Region");
        ResearchProject quantumProject = new ResearchProject("Quantum Cryptography for Secure Communication");

        UniversityKernel.getInstance().getResearchProjects().add(nlpProject);
        UniversityKernel.getInstance().getResearchProjects().add(blockchainProject);
        UniversityKernel.getInstance().getResearchProjects().add(smartGridProject);
        UniversityKernel.getInstance().getResearchProjects().add(quantumProject);

        List<IResearcher> allResearchers = new ArrayList<>();
        for (User u : UniversityKernel.getInstance().getUsers()) {
            if (u instanceof IResearcher) {
                allResearchers.add((IResearcher) u);
            }
        }

        try {
            if (!allResearchers.isEmpty()) {
                nlpProject.addParticipant(allResearchers.get(0));
                smartGridProject.addParticipant(allResearchers.get(0));
            }
            if (allResearchers.size() > 1) {
                nlpProject.addParticipant(allResearchers.get(1));
                blockchainProject.addParticipant(allResearchers.get(1));
            }
            if (allResearchers.size() > 2) {
                quantumProject.addParticipant(allResearchers.get(2));
            }
        } catch (NonResearcherException e) {
            System.err.println("Error adding participants to projects: " + e.getMessage());
        }
    }

    private static void populateNews() {
        System.out.println("Seeding Announcement News board...");

        NewsEntry news1 = new NewsEntry(
                "University signs partnership with MIT for dual degree programs!",
                "We are thrilled to announce a strategic partnership with Massachusetts Institute of Technology. Joint initiatives begin next academic year."
        );
        NewsEntry news2 = new NewsEntry(
                "Research Cabinet is officially live!",
                "Our new modular research cabinets allow Bachelor, Master, PhD students and Faculty members to submit research papers and calculate h-index automatically."
        );
        NewsEntry news3 = new NewsEntry(
                "Annual Hackathon 2026 registration is now open!",
                "Assemble your teams of 3-5 students and showcase your innovative software products. SITE offers 1,000,000 grand prizes."
        );

        UniversityKernel.getInstance().getNews().add(news1);
        UniversityKernel.getInstance().getNews().add(news2);
        UniversityKernel.getInstance().getNews().add(news3);
    }

    private static void seedIEEEPapersForSITE(Teacher p1, Teacher p2, Teacher p3) {
        ResearchPaper paper1 = new ResearchPaper(
                "Federated Learning for Privacy-Preserving Collaborative AI",
                List.of("Pakita Shamoi", "D. Watson", "K. Smith"),
                "IEEE Transactions on Information Forensics and Security",
                19,
                LocalDate.of(2021, 6, 12),
                74,
                "10.1109/TIFS.2021.3061294"
        );
        paper1.setApproved(true);
        p1.getResearchComponent().getPapers().add(paper1);

        ResearchPaper paper2 = new ResearchPaper(
                "Deep Learning for Natural Language Processing: A Review",
                List.of("Pakita Shamoi", "A. Johnson"),
                "IEEE Transactions on Computational Intelligence",
                22,
                LocalDate.of(2021, 10, 5),
                89,
                "10.1109/IEEESTD.2021.9766691"
        );
        paper2.setApproved(true);
        p1.getResearchComponent().getPapers().add(paper2);

        ResearchPaper paper3 = new ResearchPaper(
                "Explainable AI in Healthcare: Trust and Interpretability",
                List.of("Pakita Shamoi", "E. Davis"),
                "IEEE Journal of Biomedical and Health Informatics",
                13,
                LocalDate.of(2023, 3, 20),
                15,
                "10.1109/EMB-M.2023.3298104"
        );
        paper3.setApproved(true);
        p1.getResearchComponent().getPapers().add(paper3);

        ResearchPaper paper4 = new ResearchPaper(
                "Quantum Computing Algorithms for Optimization Problems",
                List.of("Wladimir Krasnoslobodtsev", "M. Dirac"),
                "IEEE Transactions on Information Theory",
                28,
                LocalDate.of(2021, 8, 14),
                55,
                "10.1109/TIT.2021.3092543"
        );
        paper4.setApproved(true);
        p2.getResearchComponent().getPapers().add(paper4);

        ResearchPaper paper5 = new ResearchPaper(
                "A Hybrid Machine Learning Approach for Stock Price Prediction",
                List.of("Wladimir Krasnoslobodtsev", "F. Knight"),
                "IEEE Journal of Selected Topics in Signal Processing",
                12,
                LocalDate.of(2020, 11, 28),
                32,
                "10.1109/JPROC.2020.3015482"
        );
        paper5.setApproved(true);
        p2.getResearchComponent().getPapers().add(paper5);

        ResearchPaper paper6 = new ResearchPaper(
                "Edge Computing in 5G Networks: A Survey of Architectures",
                List.of("Wladimir Krasnoslobodtsev", "T. Bell"),
                "IEEE Wireless Communications",
                14,
                LocalDate.of(2023, 1, 15),
                12,
                "10.1109/MWC.2023.3278142"
        );
        paper6.setApproved(true);
        p2.getResearchComponent().getPapers().add(paper6);

        ResearchPaper paper7 = new ResearchPaper(
                "Blockchain in Smart Grid: A Comprehensive Survey",
                List.of("Timur Umarov", "N. Tesla", "T. Edison"),
                "IEEE Access",
                18,
                LocalDate.of(2022, 5, 2),
                25,
                "10.1109/ACCESS.2022.3168212"
        );
        paper7.setApproved(true);
        p3.getResearchComponent().getPapers().add(paper7);

        ResearchPaper paper8 = new ResearchPaper(
                "IoT Security Architectures: Vulnerabilities and Mitigations",
                List.of("Timur Umarov", "A. Turing"),
                "IEEE Communications Surveys & Tutorials",
                15,
                LocalDate.of(2023, 7, 19),
                43,
                "10.1109/COMST.2023.3245162"
        );
        paper8.setApproved(true);
        p3.getResearchComponent().getPapers().add(paper8);

        ResearchPaper paper9 = new ResearchPaper(
                "Robotic Process Automation in Banking: Benefits and Challenges",
                List.of("Timur Umarov", "H. Ford"),
                "IEEE Transactions on Engineering Management",
                10,
                LocalDate.of(2022, 9, 30),
                8,
                "10.1109/TEM.2022.3195240"
        );
        paper9.setApproved(true);
        p3.getResearchComponent().getPapers().add(paper9);
    }

    private static void seedIEEEPapersForBS(Teacher p) {
        ResearchPaper paper1 = new ResearchPaper(
                "Strategic Leadership and Digital Transformation in Commercial Banking",
                List.of("Asemgul Kalidoldayeva", "P. Drucker"),
                "IEEE Transactions on Engineering Management",
                15,
                LocalDate.of(2022, 4, 11),
                14,
                "10.1109/TEM.2022.3045612"
        );
        paper1.setApproved(true);
        p.getResearchComponent().getPapers().add(paper1);

        ResearchPaper paper2 = new ResearchPaper(
                "A Multi-Criteria Decision Framework for Sustainable Supply Chains",
                List.of("Asemgul Kalidoldayeva", "H. Simon"),
                "IEEE Transactions on Systems, Man, and Cybernetics",
                17,
                LocalDate.of(2023, 8, 22),
                22,
                "10.1109/TSMC.2023.3105423"
        );
        paper2.setApproved(true);
        p.getResearchComponent().getPapers().add(paper2);

        ResearchPaper paper3 = new ResearchPaper(
                "Algorithmic Trading Platforms: Market Efficiency and Regulations",
                List.of("Asemgul Kalidoldayeva", "M. Scholes"),
                "IEEE Transactions on Evolutionary Computation",
                20,
                LocalDate.of(2021, 12, 1),
                31,
                "10.1109/TEVC.2021.3008915"
        );
        paper3.setApproved(true);
        p.getResearchComponent().getPapers().add(paper3);
    }

    private static void seedIEEEPapersForISE(Teacher p) {
        ResearchPaper paper1 = new ResearchPaper(
                "Econometric Analysis of Carbon Pricing Policies on Industrial Growth",
                List.of("Marat Isakov", "W. Nordhaus"),
                "IEEE Transactions on Power Systems",
                24,
                LocalDate.of(2020, 10, 19),
                41,
                "10.1109/TPWRS.2020.2985162"
        );
        paper1.setApproved(true);
        p.getResearchComponent().getPapers().add(paper1);

        ResearchPaper paper2 = new ResearchPaper(
                "Game Theoretic Approaches to Resource Allocation in Cloud Economics",
                List.of("Marat Isakov", "J. Nash"),
                "IEEE Transactions on Services Computing",
                18,
                LocalDate.of(2022, 6, 30),
                19,
                "10.1109/TSC.2022.3175810"
        );
        paper2.setApproved(true);
        p.getResearchComponent().getPapers().add(paper2);

        ResearchPaper paper3 = new ResearchPaper(
                "Predictive Econometric Models for Energy Markets Using Neural Networks",
                List.of("Marat Isakov", "Y. Bengio"),
                "IEEE Transactions on Neural Networks and Learning Systems",
                21,
                LocalDate.of(2023, 11, 4),
                53,
                "10.1109/TNNLS.2023.3218561"
        );
        paper3.setApproved(true);
        p.getResearchComponent().getPapers().add(paper3);
    }

    private static void seedIEEEPapersForFEOG(Teacher p) {
        ResearchPaper paper1 = new ResearchPaper(
                "Machine Learning Applications in Hydrocarbon Reservoir Simulations",
                List.of("Kanat Kaliyev", "M. King Hubbert"),
                "IEEE Geoscience and Remote Sensing Letters",
                12,
                LocalDate.of(2022, 2, 17),
                9,
                "10.1109/LGRS.2022.3114512"
        );
        paper1.setApproved(true);
        p.getResearchComponent().getPapers().add(paper1);

        ResearchPaper paper2 = new ResearchPaper(
                "Drilling Parameter Optimization Using Deep Reinforcement Learning",
                List.of("Kanat Kaliyev", "R. Sutton"),
                "IEEE Transactions on Control Systems Technology",
                16,
                LocalDate.of(2023, 9, 8),
                15,
                "10.1109/TCST.2023.3289162"
        );
        paper2.setApproved(true);
        p.getResearchComponent().getPapers().add(paper2);
    }
}
